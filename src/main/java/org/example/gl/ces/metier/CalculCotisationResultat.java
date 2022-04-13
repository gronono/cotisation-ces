package org.example.gl.ces.metier;

/**
 * Value-Object pour stocker les résultats du calcul.
 */
public class CalculCotisationResultat {

    private final Montant salaireNetPaye;
    private final Montant salaireHoraireNet;

    public CalculCotisationResultat(Montant salaireNetPaye, Montant salaireHoraireNet) {
        this.salaireNetPaye = salaireNetPaye;
        this.salaireHoraireNet = salaireHoraireNet;
    }

    public Montant getSalaireHoraireNet() {
        return salaireHoraireNet;
    }

    public Montant getSalaireNetPaye() {
        return salaireNetPaye;
    }
}
