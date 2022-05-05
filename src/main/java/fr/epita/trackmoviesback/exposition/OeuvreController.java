package fr.epita.trackmoviesback.exposition;

import fr.epita.trackmoviesback.application.OeuvreService;
import fr.epita.trackmoviesback.dto.OeuvreLightDto;
import fr.epita.trackmoviesback.dto.OeuvreLightListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trackmovies/v1")
public class OeuvreController {

    @Autowired
    OeuvreService service;

    @GetMapping("/mes_oeuvres")
    OeuvreLightListDto getAllOeuvres(){
        return service.getAllOeuvres();
    }


}


