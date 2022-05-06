package fr.epita.trackmoviesback.infrastructure.specs;

import fr.epita.trackmoviesback.domaine.Oeuvre;
import fr.epita.trackmoviesback.enumerate.EnumOperationDeRecherche;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class OeuvreSpecification implements Specification<Oeuvre> {
    private List<CritereDeRecherche> critereDeRechercheList;

    public OeuvreSpecification() {
        this.critereDeRechercheList = new ArrayList<>();
    }

    public void add(CritereDeRecherche critereDeRecherche) {
        critereDeRechercheList.add(critereDeRecherche);
    }

    @Override
    public Predicate toPredicate(Root<Oeuvre> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        //add add criteria to predicates
        for (CritereDeRecherche critere : critereDeRechercheList) {
            //cette ecriture marche bien pour genres. Il faut le rendre générique
            //je crée volontairement cette erreur de compile pour que je sache qu'il faut que je
            //gere ici le cas particulié de genres et que je test aussi sur les autres critère (statut, titre)
            -> cette ligne est une erreurVolontaire pour que la prochaine fois je travaille à partir de ce point

            predicates.add(
                    criteriaBuilder.equal(root.join("genres", JoinType.INNER).get("id"),critere.getValeur())
            );
            /* ca ca marche pour type oeuvre mais pas pour le many to many de genre
            if (critere.getOperationDeRecherche().equals(EnumOperationDeRecherche.EGAL)) {
                predicates.add(
                        criteriaBuilder.equal(root.get(critere.getNomPropriete()), critere.getValeur())
                );
            } else if (critere.getOperationDeRecherche().equals(EnumOperationDeRecherche.COMMENCE_PAR)) {
                predicates.add(
                        criteriaBuilder.like(root.get(critere.getNomPropriete()), critere.getValeur().toString()+ "%")
                );
            }*/
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

}
