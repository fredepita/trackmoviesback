package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Film;
import fr.epita.trackmoviesback.domaine.Oeuvre;
import fr.epita.trackmoviesback.domaine.Serie;
import fr.epita.trackmoviesback.dto.OeuvreDto;
import fr.epita.trackmoviesback.dto.OeuvreLightDto;
import fr.epita.trackmoviesback.dto.OeuvreLightListDto;


import java.util.Map;
import java.util.Optional;

public interface OeuvreService {

    OeuvreLightListDto getAllOeuvres();
    OeuvreLightDto convertirOeuvreEnLightDto(Oeuvre oeuvre);
    OeuvreDto getOeuvreById(Long id);

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

    /**
     * ajoute une oeuvre à notre liste dans la BDD
     *
     * @param oeuvre oeuvre à ajouter
     * @return l'id de l'oeuvre créée
     */
    Long createOeuvre(Oeuvre oeuvre);

    void deleteOeuvre(Long id);

}
