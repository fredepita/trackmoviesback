package fr.epita.trackmoviesback.dto;

import java.util.List;

public class GenreListDto {

    List<GenreDto> genres;

    public GenreListDto(List<GenreDto> genres) {
        this.genres = genres;
    }
}
