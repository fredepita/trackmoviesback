package fr.epita.trackmoviesback.dto;


import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;

import java.util.List;

/**
 * DTO à afficher sur la partie Detail
 */
public class OeuvreDto extends OeuvreLightDto{


    //il faudra créer le SaisonDto
    //private List<SaisonDto> saisons;

    //donnee propre aux films
    //private Integer duree;


    public OeuvreDto(Long id, String typeOeuvre, String titre, List<GenreDto> genres, StatutVisionnageDto statutVisionnage, Integer note,String createur, String acteur, String urlAffiche, String urlBandeAnnonce, Integer duree
           // , List<SaisonDto> saisons
    ) {
        super(id, typeOeuvre, titre, genres, statutVisionnage, note, createur, acteur, urlAffiche, urlBandeAnnonce);

    }

}
