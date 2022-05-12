package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.*;
import fr.epita.trackmoviesback.dto.OeuvreLightDto;
import fr.epita.trackmoviesback.dto.OeuvreLightListDto;
import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class OeuvreServiceImplTest {

    @Autowired
    OeuvreService oeuvreService;

    @Test
    void getOeuvresWithCriteria() {
        //oeuvre.s
    }

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
    void convertirOeuvreEnDto_doit_convertir_une_oeuvre_en_dto() {
        //Création d'une oeuvreTest
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

        Oeuvre oeuvreTest = new Oeuvre();
        oeuvreTest.setTypeOeuvre(EnumTypeOeuvre.SERIE);
        oeuvreTest.setTitre("titanic");
        oeuvreTest.setGenres(listeGenre);
        oeuvreTest.setStatutVisionnage(statutVu);
        oeuvreTest.setNote(1);
        oeuvreTest.setUrlAffiche("url...");
        oeuvreTest.setUrlBandeAnnonce("url...");
        oeuvreTest.setSaisons(listeSaison1);

        assertEquals(oeuvreService.convertirOeuvreEnDto(oeuvreTest).getId(), oeuvreTest.getId());
        assertEquals(oeuvreService.convertirOeuvreEnDto(oeuvreTest).getTypeOeuvre(), oeuvreTest.getTypeOeuvre().getLibelle());
        assertEquals(oeuvreService.convertirOeuvreEnDto(oeuvreTest).getTitre(), oeuvreTest.getTitre());
        //Parler de get[0] à Fabien
        assertEquals(oeuvreService.convertirOeuvreEnDto(oeuvreTest).getGenres().get(0).getLibelle(), oeuvreTest.getGenres().get(0).getLibelle());
        //Parler de getStatut-getStatutVisionnage à Fabien
        assertEquals(oeuvreService.convertirOeuvreEnDto(oeuvreTest).getStatut().getLibelle(), oeuvreTest.getStatutVisionnage().getLibelle());
        assertEquals(oeuvreService.convertirOeuvreEnDto(oeuvreTest).getNote(), oeuvreTest.getNote());
        assertEquals(oeuvreService.convertirOeuvreEnDto(oeuvreTest).getUrlAffiche(), oeuvreTest.getUrlAffiche());
        assertEquals(oeuvreService.convertirOeuvreEnDto(oeuvreTest).getUrlBandeAnnonce(), oeuvreTest.getUrlBandeAnnonce());
        assertEquals(oeuvreService.convertirOeuvreEnDto(oeuvreTest).getDuree(), oeuvreTest.getDuree());
    }
}
