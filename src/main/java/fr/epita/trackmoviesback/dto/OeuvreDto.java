package fr.epita.trackmoviesback.dto;


import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;

import java.util.List;

/**
 * DTO à afficher sur la partie Detail
 */
public class OeuvreDto extends OeuvreLightDto{

    private List<SaisonDto> saisons;
    //donnee propre aux films
    private Integer duree;

    public OeuvreDto(Long id, String typeOeuvre, String titre, List<GenreDto> genres, StatutVisionnageDto statutVisionnage, Integer note, String createurs, String acteurs, String urlAffiche, String urlBandeAnnonce, String description, List<SaisonDto> saisons, Integer duree) {
        super(id, typeOeuvre, titre, genres, statutVisionnage, note, createurs, acteurs, urlAffiche, urlBandeAnnonce,description);
        this.saisons = saisons;
        this.duree = duree;
    }


    public List<SaisonDto> getSaisons() {
        return saisons;
    }

    public void setSaisons(List<SaisonDto> saisons) {
        this.saisons = saisons;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    @Override
    public String toString() {
        return "OeuvreDto{" +
                "id=" + getId() +
                ", typeOeuvre='" + getTypeOeuvre() + '\'' +
                ", titre='" + getTitre() + '\'' +
                ", genres=" + getGenres() +
                ", statutVisionnage=" + getStatutVisionnage() +
                ", note=" + getNote() +
                ", createurs='" + getCreateurs() + '\'' +
                ", acteurs='" + getActeurs() + '\'' +
                ", urlAffiche='" + getUrlAffiche() + '\'' +
                ", urlBandeAnnonce='" + getUrlBandeAnnonce() + '\'' +
                "saisons=" + saisons +
                ", duree=" + duree +
                '}';
    }
}
