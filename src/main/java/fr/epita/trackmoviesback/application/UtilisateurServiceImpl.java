package fr.epita.trackmoviesback.application;


import fr.epita.trackmoviesback.domaine.Utilisateur;
import fr.epita.trackmoviesback.dto.UtilisateurDto;
import fr.epita.trackmoviesback.enumerate.EnumTypeRole;
import fr.epita.trackmoviesback.exception.MauvaisParamException;
import fr.epita.trackmoviesback.exception.UtilisateurNonLoggeException;
import fr.epita.trackmoviesback.infrastructure.utilisateur.UtilisateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private static final Logger logger = LoggerFactory.getLogger(UtilisateurServiceImpl.class);

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void creerUtilisateur(UtilisateurDto utilisateurDto) {
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
    public Utilisateur rechercherUtilisateurParLogin(String login) {
        Utilisateur utilisateur= utilisateurRepository.findByLogin(login);
        if (utilisateur==null) {
            logger.debug("utilisateur non trouvee en BDD login={}",login);
        }
        return utilisateur;
    }

    @Override
    public void supprimerUtilisateur(Long id) {

    }
    @Override
    public Utilisateur convertirUtilisateurEnEntity(UtilisateurDto utilisateurDto){
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setLogin(utilisateurDto.getIdentifiant());
        utilisateur.setMotDePasse(utilisateurDto.getMotDePasse());
        return utilisateur;
    }
    @Override
    public boolean verifierExistenceUtilisateur(UtilisateurDto utilisateurDto){
        Utilisateur utilisateur = rechercherUtilisateurParLogin(utilisateurDto.getIdentifiant());
        return utilisateur != null;
    }


    @Override
    public String getCurrentUserLoginFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user=null;
        if (authentication!=null) {
            user = (User) authentication.getPrincipal();
            return user.getUsername();
        } else {
            throw new UtilisateurNonLoggeException("utilisateur non loggé");
        }
    }
}
