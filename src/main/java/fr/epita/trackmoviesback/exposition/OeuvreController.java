package fr.epita.trackmoviesback.exposition;

import fr.epita.trackmoviesback.application.OeuvreService;
import fr.epita.trackmoviesback.application.UtilisateurService;
import fr.epita.trackmoviesback.dto.OeuvreDto;
import fr.epita.trackmoviesback.dto.OeuvreLightListDto;
import fr.epita.trackmoviesback.dto.formulaire.OeuvreFormulaireDto;
import fr.epita.trackmoviesback.exception.MauvaisParamException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/trackmovies/v1")
public class OeuvreController {
    private static final Logger logger = LoggerFactory.getLogger(OeuvreController.class);

    @Autowired
    OeuvreService service;

    @Autowired
    UtilisateurService utilisateurService;

    @ApiOperation(value = "Recuperer les oeuvres"
            , notes = "Permet de récupérer le liste des oeuvres correspondant aux critères"
    ) //info pour le swagger
    @GetMapping(value = "/mes_oeuvres", produces = {"application/json; charset=utf-8"})
    ResponseEntity<OeuvreLightListDto> getOeuvres(
            @ApiParam(value = "type d'oeuvre (film ou serie)")
            @RequestParam(name = "type", required = false) String typeOeuvre
            , @ApiParam(value = "id du genre d'oeuvre")
            @RequestParam(name = "genre", required = false) String genreId
            , @ApiParam(value = "id du statut de visionnage")
            @RequestParam(name = "statut", required = false) String statutVisionnageId
            , @ApiParam(value = "chaine correspondant au debut du titre")
            @RequestParam(name = "titre", required = false) String titre
    ) {
        //on recupère le user login de l'utilisateur en cours dans le context
        String userLogin= utilisateurService.getCurrentUserLoginFromSecurityContext();

        Map<String, String> parameters = new HashMap<>();
        if (typeOeuvre != null) parameters.put("type", typeOeuvre);
        if (genreId != null) parameters.put("genre", String.valueOf(genreId));
        if (statutVisionnageId != null) parameters.put("statut", String.valueOf(statutVisionnageId));
        if (titre != null) parameters.put("titre", titre);

        return new ResponseEntity(service.getOeuvres(userLogin,parameters), HttpStatus.OK);
    }

    @ApiOperation(value = "Recuperer une oeuvre par son id"
            , notes = "Permet de récupérer une oeuvre et son détail par son id"
    ) //info pour le swagger
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 401, message = "Non autorisé"),
            @ApiResponse(code = 404, message = "non trouvé"),
            @ApiResponse(code = 500, message = "erreur du serveur") })
    @GetMapping("/mes_oeuvres/{id}")
    //@Secured("ROLE_USER")
    ResponseEntity<OeuvreDto> getOeuvreCompleteById (@PathVariable("id") Long id) {
        //on recupère le user login de l'utilisateur en cours dans le context
        String userLogin= utilisateurService.getCurrentUserLoginFromSecurityContext();

        OeuvreDto oeuvre = service.getOeuvreCompleteById(userLogin,id);
        if (oeuvre !=null) {
            return new ResponseEntity(oeuvre, HttpStatus.OK);
        }else {
            return new ResponseEntity("Aucune Oeuvre trouvée",HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Sauver une oeuvre"
            , notes = "Permet de creer ou mettre à jour une oeuvre. Pour les element Genre et Statut visionnage, seul les id sont pris en compte. "
    ) //info pour le swagger
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Mauvais parametre recu"),
            @ApiResponse(code = 401, message = "Non autorisé"),
            @ApiResponse(code = 404, message = "non trouvé"),
            @ApiResponse(code = 500, message = "erreur du serveur") })
    @PostMapping("/oeuvre")
    public ResponseEntity<OeuvreDto> save(@Valid @RequestBody OeuvreFormulaireDto oeuvreFormulaireDto) {

        try {
            logger.debug("create : oeuvreFormulaireDto={}",oeuvreFormulaireDto);

            String userLogin= utilisateurService.getCurrentUserLoginFromSecurityContext();
            OeuvreDto oeuvreCree = service.saveOeuvre(userLogin,oeuvreFormulaireDto);
            return new ResponseEntity(oeuvreCree, HttpStatus.OK);

        } catch (MauvaisParamException e) {
            logger.error(e.toString());
            return new ResponseEntity("Mauvais parametre recu: "+e.getMessage(),HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new ResponseEntity("Erreur technique",HttpStatus.BAD_REQUEST);
        }

    }

    @ApiOperation(value = "Supprimer une oeuvre par son id"
            , notes = "Permet de supprimer une oeuvre et son détail par son id"
    ) //info pour le swagger
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 401, message = "Non autorisé"),
            @ApiResponse(code = 404, message = "non trouvé"),
            @ApiResponse(code = 500, message = "erreur du serveur") })
    @DeleteMapping("/oeuvre/{id}")
    public ResponseEntity<String> deleteOeuvre(@PathVariable(value = "id") Long id) {
        //on recupère le user login de l'utilisateur en cours dans le context
        String userLogin= utilisateurService.getCurrentUserLoginFromSecurityContext();

        service.deleteOeuvre(userLogin, id);
        return new ResponseEntity("Oeuvre supprimée", HttpStatus.OK);
    }



}


