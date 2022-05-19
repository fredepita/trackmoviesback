package fr.epita.trackmoviesback.application;


import fr.epita.trackmoviesback.domaine.*;
import fr.epita.trackmoviesback.dto.*;
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
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OeuvreServiceImplTest {
    private static int NB_OEUVRE_TOTAL_EN_BDD = 5;

    private static final Logger logger = LoggerFactory.getLogger(OeuvreServiceImpl.class);

    @Autowired
    OeuvreService oeuvreService;

    @Test
    void getAllOeuvres_doit_retourner_toutes_les_oeuvres_d_un_utilisateur() {
        OeuvreLightListDto oeuvreLightListDto = oeuvreService.getAllOeuvres();
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

        //je dois recuperer 2 series de ma base test
        Map<String, String> criteresHttp = new HashMap<>();
        OeuvreLightListDto oeuvreLightDto;

        criteresHttp.put("type","serie");
        oeuvreLightDto= oeuvreService.getOeuvres(criteresHttp);

        assertNotNull(oeuvreLightDto);
        assertNotNull(oeuvreLightDto.getOeuvres());
        assertEquals(2,oeuvreLightDto.getOeuvres().size());

        //je dois avoir 3 oeuvres d'action
        criteresHttp = new HashMap<>();
        criteresHttp.put("genre","1");
        oeuvreLightDto= oeuvreService.getOeuvres(criteresHttp);
        assertNotNull(oeuvreLightDto);
        assertNotNull(oeuvreLightDto.getOeuvres());
        assertEquals(3,oeuvreLightDto.getOeuvres().size());

        //je dois avoir 1 serie comedie qui est friends
        criteresHttp = new HashMap<>();
        criteresHttp.put("type","serie");
        criteresHttp.put("genre","2");
        oeuvreLightDto= oeuvreService.getOeuvres(criteresHttp);
        assertNotNull(oeuvreLightDto);
        assertNotNull(oeuvreLightDto.getOeuvres());
        assertEquals(1,oeuvreLightDto.getOeuvres().size());
        assertEquals("friends",oeuvreLightDto.getOeuvres().get(0).getTitre());

        //je dois avoir 2 oeuvres vues
        criteresHttp = new HashMap<>();
        criteresHttp.put("statut","3");
        oeuvreLightDto= oeuvreService.getOeuvres(criteresHttp);
        assertNotNull(oeuvreLightDto);
        assertNotNull(oeuvreLightDto.getOeuvres());
        assertEquals(2,oeuvreLightDto.getOeuvres().size());


    }

    @Test
    void getOeuvres_mauvais_critere_doit_etre_ignore_et_on_retourne_toutes_les_oeuvres() {
        logger.debug("getOeuvres_mauvais_critere_doit_etre_ignore_et_on_retourne_toutes_les_oeuvres");
        OeuvreLightListDto oeuvreLightListDto=oeuvreService.getAllOeuvres();
        logger.debug("oeuvreLightListDto:" +oeuvreLightListDto);
        oeuvreService.getAllOeuvres().getOeuvres().forEach(System.out::println);

        //test mauvais critere de recherche
        Map<String, String> criteresHttp = new HashMap<>();
        criteresHttp.put("sta","shAzA");
        assertEquals(NB_OEUVRE_TOTAL_EN_BDD,oeuvreService.getOeuvres(criteresHttp).getOeuvres().size());

        criteresHttp = new HashMap<>();
        criteresHttp.put("statut",null);
        assertEquals(NB_OEUVRE_TOTAL_EN_BDD,oeuvreService.getOeuvres(criteresHttp).getOeuvres().size());

        criteresHttp = new HashMap<>();
        criteresHttp.put("statut","");
        assertEquals(NB_OEUVRE_TOTAL_EN_BDD,oeuvreService.getOeuvres(criteresHttp).getOeuvres().size());

        criteresHttp = new HashMap<>();
        criteresHttp.put("statut"," ");
        assertEquals(NB_OEUVRE_TOTAL_EN_BDD,oeuvreService.getOeuvres(criteresHttp).getOeuvres().size());

        //test avec 2 critères
        criteresHttp = new HashMap<>();
        criteresHttp.put("statut"," ");
        criteresHttp.put("testMauvaisCritere","aez");
        assertEquals(NB_OEUVRE_TOTAL_EN_BDD,oeuvreService.getOeuvres(criteresHttp).getOeuvres().size());


    }

    @Test
    void getOeuvres_test_critere_titre_qui_doit_me_retourner_film_shazam() {
        Map<String, String> criteresHttp ;
        OeuvreLightListDto oeuvreLightListDto;

        //test titre complet
        criteresHttp = new HashMap<>();
        criteresHttp.put("titre","Shazam!");
        oeuvreLightListDto= oeuvreService.getOeuvres(criteresHttp);
        assertNotNull(oeuvreLightListDto);
        assertNotNull(oeuvreLightListDto.getOeuvres());
        assertEquals(1,oeuvreLightListDto.getOeuvres().size());
        assertEquals("Shazam!",oeuvreLightListDto.getOeuvres().get(0).getTitre());


        //test recherche partielle
        criteresHttp = new HashMap<>();
        criteresHttp.put("titre","Sh");
        oeuvreLightListDto= oeuvreService.getOeuvres(criteresHttp);
        assertNotNull(oeuvreLightListDto);
        assertNotNull(oeuvreLightListDto.getOeuvres());
        assertEquals(1,oeuvreLightListDto.getOeuvres().size());
        assertEquals("Shazam!",oeuvreLightListDto.getOeuvres().get(0).getTitre());

        //test recherche casse differente. On doit retourner l'oeuvre sans tenir compte de la casse
        criteresHttp = new HashMap<>();
        criteresHttp.put("titre","shAzA");
        oeuvreLightListDto= oeuvreService.getOeuvres(criteresHttp);
        assertNotNull(oeuvreLightListDto);
        assertNotNull(oeuvreLightListDto.getOeuvres());
        assertEquals(1,oeuvreLightListDto.getOeuvres().size());
        assertEquals("Shazam!",oeuvreLightListDto.getOeuvres().get(0).getTitre());


    }

    @Test
    void getOeuvreCompleteById_doit_retourner_une_oeuvre_pour_un_id_donné() {
        OeuvreDto oeuvreTest= oeuvreService.getOeuvreCompleteById(1L);
        assertEquals("Shazam!", oeuvreTest.getTitre());
    }

    @Test
    void getOeuvreCompleteById_doit_tester_la_duree_du_film_shazam() {
        OeuvreDto oeuvreTest= oeuvreService.getOeuvreCompleteById(1L);
        assertEquals(166, oeuvreTest.getDuree());
    }

    @Test
    void getOeuvreCompleteById_doit_tester_que_la_serie_friends_a_une_saisonList() {

        OeuvreDto oeuvreTest= oeuvreService.getOeuvreCompleteById(3L);
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


    private OeuvreDto createOeuvreDtoMinimal(String typeOeuvre,String titreFilm) {
        return new OeuvreDto(null,typeOeuvre, titreFilm,
                null,null,null,null,null,null,null,null,null);
    }

    private OeuvreDto createOeuvreDtoComplete(String typeOeuvre,String titreFilm) {
        OeuvreDto oeuvreDto=createOeuvreDtoMinimal(typeOeuvre,titreFilm);
        List<GenreDto> genreDtoList = new ArrayList<>();
        genreDtoList.add(new GenreDto(1L,"Action"));
        genreDtoList.add(new GenreDto(2L,"Comédie"));
        oeuvreDto.setGenres(genreDtoList);
        StatutVisionnageDto statutVisionnageDto= new StatutVisionnageDto(1L,"A voir");
        oeuvreDto.setStatutVisionnage(statutVisionnageDto);
        oeuvreDto.setNote(2);
        oeuvreDto.setCreateurs("createur1");
        oeuvreDto.setActeurs("Acteur1, Acteur2");
        oeuvreDto.setUrlAffiche("monAfficheTest");
        oeuvreDto.setUrlBandeAnnonce("maBOTest");

        if (typeOeuvre.equals(EnumTypeOeuvre.FILM.getLibelle())) {
            oeuvreDto.setDuree(120);
        } else {
            //on ajoute les saisons (on reutilise le statut visionnage de l'oeuvre pour les saisons)
            List<SaisonDto> saisonDtoList = new ArrayList<>();
            saisonDtoList.add(new SaisonDto ( null ,"S01_test",oeuvreDto.getStatutVisionnage(),5));
            saisonDtoList.add(new SaisonDto(null,"S02_test",oeuvreDto.getStatutVisionnage(),5));
            oeuvreDto.setSaisons(saisonDtoList);
        }

        return oeuvreDto;
    }


    @Test
    void createOeuvre_la_creation_du_film_et_de_la_serie_doit_fonctionner_avec_les_champs_minimum() {
        final String titreFilm= "film de test";
        final String titreSerie= "série de test";

        //creation d'un film
        OeuvreDto newFilm=createOeuvreDtoMinimal(EnumTypeOeuvre.FILM.getLibelle(), titreFilm);

        Long idFilmCree = oeuvreService.saveOeuvre(newFilm);

        assertNotNull(idFilmCree);
        assertTrue(idFilmCree>0);

        //on verifie que le film insere est correcte en le rechargant
        OeuvreDto filmInsere=oeuvreService.getOeuvreCompleteById(idFilmCree);
        assertNotNull(filmInsere);
        assertEquals(EnumTypeOeuvre.FILM.getLibelle(),filmInsere.getTypeOeuvre());
        assertEquals(titreFilm,filmInsere.getTitre());

        //on supprime le film de la base test
        oeuvreService.deleteOeuvre(filmInsere.getId());


        //creation d'une serie
        OeuvreDto newSerie=createOeuvreDtoMinimal(EnumTypeOeuvre.SERIE.getLibelle(), titreSerie);

        Long idSerieCree = oeuvreService.saveOeuvre(newSerie);

        assertNotNull(idSerieCree);
        assertTrue(idSerieCree>0);

        OeuvreDto serieInseree=oeuvreService.getOeuvreCompleteById(idSerieCree);
        assertNotNull(serieInseree);
        assertEquals(EnumTypeOeuvre.SERIE.getLibelle(),serieInseree.getTypeOeuvre());
        assertEquals(titreSerie,serieInseree.getTitre());

        oeuvreService.deleteOeuvre(serieInseree.getId());

    }


    @Test
    void createOeuvre_la_creation_avec_un_statut_visionnage_doit_fonctionner() {
        final String titreFilm= "film de test";

        OeuvreDto newFilm=createOeuvreDtoMinimal(EnumTypeOeuvre.FILM.getLibelle(), titreFilm);

        StatutVisionnageDto statutVisionnage= new StatutVisionnageDto(1L,null);
        newFilm.setStatutVisionnage(statutVisionnage);

        Long idFilmCree = oeuvreService.saveOeuvre(newFilm);

        assertNotNull(idFilmCree);
        assertTrue(idFilmCree>0);

        OeuvreDto oeuvreDtoInseree=oeuvreService.getOeuvreCompleteById(idFilmCree);
        assertNotNull(oeuvreDtoInseree);
        assertEquals(EnumTypeOeuvre.FILM.getLibelle(),oeuvreDtoInseree.getTypeOeuvre());
        assertEquals(1L,oeuvreDtoInseree.getStatutVisionnage().getId());

        oeuvreService.deleteOeuvre(oeuvreDtoInseree.getId());
    }

    @Test
    void createOeuvre_la_creation_avec_un_ou_plusieurs_genre_doit_fonctionner() {
        final String titreFilm= "film de test";

        OeuvreDto newFilm=createOeuvreDtoMinimal(EnumTypeOeuvre.FILM.getLibelle(), titreFilm);

        List<GenreDto> genreDtoList = new ArrayList<>();
        genreDtoList.add(new GenreDto(1L,null));
        genreDtoList.add(new GenreDto(2L,null));
        newFilm.setGenres(genreDtoList);

        Long idFilmCree = oeuvreService.saveOeuvre(newFilm);

        assertNotNull(idFilmCree);
        assertTrue(idFilmCree>0);

        OeuvreDto oeuvreDtoInseree=oeuvreService.getOeuvreCompleteById(idFilmCree);
        assertNotNull(oeuvreDtoInseree);
        assertEquals(EnumTypeOeuvre.FILM.getLibelle(),oeuvreDtoInseree.getTypeOeuvre());
        assertEquals(2,oeuvreDtoInseree.getGenres().size());

        System.out.println(oeuvreDtoInseree);

        oeuvreService.deleteOeuvre(oeuvreDtoInseree.getId());
    }

    @Test
    void createOeuvre_testCreation_film() {
        //creation du film
        final String titreFilm= "film de test";
        OeuvreDto newFilm=createOeuvreDtoComplete(EnumTypeOeuvre.FILM.getLibelle(), titreFilm);
        newFilm.setDuree(155);
        //fin creation du film

        Long idFilmCree = oeuvreService.saveOeuvre(newFilm);

        assertNotNull(idFilmCree);
        assertTrue(idFilmCree>0);

        OeuvreDto oeuvreDtoInseree=oeuvreService.getOeuvreCompleteById(idFilmCree);
        assertNotNull(oeuvreDtoInseree);

        assertEquals(EnumTypeOeuvre.FILM.getLibelle(),oeuvreDtoInseree.getTypeOeuvre());
        assertEquals(newFilm.getTitre(),oeuvreDtoInseree.getTitre());
        assertEquals(newFilm.getGenres().size(),oeuvreDtoInseree.getGenres().size());
        assertEquals(newFilm.getStatutVisionnage().getLibelle(),oeuvreDtoInseree.getStatutVisionnage().getLibelle());
        assertEquals(newFilm.getNote(),oeuvreDtoInseree.getNote());
        assertEquals(newFilm.getCreateurs(),oeuvreDtoInseree.getCreateurs());
        assertEquals(newFilm.getActeurs(),oeuvreDtoInseree.getActeurs());
        assertEquals(newFilm.getUrlAffiche(),oeuvreDtoInseree.getUrlAffiche());
        assertEquals(newFilm.getUrlBandeAnnonce(),oeuvreDtoInseree.getUrlBandeAnnonce());
        assertEquals(newFilm.getDuree(),oeuvreDtoInseree.getDuree());

        oeuvreService.deleteOeuvre(oeuvreDtoInseree.getId());
    }
    @Test
    void createOeuvre_testCreation_serie() {

        //creation de la serie
        final String titreOeuvre= "serie de test";
        OeuvreDto newSerie =createOeuvreDtoComplete(EnumTypeOeuvre.SERIE.getLibelle(), titreOeuvre);
        //fin de la creation de la serie

        logger.debug("Debut insertion Serie test. Titre= {}",newSerie.getTitre());
        logger.debug("Detail de la serie: {}",newSerie);
        Long idSerieCree = oeuvreService.saveOeuvre(newSerie);

        assertNotNull(idSerieCree);
        assertTrue(idSerieCree>0);

        OeuvreDto oeuvreDtoInseree=oeuvreService.getOeuvreCompleteById(idSerieCree);
        assertNotNull(oeuvreDtoInseree);

        assertEquals(EnumTypeOeuvre.SERIE.getLibelle(),oeuvreDtoInseree.getTypeOeuvre());
        assertEquals(newSerie.getTitre(),oeuvreDtoInseree.getTitre());
        assertEquals(newSerie.getGenres().size(),oeuvreDtoInseree.getGenres().size());
        assertEquals(newSerie.getStatutVisionnage().getLibelle(),oeuvreDtoInseree.getStatutVisionnage().getLibelle());
        assertEquals(newSerie.getNote(),oeuvreDtoInseree.getNote());
        assertEquals(newSerie.getCreateurs(),oeuvreDtoInseree.getCreateurs());
        assertEquals(newSerie.getActeurs(),oeuvreDtoInseree.getActeurs());
        assertEquals(newSerie.getUrlAffiche(),oeuvreDtoInseree.getUrlAffiche());
        assertEquals(newSerie.getUrlBandeAnnonce(),oeuvreDtoInseree.getUrlBandeAnnonce());
        assertEquals(newSerie.getSaisons().size(),oeuvreDtoInseree.getSaisons().size());

        oeuvreService.deleteOeuvre(oeuvreDtoInseree.getId());

    }

    @Test
    void createOeuvre_modification_dune_saison_doit_fonctionner() {
        //je recupere une serie existante
        //friends
        Long oeuvreId=3L;

        OeuvreDto oeuvreFriends= oeuvreService.getOeuvreCompleteById(oeuvreId);

        //je modifie sa saison S2 en mettant nb episode = 99
        for (SaisonDto saison: oeuvreFriends.getSaisons()) {
            if (saison.getNumero().equals("S2")) {
                assertEquals(10,saison.getNbEpisodes());
                saison.setNbEpisodes(99);
            }
        }

        //je sauve
        Long id=oeuvreService.saveOeuvre(oeuvreFriends);
        //on check que l'id est le meme
        assertEquals(oeuvreId,id);

        //on verifie les données en base que la saison fait bien 99 episode maintenant
        oeuvreService.getOeuvreCompleteById(oeuvreId);
        for (SaisonDto saison: oeuvreFriends.getSaisons()) {
            if (saison.getNumero().equals("S2")) {
                assertEquals(99,saison.getNbEpisodes());
            }
        }

        //on vérifie que si on essaye de changer le type d'une oeuvre existante, on a une exception
        assertThrows(MauvaisParamException.class, () -> {
            oeuvreFriends.setTypeOeuvre(EnumTypeOeuvre.FILM.getLibelle());
            oeuvreService.saveOeuvre(oeuvreFriends);
        });


    }

    @Test
    void convertirOeuvreDtoEnOeuvre_doit_convertir_un_OeuvreDto_en_Oeuvre() {

        //on check pour 1 film
        OeuvreDto oeuvreDto = createOeuvreDtoComplete(EnumTypeOeuvre.FILM.getLibelle(), "film test");
        assertNull(oeuvreService.convertirOeuvreDtoEnOeuvre(null));

        Oeuvre oeuvre=oeuvreService.convertirOeuvreDtoEnOeuvre(oeuvreDto);
        assertTrue(oeuvre instanceof Film);
        Film film=(Film) oeuvre;
        assertEquals(oeuvreDto.getTitre(),film.getTitre());
        assertEquals(oeuvreDto.getDuree(),film.getDuree());
        assertEquals(oeuvreDto.getStatutVisionnage().getLibelle(),film.getStatutVisionnage().getLibelle());
        assertEquals(oeuvreDto.getGenres().size(),film.getGenres().size());

        //on check pour 1 serie
        oeuvreDto = createOeuvreDtoComplete(EnumTypeOeuvre.SERIE.getLibelle(), "serie test");
        assertNull(oeuvreService.convertirOeuvreDtoEnOeuvre(null));

        oeuvre=oeuvreService.convertirOeuvreDtoEnOeuvre(oeuvreDto);
        assertTrue(oeuvre instanceof Serie);
        Serie serie=(Serie) oeuvre;
        assertEquals(oeuvreDto.getTitre(),serie.getTitre());
        assertNotNull(serie.getSaisons());
        assertEquals(oeuvreDto.getSaisons().size(),serie.getSaisons().size());
        assertEquals(oeuvreDto.getStatutVisionnage().getLibelle(),serie.getStatutVisionnage().getLibelle());
        assertEquals(oeuvreDto.getGenres().size(),serie.getGenres().size());


    }

}
