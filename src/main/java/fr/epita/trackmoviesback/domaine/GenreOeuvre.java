package fr.epita.trackmoviesback.domaine;

public class GenreOeuvre {
    private Long id;
    private String libelle;

    public GenreOeuvre() {
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
        return "GenreOeuvre{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
