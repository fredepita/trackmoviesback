package fr.epita.trackmoviesback.application;


import fr.epita.trackmoviesback.Utilitaire.GenerateurObjetTest;
import fr.epita.trackmoviesback.domaine.*;
import fr.epita.trackmoviesback.dto.*;
import fr.epita.trackmoviesback.dto.formulaire.OeuvreFormulaireDto;
import fr.epita.trackmoviesback.dto.formulaire.SaisonFormulaireDto;
import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;
import fr.epita.trackmoviesback.exception.MauvaisParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OeuvreServiceImplTest {
    private static int NB_OEUVRE_TOTAL_EN_BDD = 5;

    private static final Logger logger = LoggerFactory.getLogger(OeuvreServiceImplTest.class);

    @Autowired
    OeuvreService oeuvreService;

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    GenreService genreService;

    @Test
    void getAllOeuvres_doit_retourner_toutes_les_oeuvres_d_un_utilisateur() {
        Utilisateur utilisateurTest=utilisateurService.rechercherUtilisateurParLogin("u1");

        OeuvreLightListDto oeuvreLightListDto = oeuvreService.getAllOeuvres(utilisateurTest.getLogin());
        List<OeuvreLightDto> oeuvreLightDtoList = oeuvreLightListDto.getOeuvres();

        assertEquals(oeuvreLightDtoList.size(), NB_OEUVRE_TOTAL_EN_BDD);

        boolean foundShazam = false;
        for (OeuvreLightDto oeuvre : oeuvreLightDtoList) {
            if (oeuvre.getTitre().equals("Shazam!")) {
                foundShazam = true;
                break;
            }
        }
        assertTrue(foundShazam);

        //je dois recupérer 0 oeuvre si le user n'en n'a pas
        oeuvreLightDtoList = oeuvreService.getAllOeuvres("user_sans_oeuvre").getOeuvres();
        assertEquals(0,oeuvreLightDtoList.size());

    }

    @Test
    void convertirOeuvreEnLightDto_doit_convertir_une_oeuvre_en_light_dto() {
        //Création d'une oeuvreTest
        //Création d'une liste de Genres
        Genre genreComedie = new Genre();
        genreComedie.setId(2L);
        genreComedie.setLibelle("Action");
        List<Genre> listeGenre = new ArrayList<>();
        listeGenre.add(genreComedie);
        //Création d'un statut de visionnage
        StatutVisionnage statutVu = new StatutVisionnage();
        statutVu.setId(3L);
        statutVu.setLibelle("Vu");

        Oeuvre oeuvreTest = new Film();
        oeuvreTest.setTitre("titanic");
        oeuvreTest.setGenres(listeGenre);
        oeuvreTest.setStatutVisionnage(statutVu);
        oeuvreTest.setNote(1);
        oeuvreTest.setUrlAffiche("url...");
        oeuvreTest.setUrlBandeAnnonce("url...");

        OeuvreLightDto oeuvreLightDto= oeuvreService.convertirOeuvreEnLightDto(oeuvreTest);


        assertEquals(oeuvreLightDto.getId(), oeuvreTest.getId());
        assertEquals(oeuvreLightDto.getTitre(), oeuvreTest.getTitre());
        assertEquals(oeuvreLightDto.getGenres().size(), oeuvreTest.getGenres().size() );
        //La conversion de Genre en Dto est vérifiée dans GenreServiceImplTest
        assertEquals(oeuvreLightDto.getStatutVisionnage().getLibelle(), oeuvreTest.getStatutVisionnage().getLibelle());
        assertEquals(oeuvreLightDto.getNote(), oeuvreTest.getNote());
        assertEquals(oeuvreLightDto.getUrlAffiche(), oeuvreTest.getUrlAffiche());
        assertEquals(oeuvreLightDto.getUrlBandeAnnonce(), oeuvreTest.getUrlBandeAnnonce());
    }

    @Test
    void getOeuvres_doit_me_retourner_les_oeuvres_correspondant_aux_criteres() {
        Utilisateur utilisateurTest=utilisateurService.rechercherUtilisateurParLogin("u1");

        //je dois recuperer 2 series de ma base test
        Map<String, String> criteresHttp = new HashMap<>();
        OeuvreLightListDto oeuvreLightDto;

        criteresHttp.put("type","serie");
        oeuvreLightDto= oeuvreService.getOeuvres(utilisateurTest.getLogin(), criteresHttp);

        assertNotNull(oeuvreLightDto);
        assertNotNull(oeuvreLightDto.getOeuvres());
        assertEquals(2,oeuvreLightDto.getOeuvres().size());

        //je dois avoir 3 oeuvres d'action
        criteresHttp = new HashMap<>();
        criteresHttp.put("genre","1");
        oeuvreLightDto= oeuvreService.getOeuvres(utilisateurTest.getLogin(),criteresHttp);
        assertNotNull(oeuvreLightDto);
        assertNotNull(oeuvreLightDto.getOeuvres());
        assertEquals(3,oeuvreLightDto.getOeuvres().size());

        //je dois avoir 1 serie comedie qui est friends
        criteresHttp = new HashMap<>();
        criteresHttp.put("type","serie");
        criteresHttp.put("genre","2");
        oeuvreLightDto= oeuvreService.getOeuvres(utilisateurTest.getLogin(),criteresHttp);
        assertNotNull(oeuvreLightDto);
        assertNotNull(oeuvreLightDto.getOeuvres());
        assertEquals(1,oeuvreLightDto.getOeuvres().size());
        assertEquals("friends",oeuvreLightDto.getOeuvres().get(0).getTitre());

        //je dois avoir 2 oeuvres vues
        criteresHttp = new HashMap<>();
        criteresHttp.put("statut","3");
        oeuvreLightDto= oeuvreService.getOeuvres(utilisateurTest.getLogin(),criteresHttp);
        assertNotNull(oeuvreLightDto);
        assertNotNull(oeuvreLightDto.getOeuvres());
        assertEquals(2,oeuvreLightDto.getOeuvres().size());


    }

    @Test
    void getOeuvres_mauvais_critere_doit_etre_ignore_et_on_retourne_toutes_les_oeuvres() {
        Utilisateur utilisateurTest=utilisateurService.rechercherUtilisateurParLogin("u1");

        /*
        logger.debug("getOeuvres_mauvais_critere_doit_etre_ignore_et_on_retourne_toutes_les_oeuvres");
        OeuvreLightListDto oeuvreLightListDto=oeuvreService.getAllOeuvres(utilisateurTest.getLogin());
        logger.debug("oeuvreLightListDto:" +oeuvreLightListDto);
        oeuvreService.getAllOeuvres(utilisateurTest.getLogin()).getOeuvres().forEach(System.out::println);*/

        //test mauvais critere de recherche
        Map<String, String> criteresHttp = new HashMap<>();
        criteresHttp.put("sta","shAzA");
        assertEquals(NB_OEUVRE_TOTAL_EN_BDD,oeuvreService.getOeuvres(utilisateurTest.getLogin(),criteresHttp).getOeuvres().size());

        criteresHttp = new HashMap<>();
        criteresHttp.put("statut",null);
        assertEquals(NB_OEUVRE_TOTAL_EN_BDD,oeuvreService.getOeuvres(utilisateurTest.getLogin(),criteresHttp).getOeuvres().size());

        criteresHttp = new HashMap<>();
        criteresHttp.put("statut","");
        assertEquals(NB_OEUVRE_TOTAL_EN_BDD,oeuvreService.getOeuvres(utilisateurTest.getLogin(),criteresHttp).getOeuvres().size());

        criteresHttp = new HashMap<>();
        criteresHttp.put("statut"," ");
        assertEquals(NB_OEUVRE_TOTAL_EN_BDD,oeuvreService.getOeuvres(utilisateurTest.getLogin(),criteresHttp).getOeuvres().size());

        //test avec 2 critères
        criteresHttp = new HashMap<>();
        criteresHttp.put("statut"," ");
        criteresHttp.put("testMauvaisCritere","aez");
        assertEquals(NB_OEUVRE_TOTAL_EN_BDD,oeuvreService.getOeuvres(utilisateurTest.getLogin(),criteresHttp).getOeuvres().size());


    }

    @Test
    void getOeuvres_test_critere_titre_qui_doit_me_retourner_film_shazam() {
        Utilisateur utilisateurTest=utilisateurService.rechercherUtilisateurParLogin("u1");

        Map<String, String> criteresHttp ;
        OeuvreLightListDto oeuvreLightListDto;

        //test titre complet
        criteresHttp = new HashMap<>();
        criteresHttp.put("titre","Shazam!");
        oeuvreLightListDto= oeuvreService.getOeuvres(utilisateurTest.getLogin(),criteresHttp);
        assertNotNull(oeuvreLightListDto);
        assertNotNull(oeuvreLightListDto.getOeuvres());
        assertEquals(1,oeuvreLightListDto.getOeuvres().size());
        assertEquals("Shazam!",oeuvreLightListDto.getOeuvres().get(0).getTitre());


        //test recherche partielle
        criteresHttp = new HashMap<>();
        criteresHttp.put("titre","Sh");
        oeuvreLightListDto= oeuvreService.getOeuvres(utilisateurTest.getLogin(),criteresHttp);
        assertNotNull(oeuvreLightListDto);
        assertNotNull(oeuvreLightListDto.getOeuvres());
        assertEquals(1,oeuvreLightListDto.getOeuvres().size());
        assertEquals("Shazam!",oeuvreLightListDto.getOeuvres().get(0).getTitre());

        //test recherche casse differente. On doit retourner l'oeuvre sans tenir compte de la casse
        criteresHttp = new HashMap<>();
        criteresHttp.put("titre","shAzA");
        oeuvreLightListDto= oeuvreService.getOeuvres(utilisateurTest.getLogin(),criteresHttp);
        assertNotNull(oeuvreLightListDto);
        assertNotNull(oeuvreLightListDto.getOeuvres());
        assertEquals(1,oeuvreLightListDto.getOeuvres().size());
        assertEquals("Shazam!",oeuvreLightListDto.getOeuvres().get(0).getTitre());


    }

    @Test
    void getOeuvreCompleteById_doit_retourner_une_oeuvre_pour_un_id_donné() {
        Utilisateur utilisateurTest=utilisateurService.rechercherUtilisateurParLogin("u1");
        OeuvreDto oeuvreTest= oeuvreService.getOeuvreCompleteById(utilisateurTest.getLogin(), 1L);
        assertEquals("Shazam!", oeuvreTest.getTitre());
        //et que l'oeuvre a 2 genre dont 1 genre Action
        assertEquals(2,oeuvreTest.getGenres().size());
        for (GenreDto genre:oeuvreTest.getGenres()) {
            if (genre.getId()==1L) {
                assertEquals("Action",genre.getLibelle());
                break;
            }
        }

    }

    @Test
    void getOeuvreCompleteById_doit_tester_la_duree_du_film_shazam() {
        Utilisateur utilisateurTest=utilisateurService.rechercherUtilisateurParLogin("u1");
        OeuvreDto oeuvreTest= oeuvreService.getOeuvreCompleteById(utilisateurTest.getLogin(),1L);
        assertEquals(166, oeuvreTest.getDuree());
    }

    @Test
    void getOeuvreCompleteById_doit_tester_que_la_serie_friends_a_une_saisonList() {
        Utilisateur utilisateurTest=utilisateurService.rechercherUtilisateurParLogin("u1");

        OeuvreDto oeuvreTest= oeuvreService.getOeuvreCompleteById(utilisateurTest.getLogin(),3L);
        assertNotNull(oeuvreTest.getSaisons());
        assertEquals(2, oeuvreTest.getSaisons().size());
        boolean saisonTrouvee=false;
        for (SaisonDto saison: oeuvreTest.getSaisons()) {
            if (saison.getNumero().equals("S1")) {
                assertEquals(1, saison.getId());
                assertNotNull(saison.getStatutVisionnage());
                assertEquals("En cours", saison.getStatutVisionnage().getLibelle());
                assertEquals(5, saison.getNbEpisodes());
                saisonTrouvee=true;
            }
        }
        assertTrue(saisonTrouvee);

    }

    @Test
    void convertirOeuvreEnOeuvreDto_doit_convertir_une_serie_et_un_film_en_dto() {


        //Création d'une liste de Genres
        Genre genreComedie = new Genre();
        genreComedie.setId(2L);
        genreComedie.setLibelle("Action");
        List<Genre> listeGenre = new ArrayList<>();
        listeGenre.add(genreComedie);
        //Création d'un statut de visionnage
        StatutVisionnage statutVu = new StatutVisionnage();
        statutVu.setId(3L);
        statutVu.setLibelle("Vu");
        //Création d'une saisonList
        Saison saisonTest = new Saison(1L,"S1", statutVu,1);
        List<Saison> saisonListTest = new ArrayList<>();
        saisonListTest.add(saisonTest);

        //Création d'un filmTest
        Film filmTest = new Film();
        filmTest.setTitre("titanic");
        filmTest.setGenres(listeGenre);
        filmTest.setStatutVisionnage(statutVu);
        filmTest.setNote(1);
        filmTest.setUrlAffiche("url...");
        filmTest.setUrlBandeAnnonce("url...");
        filmTest.setDuree(120);
        //Création d'une serieTest
        Serie serieTest = new Serie();
        serieTest.setTitre("titanic");
        serieTest.setGenres(listeGenre);
        serieTest.setStatutVisionnage(statutVu);
        serieTest.setNote(1);
        serieTest.setUrlAffiche("url...");
        serieTest.setUrlBandeAnnonce("url...");
        serieTest.setSaisons(saisonListTest);

        //création d'un oeuvreFilmDto et d'un oeuvreSerieDto à comparer avec filmTest et serieTest
        OeuvreDto oeuvreFilmDto= oeuvreService.convertirOeuvreEnDto(filmTest);
        OeuvreDto oeuvreSerieDto = oeuvreService.convertirOeuvreEnDto(serieTest);

        //Tests sur le filmTest
        assertEquals(oeuvreFilmDto.getId(), filmTest.getId());
        assertEquals(oeuvreFilmDto.getTitre(), filmTest.getTitre());
        assertEquals(oeuvreFilmDto.getGenres().size(), filmTest.getGenres().size() );
        //La conversion de Genre en Dto est vérifiée dans GenreServiceImplTest
        assertEquals(oeuvreFilmDto.getStatutVisionnage().getLibelle(), filmTest.getStatutVisionnage().getLibelle());
        assertEquals(oeuvreFilmDto.getNote(), filmTest.getNote());
        assertEquals(oeuvreFilmDto.getUrlAffiche(), filmTest.getUrlAffiche());
        assertEquals(oeuvreFilmDto.getUrlBandeAnnonce(), filmTest.getUrlBandeAnnonce());
        assertEquals(oeuvreFilmDto.getDuree(), filmTest.getDuree());

        //Tests sur la serieTest
        assertEquals(oeuvreSerieDto.getId(), serieTest.getId());
        assertEquals(oeuvreSerieDto.getTitre(), serieTest.getTitre());
        assertEquals(oeuvreSerieDto.getGenres().size(), serieTest.getGenres().size() );
        //La conversion de Genre en Dto est vérifiée dans GenreServiceImplTest
        assertEquals(oeuvreSerieDto.getStatutVisionnage().getLibelle(), serieTest.getStatutVisionnage().getLibelle());
        assertEquals(oeuvreSerieDto.getNote(), serieTest.getNote());
        assertEquals(oeuvreSerieDto.getUrlAffiche(), serieTest.getUrlAffiche());
        assertEquals(oeuvreSerieDto.getUrlBandeAnnonce(), serieTest.getUrlBandeAnnonce());
        //Test concernant la liste des saisons
        List<SaisonDto> saisonDtoList = oeuvreSerieDto.getSaisons();
        boolean foundS1 = false;
        for (SaisonDto saison : saisonDtoList) {
            if (saison.getNumero().equals("S1")) {
                foundS1 = true;
                break;
            }
        }
        assertTrue(foundS1);
    }


    @Test
    void createOeuvre_la_creation_du_film_et_de_la_serie_doit_fonctionner_avec_les_champs_minimum() {
        Utilisateur utilisateurTest=utilisateurService.rechercherUtilisateurParLogin("u1");

        final String titreFilm= "film de test";
        final String titreSerie= "série de test";

        //creation d'un film vide doit échouer
        assertThrows(MauvaisParamException.class, () -> {
            OeuvreDto filmCreeVide = oeuvreService.saveOeuvre(utilisateurTest.getLogin(),null);
        });

        //creation d'un film
        OeuvreFormulaireDto newFilm= GenerateurObjetTest.createOeuvreDtoFormulaireMinimal(EnumTypeOeuvre.FILM.getLibelle(), titreFilm);

        OeuvreDto filmCree = oeuvreService.saveOeuvre(utilisateurTest.getLogin(),newFilm);

        assertNotNull(filmCree);
        assertTrue(filmCree.getId()>0);

        //on verifie que le film insere est correcte en le rechargant
        OeuvreDto filmInsere=oeuvreService.getOeuvreCompleteById(utilisateurTest.getLogin(),filmCree.getId());
        assertNotNull(filmInsere);
        assertEquals(EnumTypeOeuvre.FILM.getLibelle(),filmInsere.getTypeOeuvre());
        assertEquals(titreFilm,filmInsere.getTitre());

        //on supprime le film de la base test
        oeuvreService.deleteOeuvre(utilisateurTest.getLogin(),filmInsere.getId());


        //creation d'une serie
        OeuvreFormulaireDto newSerie= GenerateurObjetTest.createOeuvreDtoFormulaireMinimal(EnumTypeOeuvre.SERIE.getLibelle(), titreSerie);

        OeuvreDto serieCree = oeuvreService.saveOeuvre(utilisateurTest.getLogin(),newSerie);

        assertNotNull(serieCree);
        assertTrue(serieCree.getId()>0);

        OeuvreDto serieInseree=oeuvreService.getOeuvreCompleteById(utilisateurTest.getLogin(),serieCree.getId());
        assertNotNull(serieInseree);
        assertEquals(EnumTypeOeuvre.SERIE.getLibelle(),serieInseree.getTypeOeuvre());
        assertEquals(titreSerie,serieInseree.getTitre());

        oeuvreService.deleteOeuvre(utilisateurTest.getLogin(),serieInseree.getId());

    }


    @Test
    void createOeuvre_la_creation_avec_un_statut_visionnage_doit_fonctionner() {
        Utilisateur utilisateurTest=utilisateurService.rechercherUtilisateurParLogin("u1");

        final String titreFilm= "film de test";

        OeuvreFormulaireDto newFilm= GenerateurObjetTest.createOeuvreDtoFormulaireMinimal(EnumTypeOeuvre.FILM.getLibelle(), titreFilm);
        newFilm.setStatutVisionnageId(1L);

        OeuvreDto filmCree = oeuvreService.saveOeuvre(utilisateurTest.getLogin(),newFilm);

        assertNotNull(filmCree);
        assertTrue(filmCree.getId()>0);

        OeuvreDto oeuvreDtoInseree=oeuvreService.getOeuvreCompleteById(utilisateurTest.getLogin(),filmCree.getId());
        assertNotNull(oeuvreDtoInseree);
        assertEquals(EnumTypeOeuvre.FILM.getLibelle(),oeuvreDtoInseree.getTypeOeuvre());
        assertEquals(1L,oeuvreDtoInseree.getStatutVisionnage().getId());

        oeuvreService.deleteOeuvre(utilisateurTest.getLogin(),oeuvreDtoInseree.getId());
    }

    @Test
    void createOeuvre_la_creation_avec_un_ou_plusieurs_genre_doit_fonctionner() {
        Utilisateur utilisateurTest=utilisateurService.rechercherUtilisateurParLogin("u1");

        final String titreFilm= "film de test";

        OeuvreFormulaireDto newFilm= GenerateurObjetTest.createOeuvreDtoFormulaireMinimal(EnumTypeOeuvre.FILM.getLibelle(), titreFilm);

        //check genre presents en base et s'assurer qu'il y a action avec ID 1 dedans
        GenreListDto genreListDto =genreService.getAllGenres();
        List<GenreDto> genreDtoList= genreListDto.getGenres();

        for (GenreDto genre:genreDtoList) {
            //System.out.println(genre);
            if (genre.getId()==1L) {
                assertEquals("Action",genre.getLibelle());
                break;
            }
        }

        List<Long> genreIdsList = new ArrayList<>();
        genreIdsList.add(1L);
        genreIdsList.add(2L);
        newFilm.setGenreIds(genreIdsList);

        OeuvreDto filmCree = oeuvreService.saveOeuvre(utilisateurTest.getLogin(), newFilm);

        assertNotNull(filmCree);
        assertTrue(filmCree.getId()>0);

        OeuvreDto oeuvreDtoInseree=oeuvreService.getOeuvreCompleteById(utilisateurTest.getLogin(),filmCree.getId());
        assertNotNull(oeuvreDtoInseree);
        assertEquals(EnumTypeOeuvre.FILM.getLibelle(),oeuvreDtoInseree.getTypeOeuvre());
        assertEquals(2,oeuvreDtoInseree.getGenres().size());
        for (GenreDto genre:oeuvreDtoInseree.getGenres()) {
            if (genre.getId()==1L) {
                assertEquals("Action",genre.getLibelle());
                break;
            }
        }

        oeuvreService.deleteOeuvre(utilisateurTest.getLogin(),oeuvreDtoInseree.getId());
    }

    @Test
    //@Transactional //on positionne en Transactionnel car on fait des ajout/suppression. Sinon, cela genere une javax.persistence.TransactionRequiredException
    void createOeuvre_testCreation_film() {
        Utilisateur utilisateurTest=utilisateurService.rechercherUtilisateurParLogin("u1");

        //creation du film
        final String titreFilm= "film de test";
        OeuvreFormulaireDto newFilm=GenerateurObjetTest.createOeuvreFormulaireDtoComplete(EnumTypeOeuvre.FILM.getLibelle(), titreFilm);
        newFilm.setDuree(155);
        //fin creation du film

        OeuvreDto filmCree = oeuvreService.saveOeuvre(utilisateurTest.getLogin(),newFilm);

        //check genre presents en base et s'assurer qu'il y a toujour action avec ID 1 dedans
        GenreListDto genreListDto =genreService.getAllGenres();
        List<GenreDto> genreDtoList= genreListDto.getGenres();
        genreDtoList.stream().forEach(System.out::println);
        assertEquals(4,genreDtoList.size());
        for (GenreDto genre:genreDtoList) {
            //System.out.println(genre);
            if (genre.getId()==1L) {
                assertEquals("Action",genre.getLibelle());
                break;
            }
        }

        assertNotNull(filmCree);
        assertTrue(filmCree.getId()>0);

        //check a virer
        OeuvreDto oeuvreDtoShazam=oeuvreService.getOeuvreCompleteById(utilisateurTest.getLogin(),1L);


        OeuvreDto oeuvreDtoInseree=oeuvreService.getOeuvreCompleteById(utilisateurTest.getLogin(),filmCree.getId());
        assertNotNull(oeuvreDtoInseree);

        assertEquals(EnumTypeOeuvre.FILM.getLibelle(),oeuvreDtoInseree.getTypeOeuvre());
        assertEquals(newFilm.getTitre(),oeuvreDtoInseree.getTitre());
        assertEquals(newFilm.getGenreIds().size(),oeuvreDtoInseree.getGenres().size());
        assertEquals(newFilm.getStatutVisionnageId(),oeuvreDtoInseree.getStatutVisionnage().getId());
        assertEquals(newFilm.getNote(),oeuvreDtoInseree.getNote());
        assertEquals(newFilm.getCreateurs(),oeuvreDtoInseree.getCreateurs());
        assertEquals(newFilm.getActeurs(),oeuvreDtoInseree.getActeurs());
        assertEquals(newFilm.getUrlAffiche(),oeuvreDtoInseree.getUrlAffiche());
        assertEquals(newFilm.getUrlBandeAnnonce(),oeuvreDtoInseree.getUrlBandeAnnonce());
        assertEquals(newFilm.getDuree(),oeuvreDtoInseree.getDuree());
        assertEquals(2,oeuvreDtoInseree.getGenres().size());
        for (GenreDto genre:oeuvreDtoInseree.getGenres()) {
            if (genre.getId()==1L) {
                assertEquals("Action",genre.getLibelle());
                break;
            }
        }


        //je ne dois pas pouvoir sauver une oeuvre avec le meme titre mais un id different
        newFilm.setTitre("film de test");
        assertThrows(MauvaisParamException.class, () -> {
            oeuvreService.saveOeuvre(utilisateurTest.getLogin(),newFilm);
        });

        oeuvreService.deleteOeuvre(utilisateurTest.getLogin(),oeuvreDtoInseree.getId());
    }
    @Test
    void createOeuvre_testCreation_serie() {
        Utilisateur utilisateurTest=utilisateurService.rechercherUtilisateurParLogin("u1");

        //creation de la serie
        final String titreOeuvre= "serie de test";
        OeuvreFormulaireDto newSerie =GenerateurObjetTest.createOeuvreFormulaireDtoComplete(EnumTypeOeuvre.SERIE.getLibelle(), titreOeuvre);
        //fin de la creation de la serie

        logger.debug("Debut insertion Serie test. Titre= {}",newSerie.getTitre());
        logger.debug("Detail de la serie: {}",newSerie);
        OeuvreDto serieCree = oeuvreService.saveOeuvre(utilisateurTest.getLogin(),newSerie);

        assertNotNull(serieCree);
        assertTrue(serieCree.getId()>0);

        OeuvreDto oeuvreDtoInseree=oeuvreService.getOeuvreCompleteById(utilisateurTest.getLogin(),serieCree.getId());
        assertNotNull(oeuvreDtoInseree);

        assertEquals(EnumTypeOeuvre.SERIE.getLibelle(),oeuvreDtoInseree.getTypeOeuvre());
        assertEquals(newSerie.getTitre(),oeuvreDtoInseree.getTitre());
        assertEquals(newSerie.getGenreIds().size(),oeuvreDtoInseree.getGenres().size());
        assertEquals(newSerie.getStatutVisionnageId(),oeuvreDtoInseree.getStatutVisionnage().getId());
        assertEquals(newSerie.getNote(),oeuvreDtoInseree.getNote());
        assertEquals(newSerie.getCreateurs(),oeuvreDtoInseree.getCreateurs());
        assertEquals(newSerie.getActeurs(),oeuvreDtoInseree.getActeurs());
        assertEquals(newSerie.getUrlAffiche(),oeuvreDtoInseree.getUrlAffiche());
        assertEquals(newSerie.getUrlBandeAnnonce(),oeuvreDtoInseree.getUrlBandeAnnonce());
        assertEquals(newSerie.getSaisons().size(),oeuvreDtoInseree.getSaisons().size());

        assertThrows(MauvaisParamException.class, () -> {
            //dois jeter une exception si je ne suis pas proprietaire de l'oeuvre a supprimer
            oeuvreService.deleteOeuvre("mauvais_login", oeuvreDtoInseree.getId());
        });

        oeuvreService.deleteOeuvre(utilisateurTest.getLogin(),oeuvreDtoInseree.getId());

    }

    private SaisonFormulaireDto convertSaisonDtoToFormulaire(SaisonDto saisonDto) {
        return new SaisonFormulaireDto(
                saisonDto.getId(),
                saisonDto.getNumero(),
                saisonDto.getStatutVisionnage().getId(),
                saisonDto.getNbEpisodes()
        );
    }


    private OeuvreFormulaireDto convertOeuvreDtoToFormulaire(OeuvreDto oeuvreDto) {
        return new OeuvreFormulaireDto(
                oeuvreDto.getId(),
                oeuvreDto.getTypeOeuvre(),
                oeuvreDto.getTitre(),
                oeuvreDto.getGenres().stream().map(g -> g.getId()).collect(Collectors.toList()),
                oeuvreDto.getStatutVisionnage().getId(),
                oeuvreDto.getNote(),
                oeuvreDto.getCreateurs(),
                oeuvreDto.getActeurs(),
                oeuvreDto.getUrlAffiche(),
                oeuvreDto.getUrlBandeAnnonce(),
                oeuvreDto.getDescription(),
                oeuvreDto.getSaisons().stream().map(this::convertSaisonDtoToFormulaire).collect(Collectors.toList()),
                oeuvreDto.getDuree()
        );
    }

    @Test
    void createOeuvre_modification_dune_saison_doit_fonctionner() {
        Utilisateur utilisateurTest=utilisateurService.rechercherUtilisateurParLogin("u1");

        //je recupere une serie existante
        //friends
        final Long oeuvreId=3L;

        OeuvreDto oeuvreFriends= oeuvreService.getOeuvreCompleteById(utilisateurTest.getLogin(),oeuvreId);

        //je modifie sa saison S2 en mettant nb episode = 99
        for (SaisonDto saison: oeuvreFriends.getSaisons()) {
            if (saison.getNumero().equals("S2")) {
                assertEquals(10,saison.getNbEpisodes());
                saison.setNbEpisodes(99);
            }
        }

        OeuvreFormulaireDto oeuvreFormulaireDto= convertOeuvreDtoToFormulaire(oeuvreFriends);

        //je sauve
        OeuvreDto serieFriends=oeuvreService.saveOeuvre(utilisateurTest.getLogin(),oeuvreFormulaireDto);
        //on check que l'id est le meme
        assertEquals(oeuvreId,serieFriends.getId());

        //on verifie les données en base que la saison fait bien 99 episode maintenant
        oeuvreFriends=  oeuvreService.getOeuvreCompleteById(utilisateurTest.getLogin(),oeuvreId);
        for (SaisonDto saison: oeuvreFriends.getSaisons()) {
            if (saison.getNumero().equals("S2")) {
                assertEquals(99,saison.getNbEpisodes());
            }
        }

        //on vérifie que si on essaye de changer le type d'une oeuvre existante, on a une exception

        assertThrows(MauvaisParamException.class, () -> {
            OeuvreDto oeuvreFriends2=  oeuvreService.getOeuvreCompleteById(utilisateurTest.getLogin(),oeuvreId);
            oeuvreFriends2.setTypeOeuvre(EnumTypeOeuvre.FILM.getLibelle());
            OeuvreFormulaireDto oeuvreFormulaireDto2= convertOeuvreDtoToFormulaire(oeuvreFriends2);
            oeuvreService.saveOeuvre(utilisateurTest.getLogin(),oeuvreFormulaireDto2);
        });

        //je modifie sa saison S2 pour la nommer S1 comme la premiere. La sauvegarde doit echouer car on ne peut avoir 2 saisons avec le meme numero
        assertThrows(MauvaisParamException.class, () -> {
            OeuvreDto oeuvreFriends3=  oeuvreService.getOeuvreCompleteById(utilisateurTest.getLogin(),oeuvreId);
            for (SaisonDto saison: oeuvreFriends3.getSaisons()) {
                if (saison.getNumero().equals("S2")) {
                    saison.setNumero("S1");
                }
            }
            OeuvreFormulaireDto oeuvreFormulaireDto2= convertOeuvreDtoToFormulaire(oeuvreFriends3);
            oeuvreService.saveOeuvre(utilisateurTest.getLogin(),oeuvreFormulaireDto2);
        });


    }


    @Test
    void convertirOeuvreDtoEnOeuvre_doit_convertir_un_OeuvreDto_en_Oeuvre() {
        Utilisateur utilisateurTest=utilisateurService.rechercherUtilisateurParLogin("u1");

        //on check pour 1 film
        OeuvreDto oeuvreDto = GenerateurObjetTest.createOeuvreDtoComplete(EnumTypeOeuvre.FILM.getLibelle(), "film test");
        assertNull(oeuvreService.convertirOeuvreDtoEnOeuvre(null,null));

        Oeuvre oeuvre = oeuvreService.convertirOeuvreDtoEnOeuvre(utilisateurTest.getLogin(),oeuvreDto);
        assertTrue(oeuvre instanceof Film);
        Film film = (Film) oeuvre;
        assertEquals(oeuvreDto.getTitre(), film.getTitre());
        assertEquals(oeuvreDto.getDuree(), film.getDuree());
        assertEquals(oeuvreDto.getStatutVisionnage().getLibelle(), film.getStatutVisionnage().getLibelle());
        assertEquals(oeuvreDto.getGenres().size(), film.getGenres().size());

        GenreDto firstGenreDto = oeuvreDto.getGenres().get(0);
        for (Genre genre : oeuvre.getGenres()) {
            if (genre.getId() == firstGenreDto.getId()) {
                assertEquals(firstGenreDto.getLibelle(), genre.getLibelle());
            }
        }


        //on check pour 1 serie
        oeuvreDto = GenerateurObjetTest.createOeuvreDtoComplete(EnumTypeOeuvre.SERIE.getLibelle(), "serie test");
        assertNull(oeuvreService.convertirOeuvreDtoEnOeuvre(utilisateurTest.getLogin(),null));

        oeuvre = oeuvreService.convertirOeuvreDtoEnOeuvre(utilisateurTest.getLogin(),oeuvreDto);
        assertTrue(oeuvre instanceof Serie);
        Serie serie = (Serie) oeuvre;
        assertEquals(oeuvreDto.getTitre(), serie.getTitre());
        assertNotNull(serie.getSaisons());
        assertEquals(oeuvreDto.getSaisons().size(), serie.getSaisons().size());
        assertEquals(oeuvreDto.getStatutVisionnage().getLibelle(), serie.getStatutVisionnage().getLibelle());
        assertEquals(oeuvreDto.getGenres().size(), serie.getGenres().size());

        firstGenreDto = oeuvreDto.getGenres().get(0);
        for (Genre genre : oeuvre.getGenres()) {
            if (genre.getId() == firstGenreDto.getId()) {
                assertEquals(firstGenreDto.getLibelle(), genre.getLibelle());
            }
        }

    }

    @Test
    void convertirOeuvreFormulaireDtoEnOeuvre_doit_convertir_un_OeuvreFormulaireDto_en_Oeuvre() {
        Utilisateur utilisateurTest=utilisateurService.rechercherUtilisateurParLogin("u1");

        //on check pour 1 film
        OeuvreFormulaireDto oeuvreFormulaireDto = GenerateurObjetTest.createOeuvreFormulaireDtoComplete(EnumTypeOeuvre.FILM.getLibelle(), "film test");
        assertNull(oeuvreService.convertirOeuvreFormulaireDtoEnOeuvre(utilisateurTest.getLogin(),null));

        Oeuvre oeuvre=oeuvreService.convertirOeuvreFormulaireDtoEnOeuvre(utilisateurTest.getLogin(),oeuvreFormulaireDto);
        assertTrue(oeuvre instanceof Film);
        Film film=(Film) oeuvre;
        assertEquals(oeuvreFormulaireDto.getTitre(),film.getTitre());
        assertEquals(oeuvreFormulaireDto.getDuree(),film.getDuree());
        assertEquals(oeuvreFormulaireDto.getStatutVisionnageId(),film.getStatutVisionnage().getId());
        assertEquals("",film.getStatutVisionnage().getLibelle());
        assertEquals(oeuvreFormulaireDto.getGenreIds().size(),film.getGenres().size());
        //ts les libelle genre doivent etre vides
        //et je dois trouver au moins un de mes genres du formulaire
        Long firstGenreId=oeuvreFormulaireDto.getGenreIds().get(0);
        boolean found=false;
        for(Genre genre: film.getGenres()) {
            assertEquals("",genre.getLibelle());
            if (genre.getId()==firstGenreId) {
                found=true;
            }
        }
        assertTrue(found);

        //on check pour 1 serie
        oeuvreFormulaireDto = GenerateurObjetTest.createOeuvreFormulaireDtoComplete(EnumTypeOeuvre.SERIE.getLibelle(), "serie test");
        assertNull(oeuvreService.convertirOeuvreFormulaireDtoEnOeuvre(utilisateurTest.getLogin(),null));

        oeuvre=oeuvreService.convertirOeuvreFormulaireDtoEnOeuvre(utilisateurTest.getLogin(),oeuvreFormulaireDto);
        assertTrue(oeuvre instanceof Serie);
        Serie serie=(Serie) oeuvre;
        assertEquals(oeuvreFormulaireDto.getTitre(),serie.getTitre());
        assertNotNull(serie.getSaisons());
        assertEquals(oeuvreFormulaireDto.getSaisons().size(),serie.getSaisons().size());
        assertEquals(oeuvreFormulaireDto.getStatutVisionnageId(),film.getStatutVisionnage().getId());
        assertEquals("",film.getStatutVisionnage().getLibelle());
        assertEquals(oeuvreFormulaireDto.getGenreIds().size(),film.getGenres().size());
        //ts les libelle genre doivent etre vides
        //et je dois trouver au moins un de mes genres du formulaire
        firstGenreId=oeuvreFormulaireDto.getGenreIds().get(0);
        found=false;
        for(Genre genre: film.getGenres()) {
            assertEquals("",genre.getLibelle());
            if (genre.getId()==firstGenreId) {
                found=true;
            }
        }
    }


}
