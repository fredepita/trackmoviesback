package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Oeuvre;
import fr.epita.trackmoviesback.dto.OeuvreLightDto;
import fr.epita.trackmoviesback.dto.OeuvreLightListDto;


import java.util.Map;

public interface OeuvreService {

    OeuvreLightListDto getAllOeuvres();
    public OeuvreLightDto convertirOeuvreEnDto(Oeuvre oeuvre);

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
