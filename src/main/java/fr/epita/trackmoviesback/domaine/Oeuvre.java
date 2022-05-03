package fr.epita.trackmoviesback.domaine;

public abstract class Oeuvre {

    public final String TYPE_FILM = "film";
    public final String TYPE_SERIE = "serie";

    private Long id;
    private String type;
    private String titre;
    private GenreOeuvre genreOeuvre;
    private StatutVisionnage statutVisionnage;
    private Integer note;
    private String video;


    public Oeuvre() {
    }

    public Oeuvre(String type, String titre, GenreOeuvre genreOeuvre, StatutVisionnage statutVisionnage, Integer note, String video) {
        this.type = type;
        this.titre = titre;
        this.genreOeuvre = genreOeuvre;
        this.statutVisionnage = statutVisionnage;
        this.note = note;
        this.video = video;
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
        this.type = type;
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
                '}';
    }

}
