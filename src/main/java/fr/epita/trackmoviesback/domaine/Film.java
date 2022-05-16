package fr.epita.trackmoviesback.domaine;

import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;

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
        super.setTypeOeuvre(EnumTypeOeuvre.FILM);
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


    public String toString() {
        return "Film{" +
                "id=" + getId() +
                ", typeOeuvre=" + getTypeOeuvre() +
                ", titre='" + getTitre() + '\'' +
                ", genres=" + getGenres() +
                ", statutVisionnage=" + getStatutVisionnage() +
                ", note=" + getNote() +
                ", createur='" + getCreateur() + '\'' +
                ", acteur='" + getActeur() + '\'' +
                ", urlAffiche='" + getUrlAffiche() + '\'' +
                ", urlBandeAnnonce='" + getUrlBandeAnnonce() + '\'' +
                "duree=" + duree +
                '}';
    }
}
