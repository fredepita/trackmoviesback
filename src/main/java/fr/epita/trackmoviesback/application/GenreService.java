package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Genre;
import fr.epita.trackmoviesback.dto.GenreDto;
import fr.epita.trackmoviesback.dto.GenreListDto;

import java.util.List;

public interface GenreService {

    GenreListDto getAllGenres();

    GenreDto convertirGenreEnDto(Genre genre);
    List<GenreDto> convertirListGenreEnDto(List<Genre> genres);

    Genre convertirGenreDtoEnGenre(GenreDto genreDto);

    List<Genre> convertirListGenreDtoEnListGenre(List<GenreDto> genreDtoList);

    List<Genre> convertirListIdsEnListGenre(List<Long> idList);
}
