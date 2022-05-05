package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.StatutVisionnage;
import fr.epita.trackmoviesback.dto.GenreDto;
import fr.epita.trackmoviesback.dto.OeuvreLightListDto;
import fr.epita.trackmoviesback.dto.StatutVisionnageDto;
import fr.epita.trackmoviesback.dto.StatutVisionnageListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatutVisionnageServiceImpl implements StatutVisionnageService{

    @Autowired
    StatutVisionnageDao dao;

    @Override
    public StatutVisionnageListDto getAllStatutVisionnage() {
        List<StatutVisionnageDto> statutVisionnagesDto = dao.getAllStatutVisionnage().stream()
                .map(this::convertirStatutVisionnageEnStatutVisionnageDto);
        return new StatutVisionnageListDto(statutVisionnagesDto);
    }

    private StatutVisionnageDto convertirStatutVisionnageEnStatutVisionnageDto(StatutVisionnage statutVisionnage) {
        StatutVisionnageDto statutVisionnageDto =new StatutVisionnageDto(statutVisionnage.getId(), statutVisionnage.getLibelle());
        return statutVisionnageDto;
}
