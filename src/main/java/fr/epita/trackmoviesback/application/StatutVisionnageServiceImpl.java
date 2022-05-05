package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Genre;
import fr.epita.trackmoviesback.domaine.StatutVisionnage;
import fr.epita.trackmoviesback.dto.GenreDto;
import fr.epita.trackmoviesback.dto.StatutVisionnageDto;
import fr.epita.trackmoviesback.dto.StatutVisionnageListDto;
import fr.epita.trackmoviesback.infrastructure.statutvisionnage.StatutVisionnageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatutVisionnageServiceImpl implements StatutVisionnageService {

    @Autowired
    StatutVisionnageRepository statutVisionnageRepository;

    @Override
    public StatutVisionnageListDto getAllStatutVisionnage() {
        List<StatutVisionnage> statutVisionnages = statutVisionnageRepository.findAll();
        List<StatutVisionnageDto> statutVisionnagesDto = statutVisionnages.stream()
                .map(this::convertirStatutVisionnageEnStatutVisionnageDto).collect(Collectors.toList());
        return new StatutVisionnageListDto(statutVisionnagesDto);
    }

    public StatutVisionnageDto convertirStatutVisionnageEnStatutVisionnageDto(StatutVisionnage statutVisionnage) {
        return new StatutVisionnageDto(statutVisionnage.getId(), statutVisionnage.getLibelle());

    }
}
