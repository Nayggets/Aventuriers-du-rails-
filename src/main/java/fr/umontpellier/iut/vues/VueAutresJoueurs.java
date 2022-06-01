package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.Joueur;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends Pane {

    IJoueur joueur;
    Label nombreGare;
    Label nombreWagon;
    Label score;
    IntegerProperty nbGare;
    IntegerProperty nbWagon;
    IntegerProperty nbScore;
    HBox box;
    ImageView gare;
    ImageView wagon;
    ImageView imScore;
    ImageView avatar;

    public VueAutresJoueurs(IJoueur joueur){
        nombreWagon = new Label();
        nombreGare = new Label();
        score = new Label();
        System.out.println(joueur.getCouleur().toString().toLowerCase());
        box = new HBox();
        this.joueur = joueur;

        Rectangle rect = new Rectangle(1000,1000);
        rect.setArcHeight(300.0);
        rect.setArcWidth(150.0);
        this.setShape(rect);
        this.setStyle("-fx-background-color: " + traduceColor(joueur.getCouleur().toString().toLowerCase()));
        this.setMinWidth(400);
        this.setMinHeight(125);

        this.gare = new ImageView("/images/gares/gare-" + joueur.getCouleur().toString().toUpperCase() + ".png");
        this.wagon = new ImageView("/images/wagons/image-wagon-" + joueur.getCouleur().toString().toUpperCase() + ".png");
        this.imScore = new ImageView("/images/wagons/image-wagon-" + joueur.getCouleur().toString().toUpperCase() + ".png");
        this.avatar = new ImageView("/images/avatar/avatar-" + joueur.getCouleur().toString().toUpperCase() + ".png");
        this.gare.setFitHeight(50);
        this.gare.setFitWidth(50);
        this.wagon.setFitHeight(50);
        this.imScore.setFitHeight(50);
        this.avatar.setFitHeight(50);
        this.wagon.setFitWidth(50);
        this.imScore.setFitWidth(50);
        this.avatar.setFitWidth(50);


        nbGare = new SimpleIntegerProperty(joueur.getNbGares());
        nbWagon = new SimpleIntegerProperty(joueur.getNbWagons());
        nbScore = new SimpleIntegerProperty(joueur.getScore());
        nombreGare.textProperty().bind(nbGare.asString());
        nombreWagon.textProperty().bind(nbWagon.asString());
        score.textProperty().bind(nbScore.asString());
        box.getChildren().addAll(avatar,imScore,score,gare,nombreGare,wagon,nombreWagon);


        this.getChildren().add(box);
    }

    private String traduceColor(String color){
        if(color.equals("bleu")){
            return "blue";
        }
        if(color.equals("rose")){
            return "pink";
        }
        if(color.equals("rouge")){
            return "red";
        }
        if (color.equals("vert")){
            return "green";
        }
        else{
            return "yellow";
        }
    }

}
