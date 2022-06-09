package fr.epita.trackmoviesback.exposition;

import fr.epita.trackmoviesback.application.StatutVisionnageService;
import fr.epita.trackmoviesback.domaine.StatutVisionnage;
import fr.epita.trackmoviesback.dto.StatutVisionnageListDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/trackmovies/v1")
public class StatutVisionnageController {

    @Autowired
    StatutVisionnageService service;

    @ApiOperation(value = "Recuperer les statuts de visionnage"
            , notes = "Permet de récupérer la liste des statuts de visionnage"
    ) //info pour le swagger

    @GetMapping(value = "/statuts_visionnage", produces = {"application/json; charset=utf-8"})
    StatutVisionnageListDto getAllStatutVisionnage(){
        return service.getAllStatutVisionnage();
    }
}