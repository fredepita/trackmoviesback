package fr.epita.trackmoviesback.infrastructure.oeuvre;

import fr.epita.trackmoviesback.domaine.Oeuvre;
import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OeuvreRepository extends JpaRepository<Oeuvre,Long> , JpaSpecificationExecutor<Oeuvre> {
     //Retour le type d'une oeuvre uniquement
    @Query(value = "select o.type_oeuvre from Oeuvre AS o WHERE o.id=:id ")
    EnumTypeOeuvre getTypeOeuvre(Long id);

    List<Oeuvre> findByTitre(String titre);
}
