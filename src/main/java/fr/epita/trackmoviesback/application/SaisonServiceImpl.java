package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Saison;
import fr.epita.trackmoviesback.dto.SaisonDto;
import fr.epita.trackmoviesback.dto.StatutVisionnageDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class SaisonServiceImpl {

    @Autowired
    StatutVisionnageService statutVisionnageService;


    SaisonDto convertirSaisonEnDto(Saison saison) {

        StatutVisionnageDto statutVisionnageDto = statutVisionnageService.convertirStatutVisionnageEnDto(saison.getStatutVisionnage());
        return new SaisonDto(saison.getId(), saison.getNumero(), statutVisionnageDto, saison.getNbEpisodes());
    }

    List<SaisonDto> convertirListSaisonEnDto(List<Saison> saisons){
        return saisons.stream().map(this::convertirSaisonEnDto).collect(Collectors.toList());


    }
}
