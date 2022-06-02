package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Film;
import fr.epita.trackmoviesback.domaine.Serie;
import fr.epita.trackmoviesback.domaine.Utilisateur;
import fr.epita.trackmoviesback.dto.UtilisateurDto;
import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;
import fr.epita.trackmoviesback.enumerate.EnumTypeRole;
import fr.epita.trackmoviesback.exception.MauvaisParamException;
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
    public void creerUtilisateur(UtilisateurDto utilisateurDto) {

        if (utilisateurDto != null) {
            if (verifierExistanceUtilisateur(utilisateurDto)) {
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
                utilisateurRepository.save(utilisateur);
            }
        }
    }

    @Override
    public void modifierUtilisateur(Long id, Utilisateur utilisateur) {

    }

    @Override
    public Utilisateur rechercherUtilisateurParId(Long id) {
        return null;
    }

    @Override
    public Utilisateur rechercherUtilisateurParIdentifiant(String identifiant) {
        return (utilisateurRepository.findByIdentifiant(identifiant));
    }

    @Override
    public void supprimerUtilisateur(Long id) {

    }

    public Utilisateur convertirUtilisateurEnEntity(UtilisateurDto utilisateurDto){
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setIdentifiant(utilisateurDto.getIdentifiant());
        utilisateur.setMotDePasse(utilisateurDto.getMotDePasse());
        return utilisateur;
    }

    private Boolean verifierExistanceUtilisateur(UtilisateurDto utilisateurDto){
        Utilisateur utilisateur = rechercherUtilisateurParIdentifiant(utilisateurDto.getIdentifiant());
        if (utilisateur != null) {
            return true;
        }
        return false;
    }
}
