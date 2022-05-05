package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.StatutVisionnage;
import fr.epita.trackmoviesback.dto.StatutVisionnageDto;
import fr.epita.trackmoviesback.dto.StatutVisionnageListDto;

import java.util.List;

public interface StatutVisionnageService {

    StatutVisionnageListDto getAllStatutVisionnage();

    StatutVisionnageDto convertirStatutVisionnageEnStatutVisionnageDto(StatutVisionnage statutVisionnage);
}
