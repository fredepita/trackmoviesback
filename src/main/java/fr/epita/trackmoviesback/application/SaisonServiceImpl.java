package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Saison;
import fr.epita.trackmoviesback.domaine.StatutVisionnage;
import fr.epita.trackmoviesback.dto.SaisonDto;
import fr.epita.trackmoviesback.dto.StatutVisionnageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaisonServiceImpl implements SaisonService {

    @Autowired
    StatutVisionnageService statutVisionnageService;

    @Override
    public SaisonDto convertirSaisonEnDto(Saison saison) {
        if (saison==null) return null;
        StatutVisionnageDto statutVisionnageDto = statutVisionnageService.convertirStatutVisionnageEnDto(saison.getStatutVisionnage());
        return new SaisonDto(saison.getId(), saison.getNumero(), statutVisionnageDto, saison.getNbEpisodes());
    }

    @Override
    public List<SaisonDto> convertirListSaisonEnDto(List<Saison> saisons){
        return saisons==null?null:saisons.stream().map(this::convertirSaisonEnDto).collect(Collectors.toList());
    }

    @Override
    public Saison convertirSaisonDtoEnSaison(SaisonDto saisonDto) {
        if (saisonDto==null) return null;
        StatutVisionnage statutVisionnage = statutVisionnageService.convertirStatutVisionnageDtoEnStatutVisionnage(saisonDto.getStatutVisionnage());
        return new Saison(saisonDto.getId(), saisonDto.getNumero(), statutVisionnage, saisonDto.getNbEpisodes());
    }

    @Override
    public List<Saison> convertirListSaisonDtoEnListSaion(List<SaisonDto> saisonDtoList) {
        return saisonDtoList==null?null:saisonDtoList.stream().map(this::convertirSaisonDtoEnSaison).collect(Collectors.toList());
    }
}
