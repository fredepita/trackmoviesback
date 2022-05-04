package fr.epita.trackmoviesback.domaine;

import fr.epita.trackmoviesback.exception.MauvaisParamException;

import javax.persistence.*;
import java.util.List;

@Entity
public class Oeuvre {

    @Transient
    public final String TYPE_FILM = "film";
    @Transient
    public final String TYPE_SERIE = "serie";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String typeOeuvre;

    @Column(nullable = false,unique = true)
    private String titre;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    /*@JoinTable( //sert à forcer les nommage de la table de liaison (evite de se retrouver avec des pluriels)
            name="oeuvre_genre",
            joinColumns = {
                    @JoinColumn(name = "oeuvre_id",referencedColumnName = "id"),
                    @JoinColumn(name = "genre_id", referencedColumnName = "id")}
    )*/
    @JoinTable( //sert à forcer les nommage de la table de liaison et les colonnes(evite de se retrouver avec des pluriels)
            name="oeuvre_genre",
            joinColumns = @JoinColumn(name = "oeuvre_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private StatutVisionnage statutVisionnage;
    private Integer note;
    private String video;

    //donnee propre aux series
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
   /* @JoinTable( //sert à forcer les nommage de la table de liaison (evite de se retrouver avec des pluriels)
            name="oeuvre_saison",
            joinColumns = @JoinColumn( name="oeuvre_id"),
            inverseJoinColumns = @JoinColumn( name="saison_id")
    )*/
    @JoinColumn(name = "oeuvre_id") //définir le JoinColumn permet d'eviter la création d'une table de liaison inutile entre saison et episode puisqu'un episode appartient à une seule saison
    private List<Saison> saisons;

    //donnee propre aux films
    //duree exprimée en minute
    private Integer duree;


    public Oeuvre() {
    }

    public Oeuvre(String type, String titre, List<Genre> genres, StatutVisionnage statutVisionnage, Integer note, String video, List<Saison> saisons, Integer duree) {
        setTypeOeuvre(type);
        this.titre = titre;
        this.genres = genres;
        this.statutVisionnage = statutVisionnage;
        this.note = note;
        this.video = video;
        setSaisons(saisons);
        setDuree(duree);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeOeuvre() {
        return typeOeuvre;
    }

    public void setTypeOeuvre(String typeOeuvre) {
        if(typeOeuvre.equals(TYPE_FILM) || typeOeuvre.equals(TYPE_SERIE)){
            this.typeOeuvre = typeOeuvre;
        } else {
            throw new MauvaisParamException("Valeur recue : " + typeOeuvre + ". Valeurs acceptees :" + TYPE_SERIE + " ou " + TYPE_FILM);
        }
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public List<Saison> getSaisons() {
        return saisons;
    }

    public void setSaisons(List<Saison> saisons) {
        if(typeOeuvre.equals(TYPE_SERIE)){
            this.saisons = saisons;
        } else {
            throw new MauvaisParamException("Type d'oeuvre : " + typeOeuvre + ". Seules les series peuvent avoir des saisons");
        }
    }

    /**
     *
     * @return duree de l'oeuvre en minutes
     */
    public Integer getDuree() {
        return duree;
    }

    /**
     *
     * @param duree de l'oeuvre en minutes
     */
    public void setDuree(Integer duree) {
        if(typeOeuvre.equals(TYPE_FILM)){
            this.duree = duree;
        } else {
            throw new MauvaisParamException("Type d'oeuvre : " + typeOeuvre + ". Seules les films peuvent avoir des durees");
        }
    }

    @Override
    public String toString() {
        return "Oeuvre{" +
                "id=" + id +
                ", typeOeuvre='" + typeOeuvre + '\'' +
                ", titre='" + titre + '\'' +
                ", genres=" + genres +
                ", statutVisionnage=" + statutVisionnage +
                ", note=" + note +
                ", video='" + video + '\'' +
                ", saisons=" + saisons +
                ", duree=" + duree +
                '}';
    }
}
