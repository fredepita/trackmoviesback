package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Genre;
import fr.epita.trackmoviesback.domaine.Saison;
import fr.epita.trackmoviesback.domaine.StatutVisionnage;
import fr.epita.trackmoviesback.dto.GenreDto;
import fr.epita.trackmoviesback.dto.SaisonDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SaisonServiceImplTest {

    @Autowired
    SaisonService saisonService;

    @Test
    void convertirSaisonEnDto_doit_convertir_une_saison_en_dto() {
        //Création d'un statut de visionnage
        StatutVisionnage statutVu = new StatutVisionnage();
        statutVu.setId(3L);
        statutVu.setLibelle("Vu");
        //Création d'une Saison
        Saison saisonTest = new Saison();
        saisonTest.setId(1L);
        saisonTest.setNumero("S1");
        saisonTest.setStatutVisionnage(statutVu);
        saisonTest.setNbEpisodes(10);
        //création d'une saisonDto à comparer avec notre saisonTest
        SaisonDto saisonDto = saisonService.convertirSaisonEnDto(saisonTest);

        assertEquals(saisonDto.getNumero(), saisonTest.getNumero());
        assertEquals(saisonDto.getStatutVisionnage().getLibelle(), saisonTest.getStatutVisionnage().getLibelle());
        assertEquals(saisonDto.getNbEpisodes(), saisonTest.getNbEpisodes());

        saisonDto = saisonService.convertirSaisonEnDto(null);
        assertNull(saisonDto);
    }

    @Test
    void convertirListSaisonEnDto_doit_convertir_et_collecter_une_ListeSaison_en_dto() {
        //Création d'une liste de Genres
        //Création d'un statut de visionnage
        StatutVisionnage statutVu = new StatutVisionnage();
        statutVu.setId(3L);
        statutVu.setLibelle("Vu");
        //Création d'une Saison
        Saison saisonTest = new Saison();
        saisonTest.setId(1L);
        saisonTest.setNumero("S1");
        saisonTest.setStatutVisionnage(statutVu);
        saisonTest.setNbEpisodes(10);
        List<Saison> saisonListTest = new ArrayList<>();
        saisonListTest.add(saisonTest);

        //création d'une ListGenreDto à comparer avec notre listeGenreTest
        List<SaisonDto> saisonListDto = saisonService.convertirListSaisonEnDto(saisonListTest);
        //Vérification sur la taille de la liste
        assertEquals(saisonListDto.size(), saisonListTest.size());

        saisonListDto = saisonService.convertirListSaisonEnDto(null);
        assertNull(saisonListDto);
    }
}