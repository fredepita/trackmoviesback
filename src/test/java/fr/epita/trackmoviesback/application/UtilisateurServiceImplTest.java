package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Utilisateur;
import fr.epita.trackmoviesback.dto.UtilisateurDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UtilisateurServiceImplTest {

    @Autowired
    UtilisateurService utilisateurService;

    @Test
    void devrait_creer_utilisateur(){

    }

    @Test
    void devrait_ne_pas_creer_utilisateur_deja_existant(){

    }
    @Test
    void devrait_trouver_existence_utilisateur(){

    }

    @Test
    void devrait_ne_pas_trouver_existence_utilisateur(){

    }

    @Test
    void devrait_convertir_utilisateur_dto_en_entity(){

        UtilisateurDto utilisateurDto = new UtilisateurDto();
        utilisateurDto.setIdentifiant("identifiant1");
        utilisateurDto.setMotDePasse("motDePasse1");

        Utilisateur utilisateur = utilisateurService.convertirUtilisateurEnEntity(utilisateurDto);

        assertEquals(utilisateurDto.getIdentifiant(),utilisateur.getLogin());
        assertEquals(utilisateurDto.getMotDePasse(),utilisateur.getMotDePasse());
    }
}
