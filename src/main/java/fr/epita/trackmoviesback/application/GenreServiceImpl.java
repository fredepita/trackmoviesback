package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.GenreOeuvre;
import fr.epita.trackmoviesback.dto.GenreDto;
import fr.epita.trackmoviesback.dto.GenreListDto;
import fr.epita.trackmoviesback.dto.StatutVisionnageListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    GenreOeuvreDao dao;

    @Override
    public GenreListDto getAllGenreOeuvre() {
    List<GenreDto> genresDto = dao.getAllGenre().stream()
            .map(this::convertirGenreEnGenreDto);
        return new StatutVisionnageListDto(genresDto);
}
    private GenreDto convertirGenreEnGenreDto(GenreOeuvre genre) {
        GenreDto genreDto =new GenreDto(genre.getId(), genre.getLibelle());
        return genreDto;
}
