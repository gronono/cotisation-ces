package org.example.gl.ces.metier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculCotisationServiceTest {

    @Test
    void testCalcul() {
        var salaireHoraireBrut = Montant.montantDecimal(915.42);
        var nbHeures = 8;
        var resultat = new CalculCotisationService().calculerCotisations(salaireHoraireBrut, nbHeures);
        Assertions.assertEquals("7 429 F", resultat.getSalaireNetPaye().format());
        Assertions.assertEquals("928,62 F", resultat.getSalaireHoraireNet().format());
    }
}
