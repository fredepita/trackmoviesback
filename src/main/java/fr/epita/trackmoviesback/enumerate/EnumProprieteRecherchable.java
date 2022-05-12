package fr.epita.trackmoviesback.enumerate;

import fr.epita.trackmoviesback.exception.MauvaisParamException;

public enum EnumProprieteRecherchable {
    TYPE_OEUVRE("typeOeuvre","type"),
    GENRE("genres","genre"),
    TITRE("titre","titre"),
    STATUT_VISIONNAGE("statutVisionnage","statut");
    //SERIE("serie");

    private String proprieteBDD;
    private String parametreRequeteHttp;

    EnumProprieteRecherchable(String proprieteBDD, String parametreRequeteHttp) {
        this.proprieteBDD = proprieteBDD;
        this.parametreRequeteHttp = parametreRequeteHttp;
    }

    public String getProprieteBDD() {
        return proprieteBDD;
    }

    public String getParametreRequeteHttp() {
        return parametreRequeteHttp;
    }

    public static EnumProprieteRecherchable getEnumFromValeurParametreRequeteHttp(String parametreRequeteHttp) {
        if (parametreRequeteHttp==null) throw new MauvaisParamException("parametreRequeteHttp ne peut pas etre null");
        for (EnumProprieteRecherchable enumProprieteRecherchable:EnumProprieteRecherchable.values()) {
            if (enumProprieteRecherchable.getParametreRequeteHttp().equals(parametreRequeteHttp)) return enumProprieteRecherchable;
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
