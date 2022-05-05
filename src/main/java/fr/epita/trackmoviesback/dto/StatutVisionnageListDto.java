package fr.epita.trackmoviesback.dto;

import java.util.List;

public class StatutVisionnageListDto {

    List<StatutVisionnageDto> statuts;

    public StatutVisionnageListDto(List<StatutVisionnageDto> statuts) {
        this.statuts = statuts;
    }


    public List<StatutVisionnageDto> getStatuts() {
        return statuts;
    }

    public void setStatuts(List<StatutVisionnageDto> statuts) {
        this.statuts = statuts;
    }

    @Override
    public String toString() {
        return "StatutVisionnageListDto{" +
                "statuts=" + statuts +
                '}';
    }
}
