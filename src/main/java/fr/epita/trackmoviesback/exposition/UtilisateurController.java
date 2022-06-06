package fr.epita.trackmoviesback.exposition;

import fr.epita.trackmoviesback.application.UtilisateurService;
import fr.epita.trackmoviesback.dto.UtilisateurDto;
import fr.epita.trackmoviesback.exception.MauvaisParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/trackmovies/v1")
public class UtilisateurController {

    private static Logger logger = LoggerFactory.getLogger(UtilisateurController.class);

    @Autowired
    UtilisateurService utilisateurService;

    @PostMapping(value = "/utilisateur")
    public ResponseEntity<UtilisateurDto> creerUtilisateur(@RequestBody UtilisateurDto utilisateurDto){
        try {
            utilisateurService.creerUtilisateur(utilisateurDto);
            logger.info("User cree");
            return new ResponseEntity(utilisateurDto, HttpStatus.CREATED);
        }
        catch (MauvaisParamException exception){
            logger.error("Mauvais paramètre reçu: {}",exception.getMessage());
            return new ResponseEntity("Mauvais paramètre reçu",HttpStatus.BAD_REQUEST);
        }
    }
}
