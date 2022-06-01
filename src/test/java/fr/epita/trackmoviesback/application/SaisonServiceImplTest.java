package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Genre;
import fr.epita.trackmoviesback.domaine.Saison;
import fr.epita.trackmoviesback.domaine.StatutVisionnage;
import fr.epita.trackmoviesback.dto.GenreDto;
import fr.epita.trackmoviesback.dto.SaisonDto;
import fr.epita.trackmoviesback.dto.StatutVisionnageDto;
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

    @Test
    void convertirSaisonDtoEnSaison_doit_convertir_SaisonDto_en_Saison() {
        //Création d'un statut de visionnage
        StatutVisionnageDto statutVu = new StatutVisionnageDto(3L,"Vu");
        //Création d'une Saison
        SaisonDto saisonDto = new SaisonDto(1L,"S1",statutVu,10);

        assertNull(saisonService.convertirSaisonDtoEnSaison(null));
        Saison saison=saisonService.convertirSaisonDtoEnSaison(saisonDto);
        assertNotNull(saison);
        assertEquals(saisonDto.getId(),saison.getId());
        assertEquals(saisonDto.getNumero(),saison.getNumero());
        assertNotNull(saison.getStatutVisionnage());
        assertEquals(statutVu.getId(),saison.getStatutVisionnage().getId());
        assertEquals(statutVu.getLibelle(),saison.getStatutVisionnage().getLibelle());
        assertEquals(saisonDto.getNbEpisodes(),saison.getNbEpisodes());
    }

    @Test
    void convertirListSaisonDtoEnListSaison_doit_convertir_ListSaisonDto_en_ListSaison() {
        //Création d'un statut de visionnage
        StatutVisionnageDto statutVu = new StatutVisionnageDto(3L,"Vu");
        //Création d'une Saison
        SaisonDto saisonDto = new SaisonDto(1L,"S1",statutVu,10);
        List<SaisonDto> saisonDtoList =  new ArrayList<>();
        saisonDtoList.add(saisonDto);

        assertNull(saisonService.convertirListSaisonDtoEnListSaison(null));
        assertEquals(0,saisonService.convertirListSaisonDtoEnListSaison(new ArrayList<SaisonDto>()).size());
        assertEquals(1,saisonService.convertirListSaisonDtoEnListSaison(saisonDtoList).size());

    }
}