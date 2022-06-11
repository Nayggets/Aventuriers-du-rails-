package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.Joueur;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends Pane {

    private ImageView star;

    private IJoueur joueur;
    private Label nomDuJoueur;
    private Label nombreGare;
    private Label nombreWagon;
    private Label score;
    private IntegerProperty nbGare;
    private IntegerProperty nbWagon;
    private IntegerProperty nbScore;
    private HBox box;
    private ImageView gare;
    private ImageView wagon;
    private ImageView imScore;
    private ImageView avatar;

    private HBox h1a;
    private HBox h1b;
    private HBox v1;
    private HBox h;
    private VBox v;
    private BorderPane bp;
    public VueAutresJoueurs(IJoueur joueur){
        nomDuJoueur = new Label(joueur.getNom());
        nombreWagon = new Label();
        nombreGare = new Label();
        score = new Label();
        box = new HBox();
        this.joueur = joueur;
        star = new ImageView("/images/Etoile.png");
        star.setFitWidth(75);
        star.setFitHeight(60);

        //---rectangle----
        Rectangle rect = new Rectangle(1000,1000);
        rect.setArcHeight(300.0);
        rect.setArcWidth(150.0);
        this.setShape(rect);
        this.setStyle("-fx-background-color: " + traduceColor(joueur.getCouleur().toString().toLowerCase()));
        this.setMinWidth(500);
        this.setMinHeight(175);

        //---Contenu rectangle---
        bp = new BorderPane();
        this.gare = new ImageView("/images/gares/gare-" + joueur.getCouleur().toString().toUpperCase() + ".png");
        this.wagon = new ImageView("/images/wagons/image-wagon-" + joueur.getCouleur().toString().toUpperCase() + ".png");
        this.imScore = new ImageView("/images/wagons/image-wagon-" + joueur.getCouleur().toString().toUpperCase() + ".png");
        this.avatar = new ImageView("/images/avatar/avatar-" + joueur.getCouleur().toString().toUpperCase() + ".png");
        this.gare.setFitHeight(75);
        this.gare.setFitWidth(75);
        this.wagon.setFitWidth(75);
        this.wagon.setFitHeight(75);
        this.imScore.setFitWidth(75);
        this.imScore.setFitHeight(75);
        this.avatar.setFitWidth(100);
        this.avatar.setFitHeight(100);
        h1a = new HBox(avatar);
        h1a.setAlignment(Pos.TOP_LEFT);
        nomDuJoueur.setStyle("-fx-font-size: 40; -fx-font-weight: bold");
        h1b = new HBox(nomDuJoueur);
        h1b.setAlignment(Pos.CENTER);
        v1 = new HBox(h1a,h1b);
        v1.setAlignment(Pos.CENTER_LEFT);
        v1.setSpacing(20);
        h = new HBox(imScore,score,gare,nombreGare,wagon,nombreWagon);
        v = new VBox(v1,h);
        bp.setCenter(v);


        nbGare = new SimpleIntegerProperty(joueur.getNbGares());
        nbWagon = new SimpleIntegerProperty(joueur.getNbWagons());
        nbScore = new SimpleIntegerProperty(joueur.getScore());
        nombreGare.textProperty().bind(nbGare.asString());
        nombreWagon.textProperty().bind(nbWagon.asString());
        score.textProperty().bind(nbScore.asString());
        box.getChildren().addAll(v);
        box.getChildren().add(star);
        star.setVisible(false);

        this.getChildren().add(box);
    }

    public IJoueur getJoueur() {
        return joueur;
    }

    public void putStars(){
        star.setVisible(true);
    }

    public void removeStars(){
        star.setVisible(false);
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
