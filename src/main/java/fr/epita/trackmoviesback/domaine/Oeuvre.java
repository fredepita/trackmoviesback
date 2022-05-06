package fr.epita.trackmoviesback.domaine;

import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;
import fr.epita.trackmoviesback.exception.MauvaisParamException;
import fr.epita.trackmoviesback.infrastructure.converter.TypeOeuvreAttributeConverter;

import javax.persistence.*;
import java.util.List;

@Entity
public class Oeuvre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Convert(converter = TypeOeuvreAttributeConverter.class) //permet de convertir en string l'enum pour le stocker en base de donnée. On utilise son libelle
    private EnumTypeOeuvre typeOeuvre;

    @Column(nullable = false,unique = true)
    private String titre;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable( //sert à forcer les nommage de la table de liaison et les colonnes(evite de se retrouver avec des pluriels)
            name="oeuvre_genre",
            joinColumns = @JoinColumn(name = "oeuvre_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private StatutVisionnage statutVisionnage;
    private Integer note;
    //lien url vers l'affiche de l'oeuvre
    private String urlAffiche;
    //lien url vers la Bande Annonce de l'oeuvre
    private String urlBandeAnnonce;

    //donnee propre aux series
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name = "oeuvre_id") //définir le JoinColumn permet d'eviter la création d'une table de liaison inutile entre saison et episode puisqu'un episode appartient à une seule saison
    private List<Saison> saisons;

    //donnee propre aux films
    //duree exprimée en minute
    private Integer duree;


    public Oeuvre() {
    }

    public Oeuvre(EnumTypeOeuvre typeOeuvre, String titre, List<Genre> genres, StatutVisionnage statutVisionnage, Integer note, String urlAffiche, String urlBandeAnnonce, List<Saison> saisons, Integer duree) {
        setTypeOeuvre(typeOeuvre);
        this.titre = titre;
        this.genres = genres;
        this.statutVisionnage = statutVisionnage;
        this.note = note;
        this.urlAffiche = urlAffiche;
        this.urlBandeAnnonce = urlBandeAnnonce;
        setSaisons(saisons);
        setDuree(duree);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumTypeOeuvre getTypeOeuvre() {
        return typeOeuvre;
    }

    public void setTypeOeuvre(EnumTypeOeuvre typeOeuvre) {
        if (typeOeuvre==null)
            throw new MauvaisParamException("typeOeuvre ne peut pas etre null");
        this.typeOeuvre = typeOeuvre;
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

    public String getUrlAffiche() { return urlAffiche;}

    public void setUrlAffiche(String image) { this.urlAffiche = image;}

    public String getUrlBandeAnnonce() {
        return urlBandeAnnonce;
    }

    public void setUrlBandeAnnonce(String video) {
        this.urlBandeAnnonce = video;
    }

    public List<Saison> getSaisons() {
        return saisons;
    }

    public void setSaisons(List<Saison> saisons) {
        if(typeOeuvre==EnumTypeOeuvre.SERIE){
            this.saisons = saisons;
        } else {
            throw new MauvaisParamException("Type d'oeuvre : " + typeOeuvre.getLibelle() + ". Seule "+EnumTypeOeuvre.SERIE.getLibelle()+" peut avoir des saisons");
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
        if(typeOeuvre==EnumTypeOeuvre.FILM){
            this.duree = duree;
        } else {
            throw new MauvaisParamException("Type d'oeuvre : " + typeOeuvre + ". Seul "+EnumTypeOeuvre.FILM.getLibelle()+" peuvent avoir des durees");
        }
    }


    /**
     *
     * @return le contenu d'une oeuvre sans les saisons (pour les séries)
     */
    @Override
    public String toString() {
        //on ne retourne pas les saisons ici car on est en LAZY load sur cette partie
        return "Oeuvre{" +
                "id=" + id +
                ", typeOeuvre='" + typeOeuvre + '\'' +
                ", titre='" + titre + '\'' +
                ", genres=" + genres +
                ", statutVisionnage=" + statutVisionnage +
                ", note=" + note +
                ", urlAffiche='" + urlAffiche + '\'' +
                ", urlBandeAnnonce='" + urlBandeAnnonce + '\'' +
                ", duree=" + duree +
                '}';
    }

    /**
     *
     * @return le contenu d'une oeuvre avec les saisons pour les séries
     */
    public String toStringAvecSaison() {
        return "Oeuvre{" +
                "id=" + id +
                ", typeOeuvre='" + typeOeuvre + '\'' +
                ", titre='" + titre + '\'' +
                ", genres=" + genres +
                ", statutVisionnage=" + statutVisionnage +
                ", note=" + note +
                ", urlAffiche='" + urlAffiche + '\'' +
                ", urlBandeAnnonce='" + urlBandeAnnonce + '\'' +
                ", saisons=" + saisons +
                ", duree=" + duree +
                '}';
    }
}
