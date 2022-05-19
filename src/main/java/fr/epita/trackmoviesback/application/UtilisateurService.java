package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Utilisateur;
import fr.epita.trackmoviesback.dto.UtilisateurDto;

public interface UtilisateurService {

    public void creerUtilisateur (UtilisateurDto utilisateurDto);

    public void modifierUtilisateur (Long id, Utilisateur utilisateur);

    public Utilisateur rechercherUtilisateurParId (Long id);

    public Utilisateur rechercherUtilisateurParIdentifiant (String Identifiant);

    public void supprimerUtilisateur (Long id);

}
