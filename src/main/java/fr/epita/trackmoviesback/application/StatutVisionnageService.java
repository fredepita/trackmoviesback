package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.StatutVisionnage;
import fr.epita.trackmoviesback.dto.StatutVisionnageDto;
import fr.epita.trackmoviesback.dto.StatutVisionnageListDto;

public interface StatutVisionnageService {
    StatutVisionnageListDto getAllStatutVisionnage();
    StatutVisionnageDto convertirStatutVisionnageEnDto(StatutVisionnage statutVisionnage);

    StatutVisionnage convertirStatutVisionnageDtoEnStatutVisionnage(StatutVisionnageDto statutVisionnageDto);

    StatutVisionnage convertirStatutVisionnageIdEnStatutVisionnage(Long statutVisionnageId);
}
