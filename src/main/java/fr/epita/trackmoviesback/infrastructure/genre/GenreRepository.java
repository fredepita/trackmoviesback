package fr.epita.trackmoviesback.infrastructure.genre;

import fr.epita.trackmoviesback.domaine.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre,Long> {

}
