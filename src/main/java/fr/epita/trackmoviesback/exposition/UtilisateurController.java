package fr.epita.trackmoviesback.exposition;

import fr.epita.trackmoviesback.application.UtilisateurService;
import fr.epita.trackmoviesback.configuration.security.jwt.JwtRequest;
import fr.epita.trackmoviesback.configuration.security.jwt.JwtResponse;
import fr.epita.trackmoviesback.domaine.Utilisateur;
import fr.epita.trackmoviesback.infrastructure.utilisateur.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/trackmovies/v1")
public class UtilisateurController {

    @Autowired
    UtilisateurService utilisateurService;

    @PostMapping(value = "/utilisateur")
    public void creerUtilisateur(@RequestBody Utilisateur utilisateur){
        utilisateurService.creerUtilisateur(utilisateur);
    }
}
