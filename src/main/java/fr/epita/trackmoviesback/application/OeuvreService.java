package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Oeuvre;
import fr.epita.trackmoviesback.dto.OeuvreDto;
import fr.epita.trackmoviesback.dto.OeuvreLightDto;
import fr.epita.trackmoviesback.dto.OeuvreLightListDto;


import java.util.Map;

public interface OeuvreService {

    OeuvreLightListDto getAllOeuvres();
    OeuvreLightDto convertirOeuvreEnLightDto(Oeuvre oeuvre);
    // getOeuvreCompleteById retourne une oeuvre et ses saisons (pour les séries) ou sa durée (pour les films)
    OeuvreDto getOeuvreCompleteById(Long id);
    OeuvreDto convertirOeuvreEnOeuvreDto(Oeuvre oeuvre);

    /**
     * Retourne une liste d'oeuvre (ordonnée par le titre) correspondant aux critères passés en paramètre.
     * A noter :
     *      - sur le titre, une recherche de type like, insensible à la casse, est appliquée.
     *      - pour les autres propriété on applique une égalité stricte
     *
     * @param criteres Map avec la liste des critères (Clé = nom propriete recherchee / Valeur = valeur recherchee)
     * @return listes des oeuvres correspondant aux critères de recherches
     */
    OeuvreLightListDto getOeuvres(Map<String,String> criteres);

}
