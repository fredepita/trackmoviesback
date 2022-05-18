package fr.epita.trackmoviesback.application;


import fr.epita.trackmoviesback.domaine.*;
import fr.epita.trackmoviesback.dto.*;
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

    @Autowired
    OeuvreService oeuvreService;

    @Test
    void getAllOeuvres_doit_retourner_toutes_les_oeuvres_d_un_utilisateur() {
        OeuvreLightListDto oeuvreLightListDto = oeuvreService.getAllOeuvres();
        List<OeuvreLightDto> oeuvreLightDtoList = oeuvreLightListDto.getOeuvres();

        assertEquals(4, oeuvreLightDtoList.size());

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
        //test mauvais critere de recherche
        Map<String, String> criteresHttp = new HashMap<>();
        criteresHttp.put("sta","shAzA");
        assertEquals(4,oeuvreService.getOeuvres(criteresHttp).getOeuvres().size());

        criteresHttp = new HashMap<>();
        criteresHttp.put("statut",null);
        assertEquals(4,oeuvreService.getOeuvres(criteresHttp).getOeuvres().size());

        criteresHttp = new HashMap<>();
        criteresHttp.put("statut","");
        assertEquals(4,oeuvreService.getOeuvres(criteresHttp).getOeuvres().size());

        criteresHttp = new HashMap<>();
        criteresHttp.put("statut"," ");
        assertEquals(4,oeuvreService.getOeuvres(criteresHttp).getOeuvres().size());

        //test avec 2 critères
        criteresHttp = new HashMap<>();
        criteresHttp.put("statut"," ");
        criteresHttp.put("testMauvaisCritere","aez");
        assertEquals(4,oeuvreService.getOeuvres(criteresHttp).getOeuvres().size());


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
        //Création d'un statut de visionnage
        StatutVisionnageDto statutVu = new StatutVisionnageDto(2L,"En cours");
        //Création d'une saison
        SaisonDto saisonTest = new SaisonDto(1L,"S1", statutVu,1);

        OeuvreDto oeuvreTest= oeuvreService.getOeuvreCompleteById(3L);
        assertEquals(2, oeuvreTest.getSaisons().size());
        assertEquals(saisonTest.getId(), oeuvreTest.getSaisons().get(0).getId());
        assertEquals(saisonTest.getNumero(), oeuvreTest.getSaisons().get(0).getNumero());
        assertEquals(saisonTest.getStatutVisionnage().getLibelle(), oeuvreTest.getSaisons().get(0).getStatutVisionnage().getLibelle());
        assertEquals(saisonTest.getNbEpisodes(), oeuvreTest.getSaisons().get(0).getNbEpisodes());
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

}


