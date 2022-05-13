package fr.epita.trackmoviesback.infrastructure.specs;

import fr.epita.trackmoviesback.enumerate.EnumOperationDeRecherche;
import fr.epita.trackmoviesback.enumerate.EnumProprieteRecherchableSurOeuvre;
import fr.epita.trackmoviesback.exception.MauvaisParamException;

/**
 * Permet de spécifier un critère de recherche
 * Aucun setter, il faut passer par le constructeur afin d'appliquer les controles de coherence metier
 */
public class CritereDeRecherche {
    private EnumProprieteRecherchableSurOeuvre proprieteRecherchee;

    private Object valeur;
    private EnumOperationDeRecherche operationDeRecherche;

    /**
     * Permet de spécifier un critère de recherche
     *
     * @param proprieteRecherchee propriete sur laquelle porte la recherche. Valeurs possibles dans EnumProprieteRecherchable
     * @param valeur valeur que l'on recherche
     * @param operationDeRecherche operation de recherche. Valeur possible dans EnumOperationDeRecherche
     *
     * @throws MauvaisParamException
     *      Aucun parametre d'entree ne doit etre null
     *      seule la propriete recherchable "titre" accepte l'operation commence par
     */
    public CritereDeRecherche(EnumProprieteRecherchableSurOeuvre proprieteRecherchee, Object valeur, EnumOperationDeRecherche operationDeRecherche) {
        if (proprieteRecherchee==null || valeur==null || operationDeRecherche==null)
            throw new MauvaisParamException("Aucun parametre de CritereDeRecherche ne peut etre null. Valeur recues: propriete="+proprieteRecherchee+" ; valeur="+ valeur+" ; operationDeRecherche="+operationDeRecherche);
        if (proprieteRecherchee!= EnumProprieteRecherchableSurOeuvre.TITRE && operationDeRecherche==EnumOperationDeRecherche.COMMENCE_PAR)
            throw new MauvaisParamException("Seule la propriete "+ EnumProprieteRecherchableSurOeuvre.TITRE.getProprieteBDD()+" accepte l'operation de recherche "+EnumOperationDeRecherche.COMMENCE_PAR);
        this.proprieteRecherchee = proprieteRecherchee;
        this.valeur = valeur;
        this.operationDeRecherche = operationDeRecherche;
    }

    public EnumProprieteRecherchableSurOeuvre getProprieteRecherchee() {
        return proprieteRecherchee;
    }

    public Object getValeur() {
        return valeur;
    }

    public EnumOperationDeRecherche getOperationDeRecherche() {
        return operationDeRecherche;
    }

    @Override
    public String toString() {
        return "CritereDeRecherche{" +
                "nomPropriete='" + proprieteRecherchee + '\'' +
                ", valeur=" + valeur +
                ", operationDeRecherche=" + operationDeRecherche +
                '}';
    }

}
