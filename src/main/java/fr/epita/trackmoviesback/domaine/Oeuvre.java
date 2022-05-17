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

    private String createurs;
    private String acteurs;

    //lien url vers l'affiche de l'oeuvre
    private String urlAffiche;
    //lien url vers la Bande Annonce de l'oeuvre
    private String urlBandeAnnonce;

    public Oeuvre() {
    }

    public Oeuvre(Long id, String titre, List<Genre> genres, StatutVisionnage statutVisionnage, Integer note, String createurs, String acteurs, String urlAffiche, String urlBandeAnnonce) {
        this.id = id;
        this.titre = titre;
        this.genres = genres;
        this.statutVisionnage = statutVisionnage;
        this.note = note;
        this.createurs = createurs;
        this.acteurs = acteurs;
        this.urlAffiche = urlAffiche;
        this.urlBandeAnnonce = urlBandeAnnonce;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCreateurs() {
        return createurs;
    }

    public void setCreateurs(String createur) {
        this.createurs = createur;
    }

    public String getActeurs() {
        return acteurs;
    }

    public void setActeurs(String acteur) {
        this.acteurs = acteur;
    }

    @Override
    public String toString() {
        return "Oeuvre{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", genres=" + genres +
                ", statutVisionnage=" + statutVisionnage +
                ", note=" + note +
                ", createurs='" + createurs + '\'' +
                ", acteurs='" + acteurs + '\'' +
                ", urlAffiche='" + urlAffiche + '\'' +
                ", urlBandeAnnonce='" + urlBandeAnnonce + '\'' +
                '}';
    }
}