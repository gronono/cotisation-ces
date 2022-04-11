package org.example.gl.ces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée de l'application.
 *
 * Lance le contexte Spring initialisant le serveur Web et Vaadin Flow.
 */
@SpringBootApplication
public class CotisationCesApplication {
    public static void main(String[] args) {
        SpringApplication.run(CotisationCesApplication.class, args);
    }
}
