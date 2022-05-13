package fr.epita.trackmoviesback.exposition;

import fr.epita.trackmoviesback.application.OeuvreService;
import fr.epita.trackmoviesback.dto.OeuvreLightDto;
import fr.epita.trackmoviesback.dto.OeuvreLightListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/trackmovies/v1")
public class OeuvreController {

    @Autowired
    OeuvreService service;

    @GetMapping("/mes_oeuvres")
    OeuvreLightListDto getOeuvres(@RequestParam(name = "type") String typeOeuvre
            , @RequestParam(name = "genre") Integer genreId
            , @RequestParam(name = "statut") Integer statutVisionnageId
            , @RequestParam(name = "titre") String titre
    ){
        Map<String,String> parameters = new HashMap<>();
        parameters.put("type",typeOeuvre);
        parameters.put("genre", String.valueOf(genreId));
        parameters.put("statut",String.valueOf(statutVisionnageId));
        parameters.put("titre",titre);
        return service.getOeuvres(parameters);
    }


}


