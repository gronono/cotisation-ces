package org.example.gl.ces.metier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MontantTest {

    @Test
    void testFormat_Montant_Fix() {
        Assertions.assertEquals("24 F", Montant.montantFixe(24).format());
        Assertions.assertEquals("1 147 F", Montant.montantFixe(1_147).format());
        Assertions.assertEquals("87 178 147 F", Montant.montantFixe(87_178_147).format());
        Assertions.assertEquals("78 787 178 147 F", Montant.montantFixe(78_787_178_147L).format());
    }

    @Test
    void testFormat_Montant_Decimal() {
        Assertions.assertEquals("24,00 F", Montant.montantDecimal(24).format());
        Assertions.assertEquals("24,74 F", Montant.montantDecimal(24.74).format());
        Assertions.assertEquals("1 147,74 F", Montant.montantDecimal(1_147.744).format());
        Assertions.assertEquals("87 178 147,74 F", Montant.montantDecimal(87_178_147.746).format());
        Assertions.assertEquals("78 787 178 147,75 F", Montant.montantDecimal(78_787_178_147.754).format());
    }

    @Test
    void testMultiplierPar() {
        Assertions.assertEquals("48 F", Montant.montantFixe(24).multiplierPar(2).format());
    }

    @Test
    void testDiviserPar() {
        Assertions.assertEquals("12,00 F", Montant.montantFixe(24).diviserPar(2).format());
    }

    @Test
    void testAppliquerTaux() {
        Assertions.assertEquals("2,40 F", Montant.montantFixe(24).appliquerTaux(0.1).format());
        Assertions.assertEquals("626,07 F", Montant.montantFixe(8_055).appliquerTaux(0.077725).format());
    }

    @Test
    void testAdditionner() {
        Assertions.assertEquals("37 F", Montant.montantFixe(24).additionner(Montant.montantFixe(13)).format());
        Assertions.assertEquals("37,85 F", Montant.montantDecimal(24.71).additionner(Montant.montantDecimal(13.14)).format());
        Assertions.assertEquals("38,00 F", Montant.montantDecimal(24.71).additionner(Montant.montantDecimal(13.29)).format());
    }

    @Test
    void testSoustraire() {
        Assertions.assertEquals("11 F", Montant.montantFixe(24).soustraire(Montant.montantFixe(13)).format());
        Assertions.assertEquals("11,57 F", Montant.montantDecimal(24.71).soustraire(Montant.montantDecimal(13.14)).format());
        Assertions.assertEquals("11,00 F", Montant.montantDecimal(24.71).soustraire(Montant.montantDecimal(13.71)).format());
    }
}
