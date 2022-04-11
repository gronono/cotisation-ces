package org.example.gl.ces.gui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

/**
 * Page d'accueil (d'index) de l'application.
 */
@Route(value = "")
public class IndexPage extends Div {
    public IndexPage() {
        this.add("Hello");
    }
}
