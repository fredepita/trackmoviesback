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

    @OneToMany (cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    @JoinColumn(name = "saison_id") //définir le JoinColumn permet d'eviter la création d'une table de liaison inutile entre saison et episode puisqu'un episode appartient à une seule saison
    private List<Episode> episodes;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private StatutVisionnage statutVisionnage;

    private Integer note;

    public Saison() {
    }

    public Saison(String numero, List<Episode> episodes, StatutVisionnage statutVisionnage, Integer note) {
        this.numero = numero;
        this.episodes = episodes;
        this.statutVisionnage = statutVisionnage;
        this.note = note;
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

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public StatutVisionnage getStatutVisionnage() {
        return statutVisionnage;
    }

    public void setStatutVisionnage(StatutVisionnage statutVisionnage) {
        this.statutVisionnage = statutVisionnage;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Saison{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", episodes=" + episodes +
                ", statutVisionnage=" + statutVisionnage +
                ", note=" + note +
                '}';
    }
}
