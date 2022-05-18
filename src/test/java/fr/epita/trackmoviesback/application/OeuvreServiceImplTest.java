package fr.epita.trackmoviesback.application;


import fr.epita.trackmoviesback.domaine.*;
import fr.epita.trackmoviesback.dto.OeuvreDto;
import fr.epita.trackmoviesback.dto.OeuvreLightDto;
import fr.epita.trackmoviesback.dto.OeuvreLightListDto;
import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;
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
        System.out.println("getOeuvres_mauvais_critere_doit_etre_ignore_et_on_retourne_toutes_les_oeuvres");
        OeuvreLightListDto oeuvreLightListDto=oeuvreService.getAllOeuvres();
        System.out.println("oeuvreLightListDto:" +oeuvreLightListDto);
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
        assertEquals(oeuvreTest.getTitre(), "Shazam!");
    }

    @Test
    void getOeuvreCompleteById_doit_tester_la_duree_du_film_shazam() {
        OeuvreDto oeuvreTest= oeuvreService.getOeuvreCompleteById(1L);
        assertEquals(oeuvreTest.getDuree(), 166);
    }

    @Test
    void createOeuvre_la_creation_du_film_et_de_la_serie_doit_fonctionner_avec_les_champs_minimum() {
        final String titreFilm= "film de test";
        final String titreSerie= "série de test";

        //creation d'un film
        Film newFilm= new Film();
        newFilm.setTitre(titreFilm);
        Long idFilmCree = oeuvreService.createOeuvre(newFilm);

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
        Serie newSerie= new Serie();
        newSerie.setTitre(titreSerie);
        Long idSerieCree = oeuvreService.createOeuvre(newSerie);

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

        Film newFilm= new Film();
        newFilm.setTitre(titreFilm);
        StatutVisionnage statutVisionnage= new StatutVisionnage(1L,null);
        newFilm.setStatutVisionnage(statutVisionnage);

        Long idFilmCree = oeuvreService.createOeuvre(newFilm);

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

        Film newFilm= new Film();
        newFilm.setTitre(titreFilm);

        List<Genre> genreList = new ArrayList<>();
        genreList.add(new Genre(1L,null));
        genreList.add(new Genre(2L,null));
        newFilm.setGenres(genreList);

        Long idFilmCree = oeuvreService.createOeuvre(newFilm);

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

        Film newFilm= new Film();
        newFilm.setTitre(titreFilm);
        List<Genre> genreList = new ArrayList<>();
        genreList.add(new Genre(1L,"Action"));
        genreList.add(new Genre(2L,"Comédie"));
        newFilm.setGenres(genreList);
        StatutVisionnage statutVisionnage= new StatutVisionnage(1L,"A voir");
        newFilm.setStatutVisionnage(statutVisionnage);
        newFilm.setNote(2);
        newFilm.setCreateurs("createur1");
        newFilm.setActeurs("Acteur1, Acteur2");
        newFilm.setUrlAffiche("monAfficheTest");
        newFilm.setUrlBandeAnnonce("maBOTest");
        newFilm.setDuree(155);
        //fin creation du film

        Long idFilmCree = oeuvreService.createOeuvre(newFilm);

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
        final String titreFilm= "serie de test";

        Serie newSerie= new Serie();
        newSerie.setTitre(titreFilm);
        List<Genre> genreList = new ArrayList<>();
        genreList.add(new Genre(1L,"Action"));
        genreList.add(new Genre(2L,"Comédie"));
        newSerie.setGenres(genreList);
        StatutVisionnage statutVisionnage= new StatutVisionnage(1L,"A voir");
        newSerie.setStatutVisionnage(statutVisionnage);
        newSerie.setNote(2);
        newSerie.setCreateurs("createur1");
        newSerie.setActeurs("Acteur1, Acteur2");
        newSerie.setUrlAffiche("monAfficheTest");
        newSerie.setUrlBandeAnnonce("maBOTest");

        List<Saison> saisonList = new ArrayList<>();
        saisonList.add(new Saison(0L,"S01_test",statutVisionnage,5));
        saisonList.add(new Saison(0L,"S02_test",statutVisionnage,5));
        newSerie.setSaisons(saisonList);
        //fin creation de la serie

        Long idSerieCree = oeuvreService.createOeuvre(newSerie);

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
        //assertEquals(newSerie.getSaisons().size(),oeuvreDtoInseree.getSaisons().size());

        oeuvreService.deleteOeuvre(oeuvreDtoInseree.getId());

    }

    @Test
    void createOeuvre_testCreation_echec_contrainte() {
        //une oeuvre doit avoir un titre
        //autre contrainte?

    }

}
