package fr.epita.trackmoviesback.domaine;

public class Film extends Oeuvre{

    private Integer duree;

    public Film(String type, String titre, GenreOeuvre genreOeuvre, StatutVisionnage statutVisionnage, Integer note, String video, Integer duree) {
        super(type, titre, genreOeuvre, statutVisionnage, note, video);
        this.duree = duree;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    @Override
    public String toString() {
        return "Film{" +
                "duree=" + duree +
                ", " + super.toString() +
                "}";
    }
}
