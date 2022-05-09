package fr.epita.trackmoviesback.enumerate;

public enum EnumProprieteRecherchable {
    TYPE_OEUVRE("typeOeuvre"),
    GENRE("genres"),
    TITRE("titre");
    //SERIE("serie");



    private String proprieteBDD;

    EnumProprieteRecherchable(String proprieteBDD) {
        this.proprieteBDD = proprieteBDD;
    }

    public String getProprieteBDD() {
        return proprieteBDD;
    }

    @Override
    public String toString() {
        return "EnumProprieteRecherchable{" +
                "proprieteBDD='" + proprieteBDD + '\'' +
                '}';
    }
}
