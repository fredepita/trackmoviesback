package fr.epita.trackmoviesback.dto;

public class UtilisateurDto {

    private String identifiant;

    private String motDePasse;

    public String getIdentifiant() {
        return this.identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getMotDePasse() {
        return this.motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    @Override
    public String toString() {
        return "UtilisateurDto{" +
                "identifiant='" + this.identifiant + '\'' +
                ", motDePasse='" + this.motDePasse + '\'' +
                '}';
    }
}
