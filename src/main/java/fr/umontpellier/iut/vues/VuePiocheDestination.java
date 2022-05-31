package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class VuePiocheDestination extends Button {


    public VuePiocheDestination() {
        ImageView carteRetourner = new ImageView("/images/destinations.png");
        carteRetourner.setFitWidth(150);
        carteRetourner.setFitHeight(120);
        this.setGraphic(carteRetourner);
    }
}
