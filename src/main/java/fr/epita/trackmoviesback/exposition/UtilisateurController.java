package fr.epita.trackmoviesback.exposition;

import fr.epita.trackmoviesback.application.UtilisateurService;
import fr.epita.trackmoviesback.dto.UtilisateurDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/trackmovies/v1")
public class UtilisateurController {

    @Autowired
    UtilisateurService utilisateurService;

    @PostMapping(value = "/utilisateur")
    public void creerUtilisateur(@RequestBody UtilisateurDto utilisateurDto){
        utilisateurService.creerUtilisateur(utilisateurDto);
    }
}
