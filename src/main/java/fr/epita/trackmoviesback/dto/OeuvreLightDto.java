package fr.epita.trackmoviesback.dto;

import java.util.List;

/**
 * DTO à afficher sur la partie List
 */
public class OeuvreLightDto {

    private Long id;
    private String typeOeuvre;
    private String titre;
    private List<GenreDto> genres;
    private StatutVisionnageDto statutVisionnage;
    private Integer note;
    private String createurs;
    private String acteurs;
    private String urlAffiche;
    private String urlBandeAnnonce;
    private String description;

    public OeuvreLightDto(Long id, String typeOeuvre, String titre, List<GenreDto> genres, StatutVisionnageDto statutVisionnage, Integer note, String createurs, String acteurs, String urlAffiche, String urlBandeAnnonce, String description) {
        this.id = id;
        this.typeOeuvre = typeOeuvre;
        this.titre = titre;
        this.genres = genres;
        this.statutVisionnage = statutVisionnage;
        this.note = note;
        this.createurs = createurs;
        this.acteurs = acteurs;
        this.urlAffiche = urlAffiche;
        this.urlBandeAnnonce = urlBandeAnnonce;
        this.description = description;
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
        this.typeOeuvre = typeOeuvre;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public List<GenreDto> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDto> genres) {
        this.genres = genres;
    }

    public StatutVisionnageDto getStatutVisionnage() {
        return statutVisionnage;
    }

    public void setStatutVisionnage(StatutVisionnageDto statutVisionnage) {
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

    public void setUrlAffiche(String urlAffiche) {
        this.urlAffiche = urlAffiche;
    }

    public String getUrlBandeAnnonce() {
        return urlBandeAnnonce;
    }

    public void setUrlBandeAnnonce(String urlBandeAnnonce) {
        this.urlBandeAnnonce = urlBandeAnnonce;
    }

    public String getCreateurs() {
        return createurs;
    }

    public void setCreateurs(String createurs) {
        this.createurs = createurs;
    }

    public String getActeurs() {
        return acteurs;
    }

    public void setActeurs(String acteurs) {
        this.acteurs = acteurs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "OeuvreLightDto{" +
                "id=" + id +
                ", typeOeuvre='" + typeOeuvre + '\'' +
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
