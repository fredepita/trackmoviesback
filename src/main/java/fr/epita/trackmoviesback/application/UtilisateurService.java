package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Utilisateur;
import fr.epita.trackmoviesback.dto.UtilisateurDto;


public interface UtilisateurService {

    void creerUtilisateur (UtilisateurDto utilisateurDto);

    void modifierUtilisateur (Long id, Utilisateur utilisateur);

    Utilisateur rechercherUtilisateurParId (Long id);

    /**
     * recherche l'utilisateur dans la BDD
     * @param login du user
     * @return le user
     */
    Utilisateur rechercherUtilisateurParLogin (String login);

    void supprimerUtilisateur (Long id);

    Utilisateur convertirUtilisateurEnEntity(UtilisateurDto utilisateurDto);

    boolean verifierExistenceUtilisateur(UtilisateurDto utilisateurDto);

    /**
     * recupere l'identifiant du user loggé dans le contexte securite
     * @return identifiant
     */
    String getCurrentUserLoginFromSecurityContext();

}
