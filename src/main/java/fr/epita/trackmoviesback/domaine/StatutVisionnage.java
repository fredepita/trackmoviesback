package fr.epita.trackmoviesback.domaine;

public class StatutVisionnage {

    private Long id;
    private String libelle;

    public StatutVisionnage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "StatutVisionnage{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
