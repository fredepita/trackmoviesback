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
            Oeuvre oeuvreTest = new Film();
            oeuvreTest.setTypeOeuvre(null);
        });

        //test de l'Enum Film
        Oeuvre oeuvreTest = new Film();
        assertEquals("film", oeuvreTest.getTypeOeuvre().getLibelle());
    }

    @Test
    void setTitre_doit_verifier_que_le_titre_est_non_null_et_non_vide() {

        assertThrows(MauvaisParamException.class, () -> {
            Oeuvre oeuvreTest = new Film();
            oeuvreTest.setTitre(null);
        });

        assertThrows(MauvaisParamException.class, () -> {
            Oeuvre oeuvreTest = new Film();
            oeuvreTest.setTitre("");
        });

        assertThrows(MauvaisParamException.class, () -> {
            Oeuvre oeuvreTest = new Film();
            oeuvreTest.setTitre(" ");
        });

        Oeuvre oeuvreTest = new Film();
        oeuvreTest.setTitre("aa");
        assertEquals("aa", oeuvreTest.getTitre());
    }



}