package fr.epita.trackmoviesback.exposition;

import fr.epita.trackmoviesback.application.GenreService;
import fr.epita.trackmoviesback.dto.GenreListDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/trackmovies/v1")
public class GenreController {

    @Autowired
    GenreService service;

    @ApiOperation(value = "Recuperer les genres"
            , notes = "Permet de récupérer la liste des genres"
    ) //info pour le swagger
    @GetMapping(value = "/genres", produces = {"application/json; charset=utf-8"})
    GenreListDto getAllGenres(){
        return service.getAllGenres();
    }
}
