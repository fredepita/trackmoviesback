package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Genre;
import fr.epita.trackmoviesback.dto.GenreDto;
import fr.epita.trackmoviesback.dto.GenreListDto;
import fr.epita.trackmoviesback.infrastructure.genre.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    GenreRepository genreRepository;

    @Override
    public GenreListDto getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        List<GenreDto> genresDto = convertirListGenreEnDto(genres);
        return new GenreListDto(genresDto);
    }

    @Override
    public GenreDto convertirGenreEnDto(Genre genre) {

        return new GenreDto(genre.getId(), genre.getLibelle());
    }

    @Override
    public List<GenreDto> convertirListGenreEnDto(List<Genre> genres) {
        return genres==null?null:genres.stream().map(this::convertirGenreEnDto).collect(Collectors.toList());
    }

    @Override
    public Genre convertirGenreDtoEnGenre(GenreDto genreDto) {
        return new Genre(genreDto.getId(), genreDto.getLibelle());
    }

    @Override
    public List<Genre> convertirListGenreDtoEnListGenre(List<GenreDto> genreDtoList) {
        return genreDtoList==null?null:genreDtoList.stream().map(this::convertirGenreDtoEnGenre).collect(Collectors.toList());
    }

    private Genre convertirGenreIdEnGenre(Long id) {
        return new Genre(id, "");
    }

    @Override
    public List<Genre> convertirListIdsEnListGenre(List<Long> idList) {
        return idList==null?null:idList.stream().map(this::convertirGenreIdEnGenre).collect(Collectors.toList());
    }
}
