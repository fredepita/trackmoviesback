package fr.epita.trackmoviesback.infrastructure.oeuvre;

import fr.epita.trackmoviesback.domaine.Genre;
import fr.epita.trackmoviesback.domaine.Oeuvre;
import fr.epita.trackmoviesback.enumerate.EnumOperationDeRecherche;
import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;
import fr.epita.trackmoviesback.infrastructure.specs.CritereDeRecherche;
import fr.epita.trackmoviesback.infrastructure.specs.OeuvreSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

//utilise la base pour l'instant mais à brancher sur h2
@SpringBootTest
public class OeuvreRepositoryTest {
    @Autowired
    OeuvreRepository oeuvreRepository;

    @Test
    public void findAll_doit_retourner_toutes_les_oeuvres_d_un_utilisateur_sans_les_saisons_pour_les_series() {
     /*   List<Oeuvre> oeuvres=oeuvreRepository.findAll();

        oeuvres.stream().forEach(System.out::println);


        //recherche que des films
        System.out.println("recherche des films:");
        OeuvreSpecification criteresDeRecherche= new OeuvreSpecification();
        criteresDeRecherche.add(new CritereDeRecherche("typeOeuvre", EnumTypeOeuvre.FILM, EnumOperationDeRecherche.EGAL));
        oeuvres = oeuvreRepository.findAll(criteresDeRecherche);
        oeuvres.stream().forEach(System.out::println);
*/
        System.out.println("recherche des series comedie:");
        OeuvreSpecification criteresDeRecherche2= new OeuvreSpecification();
       // criteresDeRecherche.add(new CritereDeRecherche("typeOeuvre", EnumTypeOeuvre.SERIE, EnumOperationDeRecherche.EGAL));

        //new ArrayList<Genre>().add(new Genre(Long.valueOf(2),"Comédie"))
        //ca ca marche
        /*criteresDeRecherche2.add(new CritereDeRecherche("genres",2 , EnumOperationDeRecherche.EGAL));
        List<Oeuvre> oeuvres2= oeuvreRepository.findAll(criteresDeRecherche2);
        oeuvres2.stream().forEach(System.out::println);*/

        criteresDeRecherche2.add(new CritereDeRecherche("genres",1 , EnumOperationDeRecherche.EGAL));
        List<Oeuvre> oeuvres2= oeuvreRepository.findAll(criteresDeRecherche2);
        oeuvres2.stream().forEach(System.out::println);


    }
}
