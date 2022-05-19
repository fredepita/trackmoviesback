package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.*;
import fr.epita.trackmoviesback.dto.GenreDto;
import fr.epita.trackmoviesback.dto.GenreListDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GenreServiceImplTest {

    @Autowired
    GenreService genreService;

    @Test
    void getAllGenres_doit_retourner_toutes_les_genres_existants() {
        GenreListDto genreListDto = genreService.getAllGenres();
        List<GenreDto> genreDtoList = genreListDto.getGenres();

        assertEquals(genreDtoList.size(), 4);

        boolean foundComedie = false;
        for (GenreDto genre : genreDtoList) {
            if (genre.getLibelle().equals("Action")) {
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
    void convertirListGenreEnDto_doit_convertir_une_ListGenre_en_dto_et_gerer_null_ou_vide() {
        //Création d'une liste de Genres
        Genre genreComedie = new Genre();
        genreComedie.setId(1L);
        genreComedie.setLibelle("comédie");
        List<Genre> GenreListTest = new ArrayList<>();
        GenreListTest.add(genreComedie);

        //création d'une GenreListDto à comparer avec notre GenrelistTest
        List<GenreDto> genreDtoList = genreService.convertirListGenreEnDto(GenreListTest);
        //Vérification sur la taille de la liste
        assertEquals(genreDtoList.size(), GenreListTest.size());

        genreDtoList = genreService.convertirListGenreEnDto(null);
        assertNull(genreDtoList);

        genreDtoList = genreService.convertirListGenreEnDto(new ArrayList<>());
        assertNotNull(genreDtoList);
        assertEquals(0,genreDtoList.size());
    }

    @Test
    void convertirListGenreDtoEnListGenre_doit_convertir_un_GenreDtoList_en_GenreList() {
        assertNull(genreService.convertirListGenreDtoEnListGenre(null));
    }

}
