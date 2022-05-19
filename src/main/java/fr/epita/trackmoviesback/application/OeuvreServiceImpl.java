package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.*;
import fr.epita.trackmoviesback.dto.*;
import fr.epita.trackmoviesback.enumerate.EnumOperationDeRecherche;
import fr.epita.trackmoviesback.enumerate.EnumProprieteRecherchableSurOeuvre;
import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;
import fr.epita.trackmoviesback.exception.MauvaisParamException;
import fr.epita.trackmoviesback.infrastructure.oeuvre.FilmRepository;
import fr.epita.trackmoviesback.infrastructure.oeuvre.OeuvreRepository;
import fr.epita.trackmoviesback.infrastructure.oeuvre.SerieRepository;
import fr.epita.trackmoviesback.infrastructure.specs.CritereDeRecherche;
import fr.epita.trackmoviesback.infrastructure.specs.OeuvreSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OeuvreServiceImpl implements OeuvreService {
    private static final Logger logger = LoggerFactory.getLogger(OeuvreServiceImpl.class);


    @Autowired
    OeuvreRepository oeuvreRepository;
    @Autowired
    FilmRepository filmRepository;
    @Autowired
    SerieRepository serieRepository;
    @Autowired
    GenreService genreService;
    @Autowired
    StatutVisionnageService statutVisionnageService;
    @Autowired
    SaisonService saisonService;

    @Override
    public OeuvreLightListDto getAllOeuvres() {
        List<Oeuvre> oeuvres = oeuvreRepository.findAll();
        List<OeuvreLightDto> oeuvresLightDto = oeuvres.stream().map(this::convertirOeuvreEnLightDto).collect(Collectors.toList());
        return new OeuvreLightListDto(1, 1, oeuvresLightDto);
    }

    @Override
    public Long saveOeuvre(OeuvreDto oeuvreDto) {
        Oeuvre oeuvreASauver=convertirOeuvreDtoEnOeuvre(oeuvreDto);

        //si ce n'est pas une nouvelle oeuvre
        if (oeuvreASauver.getId()!=null) {
            //on vérifie que son type n'a pas changé
            EnumTypeOeuvre typeOeuvreEnBdd= oeuvreRepository.getTypeOeuvre(oeuvreASauver.getId());
            if ((oeuvreASauver instanceof Film && typeOeuvreEnBdd!=EnumTypeOeuvre.FILM) ||
            (oeuvreASauver instanceof Serie && typeOeuvreEnBdd!=EnumTypeOeuvre.SERIE))
                throw new MauvaisParamException("L'oeuvre avec l'id "+oeuvreASauver.getId()+" existe deja en base. Vous ne pouvez pas modifier son type. Type actuellement en base= "+ typeOeuvreEnBdd);
        }

        //on crée/modifie l'oeuvre
        Oeuvre oeuvreCree = oeuvreRepository.save(oeuvreASauver);
        logger.info("Oeuvre {} ajoutée a la librairie avec l'id : {}",oeuvreCree.getTitre(),oeuvreCree.getId());
        return oeuvreCree.getId();
    }

    @Override
    public void deleteOeuvre(Long id) {
        logger.info("Suppression de l'oeuvre avec id = {} ",id);
        oeuvreRepository.deleteById(id);
    }

    @Override
    public OeuvreLightDto convertirOeuvreEnLightDto(Oeuvre oeuvre) {
        List<GenreDto> genresDto = genreService.convertirListGenreEnDto(oeuvre.getGenres());
        StatutVisionnageDto statutVisionnageDto = statutVisionnageService.convertirStatutVisionnageEnDto(oeuvre.getStatutVisionnage());

        String typeOeuvre = null;
        if (oeuvre instanceof Film) {
            typeOeuvre = EnumTypeOeuvre.FILM.getLibelle();
        } else if (oeuvre instanceof Serie) {
            typeOeuvre = EnumTypeOeuvre.SERIE.getLibelle();
        }
        return new OeuvreLightDto(oeuvre.getId(), typeOeuvre, oeuvre.getTitre(), genresDto, statutVisionnageDto, oeuvre.getNote(), oeuvre.getCreateurs(), oeuvre.getActeurs(), oeuvre.getUrlAffiche(), oeuvre.getUrlBandeAnnonce());
    }

    @Override
    public OeuvreDto getOeuvreCompleteById(Long id) {
        if (id != null && id > 0) {
            EnumTypeOeuvre oeuvre = oeuvreRepository.getTypeOeuvre(id);
            if (oeuvre == EnumTypeOeuvre.FILM) {
                Oeuvre oeuvreFilm = filmRepository.findById(id).get();
                return convertirOeuvreEnDto(oeuvreFilm);
            } else {
                Oeuvre oeuvreSerie = serieRepository.getSerieComplete(id);
                return convertirOeuvreEnDto(oeuvreSerie);
            }
        } else {
            return null;
        }
    }

    @Override
    public OeuvreDto convertirOeuvreEnDto (Oeuvre oeuvre){
        if (oeuvre == null) return null;
        List<GenreDto> genresDto = genreService.convertirListGenreEnDto(oeuvre.getGenres());
        StatutVisionnageDto statutVisionnageDto = statutVisionnageService.convertirStatutVisionnageEnDto(oeuvre.getStatutVisionnage());
        String typeOeuvre = null;
        Integer duree = null;
        List<SaisonDto> saisonDtoList = null;
        if (oeuvre instanceof Film) {
            Film film = (Film) oeuvre;
            duree = film.getDuree();
            typeOeuvre = EnumTypeOeuvre.FILM.getLibelle();
        } else {
            Serie serie = (Serie) oeuvre;
            saisonDtoList = saisonService.convertirListSaisonEnDto(serie.getSaisons());
            typeOeuvre = EnumTypeOeuvre.SERIE.getLibelle();
        }

        return new OeuvreDto(oeuvre.getId(), typeOeuvre, oeuvre.getTitre(), genresDto, statutVisionnageDto, oeuvre.getNote(), oeuvre.getCreateurs(), oeuvre.getActeurs(), oeuvre.getUrlAffiche(), oeuvre.getUrlBandeAnnonce(), saisonDtoList, duree);
    }

    @Override
    public OeuvreLightListDto getOeuvres (Map < String, String > criteres){
        logger.info("debut recherche avec criteres {}", criteres);
        //on construit les critères
        OeuvreSpecification criteresDeRecherche = buildOeuvreSpecification(criteres);

        //on récupère la liste
        List<Oeuvre> oeuvres = oeuvreRepository.findAll(criteresDeRecherche);
        logger.info("fin recherche en BDD. Nb oeuvres trouvees=", oeuvres.size());
        List<OeuvreLightDto> oeuvresLightDto = oeuvres.stream().map(this::convertirOeuvreEnLightDto).collect(Collectors.toList());
        return new OeuvreLightListDto(1, 1, oeuvresLightDto);
    }

    /**
     * converti la liste des critères passés à l'api sous forme de cle/valeur en un objet OeuvreSpecification utilisable par findAll
     * La cle de la map est le nom du critere de recherche: type, genre, statut...
     * La valeur dans la map es tla valeur recherchee
     *
     * @param criteres map avec la liste des criteres
     * @return oeuvreSpecification avec la liste des criteres de recherches dedans
     */
    private OeuvreSpecification buildOeuvreSpecification (Map < String, String > criteres){
        OeuvreSpecification oeuvreSpecification = new OeuvreSpecification();

        //pour cahque critere en paramettre
        criteres.keySet().forEach(proprieteRecherchee -> {
            //on recupère la valeur recherchee correspondant dans la map
            String valeurRecherchee = criteres.get(proprieteRecherchee);
            CritereDeRecherche critereDeRecherche = buildCritereDeRecherche(proprieteRecherchee, valeurRecherchee);
            if (critereDeRecherche != null)
                oeuvreSpecification.add(critereDeRecherche);
        });
        return oeuvreSpecification;
    }

    /**
     * converti en critere de recherche pour la BDD, un critere de recherche recu dans la requete http
     *
     * @param proprieteRecherchee clé présente dans la query string ex: type, genre, statut...
     * @param valeurRecherchee    valeur correspondant à la clé dans la query string
     * @return
     *      null si la valeur recherchee est vide (null, "", " ") ou si le critere n'est pas bon. Ce qui vaut à ignorer le critere recue
     *      sinon le CritereDeRecherche formaté pour la BDD
     */
    private CritereDeRecherche buildCritereDeRecherche (String proprieteRecherchee, String valeurRecherchee){
        //on converti la propriete recherche en enum afin de controler que c'est bien une valeur connu
        EnumProprieteRecherchableSurOeuvre enumProprieteRecherchee;
        try {
            enumProprieteRecherchee = EnumProprieteRecherchableSurOeuvre.getEnumFromValeurParametreRequeteHttp(proprieteRecherchee);
        } catch (MauvaisParamException e) {
            logger.debug("critere de recherche non gere. critere recu= {}", proprieteRecherchee);
            return null;
        }

        //si la valeur recherchee est vide ou null, on ne tient pas compte du critere
        if (valeurRecherchee == null || !StringUtils.hasText(valeurRecherchee)) {
            logger.debug("valeur recherchee vide pour critere : {}. Valeur recue= {}", proprieteRecherchee, valeurRecherchee);
            return null;
        }


        //cas particulier du type d'oeuvre qui est un enumere, dans ce cas, la valeurRecherchee (initialement en string) doit etre convertie dans son enumeree correspondant
        Object valeurRechercheeFinale = valeurRecherchee;

        //Warning fab gere type oeuvre
        if (enumProprieteRecherchee == EnumProprieteRecherchableSurOeuvre.TYPE_OEUVRE)
            valeurRechercheeFinale = EnumTypeOeuvre.getEnumFromlabel(valeurRecherchee);

        //on définit l'operateur (= par defaut, "commence par" pour titre)
        EnumOperationDeRecherche operationDeRecherche = EnumOperationDeRecherche.EGAL;
        if (enumProprieteRecherchee == EnumProprieteRecherchableSurOeuvre.TITRE) {
            operationDeRecherche = EnumOperationDeRecherche.COMMENCE_PAR;
        }

        //on retourne le critere de Recheche
        return new CritereDeRecherche(enumProprieteRecherchee, valeurRechercheeFinale, operationDeRecherche);
    }

    @Override
    public Oeuvre convertirOeuvreDtoEnOeuvre(OeuvreDto oeuvreDto) {
        if (oeuvreDto == null) return null;
        List<Genre> genres = genreService.convertirListGenreDtoEnListGenre(oeuvreDto.getGenres());

        StatutVisionnage statutVisionnage = statutVisionnageService.convertirStatutVisionnageDtoEnStatutVisionnage(oeuvreDto.getStatutVisionnage());

        if (oeuvreDto.getTypeOeuvre().equals(EnumTypeOeuvre.FILM.getLibelle())) {
            return new Film(oeuvreDto.getId(), oeuvreDto.getTitre(), genres,statutVisionnage
                    , oeuvreDto.getNote(), oeuvreDto.getCreateurs(), oeuvreDto.getActeurs()
                    ,oeuvreDto.getUrlAffiche(), oeuvreDto.getUrlBandeAnnonce(), oeuvreDto.getDuree());
        } else {
            List<Saison> saisonList = saisonService.convertirListSaisonDtoEnListSaison(oeuvreDto.getSaisons());
            return new Serie(oeuvreDto.getId(), oeuvreDto.getTitre(), genres,statutVisionnage
                    ,oeuvreDto.getNote(), oeuvreDto.getCreateurs(), oeuvreDto.getActeurs()
                    ,oeuvreDto.getUrlAffiche(), oeuvreDto.getUrlBandeAnnonce(), saisonList);
        }

    }
}
