package fr.epita.trackmoviesback.dto;

import java.util.List;

/**
 * Liste des DTO à afficher sur la partie List
 */
public class OeuvreLightListDto {

    private int page;
    private int totalpage;
    List<OeuvreLightDto> oeuvres;


    public OeuvreLightListDto(int page, int totalpage, List<OeuvreLightDto> oeuvres) {
        this.page = page;
        this.totalpage = totalpage;
        this.oeuvres = oeuvres;
    }
}


