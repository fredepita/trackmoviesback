package fr.epita.trackmoviesback.dto;

import fr.epita.trackmoviesback.domaine.GenreOeuvre;
import fr.epita.trackmoviesback.domaine.Saison;
import fr.epita.trackmoviesback.domaine.StatutVisionnage;

import java.util.List;

/**
 * DTO à afficher sur la partie Detail
 */
public class OeuvreDto extends OeuvreLightDto{
    //donnee propre aux series
    private List<Saison> saisons;

    public OeuvreDto(Long id, String type, String titre, GenreOeuvre genreDTO, StatutVisionnage statutVisionnage, Integer note, String video, Integer duree, List<Saison> saisons) {
        super(id, type, titre, genreDTO, statutVisionnage, note, video, duree);
        this.saisons = saisons;
    }
}
