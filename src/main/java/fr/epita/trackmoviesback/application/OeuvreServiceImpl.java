package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Oeuvre;
import fr.epita.trackmoviesback.dto.GenreDto;
import fr.epita.trackmoviesback.dto.OeuvreLightDto;

import fr.epita.trackmoviesback.dto.OeuvreLightListDto;
import fr.epita.trackmoviesback.dto.StatutVisionnageDto;
import fr.epita.trackmoviesback.infrastructure.oeuvre.OeuvreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OeuvreServiceImpl implements OeuvreService{

    @Autowired
    OeuvreRepository oeuvreRepository;
    @Autowired
    GenreService genreService;
    @Autowired
    StatutVisionnageService statutVisionnageService;

    @Override
    public OeuvreLightListDto getAllOeuvres() {
        List<Oeuvre> oeuvres = oeuvreRepository.findAll();
       List<OeuvreLightDto> oeuvresLightDto = oeuvres.stream()
               .map(this::convertirOeuvreEnDto).collect(Collectors.toList());
        return new OeuvreLightListDto(1,1, oeuvresLightDto);
    }


    private OeuvreLightDto convertirOeuvreEnDto(Oeuvre oeuvre) {
        List<GenreDto> genresDto = genreService.convertirListGenreEnDto(oeuvre.getGenres());
        StatutVisionnageDto statutVisionnageDto = statutVisionnageService.convertirStatutVisionnageEnDto(oeuvre.getStatutVisionnage());
        String typeOeuvre= oeuvre.getTypeOeuvre()==null?null:oeuvre.getTypeOeuvre().getLibelle();
        return new OeuvreLightDto(oeuvre.getId(),typeOeuvre , oeuvre.getTitre(), genresDto, statutVisionnageDto, oeuvre.getNote(), oeuvre.getVideo(), oeuvre.getDuree());
    }

}
