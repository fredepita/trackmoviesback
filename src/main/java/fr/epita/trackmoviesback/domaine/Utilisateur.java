package fr.epita.trackmoviesback.domaine;

import fr.epita.trackmoviesback.enumerate.EnumTypeRole;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String identifiant;
    private String motDePasse;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    Set<EnumTypeRole> roles = new HashSet<>();

    public Utilisateur() {
    }

    /*
    public Utilisateur(String identifiant, String motDePasse, Set<EnumTypeRole> roles) {

        this.identifiant = identifiant;
        this.motDePasse = motDePasse;
        this.roles = roles;
    }
*/
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String nom) {
        this.identifiant = nom;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Set<EnumTypeRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<EnumTypeRole> roles) {
        this.roles = roles;
    }

    public void addRole(EnumTypeRole role) {
        this.roles.add(role);
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", identifiant='" + identifiant + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", roles=" + roles +
                '}';
    }
}
