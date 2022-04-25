package org.example.gl.ces.gui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import org.example.gl.ces.metier.CalculCotisationService;
import org.example.gl.ces.metier.Montant;

public class CalculCotisationForm extends VerticalLayout {

    private final CalculCotisationService calculCotisationService = new CalculCotisationService();

    private final NumberField salaireBrutHoraireTxt;
    private final IntegerField nbHeuresTxt;
    private final TextField salaireHoraireNetTxt;
    private final TextField salaireNetPayeTxt;


    public CalculCotisationForm() {
        this.getStyle().set("width", "initial");
        this.getStyle().set("margin-left", "auto");
        this.getStyle().set("margin-right", "auto");

        salaireBrutHoraireTxt = new NumberField("Salaire Brut Horaire");
        salaireBrutHoraireTxt.getStyle().set("min-width", "9em");
        salaireBrutHoraireTxt.setAutofocus(true);
        salaireBrutHoraireTxt.setHelperText("Salaire brut en F/H");
        this.add(salaireBrutHoraireTxt);
        salaireBrutHoraireTxt.addValueChangeListener(e -> calculer());

        nbHeuresTxt = new IntegerField("Nb d'heures");
        nbHeuresTxt.getStyle().set("min-width", "9em");
        nbHeuresTxt.setHelperText("Nombre d'heures travaillées dans le mois");
        nbHeuresTxt.addValueChangeListener(e -> calculer());
        this.add(nbHeuresTxt);

        salaireNetPayeTxt = new TextField("Salaire Net Payé");
        salaireNetPayeTxt.setEnabled(false);
        salaireNetPayeTxt.setHelperText("Salaire Net payé");
        this.add(salaireNetPayeTxt);

        salaireHoraireNetTxt = new TextField("Salaire Horaire Net");
        salaireHoraireNetTxt.setEnabled(false);
        salaireHoraireNetTxt.setHelperText("Salaire Horaire Net en F/H");
        this.add(salaireHoraireNetTxt);
    }

    private void calculer() {
        if (salaireBrutHoraireTxt.getOptionalValue().isPresent() && nbHeuresTxt.getOptionalValue().isPresent()) {
            var salaireBrutHoraire = Montant.montantDecimal(salaireBrutHoraireTxt.getValue());
            var nbHeures = nbHeuresTxt.getValue();
            var resultat = calculCotisationService.calculerCotisations(salaireBrutHoraire, nbHeures);
            salaireNetPayeTxt.setValue(resultat.getSalaireNetPaye().format());
            salaireHoraireNetTxt.setValue(resultat.getSalaireHoraireNet().format());
        } else {
            salaireNetPayeTxt.setValue("N/A");
            salaireHoraireNetTxt.setValue("N/A");
        }
    }
}
