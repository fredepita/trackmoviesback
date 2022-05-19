package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Utilisateur;
import fr.epita.trackmoviesback.enumerate.EnumTypeRole;
import fr.epita.trackmoviesback.infrastructure.utilisateur.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void creerUtilisateur(Utilisateur utilisateur) {
        String motDePasseEncode = passwordEncoder.encode(utilisateur.getMotDePasse());
        utilisateur.setMotDePasse(motDePasseEncode);
        utilisateur.addRole(EnumTypeRole.ROLE_USER);
        utilisateurRepository.save(utilisateur);
    }

    @Override
    public void modifierUtilisateur(Long id, Utilisateur utilisateur) {

    }

    @Override
    public Utilisateur rechercherUtilisateurParId(Long id) {
        return null;
    }

    @Override
    public Utilisateur rechercherUtilisateurParIdentifiant(String Identifiant) {
        return null;
    }

    @Override
    public void supprimerUtilisateur(Long id) {

    }
}
