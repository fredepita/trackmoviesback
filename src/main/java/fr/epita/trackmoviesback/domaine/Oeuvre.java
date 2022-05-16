package fr.epita.trackmoviesback.domaine;

import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;
import fr.epita.trackmoviesback.exception.MauvaisParamException;
import fr.epita.trackmoviesback.infrastructure.converter.TypeOeuvreAttributeConverter;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public abstract class Oeuvre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Convert(converter = TypeOeuvreAttributeConverter.class)
    //permet de convertir en string l'enum pour le stocker en base de donnée. On utilise son libelle
    private EnumTypeOeuvre typeOeuvre;

    @Column(nullable = false, unique = true)
    private String titre;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable( //sert à forcer les nommage de la table de liaison et les colonnes(evite de se retrouver avec des pluriels)
            name = "oeuvre_genre",
            joinColumns = @JoinColumn(name = "oeuvre_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private StatutVisionnage statutVisionnage;
    private Integer note;

    private String createur;
    private String acteur;

    //lien url vers l'affiche de l'oeuvre
    private String urlAffiche;
    //lien url vers la Bande Annonce de l'oeuvre
    private String urlBandeAnnonce;



    public Oeuvre() {
    }

    public Oeuvre(Long id, EnumTypeOeuvre typeOeuvre, String titre, List<Genre> genres, StatutVisionnage statutVisionnage, Integer note, String createur, String acteur, String urlAffiche, String urlBandeAnnonce) {
        this.id = id;
        this.typeOeuvre = typeOeuvre;
        this.titre = titre;
        this.genres = genres;
        this.statutVisionnage = statutVisionnage;
        this.note = note;
        this.createur = createur;
        this.acteur = acteur;
        this.urlAffiche = urlAffiche;
        this.urlBandeAnnonce = urlBandeAnnonce;
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
        if (typeOeuvre == null)
            throw new MauvaisParamException("typeOeuvre ne peut pas etre null");
        this.typeOeuvre = typeOeuvre;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        if (StringUtils.hasText(titre)) {
            this.titre = titre;
        } else {
            throw new MauvaisParamException("Valeur recue : " + titre + ". Valeurs acceptees : le titre ne peut pas être vide ou null");
        }
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

    public String getUrlAffiche() {
        return urlAffiche;
    }

    public void setUrlAffiche(String image) {
        this.urlAffiche = image;
    }

    public String getUrlBandeAnnonce() {
        return urlBandeAnnonce;
    }

    public void setUrlBandeAnnonce(String video) {
        this.urlBandeAnnonce = video;
    }

    public String getCreateur() {
        return createur;
    }

    public void setCreateur(String createur) {
        this.createur = createur;
    }

    public String getActeur() {
        return acteur;
    }

    public void setActeur(String acteur) {
        this.acteur = acteur;
    }

    @Override
    public String toString() {
        return "Oeuvre{" +
                "id=" + id +
                ", typeOeuvre=" + typeOeuvre +
                ", titre='" + titre + '\'' +
                ", genres=" + genres +
                ", statutVisionnage=" + statutVisionnage +
                ", note=" + note +
                ", createur='" + createur + '\'' +
                ", acteur='" + acteur + '\'' +
                ", urlAffiche='" + urlAffiche + '\'' +
                ", urlBandeAnnonce='" + urlBandeAnnonce + '\'' +
                '}';
    }
}