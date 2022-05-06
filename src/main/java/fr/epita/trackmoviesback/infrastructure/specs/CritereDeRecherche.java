package fr.epita.trackmoviesback.infrastructure.specs;

import fr.epita.trackmoviesback.enumerate.EnumOperationDeRecherche;

public class CritereDeRecherche {
    private String nomPropriete;

    private Object valeur;
    private EnumOperationDeRecherche operationDeRecherche;

    public CritereDeRecherche(String nomPropriete, Object valeur, EnumOperationDeRecherche operationDeRecherche) {
        this.nomPropriete = nomPropriete;
        this.valeur = valeur;
        this.operationDeRecherche = operationDeRecherche;
    }

    public String getNomPropriete() {
        return nomPropriete;
    }

    public void setNomPropriete(String nomPropriete) {
        this.nomPropriete = nomPropriete;
    }

    public Object getValeur() {
        return valeur;
    }

    public void setValeur(Object valeur) {
        this.valeur = valeur;
    }

    public EnumOperationDeRecherche getOperationDeRecherche() {
        return operationDeRecherche;
    }

    public void setOperationDeRecherche(EnumOperationDeRecherche operationDeRecherche) {
        this.operationDeRecherche = operationDeRecherche;
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
