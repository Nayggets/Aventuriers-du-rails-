package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.CouleurWagon;
import fr.umontpellier.iut.rails.Destination;
import fr.umontpellier.iut.rails.Jeu;
import fr.umontpellier.iut.rails.Joueur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 *
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 *
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, les 5 cartes Wagons visibles, les destinations lors de l'étape d'initialisation de la partie, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends BorderPane {

    private IJeu jeu;
    private VuePlateau plateau;
    private  Button passer;
    private HBox listeDestinations;
    private HBox cartesVisibles;
    private VueJoueurCourant vueJCour;
    private HBox PlateauAndPlayer;
    private VuePlateau vuePlateau;
    private VBox Player;
    private VBox joueur;
    private boolean debutPasser;
    private VuePiocheDestination piocheDesti;
    private VuePiocheCarteWagon piocheWagon;


    public VueDuJeu(IJeu jeu) {

        Image view = new Image("/images/test5.jpg");
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
        piocheDesti = new VuePiocheDestination();
        piocheWagon = new VuePiocheCarteWagon();
        this.setBackground(new Background(new BackgroundImage(view, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));
        PlateauAndPlayer = new HBox();

        Player = new VBox();
        for(Joueur j : getJeu().getJoueurs()){
            Player.getChildren().add(new VueAutresJoueurs(j));
        }
        Player.setAlignment(Pos.TOP_RIGHT);
        PlateauAndPlayer.getChildren().addAll(vuePlateau,Player);
        PlateauAndPlayer.setSpacing(10);
        getChildren().add(PlateauAndPlayer);


        HBox h = new HBox();
        h.getChildren().addAll(passer, piocheDesti, piocheWagon,cartesVisibles);
        this.setBottom(h);


        getChildren().add(listeDestinations);
        getChildren().add(vueJCour);


    }

    public boolean isDebutPasser() {
        return debutPasser;
    }

    public void setDebutPasser(boolean debutPasser) {
        this.debutPasser = debutPasser;
    }

    public IJeu getJeu() {
        return jeu;
    }

    public VueDestination trouveVuDestination(IDestination id){
        for (int i = 0; i < listeDestinations.getChildren().size() ; i++) {
            VueDestination destination =  (VueDestination) listeDestinations.getChildren().get(i);
            if (destination.getDestination().getNom().equals(id.getNom())){
                return destination;
            }
        }
        return null;
    }


    public VueCarteWagon trouveCarteVagon(ICouleurWagon id){
        for (int i = 0; i < cartesVisibles.getChildren().size() ; i++) {
            VueCarteWagon vueWagon =  (VueCarteWagon) cartesVisibles.getChildren().get(i);
            if (vueWagon.getCouleurWagon().equals(id)){
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

                    jeu.uneDestinationAEteChoisie(b.getDestination().getNom());
            });
        }
        getJeu().destinationsInitialesProperty().addListener(listener);


        getJeu().cartesWagonVisiblesProperty().addListener(listenWagon);
        passer.setOnAction(actionEvent -> {
            getJeu().passerAEteChoisi();
        });
        vueJCour.creerBindings();
        getJeu().joueurCourantProperty().addListener(listenerJ);

        //Pioche destination

        piocheDesti.setOnAction(actionEvent -> {
            jeu.uneDestinationAEtePiochee();
        });

        piocheWagon.setOnAction(actionEvent -> {
            jeu.uneCarteWagonAEtePiochee();
        });

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

    private ChangeListener<IJoueur> listenerJ = new ChangeListener<IJoueur>() {
        @Override
        public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur oldValue, IJoueur newValue) {
            for(Node e : Player.getChildren()){
                VueAutresJoueurs vue = (VueAutresJoueurs) e;
                if(oldValue != null){
                    if(vue.joueur.getNom().equals(oldValue.getNom())){
                        vue.removeStars();
                    }
                }

                if(vue.joueur.getNom().equals(newValue.getNom())){
                    vue.putStars();
                }
            }
        }

    };

    private VueAutresJoueurs trouveVuAutreJoueur(IJoueur iJoueur) {
        for (int i = 0; i < Player.getChildren().size() ; i++) {
            VueAutresJoueurs vueAutresJoueurs =  (VueAutresJoueurs) cartesVisibles.getChildren().get(i);
            if (vueAutresJoueurs.joueur.getNom().equals(iJoueur.getNom())){
                return vueAutresJoueurs;
            }
        }
        return null;
    }

    private ListChangeListener<ICouleurWagon> listenWagon = new ListChangeListener<ICouleurWagon>() {
        @Override
        public void onChanged(Change<? extends ICouleurWagon> change ) {
            Platform.runLater(() -> {
                while (change.next()) {
                    if (change.wasAdded()) {
                        for (int i = 0; i < change.getAddedSize(); i++) {
                            VueCarteWagon vue = new VueCarteWagon(change.getAddedSubList().get(i),false);


                            cartesVisibles.getChildren().add(vue);
                        }
                    }
                    if(change.wasRemoved()){
                        for (int i = 0; i < change.getRemovedSize(); i++) {
                            System.out.println("test");
                            cartesVisibles.getChildren().remove(trouveCarteVagon(change.getRemoved().get(i)));
                        }
                    }
                }
            });
        }
    };
}
