package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Oeuvre;
import fr.epita.trackmoviesback.domaine.Utilisateur;
import fr.epita.trackmoviesback.dto.OeuvreDto;
import fr.epita.trackmoviesback.dto.OeuvreLightDto;
import fr.epita.trackmoviesback.dto.OeuvreLightListDto;
import fr.epita.trackmoviesback.dto.formulaire.OeuvreFormulaireDto;


import java.util.Map;

public interface OeuvreService {

    OeuvreLightListDto getAllOeuvres(String userLogin);
    OeuvreLightDto convertirOeuvreEnLightDto(Oeuvre oeuvre);
    // getOeuvreCompleteById retourne une oeuvre et ses saisons (pour les séries) ou sa durée (pour les films)
    OeuvreDto getOeuvreCompleteById(String userLogin,Long id);

    OeuvreDto convertirOeuvreEnDto(Oeuvre oeuvre);

    Oeuvre convertirOeuvreDtoEnOeuvre(String userLogin, OeuvreDto oeuvreDto);

    Oeuvre convertirOeuvreFormulaireDtoEnOeuvre(String userLogin, OeuvreFormulaireDto oeuvreFormulaireDto);

    /**
     * Retourne une liste d'oeuvre (ordonnée par le titre) correspondant aux critères passés en paramètre.
     * A noter :
     *      - sur le titre, une recherche de type like, insensible à la casse, est appliquée.
     *      - pour les autres propriété on applique une égalité stricte
     *
     * @param criteres Map avec la liste des critères (Clé = nom propriete recherchee / Valeur = valeur recherchee)
     * @return listes des oeuvres correspondant aux critères de recherches
     */
    OeuvreLightListDto getOeuvres(String userLogin, Map<String,String> criteres);

    /**
     * ajoute une oeuvre à notre liste dans la BDD
     * Si l'id de l'oeuvre est positionné, on modifiera l'oeuvre en question
     *
     * Une oeuvre ne peut pas changer de type (passer de film à série et inversement)
     *
     * @param oeuvreDto oeuvre à ajouter
     * @return l'oeuvre créée
     */
    OeuvreDto saveOeuvre(String userLogin,OeuvreFormulaireDto oeuvreDto);

    /**
     * supprime une oeuvre
     * seul une oeuvre appartenant au user loggé peut etre supprimée
     *
     * @param userLogin  login du user
     * @param id id de l'oeuvre
     */
    void deleteOeuvre(String userLogin,Long id);

}
