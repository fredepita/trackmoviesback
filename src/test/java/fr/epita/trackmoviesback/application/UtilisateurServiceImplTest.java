package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Utilisateur;
import fr.epita.trackmoviesback.dto.UtilisateurDto;
import fr.epita.trackmoviesback.exception.MauvaisParamException;
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

        UtilisateurDto utilisateurDtoTest = new UtilisateurDto();
        utilisateurDtoTest.setIdentifiant("utilisateurTest1");
        utilisateurDtoTest.setMotDePasse("motDePasseTest1");
        //--> utilisateurTest1 ne doit pas être trouvé
        assertFalse(utilisateurService.verifierExistenceUtilisateur(utilisateurDtoTest));
        //--> Création utilisateurTest1
        utilisateurService.creerUtilisateur(utilisateurDtoTest);
        //--> utilisateurTest1 doit être trouvé
        assertTrue(utilisateurService.verifierExistenceUtilisateur(utilisateurDtoTest));
        //--> utilisateurTest1 ne doit pas être créé si déjà existant
        assertThrows(MauvaisParamException.class, () -> { utilisateurService.creerUtilisateur(utilisateurDtoTest); });

        //--> supprimer utilisateurTest1 après test, permet de tester la suppression également
        utilisateurService.supprimerUtilisateur(utilisateurDtoTest);
        //--> utilisateurTest1 ne doit pas être trouvé
        assertFalse(utilisateurService.verifierExistenceUtilisateur(utilisateurDtoTest));
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
