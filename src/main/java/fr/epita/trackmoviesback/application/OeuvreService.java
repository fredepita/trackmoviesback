package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Oeuvre;
import fr.epita.trackmoviesback.dto.OeuvreLightDto;
import fr.epita.trackmoviesback.dto.OeuvreLightListDto;


import java.util.List;

public interface OeuvreService {

    OeuvreLightListDto getAllOeuvres();
    OeuvreLightDto convertirOeuvreEnDto(Oeuvre oeuvre);
}
