package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.*;
import fr.epita.trackmoviesback.dto.*;
import fr.epita.trackmoviesback.dto.formulaire.OeuvreFormulaireDto;
import fr.epita.trackmoviesback.dto.formulaire.SaisonFormulaireDto;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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

    @Autowired
    UtilisateurService utilisateurService;

    @Override
    public OeuvreLightListDto getAllOeuvres(String userLogin) {
        //on recupere l'utilisateur a partir du login
        Utilisateur utilisateur=utilisateurService.rechercherUtilisateurParLogin(userLogin);

        List<Oeuvre> oeuvres = oeuvreRepository.findAllByUtilisateurOrderByTitre(utilisateur);
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


    private boolean isStatutVisionnagePresentDansLaListe(StatutVisionnage statutVisionnageAControler,List<StatutVisionnageDto> statutVisionnageList){
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
    public OeuvreDto saveOeuvre(String userLogin, OeuvreFormulaireDto oeuvreFormulaireDto) {
        Oeuvre oeuvreASauver=convertirOeuvreFormulaireDtoEnOeuvre(userLogin,oeuvreFormulaireDto);

        logger.debug("saveOeuvre() - oeuvreASauver ={}",oeuvreASauver);

        if (oeuvreASauver==null)
            throw new MauvaisParamException("L'oeuvre recue est vide");

        if (oeuvreASauver.getId()!=null) {
            //si ce n'est pas une nouvelle oeuvre (un id est specifié)

            //on vérifie que son type n'a pas changé
            EnumTypeOeuvre typeOeuvreEnBdd= oeuvreRepository.getTypeOeuvre(oeuvreASauver.getId(),oeuvreASauver.getUtilisateur());
            if ((oeuvreASauver instanceof Film && typeOeuvreEnBdd!=EnumTypeOeuvre.FILM) ||
            (oeuvreASauver instanceof Serie && typeOeuvreEnBdd!=EnumTypeOeuvre.SERIE))
                throw new MauvaisParamException("L'oeuvre avec l'id "+oeuvreASauver.getId()+" existe deja en base. Vous ne pouvez pas modifier son type. Type actuellement en base= "+ typeOeuvreEnBdd);

        }

        //on controle que le titre n'existe pas dejà pour le user en cours sauf pour lui meme (dans le cas d'un update)
        List<Oeuvre> oeuvre= oeuvreRepository.findByTitreAndByUtilisateur(oeuvreASauver.getTitre(),oeuvreASauver.getUtilisateur());
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
    public void deleteOeuvre(String userLogin,Long id) {
        logger.info("deleteOeuvre() - Demande suppression de l'oeuvre avec id = {} ",id);
        //on recupere l'utilisateur dans la bdd a partir du login
        Utilisateur utilisateurLogge=utilisateurService.rechercherUtilisateurParLogin(userLogin);
        //on verifie que l'oeuvre appartient au user
        Utilisateur utilisateurDeLOeuvre = oeuvreRepository.getUtilisateurDeOeuvre(id);

        if (utilisateurLogge==null || utilisateurDeLOeuvre.getId()!=utilisateurLogge.getId()) {
            throw new MauvaisParamException("Suppression annulee. L'utilisateur n'est pas proprietaire de l'oeuvre");
        }
        //on delete l'oeuvre sinon
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
    public OeuvreDto getOeuvreCompleteById(String userLogin,Long id) {
        //on recupere l'utilisateur a partir du login
        Utilisateur utilisateur=utilisateurService.rechercherUtilisateurParLogin(userLogin);

        if (id != null && id > 0) {
            EnumTypeOeuvre typeOeuvre = oeuvreRepository.getTypeOeuvre(id,utilisateur);
            if (typeOeuvre == EnumTypeOeuvre.FILM) {
                Optional<Film> optionalOeuvre = filmRepository.findByIdAndUtilisateur(id,utilisateur);
                if (optionalOeuvre.isPresent()) {
                    Oeuvre oeuvreFilm = optionalOeuvre.get();
                    logger.debug("getOeuvreCompleteById() - Oeuvre pour id={} trouvée={}",id,oeuvreFilm);
                    return convertirOeuvreEnDto(oeuvreFilm);
                } else {
                    return null;
                }
            } else {
                Oeuvre oeuvreSerie = serieRepository.getSerieComplete(id,utilisateur);
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
    public OeuvreLightListDto getOeuvres (String userLogin,Map < String, String > criteres){
        logger.info("debut recherche avec criteres {}", criteres);

        //on recupere l'utilisateur a partir du login
        Utilisateur utilisateur=utilisateurService.rechercherUtilisateurParLogin(userLogin);

        //on construit les critères
        OeuvreSpecification criteresDeRecherche = buildOeuvreSpecification(utilisateur,criteres);

        //on récupère la liste
        List<Oeuvre> oeuvres = oeuvreRepository.findAll(criteresDeRecherche, Sort.by(Sort.Direction.ASC, "titre"));
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
    private OeuvreSpecification buildOeuvreSpecification (Utilisateur utilisateur, Map < String, String > criteres){
        OeuvreSpecification oeuvreSpecification = new OeuvreSpecification();

        //pour chaque critere en paramettre
        criteres.keySet().forEach(proprieteRecherchee -> {
            //on recupère la valeur recherchee correspondant dans la map
            String valeurRecherchee = criteres.get(proprieteRecherchee);
            CritereDeRecherche critereDeRecherche = buildCritereDeRecherche(proprieteRecherchee, valeurRecherchee);
            if (critereDeRecherche != null)
                oeuvreSpecification.add(critereDeRecherche);
        });

        //on ajoute le filtre sur le user
        CritereDeRecherche critereDeRechercheUser= new CritereDeRecherche(EnumProprieteRecherchableSurOeuvre.UTILISATEUR,utilisateur, EnumOperationDeRecherche.EGAL);
        oeuvreSpecification.add(critereDeRechercheUser);

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

        //gere type oeuvre
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
    public Oeuvre convertirOeuvreDtoEnOeuvre(String userLogin, OeuvreDto oeuvreDto) {
        if (oeuvreDto == null) return null;
        List<Genre> genres = genreService.convertirListGenreDtoEnListGenre(oeuvreDto.getGenres());

        StatutVisionnage statutVisionnage = statutVisionnageService.convertirStatutVisionnageDtoEnStatutVisionnage(oeuvreDto.getStatutVisionnage());

        //on recupere l'utilisateur a partir du login
        Utilisateur utilisateur=utilisateurService.rechercherUtilisateurParLogin(userLogin);

        if (oeuvreDto.getTypeOeuvre().equals(EnumTypeOeuvre.FILM.getLibelle())) {
            return new Film(utilisateur, oeuvreDto.getId(), oeuvreDto.getTitre(), genres,statutVisionnage
                    , oeuvreDto.getNote(), oeuvreDto.getCreateurs(), oeuvreDto.getActeurs()
                    ,oeuvreDto.getUrlAffiche(), oeuvreDto.getUrlBandeAnnonce(),
                    oeuvreDto.getDescription(), oeuvreDto.getDuree());
        } else {
            List<Saison> saisonList = saisonService.convertirListSaisonDtoEnListSaison(oeuvreDto.getSaisons());
            return new Serie(utilisateur,oeuvreDto.getId(), oeuvreDto.getTitre(), genres,statutVisionnage
                    ,oeuvreDto.getNote(), oeuvreDto.getCreateurs(), oeuvreDto.getActeurs()
                    ,oeuvreDto.getUrlAffiche(), oeuvreDto.getUrlBandeAnnonce(),
                    oeuvreDto.getDescription(), saisonList);
        }

    }

    @Override
    public Oeuvre convertirOeuvreFormulaireDtoEnOeuvre(String userLogin, OeuvreFormulaireDto oeuvreFormulaireDto) {
        if (oeuvreFormulaireDto == null) return null;
        List<Genre> genres = genreService.convertirListIdsEnListGenre(oeuvreFormulaireDto.getGenreIds());

        StatutVisionnage statutVisionnage = statutVisionnageService.convertirStatutVisionnageIdEnStatutVisionnage(oeuvreFormulaireDto.getStatutVisionnageId());

        //on recupere l'utilisateur a partir du login
        Utilisateur utilisateur=utilisateurService.rechercherUtilisateurParLogin(userLogin);

        if (oeuvreFormulaireDto.getTypeOeuvre().equals(EnumTypeOeuvre.FILM.getLibelle())) {
            return new Film(utilisateur, oeuvreFormulaireDto.getId(), oeuvreFormulaireDto.getTitre(), genres,statutVisionnage
                    , oeuvreFormulaireDto.getNote(), oeuvreFormulaireDto.getCreateurs(), oeuvreFormulaireDto.getActeurs()
                    ,oeuvreFormulaireDto.getUrlAffiche(), oeuvreFormulaireDto.getUrlBandeAnnonce(),
                    oeuvreFormulaireDto.getDescription(), oeuvreFormulaireDto.getDuree());
        } else {
            //regle metier
            if (oeuvreFormulaireDto.getSaisons()!=null) {
                //on controle qu'on n'a pas 2 saisons avec le meme numero
                List<String> saisonNumeroList = new ArrayList<>();
                for (SaisonFormulaireDto saisonFormulaireDto : oeuvreFormulaireDto.getSaisons()) {
                    if (saisonNumeroList.contains(saisonFormulaireDto.getNumero())) {
                        throw new MauvaisParamException("On ne peux pas avoir deux saisons avec le meme numero: " + saisonFormulaireDto.getNumero());
                    } else {
                        saisonNumeroList.add(saisonFormulaireDto.getNumero());
                    }
                }
            }

            List<Saison> saisonList = saisonService.convertirListSaisonFormulaireDtoEnListSaison(oeuvreFormulaireDto.getSaisons());
            return new Serie(utilisateur, oeuvreFormulaireDto.getId(), oeuvreFormulaireDto.getTitre(), genres,statutVisionnage
                    ,oeuvreFormulaireDto.getNote(), oeuvreFormulaireDto.getCreateurs(), oeuvreFormulaireDto.getActeurs()
                    ,oeuvreFormulaireDto.getUrlAffiche(), oeuvreFormulaireDto.getUrlBandeAnnonce(),
                    oeuvreFormulaireDto.getDescription(), saisonList);
        }

    }
}
