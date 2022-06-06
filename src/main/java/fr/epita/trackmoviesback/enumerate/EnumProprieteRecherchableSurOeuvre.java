package fr.epita.trackmoviesback.enumerate;

import fr.epita.trackmoviesback.exception.MauvaisParamException;

public enum EnumProprieteRecherchableSurOeuvre {
    UTILISATEUR("utilisateur","utilisateur"),
    TYPE_OEUVRE("type_oeuvre","type"),
    GENRE("genres","genre"),
    TITRE("titre","titre"),
    STATUT_VISIONNAGE("statutVisionnage","statut");
    //SERIE("serie");

    private String proprieteBDD;
    private String parametreRequeteHttp;

    EnumProprieteRecherchableSurOeuvre(String proprieteBDD, String parametreRequeteHttp) {
        this.proprieteBDD = proprieteBDD;
        this.parametreRequeteHttp = parametreRequeteHttp;
    }

    public String getProprieteBDD() {
        return proprieteBDD;
    }

    public String getParametreRequeteHttp() {
        return parametreRequeteHttp;
    }

    public static EnumProprieteRecherchableSurOeuvre getEnumFromValeurParametreRequeteHttp(String parametreRequeteHttp) {
        if (parametreRequeteHttp==null) throw new MauvaisParamException("parametreRequeteHttp ne peut pas etre null");
        for (EnumProprieteRecherchableSurOeuvre enumProprieteRecherchableSurOeuvre : EnumProprieteRecherchableSurOeuvre.values()) {
            if (enumProprieteRecherchableSurOeuvre.getParametreRequeteHttp().equals(parametreRequeteHttp)) return enumProprieteRecherchableSurOeuvre;
        }
        throw new MauvaisParamException("Propriete recherchee non geree. Valeur recue: "+parametreRequeteHttp);
    }

    @Override
    public String toString() {
        return "EnumProprieteRecherchable{" +
                "proprieteBDD='" + proprieteBDD + '\'' +
                '}';
    }
}
