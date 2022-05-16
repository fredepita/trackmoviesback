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
    private StatutVisionnageDto statut;
    private Integer note;
    private String createur;
    private String acteur;
    private String urlAffiche;
    private String urlBandeAnnonce;

    public OeuvreLightDto(Long id, String typeOeuvre, String titre, List<GenreDto> genres, StatutVisionnageDto statut, Integer note, String createur, String acteur, String urlAffiche, String urlBandeAnnonce) {
        this.id = id;
        this.typeOeuvre = typeOeuvre;
        this.titre = titre;
        this.genres = genres;
        this.statut = statut;
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

    public StatutVisionnageDto getStatut() {
        return statut;
    }

    public void setStatut(StatutVisionnageDto statut) {
        this.statut = statut;
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
        return "OeuvreLightDto{" +
                "id=" + id +
                ", typeOeuvre='" + typeOeuvre + '\'' +
                ", titre='" + titre + '\'' +
                ", genres=" + genres +
                ", statut=" + statut +
                ", note=" + note +
                ", createur='" + createur + '\'' +
                ", acteur='" + acteur + '\'' +
                ", urlAffiche='" + urlAffiche + '\'' +
                ", urlBandeAnnonce='" + urlBandeAnnonce + '\'' +
                '}';
    }
}
