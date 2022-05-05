package fr.epita.trackmoviesback.exposition;

import fr.epita.trackmoviesback.application.GenreService;
import fr.epita.trackmoviesback.dto.GenreListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//la racine d'URL convient-elle?
@RequestMapping("/trackmovies/v1")
public class GenreOeuvreController {

    @Autowired
    GenreService service;

    @GetMapping("/genres")
    GenreListDto getAllGenreOeuvre(){
        return service.getAllGenreOeuvre();
    }
}
