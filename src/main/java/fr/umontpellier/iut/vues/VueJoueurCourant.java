package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends VBox {
    private Label nomJoueur;
    private IJeu jeu;
    private VBox cartesJoueurCourant;


    public VueJoueurCourant(IJeu jeu){
        this.jeu = jeu;
        this.nomJoueur = new Label(jeu.getJoueurs().get(0).getNom());
        this.cartesJoueurCourant = new VBox();

        getChildren().add(nomJoueur);
        getChildren().add(cartesJoueurCourant);
    }

    public void creerBindings(){
        jeu.joueurCourantProperty().addListener(listener);
    }

    //nomJoueur.setText(jeu.joueurCourantProperty().get().getNom());
    ChangeListener<IJoueur> listener = new ChangeListener<IJoueur>() {
        @Override
        public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur iJoueur, IJoueur t1) {
            Platform.runLater(() -> {
                nomJoueur.setText(t1.getNom());
                for (int i = 0; i < t1.getCartesWagon().size(); i++) {
                    cartesJoueurCourant.getChildren().add(new Label (t1.getCartesWagon().get(i).toString()));
                }
            });
        }
    };
}
