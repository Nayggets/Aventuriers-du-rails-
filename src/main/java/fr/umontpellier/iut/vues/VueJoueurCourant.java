package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.CouleurWagon;
import fr.umontpellier.iut.rails.Jeu;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
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
    private HBox cartesJoueurCourant;
    private HBox listeCarteVisible;

    private HBox cartesJoueur;


    public VueJoueurCourant(IJeu jeu){
        this.jeu = jeu;
        this.nomJoueur = new Label(jeu.getJoueurs().get(0).getNom());
        this.cartesJoueurCourant = new HBox();
        cartesJoueurCourant.alignmentProperty().set(Pos.BASELINE_CENTER);
        cartesJoueurCourant.setSpacing(-75);
        cartesJoueurCourant.setPadding(new Insets(0,0,0,0));
        cartesJoueurCourant.setTranslateY(65);
        this.listeCarteVisible = new HBox();


        //nomJoueur.setStyle("-fx-text-fill: white;");
        //getChildren().add(nomJoueur);
        getChildren().add(cartesJoueurCourant);
    }

    public Label getNomJoueur() {
        return nomJoueur;
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
                nomJoueur.setText(t1.getNom());
                cartesJoueurCourant.getChildren().clear();
                for(CouleurWagon c : t1.getCartesWagon()){
                    VueCarteWagon vue = new VueCarteWagon(c,true);
                    vue.rotateProperty().add(90);
                    vue.setOnMouseEntered(actionEvent -> {
                        vue.hoverMode(150,175,-75);
                    });
                    vue.setOnMouseExited(actionEvent ->{
                        vue.hoverMode(100,125,0);
                    });
                    vue.setOnMouseClicked(mouseEvent -> {
                        jeu.uneCarteWagonAEteChoisie(((VueCarteWagon) mouseEvent.getSource()).getCouleurWagon());
                        cartesJoueurCourant.getChildren().remove((VueCarteWagon) mouseEvent.getSource());
                    });
                    cartesJoueurCourant.getChildren().add(vue);
                }
            });
        }
    };

    public VueCarteWagon trouveCarteVagon(ICouleurWagon id){
        for (int i = 0; i < cartesJoueurCourant.getChildren().size() ; i++) {
            VueCarteWagon vueWagon =  (VueCarteWagon) cartesJoueurCourant.getChildren().get(i);
            if (vueWagon.toString().equals(id.toString())){
                return vueWagon;
            }
        }
        return null;
    }


    //liste carte joueur
    private ListChangeListener<CouleurWagon> listener2 = new ListChangeListener<CouleurWagon>() {
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

