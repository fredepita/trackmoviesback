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
    private String urlAffiche;
    private String urlBandeAnnonce;
    //donnee propre aux films
    private Integer duree;


    public OeuvreLightDto(Long id, String typeOeuvre, String titre, List<GenreDto> genres, StatutVisionnageDto statut, Integer note, String urlAffiche, String urlBandeAnnonce, Integer duree) {
        this.id = id;
        this.typeOeuvre = typeOeuvre;
        this.titre = titre;
        this.genres = genres;
        this.statut = statut;
        this.note = note;
        this.urlAffiche = urlAffiche;
        this.urlBandeAnnonce = urlBandeAnnonce;
        this.duree = duree;
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
        // if(type.equals(Oeuvre.TYPE_FILM) || type.equals(Oeuvre.TYPE_SERIE)){
        this.typeOeuvre = typeOeuvre;
        // } else {
        //     throw new MauvaisParamException("Valeur recue : " + type + ". Valeurs acceptees :" + TYPE_SERIE + " ou " + TYPE_FILM);
        //  }
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

    public String getUrlAffiche() { return urlAffiche;}

    public void setUrlAffiche(String urlAffiche) { this.urlAffiche = urlAffiche;}

    public String getUrlBandeAnnonce() {
        return urlBandeAnnonce;
    }

    public void setUrlBandeAnnonce(String urlBandeAnnonce) {
        this.urlBandeAnnonce = urlBandeAnnonce;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        //  if(type.equals(TYPE_FILM)){
        this.duree = duree;
        // } else {
        //    throw new MauvaisParamException("Type d'oeuvreDTO : " + type + ". Seules les films peuvent avoir des durees");
        // }
    }

    @Override
    public String toString() {
        return "OeuvrelightDto{" +
                "idDto=" + id +
                ", typeDto='" + typeOeuvre + '\'' +
                ", titreDto='" + titre + '\'' +
                ", genreDto=" + genres +
                ", statutVisionnageDto=" + statut +
                ", noteDto=" + note +
                ", videoDto='" + urlBandeAnnonce + '\'' +
                ", dureeDto=" + duree +
                '}';
    }
}
