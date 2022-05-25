package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.CouleurWagon;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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
    private HBox listeCarteVisible;
    private Label cartes;
    private VueCarteWagon cartesImg;
    private HBox cartesJoueur;


    public VueJoueurCourant(IJeu jeu){
        this.jeu = jeu;
        this.nomJoueur = new Label(jeu.getJoueurs().get(0).getNom());
        this.cartes = new Label(jeu.getJoueurs().get(0).getCartesWagon().toString());
        this.cartesJoueurCourant = new VBox();
        this.listeCarteVisible = new HBox();

        getChildren().add(listeCarteVisible);
        cartesJoueurCourant.getChildren().add(cartes);
        getChildren().add(nomJoueur);
        getChildren().add(cartesJoueurCourant);
    }

    public void creerBindings(){
        jeu.joueurCourantProperty().addListener(listener);
        //jeu.getJoueurs().get(0).cartesWagonProperty().addListener(listener2);
        jeu.joueurCourantProperty().get().cartesWagonProperty().addListener(listener2);
        //jeu.cartesWagonVisiblesProperty().addListener(listener3);

    }

    //nomJoueur.setText(jeu.joueurCourantProperty().get().getNom());

    //joueur courant affichage nom + liste cartes
    ChangeListener<IJoueur> listener = new ChangeListener<IJoueur>() {
        @Override
        public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur iJoueur, IJoueur t1) {
            Platform.runLater(() -> {
                nomJoueur.setText(t1.getNom());
                cartes.setText(t1.getCartesWagon().toString());
            });
        }
    };

    //liste carte joueur
    ListChangeListener<CouleurWagon> listener2 = new ListChangeListener<CouleurWagon>() {
        @Override
        public void onChanged(Change<? extends CouleurWagon> change) {
            Platform.runLater(() -> {
                cartesJoueurCourant.getChildren().clear();
                for (int i = 0; i < jeu.joueurCourantProperty().get().cartesWagonProperty().size(); i++) {
                    cartesJoueurCourant.getChildren().add(new Button(change.getAddedSubList().get(i).toString()));
                }
            });
        }
    };
}
