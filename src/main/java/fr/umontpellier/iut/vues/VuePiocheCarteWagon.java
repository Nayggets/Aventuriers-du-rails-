package fr.umontpellier.iut.vues;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class VuePiocheCarteWagon extends Button {

    public VuePiocheCarteWagon(){
        ImageView carteRetourner = new ImageView("/images/wagons.png");
        carteRetourner.setFitWidth(150);
        carteRetourner.setFitHeight(120);
        this.setGraphic(carteRetourner);
        setStyle("-fx-background-color: transparent;");
    }
}
