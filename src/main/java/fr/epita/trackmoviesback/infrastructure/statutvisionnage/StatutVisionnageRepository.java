package fr.epita.trackmoviesback.infrastructure.statutvisionnage;


import fr.epita.trackmoviesback.domaine.StatutVisionnage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatutVisionnageRepository extends JpaRepository<StatutVisionnage,Long> {
}
