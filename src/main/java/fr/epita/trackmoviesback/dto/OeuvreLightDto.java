package fr.epita.trackmoviesback.dto;

import fr.epita.trackmoviesback.domaine.GenreOeuvre;
import fr.epita.trackmoviesback.domaine.StatutVisionnage;

/**
 * DTO à afficher sur la partie List
 */
public class OeuvreLightDto {

    private Long id;
    private String type;
    private String titre;
    private GenreOeuvre genreDTO;
    private StatutVisionnage statutVisionnage;
    private Integer note;
    private String video;
    //donnee propre aux films
    private Integer duree;


    public OeuvreLightDto(Long id, String type, String titre, GenreOeuvre genreDTO, StatutVisionnage statutVisionnage, Integer note, String video, Integer duree) {
        this.id = id;
        this.type = type;
        this.titre = titre;
        this.genreDTO = genreDTO;
        this.statutVisionnage = statutVisionnage;
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

    public GenreOeuvre getGenreOeuvre() {
        return genreDTO;
    }

    public void setGenreOeuvre(GenreOeuvre genreOeuvre) {
        this.genreDTO = genreOeuvre;
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
                ", genreOeuvre=" + genreDTO +
                ", statutVisionnage=" + statutVisionnage +
                ", note=" + note +
                ", video='" + video + '\'' +
                ", duree=" + duree +
                '}';
    }
}
