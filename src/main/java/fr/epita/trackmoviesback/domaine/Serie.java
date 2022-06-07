package fr.epita.trackmoviesback.domaine;

import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("serie")
public class Serie extends Oeuvre{

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name = "oeuvre_id") //définir le JoinColumn permet d'eviter la création d'une table de liaison inutile entre saison et episode puisqu'un episode appartient à une seule saison
    private List<Saison> saisons;

    public Serie() {
        super();
    }

    public Serie(Utilisateur utilisateur, Long id, String titre, List<Genre> genres, StatutVisionnage statutVisionnage, Integer note, String createurs, String acteurs, String urlAffiche, String urlBandeAnnonce, String description, List<Saison> saisons) {
        super(utilisateur, id, titre, genres, statutVisionnage, note, createurs, acteurs, urlAffiche, urlBandeAnnonce, description);
        this.saisons = saisons;
    }

    public List<Saison> getSaisons() {
        return saisons;
    }

    public void setSaisons(List<Saison> saisons) {
        this.saisons = saisons;
    }

    @Override
    public String toString() {
        return "Serie{" +
                "utilisateur=" + getUtilisateur().getLogin() +
                "id=" + getId() +
                ", titre='" + getTitre() + '\'' +
                ", genres=" + getGenres() +
                ", statutVisionnage=" + getStatutVisionnage() +
                ", note=" + getNote() +
                ", createur='" + getCreateurs() + '\'' +
                ", acteur='" + getActeurs() + '\'' +
                ", urlAffiche='" + getUrlAffiche() + '\'' +
                ", urlBandeAnnonce='" + getUrlBandeAnnonce() + '\'' +
                ", description='" + getDescription() + '\'' +
                "saisons=" + saisons +
                '}';
    }

}
