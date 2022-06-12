package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.rails.Destination;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Cette classe représente la vue d'une carte Destination.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueDestination extends Button {

    private IDestination destination;

    private ImageView view;

    public VueDestination(IDestination destination) {
        this.destination = destination;
        String str = "/images/missions/eu-" + destination.toString().toLowerCase() + ".png";
        str = str.replace("(","");
        str = str.replace(")","");
        int i = 0;
        while(i < str.length()){
            if(str.charAt(i) >= '0' && str.charAt(i) <= '9'){
                str = str.replace(str.charAt(i),' ');
            }
            i++;
        }
        str = str.replaceAll(" ","");
        //System.out.println(str);
        view = new ImageView(str);
        view.setFitWidth(150);
        view.setFitHeight(120);
        this.setGraphic(view);
        setStyle("-fx-background-color: transparent;");
    }


    public void hoverMode(double height, double width,double y){
        view.setFitWidth(width);
        view.setFitHeight(height);

        this.setTranslateY(y);
    }


    public IDestination getDestination() {
        return destination;
    }

}
