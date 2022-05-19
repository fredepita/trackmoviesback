package fr.epita.trackmoviesback.dto;


public class SaisonDto {
    private Long id;
    private String numero;
    private StatutVisionnageDto statutVisionnage;
    private Integer nbEpisodes;

    public SaisonDto(Long id, String numero, StatutVisionnageDto statutVisionnage, Integer nbEpisodes) {
        this.id = id;
        this.numero = numero;
        this.statutVisionnage = statutVisionnage;
        this.nbEpisodes = nbEpisodes;
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

    public StatutVisionnageDto getStatutVisionnage() {
        return statutVisionnage;
    }

    public void setStatutVisionnage(StatutVisionnageDto statutVisionnage) {
        this.statutVisionnage = statutVisionnage;
    }

    public Integer getNbEpisodes() {
        return nbEpisodes;
    }

    public void setNbEpisodes(Integer nbEpisodes) {
        this.nbEpisodes = nbEpisodes;
    }

    @Override
    public String toString() {
        return "SaisonDto{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", statutVisionnage=" + statutVisionnage +
                ", nbEpisodes=" + nbEpisodes +
                '}';
    }
}
