package fr.epita.trackmoviesback.infrastructure.oeuvre;

import fr.epita.trackmoviesback.domaine.Oeuvre;
import fr.epita.trackmoviesback.enumerate.EnumOperationDeRecherche;
import fr.epita.trackmoviesback.enumerate.EnumProprieteRecherchableSurOeuvre;
import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;
import fr.epita.trackmoviesback.infrastructure.specs.CritereDeRecherche;
import fr.epita.trackmoviesback.infrastructure.specs.OeuvreSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.util.List;

//utilise la base pour l'instant mais à brancher sur h2
@SpringBootTest
public class OeuvreRepositoryTest {
    @Autowired
    OeuvreRepository oeuvreRepository;

    @Test
    public void findAll_doit_retourner_toutes_les_oeuvres_d_un_utilisateur_sans_les_saisons_pour_les_series() {
        OeuvreSpecification criteresDeRecherche= new OeuvreSpecification();
        List<Oeuvre> oeuvres=null;

        System.out.println("recherche des films:");

        criteresDeRecherche.add(new CritereDeRecherche(EnumProprieteRecherchableSurOeuvre.TYPE_OEUVRE, EnumTypeOeuvre.FILM, EnumOperationDeRecherche.EGAL));
        oeuvres= oeuvreRepository.findAll(criteresDeRecherche);
        oeuvres.stream().forEach(System.out::println);
/*
        System.out.println("recherche des series comedie:");
        criteresDeRecherche= new OeuvreSpecification();
        criteresDeRecherche.add(new CritereDeRecherche(EnumProprieteRecherchable.TYPE_OEUVRE, EnumTypeOeuvre.SERIE, EnumOperationDeRecherche.EGAL));
        criteresDeRecherche.add(new CritereDeRecherche(EnumProprieteRecherchable.GENRE, 2, EnumOperationDeRecherche.EGAL));
        oeuvres= oeuvreRepository.findAll(criteresDeRecherche);
        oeuvres.stream().forEach(System.out::println);

        //new ArrayList<Genre>().add(new Genre(Long.valueOf(2),"Comédie"))
        //ca ca marche
        //criteresDeRecherche2.add(new CritereDeRecherche("genres",2 , EnumOperationDeRecherche.EGAL));
        //List<Oeuvre> oeuvres2= oeuvreRepository.findAll(criteresDeRecherche2);
        //oeuvres2.stream().forEach(System.out::println);

        //criteresDeRecherche2.add(new CritereDeRecherche("genres",1 , EnumOperationDeRecherche.EGAL));
       System.out.println("recherche des oeuvre d'action:");
        criteresDeRecherche= new OeuvreSpecification();
        criteresDeRecherche.add(new CritereDeRecherche(EnumProprieteRecherchable.GENRE,1 , EnumOperationDeRecherche.EGAL));
        oeuvres= oeuvreRepository.findAll(criteresDeRecherche);
        oeuvres.stream().forEach(System.out::println);

        System.out.println("recherche Shazam!");
        criteresDeRecherche= new OeuvreSpecification();
        criteresDeRecherche.add(new CritereDeRecherche(EnumProprieteRecherchable.TITRE,"Shazam!" , EnumOperationDeRecherche.EGAL));
        oeuvres= oeuvreRepository.findAll(criteresDeRecherche);
        oeuvres.stream().forEach(System.out::println);
*/
        System.out.println("recherche commence par Sh");
        criteresDeRecherche= new OeuvreSpecification();
        criteresDeRecherche.add(new CritereDeRecherche(EnumProprieteRecherchableSurOeuvre.TITRE,"Sh" , EnumOperationDeRecherche.COMMENCE_PAR));
        oeuvres= oeuvreRepository.findAll(criteresDeRecherche);
        oeuvres.stream().forEach(System.out::println);



//        -->il faut tester le critere titre avec commence par et = et combinaison avec autre critère

    }


}
