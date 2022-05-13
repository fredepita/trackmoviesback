package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.*;
import fr.epita.trackmoviesback.dto.GenreDto;
import fr.epita.trackmoviesback.dto.GenreListDto;
import fr.epita.trackmoviesback.dto.OeuvreLightDto;
import fr.epita.trackmoviesback.dto.OeuvreLightListDto;
import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class GenreServiceImplTest {

    @Autowired
    GenreService genreService;

    @Test
    void getAllGenres_doit_retourner_toutes_les_oeuvres_d_un_utilisateur() {
        GenreListDto genreListDto = genreService.getAllGenres();
        List<GenreDto> genreDtoList = genreListDto.getGenres();

        assertEquals(genreDtoList.size(), 4);

        boolean foundComedie = false;
        for (GenreDto genre : genreDtoList) {
            if (genre.getLibelle().equals("Comédie")) {
                foundComedie = true;
                break;
            }
        }
        assertTrue(foundComedie);

    }

    @Test
    void convertirGenreEnDto_doit_convertir_un_genre_en_dto() {
        //Création d'un Genre
        Genre genreTest = new Genre();
        genreTest.setId(1L);
        genreTest.setLibelle("comédie");
        //création d'un genreDto à comparer avec notre genreTest
        GenreDto genreDto = genreService.convertirGenreEnDto(genreTest);

        assertEquals(genreDto.getLibelle(), genreTest.getLibelle());

    }

    @Test
    void convertirListGenreEnDto_doit_convertir_et_collecter_une_ListGenre_en_dto() {
        //Création d'une liste de Genres
        Genre genreComedie = new Genre();
        genreComedie.setId(1L);
        genreComedie.setLibelle("comédie");
        List<Genre> listeGenreTest = new ArrayList<>();
        listeGenreTest.add(genreComedie);

        //création d'une ListGenreDto à comparer avec notre listeGenreTest
        List<GenreDto> listGenreDto = genreService.convertirListGenreEnDto(listeGenreTest);
        //Vérification sur la taille de la liste
        assertEquals(listGenreDto.size(), listeGenreTest.size());

    }

}
