package fr.epita.trackmoviesback.infrastructure.oeuvre;

import fr.epita.trackmoviesback.domaine.Oeuvre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

//utilise la base pour l'instant mais à brancher sur h2
@SpringBootTest
public class OeuvreRepositoryTest {
    @Autowired
    OeuvreRepository oeuvreRepository;

    @Test
    public void findAll_doit_retourner_toutes_les_oeuvres_d_un_utilisateur_sans_les_saisons_pour_les_series() {
        List<Oeuvre> oeuvres=oeuvreRepository.findAll();

        oeuvres.stream().forEach(System.out::println);

    }
}
