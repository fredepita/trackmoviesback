package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Utilisateur;
import fr.epita.trackmoviesback.dto.UtilisateurDto;
import fr.epita.trackmoviesback.enumerate.EnumTypeRole;
import fr.epita.trackmoviesback.exception.MauvaisParamException;
import fr.epita.trackmoviesback.infrastructure.utilisateur.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UtilisateurServiceImplTest {

    @Autowired
    UtilisateurService utilisateurService;

    @MockBean
    UtilisateurRepository utilisateurRepositoryMock;

    @Test
    void devrait_creer_utilisateur(){

        List utilisateurTestList = new ArrayList<Utilisateur>;
        UtilisateurDto utilisateurDtoTest = new UtilisateurDto();
        utilisateurDtoTest.setIdentifiant("utilisateurTest1");
        utilisateurDtoTest.setMotDePasse("motDePasseTest1");
        Utilisateur utilisateurTest = utilisateurService.convertirUtilisateurEnEntity(utilisateurDtoTest);

        when(utilisateurRepositoryMock.findByLogin(utilisateurDtoTest.getIdentifiant())).thenReturn(null);
        when(utilisateurRepositoryMock.save(utilisateurTest)).thenCallRealMethod();
        utilisateurService.creerUtilisateur(utilisateurDtoTest);
        /*public void creerUtilisateur(UtilisateurDto utilisateurDto) {
            if (utilisateurDto != null) {
                if (verifierExistenceUtilisateur(utilisateurDto)) {
                    throw new MauvaisParamException("l'identifiant " + utilisateurDto.getIdentifiant() + " existe déjà !");
                } else {
                    //--> Conversion des données issues du Front (Dto) en données pour la Base de données (Entity)
                    Utilisateur utilisateur = convertirUtilisateurEnEntity(utilisateurDto);
                    //--> Encodage du mot de passe avant stockage
                    String motDePasseEncode = passwordEncoder.encode(utilisateur.getMotDePasse());
                    utilisateur.setMotDePasse(motDePasseEncode);
                    //--> Attribution rôle User par défaut
                    utilisateur.addRole(EnumTypeRole.ROLE_USER);
                    //--> Création en base de données
                    utilisateurRepository.save(utilisateur);*/
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
