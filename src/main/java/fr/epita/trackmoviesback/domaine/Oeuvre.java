package fr.epita.trackmoviesback.domaine;

import fr.epita.trackmoviesback.exception.MauvaisParamException;

import java.util.List;

public class Oeuvre {

    public final String TYPE_FILM = "film";
    public final String TYPE_SERIE = "serie";

    private Long id;
    private String type;
    private String titre;
    private GenreOeuvre genreOeuvre;
    private StatutVisionnage statutVisionnage;
    private Integer note;
    private String video;
    //donnee propre aux series
    private List<Saison> saisons;
    //donnee propre aux films
    private Integer duree;


    public Oeuvre() {
    }

    public Oeuvre(String type, String titre, GenreOeuvre genreOeuvre, StatutVisionnage statutVisionnage, Integer note, String video, List<Saison> saisons, Integer duree) {
        setType(type);
        this.titre = titre;
        this.genreOeuvre = genreOeuvre;
        this.statutVisionnage = statutVisionnage;
        this.note = note;
        this.video = video;
        setSaisons(saisons);
        setDuree(duree);
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
        if(type.equals(TYPE_FILM) || type.equals(TYPE_SERIE)){
            this.type = type;
        } else {
            throw new MauvaisParamException("Valeur recue : " + type + ". Valeurs acceptees :" + TYPE_SERIE + " ou " + TYPE_FILM);
        }
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public GenreOeuvre getGenreOeuvre() {
        return genreOeuvre;
    }

    public void setGenreOeuvre(GenreOeuvre genreOeuvre) {
        this.genreOeuvre = genreOeuvre;
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

    public List<Saison> getSaisons() {
        return saisons;
    }

    public void setSaisons(List<Saison> saisons) {
        if(type.equals(TYPE_SERIE)){
            this.saisons = saisons;
        } else {
            throw new MauvaisParamException("Type d'oeuvre : " + type + ". Seules les series peuvent avoir des saisons");
        }
    }


    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        if(type.equals(TYPE_FILM)){
            this.duree = duree;
        } else {
            throw new MauvaisParamException("Type d'oeuvre : " + type + ". Seules les films peuvent avoir des durees");
        }
    }

    @Override
    public String toString() {
        return "Oeuvre{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", titre='" + titre + '\'' +
                ", genreOeuvre=" + genreOeuvre +
                ", statutVisionnage=" + statutVisionnage +
                ", note=" + note +
                ", video='" + video + '\'' +
                ", saisons=" + saisons +
                ", duree=" + duree +
                '}';
    }
}
