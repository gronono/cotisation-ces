package org.example.gl.ces.gui;

import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.Route;

/**
 * Page d'accueil (d'index) de l'application.
 */
@Route(value = "")
public class IndexPage extends FlexLayout {

    public IndexPage() {
        this.add(new CalculCotisationForm());
    }
}
