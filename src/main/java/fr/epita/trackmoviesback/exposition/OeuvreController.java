package fr.epita.trackmoviesback.exposition;

import fr.epita.trackmoviesback.application.OeuvreService;
import fr.epita.trackmoviesback.dto.OeuvreDto;
import fr.epita.trackmoviesback.dto.OeuvreLightListDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/trackmovies/v1")
public class OeuvreController {

    @Autowired
    OeuvreService service;

    @ApiOperation(value = "Recuperer les oeuvres"
            , notes = "Permet de récupérer le liste des oeuvres correspondant aux critères"
    ) //info pour le swagger
    @GetMapping(value = "/mes_oeuvres", produces = {"application/json"})
    OeuvreLightListDto getOeuvres(
            @ApiParam(value = "type d'oeuvre (film ou serie)")
            @RequestParam(name = "type", required = false) String typeOeuvre
            , @ApiParam(value = "id du genre d'oeuvre")
            @RequestParam(name = "genre", required = false) String genreId
            , @ApiParam(value = "id du statut de visionnage")
            @RequestParam(name = "statut", required = false) String statutVisionnageId
            , @ApiParam(value = "chaine correspondant au debut du titre")
            @RequestParam(name = "titre", required = false) String titre
    ) {
        Map<String, String> parameters = new HashMap<>();
        if (typeOeuvre != null) parameters.put("type", typeOeuvre);
        if (genreId != null) parameters.put("genre", String.valueOf(genreId));
        if (statutVisionnageId != null) parameters.put("statut", String.valueOf(statutVisionnageId));
        if (titre != null) parameters.put("titre", titre);
        return service.getOeuvres(parameters);
    }

    @ApiOperation(value = "Recuperer une oeuvre"
            , notes = "Permet de récupérer une oeuvre et son détail"
    ) //info pour le swagger
    @GetMapping("/mes_oeuvres/{id}")
    OeuvreDto getOeuvreById (
                             @ApiParam(value = "id de l'oeuvre")
                             @RequestParam(name = "id", required = true)
                             @PathVariable("id") Long id) {
        return service.getOeuvreCompleteById(id);
    }

}


