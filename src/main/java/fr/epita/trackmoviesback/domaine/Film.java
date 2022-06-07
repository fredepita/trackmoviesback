package fr.epita.trackmoviesback.domaine;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Entity
@DiscriminatorValue("film")
public class Film extends Oeuvre{

    //duree exprimée en minute
    private Integer duree;

    public Film() {
        super();
    }

    public Film(Utilisateur utilisateur,Long id, String titre, List<Genre> genres, StatutVisionnage statutVisionnage, Integer note, String createurs, String acteurs, String urlAffiche, String urlBandeAnnonce, String description, Integer duree) {
        super(utilisateur, id, titre, genres, statutVisionnage, note, createurs, acteurs, urlAffiche, urlBandeAnnonce, description);
        this.duree = duree;
    }

    /**
     *
     * @return duree en minute
     */
    public Integer getDuree() {
        return duree;
    }

    /**
     *
     * @param duree en minute
     */
    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    @Override
    public String toString() {
        return "Film{" +
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
                "duree=" + duree +
                '}';
    }
}
