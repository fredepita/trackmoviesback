package fr.epita.trackmoviesback.domaine;

public class Episode {
    private Long id;
    private String numero;
    private String statut;
    private String video;
    private Integer duree;


    public Episode() {
    }

    public Episode(String numero, String statut, String video, Integer duree) {
        this.numero = numero;
        this.statut = statut;
        this.video = video;
        this.duree = duree;
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

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }


}
