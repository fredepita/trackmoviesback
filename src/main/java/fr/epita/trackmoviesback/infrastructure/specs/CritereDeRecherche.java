package fr.epita.trackmoviesback.infrastructure.specs;

import fr.epita.trackmoviesback.enumerate.EnumOperationDeRecherche;
import fr.epita.trackmoviesback.enumerate.EnumProprieteRecherchable;
import fr.epita.trackmoviesback.exception.MauvaisParamException;

/**
 * Permet de spécifier un critère de recherche
 * Aucun setter, il faut passer par le constructeur afin d'appliquer les controles de coherence metier
 */
public class CritereDeRecherche {
    private EnumProprieteRecherchable nomPropriete;

    private Object valeur;
    private EnumOperationDeRecherche operationDeRecherche;

    /**
     * Permet de spécifier un critère de recherche
     *
     * @param proprieteRecherchable propriete sur laquelle porte la recherche. Valeurs possibles dans EnumProprieteRecherchable
     * @param valeur valeur que l'on recherche
     * @param operationDeRecherche operation de recherche. Valeur possible dans EnumOperationDeRecherche
     *
     * @throws MauvaisParamException
     *      Aucun parametre d'entree ne doit etre null
     *      seule la propriete recherchable "titre" accepte l'operation commence par
     */
    public CritereDeRecherche(EnumProprieteRecherchable proprieteRecherchable, Object valeur, EnumOperationDeRecherche operationDeRecherche) {
        if (proprieteRecherchable==null || valeur==null || operationDeRecherche==null)
            throw new MauvaisParamException("Aucun parametre de CritereDeRecherche ne peut etre null. Valeur recues: propriete="+proprieteRecherchable+" ; valeur="+ valeur+" ; operationDeRecherche="+operationDeRecherche);
        if (proprieteRecherchable!=EnumProprieteRecherchable.TITRE && operationDeRecherche==EnumOperationDeRecherche.COMMENCE_PAR)
            throw new MauvaisParamException("Seule la propriete "+EnumProprieteRecherchable.TITRE.getProprieteBDD()+" accepte l'operation de recherche "+EnumOperationDeRecherche.COMMENCE_PAR);
        this.nomPropriete = proprieteRecherchable;
        this.valeur = valeur;
        this.operationDeRecherche = operationDeRecherche;
    }

    public EnumProprieteRecherchable getNomPropriete() {
        return nomPropriete;
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
                "nomPropriete='" + nomPropriete + '\'' +
                ", valeur=" + valeur +
                ", operationDeRecherche=" + operationDeRecherche +
                '}';
    }

}
