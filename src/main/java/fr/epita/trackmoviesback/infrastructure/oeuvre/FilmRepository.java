package fr.epita.trackmoviesback.infrastructure.oeuvre;

import fr.epita.trackmoviesback.domaine.Film;
import fr.epita.trackmoviesback.domaine.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film,Long>, JpaSpecificationExecutor<Film> {
    Optional<Film> findByIdAndUtilisateur(Long id, Utilisateur utilisateur);
}
