package fr.epita.trackmoviesback.infrastructure.utilisateur;

import fr.epita.trackmoviesback.domaine.Utilisateur;
import fr.epita.trackmoviesback.enumerate.EnumTypeRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UtilisateurRepositoryTest {

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void devrait_creer_utilisateur() {

        Utilisateur utilisateurTest1 = new Utilisateur();
        utilisateurTest1.setLogin("utilisateurTest1");
        utilisateurTest1.setMotDePasse("motDePasse1");
        utilisateurTest1.addRole(EnumTypeRole.ROLE_USER);

        Utilisateur utilisateurTestEnregistre1 = utilisateurRepository.save(utilisateurTest1);

        assertNotNull(utilisateurTestEnregistre1);
        assertEquals(utilisateurTestEnregistre1.getId(),utilisateurTest1.getId());
        assertEquals(utilisateurTestEnregistre1.getLogin(),utilisateurTest1.getLogin());
        assertEquals(utilisateurTestEnregistre1.getMotDePasse(),utilisateurTest1.getMotDePasse());
        assertEquals(utilisateurTestEnregistre1.getRoles(),utilisateurTest1.getRoles());
    }

    @Test
    public void devrait_trouver_utilisateur() {}

    @Test
    public void devrait_modifier_utilisateur() {}

    @Test
    public void devrait_supprimer_utilisateur() {}
}
