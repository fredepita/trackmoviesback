package fr.epita.trackmoviesback.dto;

public class StatutVisionnageDto {

    private Long id;
    private String libelle;

    public StatutVisionnageDto(Long id, String libelle) {
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
        return "StatutVisionnageDto{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}

