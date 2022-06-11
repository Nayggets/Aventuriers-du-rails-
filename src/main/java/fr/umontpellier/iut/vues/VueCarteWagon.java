package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * Cette classe représente la vue d'une carte Wagon.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarteWagon extends Button {

    private ICouleurWagon couleurWagon;
    private ImageView view;

    public VueCarteWagon(ICouleurWagon couleurWagon,boolean carteJoueur) {
        this.couleurWagon = couleurWagon;

        String str = couleurWagon.toString().toLowerCase();
        view = new ImageView("/images/cartesWagons/carte-wagon-" + couleurWagon.toString().toUpperCase() + ".png");
        if(carteJoueur){
            view.setFitHeight(100);
            view.setFitWidth(125);



            this.setRotate(90);
        }
        else{
            view.setFitHeight(125);
            view.setFitWidth(150);
            this.setOnAction(actionEvent -> {
                ((VueDuJeu)getScene().getRoot()).getJeu().uneCarteWagonAEteChoisie(couleurWagon);

            });
        }

        this.setGraphic(view);

    }

    public void hoverMode(double height, double width,double y){
        view.setFitWidth(width);
        view.setFitHeight(height);

        this.setTranslateY(y);
    }



    public ICouleurWagon getCouleurWagon() {
        return couleurWagon;
    }

}
