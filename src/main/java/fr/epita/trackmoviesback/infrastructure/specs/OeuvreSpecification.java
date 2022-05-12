package fr.epita.trackmoviesback.infrastructure.specs;

import fr.epita.trackmoviesback.domaine.Oeuvre;
import fr.epita.trackmoviesback.enumerate.EnumOperationDeRecherche;
import fr.epita.trackmoviesback.enumerate.EnumProprieteRecherchable;
import fr.epita.trackmoviesback.exception.MauvaisParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class OeuvreSpecification implements Specification<Oeuvre> {
    private static final Logger logger = LoggerFactory.getLogger(OeuvreSpecification.class);

    private List<CritereDeRecherche> critereDeRechercheList;

    public OeuvreSpecification() {
        this.critereDeRechercheList = new ArrayList<>();
    }

    public void add(CritereDeRecherche critereDeRecherche) {
        critereDeRechercheList.add(critereDeRecherche);
    }

    @Override
    public Predicate toPredicate(Root<Oeuvre> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        logger.debug("Appel toPredicate");
        List<Predicate> predicates = new ArrayList<>();

        //on ajoute les criteres à la liste de predicat
        for (CritereDeRecherche critere : critereDeRechercheList) {
            logger.debug("critere={}",critere);
            switch (critere.getProprieteRecherchee()) {
                case GENRE:
                    //cas particulier du critère genres qui a une table de liaison à cause de la relation ManyToMany.
                    // Il faut construire le critere en tenant compte du join supplementaire
                    //on ne gère que l'operation = pour ce critère (pas le "commence par"), puisque on utilise l'id
                    if (critere.getOperationDeRecherche()==EnumOperationDeRecherche.EGAL)
                        predicates.add(
                            criteriaBuilder.equal(
                                    root.join(critere.getProprieteRecherchee().getProprieteBDD(), JoinType.INNER).get("id"),
                                    critere.getValeur()
                            )
                        );
                    else
                        throw new MauvaisParamException("Seule l'operation de recherche "+EnumOperationDeRecherche.EGAL+" est possible pour la propriete genre");
                    break;

                default://pour les autres critères de recherche
                    if (critere.getOperationDeRecherche().equals(EnumOperationDeRecherche.EGAL)) {
                        predicates.add(
                            criteriaBuilder.equal(
                                    root.get(critere.getProprieteRecherchee().getProprieteBDD()),
                                    critere.getValeur()
                            )
                        );
                    } else if (critere.getOperationDeRecherche().equals(EnumOperationDeRecherche.COMMENCE_PAR)) {
                        predicates.add(
                            criteriaBuilder.like(

                                criteriaBuilder.lower(root.get(critere.getProprieteRecherchee().getProprieteBDD())),
                                    critere.getValeur().toString().toLowerCase()+ "%"
                            )
                        );
                    }
            }

        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

}

