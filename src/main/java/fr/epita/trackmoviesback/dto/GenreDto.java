package fr.epita.trackmoviesback.dto;

public class GenreDto {
    private Long id;
    private String libelle;

    public GenreDto(Long id, String libelle) {
        this.id = id;
        this.libelle = libelle;
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
        return "GenreDto{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}

