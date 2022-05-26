package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.rails.CouleurWagon;
import fr.umontpellier.iut.rails.Destination;
import fr.umontpellier.iut.rails.Jeu;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 *
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 *
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, les 5 cartes Wagons visibles, les destinations lors de l'étape d'initialisation de la partie, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends VBox {

    private IJeu jeu;
    private VuePlateau plateau;
    private  Button passer;
    private HBox listeDestinations;
    private HBox cartesVisibles;
    private VueJoueurCourant vueJCour;

    private VuePlateau vuePlateau;

    private VBox joueur;

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;

        //jeu.joueurCourantProperty().get().cartesWagonProperty().add(CouleurWagon.JAUNE);
        plateau = new VuePlateau();
        listeDestinations = new HBox();
        listeDestinations.setSpacing(50);
        cartesVisibles = new HBox();
        cartesVisibles.setSpacing(60);
        vuePlateau = new VuePlateau();
        joueur = new VBox();

        passer = new Button("Passer");
        vueJCour = new VueJoueurCourant(jeu);



        getChildren().add(vuePlateau);
        getChildren().add(listeDestinations);
        getChildren().add(passer);
        getChildren().add(cartesVisibles);
        getChildren().add(vueJCour);
    }

    public IJeu getJeu() {
        return jeu;
    }

    public VueDestination trouveVuDestination(IDestination id){
        for (int i = 0; i < listeDestinations.getChildren().size() ; i++) {
            VueDestination destination =  (VueDestination) listeDestinations.getChildren().get(i);
            if (id.getNom().equals(id.getNom())){
                return destination;
            }
        }
        return null;
    }

    public VueCarteWagon trouveCarteVagon(ICouleurWagon id){
        for (int i = 0; i < cartesVisibles.getChildren().size() ; i++) {
            VueCarteWagon vueWagon =  (VueCarteWagon) cartesVisibles.getChildren().get(i);
            if (vueWagon.toString().equals(id.toString())){
                return vueWagon;
            }
        }
        return null;
    }

    public void creerBindings() {
        ObservableList<Node> liste = listeDestinations.getChildren();
        for(Node e : liste){
            VueDestination b = (VueDestination) e;
            b.setOnAction(actionEvent -> {

                    System.out.println("wesh");
                    jeu.uneDestinationAEteChoisie(b.getDestination().getNom());
                    listeDestinations.getChildren().remove(b);
            });
        }
        getJeu().destinationsInitialesProperty().addListener(listener);

        getJeu().cartesWagonVisiblesProperty().addListener(listenWagon);
        passer.setOnAction(actionEvent -> {
            getJeu().passerAEteChoisi();
        });
        vueJCour.creerBindings();

    }

    private ListChangeListener<IDestination> listener = new ListChangeListener<IDestination>() {
        @Override
        public void onChanged(Change<? extends IDestination> change ) {
            Platform.runLater(() -> {
                while (change.next()) {
                    if (change.wasAdded()) {
                        for (int i = 0; i < change.getAddedSize(); i++) {
                            VueDestination vue = new VueDestination(change.getAddedSubList().get(i));
                            vue.setOnAction(actionEvent -> {

                                System.out.println("wesh");
                                jeu.uneDestinationAEteChoisie(vue.getDestination().getNom());
                                listeDestinations.getChildren().remove(vue);
                            });
                            listeDestinations.getChildren().add(vue);

                        }
                    }
                    if(change.wasRemoved()){
                        for (int i = 0; i < change.getRemovedSize(); i++) {
                            listeDestinations.getChildren().remove(trouveVuDestination(change.getRemoved().get(i)));
                        }
                    }
                    if(change.wasUpdated()){

                    }
                }
            });
        }

    };

    private ListChangeListener<ICouleurWagon> listenWagon = new ListChangeListener<ICouleurWagon>() {
        @Override
        public void onChanged(Change<? extends ICouleurWagon> change ) {
            Platform.runLater(() -> {
                while (change.next()) {
                    if (change.wasAdded()) {
                        for (int i = 0; i < change.getAddedSize(); i++) {
                            VueCarteWagon vue = new VueCarteWagon(change.getAddedSubList().get(i),false);
                            vue.setOnAction(actionEvent -> {

                                System.out.println("wesh");
                                jeu.uneCarteWagonAEteChoisie(vue.getCouleurWagon());
                                cartesVisibles.getChildren().remove(vue);
                            });

                            cartesVisibles.getChildren().add(vue);
                        }
                    }
                    if(change.wasRemoved()){
                        for (int i = 0; i < change.getRemovedSize(); i++) {
                            cartesVisibles.getChildren().remove(trouveCarteVagon(change.getRemoved().get(i)));
                        }
                    }
                }
            });
        }
    };
}
