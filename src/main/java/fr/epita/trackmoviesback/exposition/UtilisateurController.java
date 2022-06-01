package fr.epita.trackmoviesback.exposition;

import fr.epita.trackmoviesback.application.UtilisateurService;
import fr.epita.trackmoviesback.dto.OeuvreDto;
import fr.epita.trackmoviesback.dto.UtilisateurDto;
import fr.epita.trackmoviesback.exception.MauvaisParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/trackmovies/v1")
public class UtilisateurController {

    @Autowired
    UtilisateurService utilisateurService;

    @PostMapping(value = "/utilisateur")
    public ResponseEntity<UtilisateurDto> creerUtilisateur(@RequestBody UtilisateurDto utilisateurDto){
        try {
            utilisateurService.creerUtilisateur(utilisateurDto);
            return new ResponseEntity(utilisateurDto, HttpStatus.CREATED);
        }
        catch (MauvaisParamException exception){
            return new ResponseEntity("Mauvais paramètre reçu: : "+exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
