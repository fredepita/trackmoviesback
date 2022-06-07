package fr.epita.trackmoviesback.domaine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;
import fr.epita.trackmoviesback.exception.MauvaisParamException;
import fr.epita.trackmoviesback.infrastructure.converter.TypeOeuvreAttributeConverter;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type_oeuvre", discriminatorType = DiscriminatorType.STRING)
@Table(//on indexe les colonnes utilisée dans nos requetes les plus courantes
        indexes = {
            @Index(name="utilisateur_id_index", columnList = "utilisateur_id"),//recherche des oeuvres d'un user
            @Index(name="oeuvre_et_utilisateur_id_index", columnList = "utilisateur_id,id"), //recherche detail d'une oeuvre d'un user
            @Index(name="titre_index", columnList = "titre") //recherche sur titre
        }
)
public abstract class Oeuvre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //colonne gérée par le discriminator value des subclass, doit avoir seulement un getter pas de settre
    @Column(name="type_oeuvre", insertable = false, updatable = false)
    @Convert(converter = TypeOeuvreAttributeConverter.class) //permet de convertir en string l'enum pour le stocker en base de donnée. On utilise son libelle
    protected EnumTypeOeuvre type_oeuvre; //on doit garder ce nommage pour matcher le DiscriminatorColumn name (qui lui correspond à la colonne BDD)

    @Column(nullable = false)
    private String titre;

    //genre etant une table de reference, oeuvre ne peut pas en creer de nouveau, il peut juste s'y rattacher
    //@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( //sert à forcer les nommage de la table de liaison et les colonnes(evite de se retrouver avec des pluriels)
            name = "oeuvre_genre",
            joinColumns = @JoinColumn(name = "oeuvre_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    //statut visionnage etant une table de reference, oeuvre ne peut pas en creer de nouveau, il peut juste s'y rattacher
    @ManyToOne(fetch = FetchType.EAGER)
    private StatutVisionnage statutVisionnage;
    private Integer note;

    private String createurs;
    private String acteurs;

    //lien url vers l'affiche de l'oeuvre
    private String urlAffiche;
    //lien url vers la Bande Annonce de l'oeuvre
    private String urlBandeAnnonce;

    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "utilisateur_id" ,nullable = false)
    private Utilisateur utilisateur;

    protected Oeuvre() {
    }

    protected Oeuvre(Utilisateur utilisateur, Long id, String titre, List<Genre> genres, StatutVisionnage statutVisionnage, Integer note, String createurs, String acteurs, String urlAffiche, String urlBandeAnnonce, String description) {
        this.utilisateur=utilisateur;
        this.id = id;
        this.titre = titre;
        this.genres = genres;
        this.statutVisionnage = statutVisionnage;
        this.note = note;
        //on positionne a null les champs string si on a des chaine vide ou null en parametres
        this.createurs = StringUtils.hasText(createurs)?createurs:null;
        this.acteurs = StringUtils.hasText(acteurs)?acteurs:null;
        this.urlAffiche = StringUtils.hasText(urlAffiche)?urlAffiche:null;
        this.urlBandeAnnonce = StringUtils.hasText(urlBandeAnnonce)?urlBandeAnnonce:null;
        this.description = StringUtils.hasText(description)?description:null;

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

    public EnumTypeOeuvre getType_oeuvre() {
        return type_oeuvre;
    }

    public void setType_oeuvre(EnumTypeOeuvre type_oeuvre) {
        this.type_oeuvre = type_oeuvre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public String toString() {
        return "Oeuvre{" +
                "utilisateur=" + utilisateur.getLogin() +
                "id=" + id +
                ", type_oeuvre=" + type_oeuvre +
                ", titre='" + titre + '\'' +
                ", genres=" + genres +
                ", statutVisionnage=" + statutVisionnage +
                ", note=" + note +
                ", createurs='" + createurs + '\'' +
                ", acteurs='" + acteurs + '\'' +
                ", urlAffiche='" + urlAffiche + '\'' +
                ", urlBandeAnnonce='" + urlBandeAnnonce + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}