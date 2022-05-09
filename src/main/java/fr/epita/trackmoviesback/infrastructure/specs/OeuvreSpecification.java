package fr.epita.trackmoviesback.infrastructure.specs;

import fr.epita.trackmoviesback.domaine.Oeuvre;
import fr.epita.trackmoviesback.enumerate.EnumOperationDeRecherche;
import fr.epita.trackmoviesback.enumerate.EnumProprieteRecherchable;
import fr.epita.trackmoviesback.exception.MauvaisParamException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

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
            if (critere.getNomPropriete()== EnumProprieteRecherchable.GENRE) {
                //cas particulier du critère genres qui a une table de liaison à cause de la relation ManyToMany.
                // Il faut construire le critere en tenant compte du join supplementaire

                //on ne gère que l'égalité pour ce critère (pas le "commence par"), puisque on utilise l'id
                if (critere.getOperationDeRecherche()==EnumOperationDeRecherche.EGAL)
                    predicates.add(
                            criteriaBuilder.equal(root.join(critere.getNomPropriete().getProprieteBDD(), JoinType.INNER).get("id"), critere.getValeur())
                    );
                else
                    throw new MauvaisParamException("Seule l'operation de recherche "+EnumOperationDeRecherche.EGAL+" est possible pour la propriete genre");
            } else {
                //pour les autres critères de recherche
                if (critere.getOperationDeRecherche().equals(EnumOperationDeRecherche.EGAL)) {
                    predicates.add(
                            criteriaBuilder.equal(root.get(critere.getNomPropriete().getProprieteBDD()), critere.getValeur())
                    );
                } else if (critere.getOperationDeRecherche().equals(EnumOperationDeRecherche.COMMENCE_PAR)) {
                    -> mettre un log
                    predicates.add(
                            criteriaBuilder.like(root.get(critere.getNomPropriete().getProprieteBDD().toLowerCase()), critere.getValeur().toString().toLowerCase()+ "%")
                    );
                }
            }

        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

}
