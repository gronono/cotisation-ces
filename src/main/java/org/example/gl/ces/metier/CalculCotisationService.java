package org.example.gl.ces.metier;

import org.example.gl.ces.dao.taux.TauxDao;

public class CalculCotisationService {

    public static final double TAUX_CONGES_PAYES = 0.1;
    public static final double TAUX_CCS =  0.077725;

    private final TauxDao tauxDao = new TauxDao();

    public CalculCotisationResultat calculerCotisations(Montant salaireHoraireBrut, int nbHeures) {
        checkArguments(salaireHoraireBrut, nbHeures);

        var taux = tauxDao.findAll();

        var salaireBrut = salaireHoraireBrut.multiplierPar(nbHeures).arrondir(1);
        var congesPayes = salaireBrut.appliquerTaux(taux.getTauxConges()).arrondir();
        var salaireBrutTotal = salaireBrut.additionner(congesPayes).arrondir();
        var retenueCCS = salaireBrutTotal.appliquerTaux(taux.getTauxCes()).arrondir();
        var salaireNetPaye = salaireBrutTotal.soustraire(retenueCCS);
        var salaireHoraireNet = salaireNetPaye.diviserPar(nbHeures);

        return new CalculCotisationResultat(salaireNetPaye, salaireHoraireNet);
    }

    private void checkArguments(Montant salaireHoraireBrut, double nbHeures) {
        if (salaireHoraireBrut.isNegatif()) {
            throw new IllegalArgumentException("Le salaire horaire brut doit être positif. Obtenu: " + salaireHoraireBrut);
        }
        if (nbHeures <= 0) {
            throw new IllegalArgumentException("Le nombre d'heures ne peut pas être négatif (ou zéro). Obtenu: " + nbHeures);
        }
    }


}
