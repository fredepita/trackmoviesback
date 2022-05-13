package fr.epita.trackmoviesback.domaine;

import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;
import fr.epita.trackmoviesback.exception.MauvaisParamException;
import fr.epita.trackmoviesback.infrastructure.oeuvre.OeuvreRepository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class OeuvreTest {

    @Autowired
    OeuvreRepository oeuvreRepository;

    @Test
    void setTypeOeuvre_utilise_un_enum_et_doit_verifier_que_le_type_est_non_null() {

        assertThrows(MauvaisParamException.class, () -> {
            Oeuvre oeuvreTest = new Oeuvre();
            oeuvreTest.setTypeOeuvre(null);
        });

        //test de l'Enum Film
        Oeuvre oeuvreTest = new Oeuvre();
        oeuvreTest.setTypeOeuvre(EnumTypeOeuvre.FILM);
        assertEquals("film", oeuvreTest.getTypeOeuvre().getLibelle());
    }

    @Test
    void setTitre_doit_verifier_que_le_titre_est_non_null_et_non_vide() {

        assertThrows(MauvaisParamException.class, () -> {
            Oeuvre oeuvreTest = new Oeuvre();
            oeuvreTest.setTitre(null);
        });

        assertThrows(MauvaisParamException.class, () -> {
            Oeuvre oeuvreTest = new Oeuvre();
            oeuvreTest.setTitre("");
        });

        assertThrows(MauvaisParamException.class, () -> {
            Oeuvre oeuvreTest = new Oeuvre();
            oeuvreTest.setTitre(" ");
        });

        Oeuvre oeuvreTest = new Oeuvre();
        oeuvreTest.setTitre("aa");
        assertEquals("aa", oeuvreTest.getTitre());
    }


    @Test
    void setSaisons_est_possible_que_pour_les_oeuvres_de_type_serie_et_peut_etre_null() {
        //--création d'une liste de saison pour le test--
        //Création d'une liste de Genres
        Genre genreComedie = new Genre();
        genreComedie.setId(1L);
        genreComedie.setLibelle("comédie");
        List<Genre> listeGenre = new ArrayList<>();
        listeGenre.add(genreComedie);
        //Création d'un statut de visionnage
        StatutVisionnage statutVu = new StatutVisionnage();
        statutVu.setId(1L);
        statutVu.setLibelle("vu");
        //Création d'une liste d'épisode
        Episode episode1 = new Episode("1", statutVu, "url...", 112);
        List<Episode> listeEpisode = new ArrayList<>();
        listeEpisode.add(episode1);
        //Création d'une liste de Saisons
        Saison saison1 = new Saison();
        saison1.setId(1L);
        saison1.setEpisodes(listeEpisode);
        saison1.setStatutVisionnage(statutVu);
        saison1.setNote(1);
        saison1.setNumero("1");
        List<Saison> listeSaison1 = new ArrayList<>();
        listeSaison1.add(saison1);


        assertThrows(MauvaisParamException.class, () -> {
            Oeuvre oeuvreTest = new Oeuvre();
            oeuvreTest.setTypeOeuvre(EnumTypeOeuvre.FILM);
            oeuvreTest.setSaisons(listeSaison1);
        });

        //On doit pouvoir affecter une saison vide à une oeuvre
        Oeuvre oeuvreTest = new Oeuvre();
        oeuvreTest.setTypeOeuvre(EnumTypeOeuvre.SERIE);
        assertDoesNotThrow(() -> oeuvreTest.setSaisons(null));

        Oeuvre oeuvreTest2 = new Oeuvre();
        oeuvreTest2.setTypeOeuvre(EnumTypeOeuvre.SERIE);
        oeuvreTest2.setSaisons(listeSaison1);
        assertNotNull(oeuvreTest2.getSaisons());
        assertEquals(listeSaison1.toString(), oeuvreTest2.getSaisons().toString());
    }

    @Test
    void setDuree_est_possible_que_pour_les_oeuvres_de_type_film() {

        assertThrows(MauvaisParamException.class, () -> {
            Oeuvre oeuvreTest = new Oeuvre();
            oeuvreTest.setTypeOeuvre(EnumTypeOeuvre.SERIE);
            oeuvreTest.setDuree(130);
        });

        Oeuvre oeuvreTest = new Oeuvre();
        oeuvreTest.setTypeOeuvre(EnumTypeOeuvre.FILM);
        oeuvreTest.setDuree(100);
        assertEquals(100, oeuvreTest.getDuree());
    }
}