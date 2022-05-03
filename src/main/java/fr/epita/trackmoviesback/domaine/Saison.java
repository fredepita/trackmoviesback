package fr.epita.trackmoviesback.domaine;

import java.util.List;

public class Saison {

    private Long id;
    private String numero;
    private List<Episode> episodes;
    private String statut;
    private Integer note;

    public Saison() {
    }

    public Saison(String numero, List<Episode> episodes, String statut, Integer note) {
        this.numero = numero;
        this.episodes = episodes;
        this.statut = statut;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }
}
