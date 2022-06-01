package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.*;
import fr.epita.trackmoviesback.dto.*;
import fr.epita.trackmoviesback.dto.formulaire.OeuvreFormulaireDto;
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
import java.util.Optional;
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

    /**
     * verifie que les genres présents dans l'oeuvre existent en base de données
     * @param oeuvreAControler
     *
     * @throws MauvaisParamException si un genre n'existe pas
     */
    private void checkGenrePresentEnBdd( Oeuvre oeuvreAControler) {
        if (oeuvreAControler==null || oeuvreAControler.getGenres()==null) return;
        GenreListDto genreListDtoFromBDD = genreService.getAllGenres();
        for (Genre genre: oeuvreAControler.getGenres()) {
            boolean trouve=false;
            for (GenreDto genreDansBdd: genreListDtoFromBDD.getGenres()) {
                if (genreDansBdd.getId().equals(genre.getId())) {
                    trouve=true;
                    break;
                }
            }
            if (!trouve) throw new MauvaisParamException("Le genre avec id="+genre.getId()+ " n'existe pas dans la base");
        }
    }


    public boolean isStatutVisionnagePresentDansLaListe(StatutVisionnage statutVisionnageAControler,List<StatutVisionnageDto> statutVisionnageList){
        for (StatutVisionnageDto statutVisionnageDto: statutVisionnageList) {
            if (statutVisionnageDto.getId().equals(statutVisionnageAControler.getId())) return true;
        }
        return false;
    }

    /**
     * verifie que les StatutVisionnage présents dans l'oeuvre existent en base de données
     * @param oeuvreAControler
     *
     * @throws MauvaisParamException si un StatutVisionnage n'existe pas
     */
    private void checkStatutVisionnagePresentEnBdd( Oeuvre oeuvreAControler) {
        if (oeuvreAControler==null || oeuvreAControler.getGenres()==null) return;
        List<StatutVisionnageDto> statutVisionnageListDtoFromBdd = statutVisionnageService.getAllStatutVisionnage().getStatuts();

        if (oeuvreAControler.getStatutVisionnage()!=null && !isStatutVisionnagePresentDansLaListe(oeuvreAControler.getStatutVisionnage(),statutVisionnageListDtoFromBdd))
            throw new MauvaisParamException("Le statut de visionnage id="+oeuvreAControler.getStatutVisionnage().getId()+ "de l'oeuvre "+oeuvreAControler.getId()+" n'existe pas dans la base.");

        if (oeuvreAControler instanceof Serie) {
            //si c'est une serie on verifie les statut des saisons
            Serie serie = (Serie) oeuvreAControler;
            for (Saison saison: serie.getSaisons()) {
                if (saison.getStatutVisionnage()!=null && !isStatutVisionnagePresentDansLaListe(saison.getStatutVisionnage(),statutVisionnageListDtoFromBdd))
                    throw new MauvaisParamException("Le statut de visionnage id="+oeuvreAControler.getStatutVisionnage().getId()+" de la saison id="+saison.getId()+" n'existe pas dans la base");
            }
        }
    }

    @Override
    public OeuvreDto saveOeuvre(OeuvreFormulaireDto oeuvreFormulaireDto) {
        Oeuvre oeuvreASauver=convertirOeuvreFormulaireDtoEnOeuvre(oeuvreFormulaireDto);

        logger.debug("oeuvreASauver ={}",oeuvreASauver);

        if (oeuvreASauver==null)
            throw new MauvaisParamException("L'oeuvre recue est vide");

        if (oeuvreASauver.getId()!=null) {
            //si ce n'est pas une nouvelle oeuvre (un id est specifié)

            //on vérifie que son type n'a pas changé
            EnumTypeOeuvre typeOeuvreEnBdd= oeuvreRepository.getTypeOeuvre(oeuvreASauver.getId());
            if ((oeuvreASauver instanceof Film && typeOeuvreEnBdd!=EnumTypeOeuvre.FILM) ||
            (oeuvreASauver instanceof Serie && typeOeuvreEnBdd!=EnumTypeOeuvre.SERIE))
                throw new MauvaisParamException("L'oeuvre avec l'id "+oeuvreASauver.getId()+" existe deja en base. Vous ne pouvez pas modifier son type. Type actuellement en base= "+ typeOeuvreEnBdd);

        }

        //on controle que le titre n'existe pas dejà sauf pour lui meme
        List<Oeuvre> oeuvre= oeuvreRepository.findByTitre(oeuvreASauver.getTitre());
        if (!oeuvre.isEmpty() && !oeuvre.get(0).getId().equals(oeuvreASauver.getId())) {
            //on a trouvé une oeuvre avec le meme titre et qui n'a pas le meme id. Donc on essaye de sauver en double une oeuvre
            logger.info("Sauvegarde nouvelle oeuvre annulée. L'oeuvre avec le titre '{}' existe deja en base avec l'id={}",oeuvreASauver.getTitre(),oeuvre.get(0).getId());
            throw new MauvaisParamException("L'oeuvre avec le titre '"+oeuvreASauver.getTitre()+"' existe deja.");
        }

        //on controle que les genres sont des genres existant sinon erreur
        checkGenrePresentEnBdd(oeuvreASauver);

        //on controle les statuts de visionnage
        checkStatutVisionnagePresentEnBdd(oeuvreASauver);

        //on crée/modifie l'oeuvre
        Oeuvre oeuvreCree = oeuvreRepository.save(oeuvreASauver);
        logger.info("Oeuvre \"{}\" sauvee dans la BDD avec l'id : {}",oeuvreCree.getTitre(),oeuvreCree.getId());

        return convertirOeuvreEnDto(oeuvreCree);
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
        return new OeuvreLightDto(oeuvre.getId(), typeOeuvre, oeuvre.getTitre(),
                genresDto, statutVisionnageDto, oeuvre.getNote(), oeuvre.getCreateurs(),
                oeuvre.getActeurs(), oeuvre.getUrlAffiche(), oeuvre.getUrlBandeAnnonce(),
                oeuvre.getDescription());
    }

    @Override
    public OeuvreDto getOeuvreCompleteById(Long id) {
        if (id != null && id > 0) {
            EnumTypeOeuvre typeOeuvre = oeuvreRepository.getTypeOeuvre(id);
            if (typeOeuvre == EnumTypeOeuvre.FILM) {
                Optional<Film> optionalOeuvre = filmRepository.findById(id);
                if (optionalOeuvre.isPresent()) {
                    Oeuvre oeuvreFilm = optionalOeuvre.get();
                    logger.debug("Oeuvre pour id={} trouvée={}",id,oeuvreFilm);
                    return convertirOeuvreEnDto(oeuvreFilm);
                } else {
                    return null;
                }
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

        return new OeuvreDto(
                oeuvre.getId(), typeOeuvre, oeuvre.getTitre(), genresDto, statutVisionnageDto,
                oeuvre.getNote(), oeuvre.getCreateurs(), oeuvre.getActeurs(), oeuvre.getUrlAffiche(),
                oeuvre.getUrlBandeAnnonce(), oeuvre.getDescription(), saisonDtoList, duree);
    }

    @Override
    public OeuvreLightListDto getOeuvres (Map < String, String > criteres){
        logger.info("debut recherche avec criteres {}", criteres);
        //on construit les critères
        OeuvreSpecification criteresDeRecherche = buildOeuvreSpecification(criteres);

        //on récupère la liste
        List<Oeuvre> oeuvres = oeuvreRepository.findAll(criteresDeRecherche);
        logger.info("fin recherche en BDD. Nb oeuvres trouvees= {}", oeuvres.size());
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
                    ,oeuvreDto.getUrlAffiche(), oeuvreDto.getUrlBandeAnnonce(),
                    oeuvreDto.getDescription(), oeuvreDto.getDuree());
        } else {
            List<Saison> saisonList = saisonService.convertirListSaisonDtoEnListSaison(oeuvreDto.getSaisons());
            return new Serie(oeuvreDto.getId(), oeuvreDto.getTitre(), genres,statutVisionnage
                    ,oeuvreDto.getNote(), oeuvreDto.getCreateurs(), oeuvreDto.getActeurs()
                    ,oeuvreDto.getUrlAffiche(), oeuvreDto.getUrlBandeAnnonce(),
                    oeuvreDto.getDescription(), saisonList);
        }

    }

    @Override
    public Oeuvre convertirOeuvreFormulaireDtoEnOeuvre(OeuvreFormulaireDto oeuvreFormulaireDto) {
        if (oeuvreFormulaireDto == null) return null;
        List<Genre> genres = genreService.convertirListIdsEnListGenre(oeuvreFormulaireDto.getGenreIds());

        StatutVisionnage statutVisionnage = statutVisionnageService.convertirStatutVisionnageIdEnStatutVisionnage(oeuvreFormulaireDto.getStatutVisionnageId());

        if (oeuvreFormulaireDto.getTypeOeuvre().equals(EnumTypeOeuvre.FILM.getLibelle())) {
            return new Film(oeuvreFormulaireDto.getId(), oeuvreFormulaireDto.getTitre(), genres,statutVisionnage
                    , oeuvreFormulaireDto.getNote(), oeuvreFormulaireDto.getCreateurs(), oeuvreFormulaireDto.getActeurs()
                    ,oeuvreFormulaireDto.getUrlAffiche(), oeuvreFormulaireDto.getUrlBandeAnnonce(),
                    oeuvreFormulaireDto.getDescription(), oeuvreFormulaireDto.getDuree());
        } else {
            List<Saison> saisonList = saisonService.convertirListSaisonFormulaireDtoEnListSaison(oeuvreFormulaireDto.getSaisons());
            return new Serie(oeuvreFormulaireDto.getId(), oeuvreFormulaireDto.getTitre(), genres,statutVisionnage
                    ,oeuvreFormulaireDto.getNote(), oeuvreFormulaireDto.getCreateurs(), oeuvreFormulaireDto.getActeurs()
                    ,oeuvreFormulaireDto.getUrlAffiche(), oeuvreFormulaireDto.getUrlBandeAnnonce(),
                    oeuvreFormulaireDto.getDescription(), saisonList);
        }

    }
}
