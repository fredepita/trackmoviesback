package fr.epita.trackmoviesback.exposition;

import fr.epita.trackmoviesback.application.OeuvreService;
import fr.epita.trackmoviesback.dto.OeuvreDto;
import fr.epita.trackmoviesback.dto.OeuvreLightListDto;
import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;
import fr.epita.trackmoviesback.exception.MauvaisParamException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ApiOperation(value = "Recuperer une oeuvre par son id"
            , notes = "Permet de récupérer une oeuvre et son détail par son id"
    ) //info pour le swagger
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 401, message = "Non autorisé"),
            @ApiResponse(code = 404, message = "non trouvé"),
            @ApiResponse(code = 500, message = "erreur du serveur") })
    @GetMapping("/mes_oeuvres/{id}")
    ResponseEntity<OeuvreDto> getOeuvreCompleteById (@PathVariable("id") Long id) {
        OeuvreDto oeuvre = service.getOeuvreCompleteById(id);
        if (oeuvre !=null) {
            return new ResponseEntity(oeuvre, HttpStatus.OK);

        }else {
            return new ResponseEntity("Aucune Oeuvre trouvée",HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Sauver une oeuvre"
            , notes = "Permet de creer ou mettre à jour une oeuvre. Pour les element Genre et Statut visionnage, seul les id sont pris en compte. "
    ) //info pour le swagger
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Mauvais parametre recu"),
            @ApiResponse(code = 401, message = "Non autorisé"),
            @ApiResponse(code = 404, message = "non trouvé"),
            @ApiResponse(code = 500, message = "erreur du serveur") })
    @PostMapping("/oeuvre")
    public ResponseEntity<OeuvreDto> create(@RequestBody OeuvreDto oeuvreDto) {

        try {
            /*OeuvreDto oeuvreDtoTest = new OeuvreDto(null, EnumTypeOeuvre.FILM.getLibelle(), "test creation controller",
                    null,null,null,null,null,null,null,null,null);
            OeuvreDto oeuvreCree = service.saveOeuvre(oeuvreDtoTest);*/

            OeuvreDto oeuvreCree = service.saveOeuvre(oeuvreDto);
            return new ResponseEntity(oeuvreCree, HttpStatus.OK);
        } catch (MauvaisParamException e) {
            return new ResponseEntity("Mauvais parametre recu: "+e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }
}


