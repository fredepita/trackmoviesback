package fr.epita.trackmoviesback.dto;

import java.util.List;

public class GenreListDto {

    List<GenreDto> genres;

    public List<GenreDto> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDto> genres) {
        this.genres = genres;
    }

    public GenreListDto(List<GenreDto> genres) {
        this.genres = genres;
    }
}
