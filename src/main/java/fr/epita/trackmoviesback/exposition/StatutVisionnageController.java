package fr.epita.trackmoviesback.exposition;

import fr.epita.trackmoviesback.application.StatutVisionnageService;
import fr.epita.trackmoviesback.domaine.StatutVisionnage;
import fr.epita.trackmoviesback.dto.StatutVisionnageListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trackmovies/v1")
public class StatutVisionnageController {

    @Autowired
    StatutVisionnageService service;

    @GetMapping("/statuts_visionnage")
    StatutVisionnageListDto getAllStatutVisionnage(){
        return service.getAllStatutVisionnage();
    }
}