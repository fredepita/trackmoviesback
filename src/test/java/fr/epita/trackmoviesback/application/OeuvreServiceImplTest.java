package fr.epita.trackmoviesback.application;


import fr.epita.trackmoviesback.domaine.*;
import fr.epita.trackmoviesback.dto.OeuvreLightDto;
import fr.epita.trackmoviesback.dto.OeuvreLightListDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OeuvreServiceImplTest {

    @Autowired
    OeuvreService oeuvreService;

    @Autowired
    StatutVisionnageService statutVisionnageService;


    @Test
    void getAllOeuvres_doit_retourner_toutes_les_oeuvres_d_un_utilisateur() {
        OeuvreLightListDto oeuvreLightListDto = oeuvreService.getAllOeuvres();
        List<OeuvreLightDto> oeuvreLightDtoList = oeuvreLightListDto.getOeuvres();

        assertEquals(oeuvreLightDtoList.size(), 4);

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
        genreComedie.setLibelle("Comédie");
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
        System.out.println("getOeuvres_mauvais_critere_doit_etre_ignore_et_on_retourne_toutes_les_oeuvres");
        OeuvreLightListDto oeuvreLightListDto=oeuvreService.getAllOeuvres();
        System.out.println("oeuvreLightListDto:" +oeuvreLightListDto);
        oeuvreService.getAllOeuvres().getOeuvres().forEach(System.out::println);

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
    void createOeuvre_la_creation_du_film_et_de_la_serie_doit_fonctionner_avec_les_champs_minimum() {
        final String titreFilm= "film de test";
        final String titreSerie= "série de test";


        Film newFilm= new Film();
        newFilm.setTitre(titreFilm);
        Long idFilmCree = oeuvreService.createOeuvre(newFilm);

        assertNotNull(idFilmCree);
        assertTrue(idFilmCree>0);

        Oeuvre filmInsere=oeuvreService.getOeuvre(idFilmCree);
        assertNotNull(filmInsere);
        assertInstanceOf(Film.class,filmInsere);
        assertEquals(titreFilm,filmInsere.getTitre());

        oeuvreService.deleteOeuvre(filmInsere.getId());

        Serie newSerie= new Serie();
        newSerie.setTitre(titreSerie);
        Long idSerieCree = oeuvreService.createOeuvre(newSerie);

        assertNotNull(idSerieCree);
        assertTrue(idSerieCree>0);

        Oeuvre serieInseree=oeuvreService.getOeuvre(idSerieCree);
        assertNotNull(serieInseree);
        assertInstanceOf(Serie.class,serieInseree);
        assertEquals(titreSerie,serieInseree.getTitre());

        oeuvreService.deleteOeuvre(serieInseree.getId());

    }
/*
    @Test
    void createOeuvre_la_creation_avec_un_statut_visionnage_doit_fonctionner() {
        final String titreFilm= "film de test2";

        Film newFilm= new Film();
        newFilm.setTitre(titreFilm);


        StatutVisionnage statutVisionnage= new StatutVisionnage(1L,null);
        newFilm.setStatutVisionnage(statutVisionnage);

        Long idFilmCree = oeuvreService.createOeuvre(newFilm);

        assertNotNull(idFilmCree);
        assertTrue(idFilmCree>0);

        Oeuvre oeuvreInseree=oeuvreService.getOeuvre(idFilmCree);
        assertNotNull(oeuvreInseree);
        assertInstanceOf(Film.class,oeuvreInseree);
        assertEquals(1L,oeuvreInseree.getStatutVisionnage().getId());

    }

    @Test
    void createOeuvre_la_creation_avec_un_ou_plusieurs_genre_doit_fonctionner() {
        final String titreFilm= "film de test3";

        Film newFilm= new Film();
        newFilm.setTitre(titreFilm);

        List<Genre> genreList = new ArrayList<>();
        genreList.add(new Genre(1L,null));
        genreList.add(new Genre(2L,null));
        newFilm.setGenres(genreList);

        Long idFilmCree = oeuvreService.createOeuvre(newFilm);

        assertNotNull(idFilmCree);
        assertTrue(idFilmCree>0);

        Oeuvre oeuvreInseree=oeuvreService.getOeuvre(idFilmCree);
        assertNotNull(oeuvreInseree);
        assertInstanceOf(Film.class,oeuvreInseree);
        assertEquals(1L,oeuvreInseree.getGenres().get(0).getId());
    }

    @Test
    void createOeuvre_testCreation_film() {

    }

    @Test
    void createOeuvre_testCreation_serie() {

    }

    @Test
    void createOeuvre_testCreation_echec_contrainte() {
        //une oeuvre doit avoir un titre
        //autre contrainte?

    }
*/

}

