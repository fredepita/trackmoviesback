package fr.epita.trackmoviesback.enumerate;

import fr.epita.trackmoviesback.exception.MauvaisParamException;

public enum EnumTypeOeuvre {
    FILM("film"),
    SERIE("serie");

    private String libelle;

    EnumTypeOeuvre(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    @Override
    public String toString() {
        return libelle;
    }

    public static EnumTypeOeuvre getEnumFromlabel(String libelle) {
        if (libelle==null) throw new MauvaisParamException("Type d'oeuvre ne peut pas etre null");
        for (EnumTypeOeuvre enumTypeOeuvre:EnumTypeOeuvre.values()) {
            if (enumTypeOeuvre.getLibelle().equals(libelle)) return enumTypeOeuvre;
        }
        throw new MauvaisParamException("Type d'oeuvre inconnue: ne peut pas etre null");
    }

}
