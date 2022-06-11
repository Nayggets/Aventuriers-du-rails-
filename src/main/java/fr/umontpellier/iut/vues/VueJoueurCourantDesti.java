package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.Destination;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourantDesti extends VBox {
    private Label nomJoueur;
    private IJeu jeu;
    private HBox cartesDestiJoueurCourant;
    private HBox listeCarteVisible;

    private HBox cartesJoueur;


    public VueJoueurCourantDesti(IJeu jeu){
        this.jeu = jeu;
        this.nomJoueur = new Label(jeu.getJoueurs().get(0).getNom());
        this.cartesDestiJoueurCourant = new HBox();
        cartesDestiJoueurCourant.alignmentProperty().set(Pos.BASELINE_CENTER);
        cartesDestiJoueurCourant.setSpacing(-75);
        cartesDestiJoueurCourant.setPadding(new Insets(0,0,0,0));
        cartesDestiJoueurCourant.setTranslateY(65);
        this.listeCarteVisible = new HBox();

        getChildren().add(cartesDestiJoueurCourant);
    }

    public void creerBindings(){
        jeu.joueurCourantProperty().addListener(listener);
        //jeu.getJoueurs().get(0).cartesWagonProperty().addListener(listener2);
        //jeu.joueurCourantProperty().get().cartesWagonProperty().addListener(listener2);
        //jeu.cartesWagonVisiblesProperty().addListener(listener3);

    }

    //nomJoueur.setText(jeu.joueurCourantProperty().get().getNom());

    //joueur courant affichage nom + liste cartes
    private ChangeListener<IJoueur> listener = new ChangeListener<IJoueur>() {
        @Override
        public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur iJoueur, IJoueur t1) {
            Platform.runLater(() -> {
                cartesDestiJoueurCourant.getChildren().clear();
                for(Destination d : t1.getDestinations()){
                    VueDestination vue = new VueDestination(d);
                    //vue.rotateProperty().add(90);
                    vue.setRotate(90);
                    vue.setOnMouseEntered(actionEvent -> {
                        vue.hoverMode(150,175,-75);

                    });
                    vue.setOnMouseExited(actionEvent ->{
                        vue.hoverMode(100,125,0);
                    });
                    cartesDestiJoueurCourant.getChildren().add(vue);
                }
            });
        }
    };

    public VueDestination trouveCarteVagon(IDestination id){
        for (int i = 0; i < cartesDestiJoueurCourant.getChildren().size() ; i++) {
            VueDestination vueDesti =  (VueDestination) cartesDestiJoueurCourant.getChildren().get(i);
            if (vueDesti.toString().equals(id.toString())){
                return vueDesti;
            }
        }
        return null;
    }


    //liste carte joueur
    private ListChangeListener<Destination> listener2 = new ListChangeListener<Destination>() {
        @Override
        public void onChanged(Change<? extends Destination> change) {
            Platform.runLater(() -> {
                cartesDestiJoueurCourant.getChildren().clear();
                for (int i = 0; i < jeu.joueurCourantProperty().get().cartesWagonProperty().size(); i++) {
                    cartesDestiJoueurCourant.getChildren().add(new Button(change.getAddedSubList().get(i).toString()));
                }
            });
        }
    };
}

