package fr.epita.trackmoviesback.domaine;

import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;
import fr.epita.trackmoviesback.exception.MauvaisParamException;
import fr.epita.trackmoviesback.infrastructure.oeuvre.OeuvreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


class OeuvreTest {

    @Autowired
    OeuvreRepository oeuvreRepository;

    /*@Test(expected = MauvaisParamException.class)
    public void setTitre_doit_verifier_que_le_titre_est_non_null_et_non_vide(){

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
        Episode episode1 = new Episode("1",statutVu,"url...", 112);
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


        Oeuvre oeuvreTest = new Oeuvre(EnumTypeOeuvre.SERIE,"", listeGenre, statutVu, 1, "url...", "url...", listeSaison1, 112);


        //façon plus simple...
        Oeuvre oeuvreTest2 = new Oeuvre();
        oeuvreTest2.setTitre("");
    }
*/
    @Test
    void setTitre_doit_verifier_que_le_titre_est_non_null_et_non_vide2() {

        Assertions.assertThrows(MauvaisParamException.class, () -> {
            Oeuvre oeuvreTest2 = new Oeuvre();
            oeuvreTest2.setTitre("");
        });
    }
}