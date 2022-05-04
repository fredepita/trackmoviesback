package fr.epita.trackmoviesback.domaine;

import javax.persistence.*;

@Entity
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numero;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private StatutVisionnage statutVisionnage;

    //url vers une video (teasing ou autre)
    private String video;

    //duree en minute
    private Integer duree;

    //@ManyToOne(cascade = CascadeType.ALL)
    //private Saison saison;


    public Episode() {
    }

    public Episode(String numero, StatutVisionnage statutVisionnage, String video, Integer duree) {
        this.numero = numero;
        this.statutVisionnage = statutVisionnage;
        this.video = video;
        this.duree = duree;
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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    /**
     *
     * @return duree en minutes
     */
    public Integer getDuree() {
        return duree;
    }

    /**
     *
     * @param duree en minutes
     */
    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    @Override
    public String toString() {
        return "Episode{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", statutVisionnage=" + statutVisionnage +
                ", video='" + video + '\'' +
                ", duree=" + duree +
                '}';
    }
}
