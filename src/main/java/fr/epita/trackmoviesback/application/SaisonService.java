package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Saison;

import fr.epita.trackmoviesback.dto.SaisonDto;

import java.util.List;

public interface SaisonService {
    SaisonDto convertirSaisonEnDto(Saison saison);
    List<SaisonDto> convertirListSaisonEnDto(List<Saison> saisons);

    Saison convertirSaisonDtoEnSaison(SaisonDto saisonDto);
    List<Saison> convertirListSaisonDtoEnListSaison(List<SaisonDto> saisonDtoList);
}
