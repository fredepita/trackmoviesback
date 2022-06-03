package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.StatutVisionnage;
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
        List<StatutVisionnageDto> statutsVisionnageDto = statutVisionnages.stream()
                .map(this::convertirStatutVisionnageEnDto).collect(Collectors.toList());
        return new StatutVisionnageListDto(statutsVisionnageDto);
    }

    @Override
    public StatutVisionnageDto convertirStatutVisionnageEnDto(StatutVisionnage statutVisionnage) {
        return statutVisionnage==null?null:new StatutVisionnageDto(statutVisionnage.getId(), statutVisionnage.getLibelle());
    }

    @Override
    public StatutVisionnage convertirStatutVisionnageDtoEnStatutVisionnage(StatutVisionnageDto statutVisionnageDto) {
        return statutVisionnageDto==null?null:new StatutVisionnage(statutVisionnageDto.getId(), statutVisionnageDto.getLibelle());
    }

    @Override
    public StatutVisionnage convertirStatutVisionnageIdEnStatutVisionnage(Long statutVisionnageId) {
        return statutVisionnageId==null?null:new StatutVisionnage(statutVisionnageId, "");
    }
}
