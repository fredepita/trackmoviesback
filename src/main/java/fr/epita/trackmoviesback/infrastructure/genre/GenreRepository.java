package fr.epita.trackmoviesback.infrastructure.genre;

import fr.epita.trackmoviesback.domaine.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre,Long> {

}
