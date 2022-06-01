package fr.epita.trackmoviesback.domaine;

import com.sun.org.apache.xpath.internal.operations.Equals;

import javax.persistence.*;

@Entity
public class Saison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numero;

    @ManyToOne(fetch = FetchType.EAGER)
    private StatutVisionnage statutVisionnage;

    private Integer nbEpisodes;

    public Saison() {
    }

    public Saison(Long id, String numero, StatutVisionnage statutVisionnage, Integer nbEpisodes) {
        this.id = id;
        this.numero = numero;
        this.statutVisionnage = statutVisionnage;
        this.nbEpisodes = nbEpisodes;
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

    public Integer getNbEpisodes() {
        return nbEpisodes;
    }

    public void setNbEpisodes(Integer nbEpisode) {
        this.nbEpisodes = nbEpisode;
    }

    @Override
    public String toString() {
        return "Saison{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", statutVisionnage=" + statutVisionnage +
                ", nbEpisodes=" + nbEpisodes +
                '}';
    }

}
