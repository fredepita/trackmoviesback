package fr.epita.trackmoviesback.dto;


import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;

import java.util.List;

/**
 * DTO à afficher sur la partie Detail
 */
public class OeuvreDto extends OeuvreLightDto{
    //donnee propre aux series

    //il faudra créer le SaisonDto
  //private List<SaisonDto> saisons;

    public OeuvreDto(Long id, String typeOeuvre, String titre, List<GenreDto> genres, StatutVisionnageDto statutVisionnage, Integer note, String video, Integer duree
                     // , List<SaisonDto> saisons
    ) {
        super(id, typeOeuvre, titre, genres, statutVisionnage, note, video, duree);
       // this.saisons = saisons;
    }

}
