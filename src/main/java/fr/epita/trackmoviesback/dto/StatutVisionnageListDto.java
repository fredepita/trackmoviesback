package fr.epita.trackmoviesback.dto;

import java.util.List;

public class StatutVisionnageListDto {

    List<StatutVisionnageDto> statuts;

    public StatutVisionnageListDto(List<StatutVisionnageDto> statuts) {
        this.statuts = statuts;
    }
}
