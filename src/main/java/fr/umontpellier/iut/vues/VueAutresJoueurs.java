package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.Joueur;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends Pane {

    IJoueur joueur;
    HBox box;
    Label nombreGare;
    Label nombreWagon;
    Label score;
    IntegerProperty nbGare;
    IntegerProperty nbWagon;
    IntegerProperty nbScore;

    ImageView gare;
    ImageView wagon;
    ImageView imScore;

    public VueAutresJoueurs(IJoueur joueur){

        this.joueur = joueur;
        this.setWidth(100);
        this.setHeight(100);
        this.setStyle("-fx-background-color: #" + joueur.getCouleur().toString());
        this.gare = new ImageView("/images/gares/gare-" + joueur.getCouleur().toString().toUpperCase() + "png");
        this.wagon = new ImageView("/images/wagons/image-wagon-" + joueur.getCouleur().toString().toUpperCase() + "png");
        this.imScore = new ImageView("/images/wagons/image-wagon-" + joueur.getCouleur().toString().toUpperCase() + "png");
        nbGare = new SimpleIntegerProperty(joueur.getNbGares());
        nbWagon = new SimpleIntegerProperty(joueur.getNbWagons());
        nbScore = new SimpleIntegerProperty(joueur.getScore());
        nombreGare.textProperty().bind(nbGare.asString());
        nombreWagon.textProperty().bind(nbWagon.asString());
        score.textProperty().bind(nbScore.asString());

    }

}
