package fr.epita.trackmoviesback.application;


import fr.epita.trackmoviesback.domaine.StatutVisionnage;
import fr.epita.trackmoviesback.dto.StatutVisionnageDto;
import fr.epita.trackmoviesback.dto.StatutVisionnageListDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StatutVisionnageImplTest {

    @Autowired
    StatutVisionnageService statutVisionnageService;

    @Test
    void getAllStatutVisionnage_doit_retourner_tous_les_StatusVisionnage() {
        StatutVisionnageListDto statutVisionnageListDto = statutVisionnageService.getAllStatutVisionnage();
        List<StatutVisionnageDto> statutVisionnageDtoList = statutVisionnageListDto.getStatuts();

        assertEquals(statutVisionnageDtoList.size(), 3);

        boolean foundVu = false;
        for (StatutVisionnageDto statutVisionnage : statutVisionnageDtoList) {
            if (statutVisionnage.getLibelle().equals("Vu")) {
                foundVu = true;
                break;
            }
        }
        assertTrue(foundVu);

    }

    @Test
    void convertirStatutVisionnageEnDto_doit_convertir_un_StatutVisionnage_en_dto_et_gerer_null() {
        //Création d'un statut de visionnage
        StatutVisionnage statutTest = new StatutVisionnage();
        statutTest.setId(1L);
        statutTest.setLibelle("vu");
        //création d'un statutVisionnageDto à comparer avec notre statutTest
        StatutVisionnageDto statutVisionnageDto = statutVisionnageService.convertirStatutVisionnageEnDto(statutTest);

        assertEquals(statutVisionnageDto.getLibelle(), statutTest.getLibelle());

        statutVisionnageDto = statutVisionnageService.convertirStatutVisionnageEnDto(null);
        assertNull(statutVisionnageDto);
    }

    @Test
    void convertirStatutVisionnageDtoEnStatutVisionnage_doit_gerer_null() {
        assertNull(statutVisionnageService.convertirStatutVisionnageDtoEnStatutVisionnage(null));
    }
}
