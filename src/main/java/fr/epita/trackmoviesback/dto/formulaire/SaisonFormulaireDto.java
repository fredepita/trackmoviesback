package fr.epita.trackmoviesback.dto.formulaire;

import fr.epita.trackmoviesback.dto.StatutVisionnageDto;

public class SaisonFormulaireDto {

    private Long id;
    private String numero;
    private Long statutVisionnageId;
    private Integer nbEpisodes;


    public SaisonFormulaireDto(Long id, String numero, Long statutVisionnageId, Integer nbEpisodes) {
        this.id = id;
        this.numero = numero;
        this.statutVisionnageId = statutVisionnageId;
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

    public Long getStatutVisionnageId() {
        return statutVisionnageId;
    }

    public void setStatutVisionnageId(Long statutVisionnageId) {
        this.statutVisionnageId = statutVisionnageId;
    }

    public Integer getNbEpisodes() {
        return nbEpisodes;
    }

    public void setNbEpisodes(Integer nbEpisodes) {
        this.nbEpisodes = nbEpisodes;
    }

    @Override
    public String toString() {
        return "SaisonFormulaireDto{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", statutVisionnageId=" + statutVisionnageId +
                ", nbEpisodes=" + nbEpisodes +
                '}';
    }
}
