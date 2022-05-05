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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public List<OeuvreLightDto> getOeuvres() {
        return oeuvres;
    }

    public void setOeuvres(List<OeuvreLightDto> oeuvres) {
        this.oeuvres = oeuvres;
    }

    @Override
    public String toString() {
        return "OeuvreLightListDto{" +
                "page=" + page +
                ", totalpage=" + totalpage +
                ", oeuvres=" + oeuvres +
                '}';
    }
}


