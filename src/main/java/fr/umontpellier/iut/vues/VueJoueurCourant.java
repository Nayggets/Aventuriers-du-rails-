package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends VBox {
    private Label nomJoueur;
    IJeu jeu;

    public VueJoueurCourant(IJeu jeu){
        this.jeu = jeu;
        this.nomJoueur = new Label(jeu.getJoueurs().get(0).getNom());
        this.getChildren().add(this.nomJoueur);
    }

    public void creerBindings(){

    }

}
