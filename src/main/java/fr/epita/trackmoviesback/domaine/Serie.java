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
        //uper.setTypeOeuvre(EnumTypeOeuvre.SERIE);
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
                "saisons=" + saisons +
                '}';
    }

}
