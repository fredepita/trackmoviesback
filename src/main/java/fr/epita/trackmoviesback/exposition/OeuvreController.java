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
    OeuvreLightListDto getOeuvres(@RequestParam(name = "type",required = false) String typeOeuvre
            , @RequestParam(name = "genre",required = false) String genreId
            , @RequestParam(name = "statut",required = false) String statutVisionnageId
            , @RequestParam(name = "titre",required = false) String titre
    ){
        Map<String,String> parameters = new HashMap<>();
        if (typeOeuvre!=null) parameters.put("type",typeOeuvre);
        if (genreId!=null) parameters.put("genre", genreId);
        if (statutVisionnageId!=null) parameters.put("statut",statutVisionnageId);
        if (titre!=null) parameters.put("titre",titre);
        return service.getOeuvres(parameters);
    }


}


