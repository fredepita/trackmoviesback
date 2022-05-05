package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Oeuvre;
import fr.epita.trackmoviesback.dto.OeuvreLightDto;

import fr.epita.trackmoviesback.dto.OeuvreLightListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OeuvreServiceImpl implements OeuvreService{

    @Autowired
    OeuvreDao dao;

    @Override
    public OeuvreLightListDto getAllOeuvres() {
       List<OeuvreLightDto> oeuvresLightDto = dao.getAllOeuvres().stream()
               .map(this::convertirOeuvreEnOeuvreLightDto);
        return new OeuvreLightListDto(1,1, oeuvresLightDto);
    }



    private OeuvreLightDto convertirOeuvreEnOeuvreLightDto(Oeuvre oeuvre) {
        OeuvreLightDto oeuvreLightDTO =new OeuvreLightDto(oeuvre.getId(), oeuvre.getType(), oeuvre.getTitre(), oeuvre.getGenreOeuvre(), oeuvre.getStatutVisionnage(), oeuvre.getNote(), oeuvre.getVideo(), oeuvre.getDuree());
        return oeuvreLightDTO;
    }

}
