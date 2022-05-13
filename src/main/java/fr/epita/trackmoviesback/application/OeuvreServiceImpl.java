package fr.epita.trackmoviesback.application;

import fr.epita.trackmoviesback.domaine.Oeuvre;
import fr.epita.trackmoviesback.dto.GenreDto;
import fr.epita.trackmoviesback.dto.OeuvreLightDto;
import fr.epita.trackmoviesback.dto.OeuvreLightListDto;
import fr.epita.trackmoviesback.dto.StatutVisionnageDto;
import fr.epita.trackmoviesback.enumerate.EnumOperationDeRecherche;
import fr.epita.trackmoviesback.enumerate.EnumProprieteRecherchableSurOeuvre;
import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;
import fr.epita.trackmoviesback.infrastructure.oeuvre.OeuvreRepository;
import fr.epita.trackmoviesback.infrastructure.specs.CritereDeRecherche;
import fr.epita.trackmoviesback.infrastructure.specs.OeuvreSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OeuvreServiceImpl implements OeuvreService {
    private static final Logger logger = LoggerFactory.getLogger(OeuvreServiceImpl.class);


    @Autowired
    OeuvreRepository oeuvreRepository;
    @Autowired
    GenreService genreService;
    @Autowired
    StatutVisionnageService statutVisionnageService;

    @Override
    public OeuvreLightListDto getAllOeuvres() {
        List<Oeuvre> oeuvres = oeuvreRepository.findAll();
        List<OeuvreLightDto> oeuvresLightDto = oeuvres.stream().map(this::convertirOeuvreEnDto).collect(Collectors.toList());
        return new OeuvreLightListDto(1, 1, oeuvresLightDto);
    }

    @Override
    public OeuvreLightDto convertirOeuvreEnDto(Oeuvre oeuvre) {
        List<GenreDto> genresDto = genreService.convertirListGenreEnDto(oeuvre.getGenres());
        StatutVisionnageDto statutVisionnageDto = statutVisionnageService.convertirStatutVisionnageEnDto(oeuvre.getStatutVisionnage());
        String typeOeuvre = oeuvre.getTypeOeuvre() == null ? null : oeuvre.getTypeOeuvre().getLibelle();
        return new OeuvreLightDto(oeuvre.getId(), typeOeuvre, oeuvre.getTitre(), genresDto, statutVisionnageDto, oeuvre.getNote(), oeuvre.getUrlAffiche(), oeuvre.getUrlBandeAnnonce(), oeuvre.getDuree());
    }

    @Override
    public OeuvreLightListDto getOeuvres(Map<String, String> criteres) {
        logger.info("debut recherche avec criteres {}", criteres);
        //on construit les critères
        OeuvreSpecification criteresDeRecherche = buildOeuvreSpecification(criteres);

        //on récupère la liste
        List<Oeuvre> oeuvres = oeuvreRepository.findAll(criteresDeRecherche);
        logger.info("fin recherche en BDD. Nb oeuvres trouvees=", oeuvres.size());
        List<OeuvreLightDto> oeuvresLightDto = oeuvres.stream().map(this::convertirOeuvreEnDto).collect(Collectors.toList());
        return new OeuvreLightListDto(1, 1, oeuvresLightDto);
    }

    /**
     * converti la liste des critères passés à l'api sous forme de cle/valeur en un objet OeuvreSpecification utilisable par findAll
     * La cle de la map est le nom du critere de recherche: type, genre, statut...
     * La valeur dans la map es tla valeur recherchee
     *
     * @param criteres map avec la liste des criteres
     * @return oeuvreSpecification avec la liste des criteres de recherches dedans
     */
    private OeuvreSpecification buildOeuvreSpecification(Map<String, String> criteres) {
        OeuvreSpecification oeuvreSpecification = new OeuvreSpecification();

        //pour cahque critere en paramettre
        criteres.keySet().forEach(proprieteRecherchee -> {
            //on recupère la valeur recherchee correspondant dans la map
            String valeurRecherchee = criteres.get(proprieteRecherchee);
            oeuvreSpecification.add(buildCritereDeRecherche(proprieteRecherchee, valeurRecherchee));
        });
        return oeuvreSpecification;
    }

    /**
     * converti en critere de recherche pour la BDD, un critere de recherche recu dans la requete http
     *
     * @param proprieteRecherchee clé présente dans la query string ex: type, genre, statut...
     * @param valeurRecherchee    valeur correspondant à la clé dans la query string
     * @return le CritereDeRecherche formaté pour la BDD
     */
    private CritereDeRecherche buildCritereDeRecherche(String proprieteRecherchee, String valeurRecherchee) {
        //on converti la propriete recherche en enum afin de controler que c'est bien une valeur connu
        EnumProprieteRecherchableSurOeuvre enumProprieteRecherchee = EnumProprieteRecherchableSurOeuvre.getEnumFromValeurParametreRequeteHttp(proprieteRecherchee);

        //cas particulier du type d'oeuvre qui est un enumere, dans ce cas, la valeurRecherchee (initialement en string) doit etre convertie dans son enumeree correspondant
        Object valeurRechercheeFinale=valeurRecherchee;
        if (enumProprieteRecherchee== EnumProprieteRecherchableSurOeuvre.TYPE_OEUVRE)
            valeurRechercheeFinale= EnumTypeOeuvre.getEnumFromlabel(valeurRecherchee);

        //on définit l'operateur (= par defaut, "commence par" pour titre)
        EnumOperationDeRecherche operationDeRecherche = EnumOperationDeRecherche.EGAL;
        if (enumProprieteRecherchee == EnumProprieteRecherchableSurOeuvre.TITRE)
            operationDeRecherche = EnumOperationDeRecherche.COMMENCE_PAR;

        //on retourne le critere de Recheche
        return new CritereDeRecherche(enumProprieteRecherchee, valeurRechercheeFinale, operationDeRecherche);
    }


}
