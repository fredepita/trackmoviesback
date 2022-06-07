package fr.epita.trackmoviesback.infrastructure.oeuvre;

import fr.epita.trackmoviesback.domaine.Serie;
import fr.epita.trackmoviesback.domaine.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SerieRepository extends JpaRepository<Serie,Long>, JpaSpecificationExecutor<Serie> {
    //necessaire car le get ou le find by id, ne charge pas les saisons qui sont en lazy
    @Query(value = "select DISTINCT s from Serie AS s LEFT JOIN FETCH s.saisons  WHERE s.id=:id and s.utilisateur=:utilisateur ")
    Serie getSerieComplete(Long id, Utilisateur utilisateur);

}
