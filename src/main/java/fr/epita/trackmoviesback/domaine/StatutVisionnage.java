package fr.epita.trackmoviesback.domaine;

import javax.persistence.*;

@Entity
public class StatutVisionnage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
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
