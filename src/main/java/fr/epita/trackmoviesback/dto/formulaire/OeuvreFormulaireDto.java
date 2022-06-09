package fr.epita.trackmoviesback.dto.formulaire;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


/*@JsonIgnoreProperties permet d'ignorer les champs non fourni ou fourni avec ""
qui sont mappés vers des liste comme genreIds par exemple. Cela va de paire avec l'objet ObjectMapperConfig
sinon ca genere des erreurs HttpMessageNotReadableException: JSON parse error: Cannot coerce empty String ("")
*/
@JsonIgnoreProperties(ignoreUnknown = true)
public class OeuvreFormulaireDto {
    private Long id;

    @NotNull
    private String typeOeuvre;

    @NotNull
    @Size(min = 1, max=50)
    private String titre;

    private List<Long> genreIds;
    private Long statutVisionnageId;
    private Integer note;
    @Size(max = 100)
    private String createurs;
    @Size(max = 100)
    private String acteurs;
    @Size(max = 150)
    private String urlAffiche;
    @Size(max = 150)
    private String urlBandeAnnonce;

    //donnee propre aux series
    private List<SaisonFormulaireDto> saisons;

    //donnee propre aux films
    private Integer duree;

    @Size(max = 250)
    private String description;

    public OeuvreFormulaireDto() {
    }

    public OeuvreFormulaireDto(Long id, String typeOeuvre, String titre, List<Long> genreIds, Long statutVisionnageId, Integer note, String createurs, String acteurs, String urlAffiche, String urlBandeAnnonce, String description, List<SaisonFormulaireDto> saisons, Integer duree) {
        this.id = id;
        this.typeOeuvre = typeOeuvre;
        this.titre = titre;
        this.genreIds = genreIds;
        this.statutVisionnageId = statutVisionnageId;
        this.note = note;
        this.createurs = createurs;
        this.acteurs = acteurs;
        this.urlAffiche = urlAffiche;
        this.urlBandeAnnonce = urlBandeAnnonce;
        this.description = description;
        this.saisons = saisons;
        this.duree = duree;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeOeuvre() {
        return typeOeuvre;
    }

    public void setTypeOeuvre(String typeOeuvre) {
        this.typeOeuvre = typeOeuvre;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public List<Long> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Long> genreIds) {
        this.genreIds = genreIds;
    }

    public Long getStatutVisionnageId() {
        return statutVisionnageId;
    }

    public void setStatutVisionnageId(Long statutVisionnageId) {
        this.statutVisionnageId = statutVisionnageId;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public String getCreateurs() {
        return createurs;
    }

    public void setCreateurs(String createurs) {
        this.createurs = createurs;
    }

    public String getActeurs() {
        return acteurs;
    }

    public void setActeurs(String acteurs) {
        this.acteurs = acteurs;
    }

    public String getUrlAffiche() {
        return urlAffiche;
    }

    public void setUrlAffiche(String urlAffiche) {
        this.urlAffiche = urlAffiche;
    }

    public String getUrlBandeAnnonce() {
        return urlBandeAnnonce;
    }

    public void setUrlBandeAnnonce(String urlBandeAnnonce) {
        this.urlBandeAnnonce = urlBandeAnnonce;
    }

    public List<SaisonFormulaireDto> getSaisons() {
        return saisons;
    }

    public void setSaisons(List<SaisonFormulaireDto> saisons) {
        this.saisons = saisons;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "OeuvreFormulaireDto{" +
                "id=" + id +
                ", typeOeuvre='" + typeOeuvre + '\'' +
                ", titre='" + titre + '\'' +
                ", genreIds=" + genreIds +
                ", statutVisionnageId=" + statutVisionnageId +
                ", note=" + note +
                ", createurs='" + createurs + '\'' +
                ", acteurs='" + acteurs + '\'' +
                ", urlAffiche='" + urlAffiche + '\'' +
                ", urlBandeAnnonce='" + urlBandeAnnonce + '\'' +
                ", saisons=" + saisons +
                ", duree=" + duree +
                ", description='" + description + '\'' +
                '}';
    }
}
