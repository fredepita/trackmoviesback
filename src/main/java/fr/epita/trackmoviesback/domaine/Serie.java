package fr.epita.trackmoviesback.domaine;

import java.util.List;

public class Serie extends Oeuvre{

    private List<Saison> saisons;

    public Serie() {
    }

    public Serie(String type, String titre, GenreOeuvre genreOeuvre, StatutVisionnage statutVisionnage, Integer note, String video, List<Saison> saisons) {
        super(type, titre, genreOeuvre, statutVisionnage, note, video);
        this.saisons = saisons;
    }

    @Override
    public String toString() {
        return "Serie{" +
                "saisons=" + saisons +
                ", " + super.toString() +
                "}";
    }
}
