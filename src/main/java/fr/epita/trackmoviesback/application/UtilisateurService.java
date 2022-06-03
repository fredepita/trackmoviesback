package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Utilisateur;
import fr.epita.trackmoviesback.dto.UtilisateurDto;

public interface UtilisateurService {

    void creerUtilisateur (UtilisateurDto utilisateurDto);

    void modifierUtilisateur (Long id, Utilisateur utilisateur);

    Utilisateur rechercherUtilisateurParId (Long id);

    Utilisateur rechercherUtilisateurParIdentifiant (String Identifiant);

    void supprimerUtilisateur (Long id);

    Utilisateur convertirUtilisateurEnEntity(UtilisateurDto utilisateurDto);

    Boolean verifierExistenceUtilisateur(UtilisateurDto utilisateurDto);

}
