package fr.epita.trackmoviesback.dto;

import java.util.List;

/**
 * DTO à afficher sur la partie List
 */
public class OeuvreLightDto {

    private Long id;
    private String type;
    private String titre;
    private List<GenreDto> genres;
    private StatutVisionnageDto statut;
    private Integer note;
    private String video;
    //donnee propre aux films
    private Integer duree;


    public OeuvreLightDto(Long id, String type, String titre, List<GenreDto> genres, StatutVisionnageDto statut, Integer note, String video, Integer duree) {
        this.id = id;
        this.type = type;
        this.titre = titre;
        this.genres = genres;
        this.statut = statut;
        this.note = note;
        this.video = video;
        this.duree = duree;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        // if(type.equals(Oeuvre.TYPE_FILM) || type.equals(Oeuvre.TYPE_SERIE)){
        this.type = type;
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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
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
        return "OeuvrelightDTO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", titre='" + titre + '\'' +
                ", genreOeuvre=" + genres +
                ", statutVisionnage=" + statut +
                ", note=" + note +
                ", video='" + video + '\'' +
                ", duree=" + duree +
                '}';
    }
}
