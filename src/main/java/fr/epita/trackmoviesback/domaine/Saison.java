package fr.epita.trackmoviesback.domaine;

import javax.persistence.*;
import java.util.List;

@Entity
public class Saison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numero;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private StatutVisionnage statutVisionnage;

    private Integer nbEpisode;

    public Saison() {
    }

    public Saison(Long id, String numero, StatutVisionnage statutVisionnage, Integer nbEpisode) {
        this.id = id;
        this.numero = numero;
        this.statutVisionnage = statutVisionnage;
        this.nbEpisode = nbEpisode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public StatutVisionnage getStatutVisionnage() {
        return statutVisionnage;
    }

    public void setStatutVisionnage(StatutVisionnage statutVisionnage) {
        this.statutVisionnage = statutVisionnage;
    }

    public Integer getNbEpisode() {
        return nbEpisode;
    }

    public void setNbEpisode(Integer nbEpisode) {
        this.nbEpisode = nbEpisode;
    }

    @Override
    public String toString() {
        return "Saison{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", statutVisionnage=" + statutVisionnage +
                ", nbEpisode=" + nbEpisode +
                '}';
    }
}
