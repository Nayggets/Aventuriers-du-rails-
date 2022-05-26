package fr.umontpellier.iut.vues;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Cette classe présente les routes et les villes sur le plateau.
 *
 * On y définit le listener à exécuter lorsque qu'un élément du plateau a été choisi par l'utilisateur
 * ainsi que les bindings qui mettront ?à jour le plateau après la prise d'une route ou d'une ville par un joueur
 */
public class VuePlateau extends Pane {

    public VuePlateau() {
        ImageView imageView = new ImageView("/images/euMap.jpg");
        imageView.setFitHeight(700);
        imageView.setFitWidth(1400);


        this.getChildren().add(imageView);
    }

    @FXML
    public void choixRouteOuVille() {
    }
}

