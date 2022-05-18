package fr.epita.trackmoviesback.dto;

import java.util.List;

public class SaisonListDto {

    List<SaisonDto> saisons;

    public SaisonListDto(List<SaisonDto> saisons) {
        this.saisons = saisons;
    }

    public List<SaisonDto> getSaisons() {
        return saisons;
    }

    public void setSaisons(List<SaisonDto> saisons) {
        this.saisons = saisons;
    }

    @Override
    public String toString() {
        return "SaisonListDto{" +
                "saisons=" + saisons +
                '}';
    }
}
