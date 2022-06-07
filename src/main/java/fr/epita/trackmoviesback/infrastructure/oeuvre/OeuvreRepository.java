package fr.epita.trackmoviesback.infrastructure.oeuvre;

import fr.epita.trackmoviesback.domaine.Oeuvre;
import fr.epita.trackmoviesback.domaine.Utilisateur;
import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OeuvreRepository extends JpaRepository<Oeuvre,Long> , JpaSpecificationExecutor<Oeuvre> {
     //Retour le type d'une oeuvre uniquement
    @Query(value = "select o.type_oeuvre from Oeuvre AS o WHERE o.id=:id and o.utilisateur=:utilisateur")
    EnumTypeOeuvre getTypeOeuvre(Long id,Utilisateur utilisateur);

    @Query(value = "select o.utilisateur from Oeuvre AS o WHERE o.id=:id")
    Utilisateur getUtilisateurDeOeuvre(Long id);

    @Query(value = "select o from Oeuvre AS o WHERE o.titre=:titre and o.utilisateur=:utilisateur ")
    List<Oeuvre> findByTitreAndByUtilisateur(String titre,Utilisateur utilisateur);

    List<Oeuvre> findAllByUtilisateurOrderByTitre(Utilisateur utilisateur);

}
