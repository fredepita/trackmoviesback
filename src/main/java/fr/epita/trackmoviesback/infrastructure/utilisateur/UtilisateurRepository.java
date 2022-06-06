package fr.epita.trackmoviesback.infrastructure.utilisateur;

import fr.epita.trackmoviesback.domaine.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {

    Utilisateur findByLogin(String login);
}
