package fr.epita.trackmoviesback.Utilitaire;

import fr.epita.trackmoviesback.dto.GenreDto;
import fr.epita.trackmoviesback.dto.OeuvreDto;
import fr.epita.trackmoviesback.dto.SaisonDto;
import fr.epita.trackmoviesback.dto.StatutVisionnageDto;
import fr.epita.trackmoviesback.dto.formulaire.OeuvreFormulaireDto;
import fr.epita.trackmoviesback.dto.formulaire.SaisonFormulaireDto;
import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;

import java.util.ArrayList;
import java.util.List;

public class GenerateurObjetTest {

    public static OeuvreDto createOeuvreDtoMinimal(String typeOeuvre, String titreFilm) {
        return new OeuvreDto(null,typeOeuvre, titreFilm,
                null,null,null,null,null,
                null,null,null,null,null);
    }

    public static OeuvreDto createOeuvreDtoComplete(String typeOeuvre,String titreFilm) {
        OeuvreDto oeuvreDto=createOeuvreDtoMinimal(typeOeuvre,titreFilm);
        List<GenreDto> genreDtoList = new ArrayList<>();
        genreDtoList.add(new GenreDto(1L,"Action"));
        genreDtoList.add(new GenreDto(2L,"Comédie"));
        oeuvreDto.setGenres(genreDtoList);
        StatutVisionnageDto statutVisionnageDto= new StatutVisionnageDto(1L,"A voir");
        oeuvreDto.setStatutVisionnage(statutVisionnageDto);
        oeuvreDto.setNote(2);
        oeuvreDto.setCreateurs("createur1");
        oeuvreDto.setActeurs("Acteur1, Acteur2");
        oeuvreDto.setUrlAffiche("monAfficheTest");
        oeuvreDto.setUrlBandeAnnonce("maBOTest");

        if (typeOeuvre.equals(EnumTypeOeuvre.FILM.getLibelle())) {
            oeuvreDto.setDuree(120);
        } else {
            //on ajoute les saisons (on reutilise le statut visionnage de l'oeuvre pour les saisons)
            List<SaisonDto> saisonDtoList = new ArrayList<>();
            saisonDtoList.add(new SaisonDto ( null ,"S01_test",oeuvreDto.getStatutVisionnage(),5));
            saisonDtoList.add(new SaisonDto(null,"S02_test",oeuvreDto.getStatutVisionnage(),5));
            oeuvreDto.setSaisons(saisonDtoList);
        }

        return oeuvreDto;
    }

    public static OeuvreFormulaireDto createOeuvreDtoFormulaireMinimal(String typeOeuvre, String titreFilm) {
        return new OeuvreFormulaireDto(null,typeOeuvre, titreFilm,
                null,null,null,null,null,
                null,null,null,null,null);
    }

    public static OeuvreFormulaireDto createOeuvreFormulaireDtoComplete(String typeOeuvre,String titreFilm) {
        OeuvreFormulaireDto oeuvreFormulaireDto= createOeuvreDtoFormulaireMinimal(typeOeuvre,titreFilm);
        List<Long> genreIdsList = new ArrayList<>();
        genreIdsList.add(1L);
        genreIdsList.add(2L);
        oeuvreFormulaireDto.setGenreIds(genreIdsList);
        Long statutVisionnageId= 1L;
        oeuvreFormulaireDto.setStatutVisionnageId(statutVisionnageId);
        oeuvreFormulaireDto.setNote(2);
        oeuvreFormulaireDto.setCreateurs("createur1");
        oeuvreFormulaireDto.setActeurs("Acteur1, Acteur2");
        oeuvreFormulaireDto.setUrlAffiche("monAfficheTest");
        oeuvreFormulaireDto.setUrlBandeAnnonce("maBOTest");

        if (typeOeuvre.equals(EnumTypeOeuvre.FILM.getLibelle())) {
            oeuvreFormulaireDto.setDuree(120);
        } else {
            //on ajoute les saisons (on reutilise le statut visionnage de l'oeuvre pour les saisons)
            List<SaisonFormulaireDto> saisonDtoList = new ArrayList<>();
            saisonDtoList.add(new SaisonFormulaireDto ( null ,"S01_test",oeuvreFormulaireDto.getStatutVisionnageId(),5));
            saisonDtoList.add(new SaisonFormulaireDto(null,"S02_test",oeuvreFormulaireDto.getStatutVisionnageId(),5));
            oeuvreFormulaireDto.setSaisons(saisonDtoList);
        }

        return oeuvreFormulaireDto;
    }
}
