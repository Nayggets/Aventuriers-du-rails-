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
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

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
    private Button passer;
    private HBox listeDestinations;
    private HBox cartesVisibles;
    private VueJoueurCourant vueJCour;
    private VueJoueurCourantDesti vueJCourDesti;
    private HBox PlateauAndPlayer;
    private VuePlateau vuePlateau;
    private VBox Player;
    private VBox joueur;
    private boolean debutPasser;
    private VuePiocheDestination piocheDesti;
    private VuePiocheCarteWagon piocheWagon;
    private Label instructionMSG;

    private Button opacityMap;


    public VueDuJeu(IJeu jeu) {

        Image view = new Image("/images/fondTest.jpg");
        this.jeu = jeu;
        //jeu.joueurCourantProperty().get().cartesWagonProperty().add(CouleurWagon.JAUNE);
        listeDestinations = new HBox();
        listeDestinations.setSpacing(50);
        cartesVisibles = new HBox();
        cartesVisibles.setSpacing(60);
        vuePlateau = new VuePlateau();
        joueur = new VBox();
        passer = new Button("Passer");
        vueJCour = new VueJoueurCourant(jeu);
        vueJCourDesti = new VueJoueurCourantDesti(jeu);
        piocheDesti = new VuePiocheDestination();
        piocheWagon = new VuePiocheCarteWagon();
        this.setBackground(new Background(new BackgroundImage(view, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));
        PlateauAndPlayer = new HBox();



        ImageView imageView = new ImageView(
                new Image("/images/toggle-button.png")
        );
        opacityMap = new Button("", imageView);
        opacityMap.setStyle("-fx-background-color: transparent");


        instructionMSG = new Label();
        instructionMSG.setStyle("-fx-font-weight: bold;-fx-font-size: 18");
        instructionMSG.setPadding(new Insets(20,0,0,0));
        //instructionMSG.setLayoutX(250.0);
        //instructionMSG.setLayoutX(250.0);


        //-----Paramètres visuels-----

        //plateau + joueur
        final int[] i = {0};
        opacityMap.setOnAction(actionEvent -> {
            if(i[0] ==0){
                vuePlateau.setOpacity(0.75);//opacity only map et pas wagon posé
                i[0] =1;
            }
            else{
                vuePlateau.setOpacity(1);
                i[0] =0;
            }
        });
        opacityMap.setAlignment(Pos.TOP_RIGHT);
        opacityMap.setLayoutX(-50);
        opacityMap.setLayoutY(150);
        HBox test = new HBox(opacityMap);
        test.setPadding(new Insets(100,100,0,0));
        vuePlateau.getChildren().add(opacityMap);

        Player = new VBox();
        for(Joueur j : getJeu().getJoueurs()){
            Player.getChildren().add(new VueAutresJoueurs(j));
        }
        Player.getChildren().add(instructionMSG);
        Player.setAlignment(Pos.TOP_RIGHT);
        Player.setSpacing(30);
        //vuePlateau.setPadding(new Insets(0,0,0,100));
        PlateauAndPlayer.getChildren().addAll(vuePlateau,Player);
        PlateauAndPlayer.setPadding(new Insets(0,100,0,100));
        PlateauAndPlayer.setAlignment(Pos.TOP_LEFT);
        //PlateauAndPlayer.setSpacing(0);
        getChildren().add(PlateauAndPlayer);



        //cartes
        HBox h = new HBox();
        HBox h1 = new HBox();
        HBox h2 = new HBox();
        listeDestinations.setSpacing(5);
        Label nomJoueur = vueJCour.getNomJoueur();
        nomJoueur.setStyle("-fx-text-fill: white; -fx-font-weight: bold");
        //h.getChildren().addAll(nomJoueur, listeDestinations,passer);
        h.getChildren().addAll(listeDestinations);
        h.setSpacing(10);
        piocheWagon.setRotate(90);
        piocheDesti.setRotate(90);
        cartesVisibles.setSpacing(5);
        //h1.getChildren().addAll(piocheDesti, piocheWagon,cartesVisibles);
        h1.getChildren().addAll(piocheDesti, piocheWagon,cartesVisibles,passer);
        h1.setSpacing(-15);
        h2.getChildren().addAll(vueJCour,vueJCourDesti);
        h2.setAlignment(Pos.CENTER);

        VBox v = new VBox();
        v.getChildren().addAll(h,h1,h2);
        v.setSpacing(30);
        this.setBottom(v);
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

    public void creerBindingsPostShowStage() {
        vuePlateau.creerBindings();
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


        getJeu().instructionProperty().addListener(instructionListener);


        getJeu().cartesWagonVisiblesProperty().addListener(listenWagon);
        passer.setOnAction(actionEvent -> {
            getJeu().passerAEteChoisi();
        });
        vueJCour.creerBindings();
        vueJCourDesti.creerBindings();
        getJeu().joueurCourantProperty().addListener(listenerJ);

        //Pioche destination

        piocheDesti.setOnAction(actionEvent -> {
            jeu.uneDestinationAEtePiochee();
        });

        piocheWagon.setOnAction(actionEvent -> {
            jeu.uneCarteWagonAEtePiochee();
        });

        vuePlateau.prefWidthProperty().bind(getScene().widthProperty().multiply(0.65));


    }

    private final ChangeListener<String> instructionListener = (observableValue, oldString, newString) -> {
        instructionMSG.setText(newString);
    };

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

    private ChangeListener<IJoueur> listenerJ = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur oldValue, IJoueur newValue) {
            for (Node e : Player.getChildren()) {
                if (!(e instanceof VueAutresJoueurs)) {
                    continue;
                }
                VueAutresJoueurs joueur = (VueAutresJoueurs) e;
                if (oldValue != null) {
                    if (joueur.getJoueur().getNom().equals(oldValue.getNom())) {
                        joueur.removeStars();
                    }
                }
                if (joueur.getJoueur().getNom().equals(newValue.getNom())) {
                    joueur.putStars();
                }
            }
        }

    };

    private VueAutresJoueurs trouveVuAutreJoueur(IJoueur iJoueur) {
        for (int i = 0; i < Player.getChildren().size() ; i++) {
            VueAutresJoueurs vueAutresJoueurs =  (VueAutresJoueurs) Player.getChildren().get(i);
            if (vueAutresJoueurs.getJoueur().getNom().equals(iJoueur.getNom())){
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
                            cartesVisibles.getChildren().remove(trouveCarteVagon(change.getRemoved().get(i)));
                        }
                    }
                }
            });
        }
    };
}
