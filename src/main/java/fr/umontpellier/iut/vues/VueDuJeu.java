package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.Joueur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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
    private Button passer;
    private HBox listeDestinations;
    private HBox cartesVisibles;
    private VueJoueurCourant vueJCour;
    private VueJoueurCourantDesti vueJCourDesti;
    private HBox plateauAndPlayer;
    private VuePlateau vuePlateau;
    private VBox Player;
    private VBox joueur;
    private boolean debutPasser;
    private VuePiocheDestination piocheDesti;
    private VuePiocheCarteWagon piocheWagon;
    private Label instructionMSG;

    private Button opacityMap;
    private Button switchCarte;
    private Button toutCarte;

    private HBox contenuInteraction;
    private HBox interactionChoix;
    private HBox interactionJoueur;


    public VueDuJeu(IJeu jeu) {

        Image view = new Image("/images/fondJeu.png");
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
        //this.setBackground(new Background(new BackgroundImage(view, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));
        plateauAndPlayer = new HBox();

        ImageView imageView1 = new ImageView(
                new Image("/images/jetonB.png")//jaune/bleu
        );

        imageView1.setFitWidth(100);
        imageView1.setFitHeight(50);
        switchCarte = new Button("", imageView1);
        switchCarte.setStyle("-fx-background-color: transparent");


        ImageView imageView2 = new ImageView(
                new Image("/images/jetonR.png")
        );

        imageView2.setFitWidth(100);
        imageView2.setFitHeight(50);
        toutCarte = new Button("",imageView2);
        toutCarte.setStyle("-fx-background-color: transparent");


        ImageView imageView = new ImageView(
                new Image("/images/toggle-button.png")
        );
        opacityMap = new Button("", imageView);
        opacityMap.setStyle("-fx-background-color: transparent");


        instructionMSG = new Label();
        instructionMSG.setStyle("-fx-font-weight: bold;-fx-font-size: 18");
        instructionMSG.setPadding(new Insets(20,0,0,0));
        instructionMSG.translateXProperty().set(500);
        instructionMSG.translateYProperty().set(10);


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
        HBox espacementOpacityButton = new HBox(opacityMap);
        espacementOpacityButton.setPadding(new Insets(100,100,0,0));
        vuePlateau.getChildren().add(opacityMap);

        Player = new VBox();
        for(Joueur j : getJeu().getJoueurs()){
            Player.getChildren().add(new VueAutresJoueurs(j));
        }


        Player.setAlignment(Pos.TOP_RIGHT);
        Player.setSpacing(30);
        //vuePlateau.setPadding(new Insets(0,0,0,100));
        plateauAndPlayer.getChildren().addAll(vuePlateau,Player);
        plateauAndPlayer.setPadding(new Insets(0,100,0,100));
        plateauAndPlayer.setAlignment(Pos.TOP_LEFT);
        //PlateauAndPlayer.setSpacing(0);
        getChildren().add(plateauAndPlayer);


        //cartes
        contenuInteraction = new HBox();
        interactionChoix = new HBox();
        interactionJoueur = new HBox();
        listeDestinations.setSpacing(5);
        Label nomJoueur = vueJCour.getNomJoueur();
        nomJoueur.setStyle("-fx-text-fill: white; -fx-font-weight: bold");
        //contenuInteraction.getChildren().addAll(nomJoueur, listeDestinations,passer);

        contenuInteraction.setSpacing(10);
        piocheWagon.setRotate(90);
        piocheDesti.setRotate(90);
        cartesVisibles.setSpacing(5);
        //interactionChoix.getChildren().addAll(piocheDesti, piocheWagon,cartesVisibles);
        HBox passerh = new HBox(passer);
        passerh.setPadding(new Insets(0,0,0,10));
        HBox switchCarteh = new HBox(switchCarte);
        //switchCarteh.setPadding(new Insets(0,0,0,10));
        HBox toutCarteh = new HBox(toutCarte);
        //toutCarteh.setPadding(new Insets(0,0,0,10));
        VBox boutonSwitch = new VBox(switchCarteh,toutCarteh);
        boutonSwitch.setSpacing(-10);
        boutonSwitch.setPadding(new Insets(0,0,0,-20));
        VBox bouton = new VBox(passerh,boutonSwitch);
        bouton.setSpacing(5);
        interactionChoix.getChildren().addAll(piocheDesti, piocheWagon,cartesVisibles,bouton);
        interactionChoix.setSpacing(-15);
        //interactionChoix.setPadding(new Insets(50,0,0,0));
        interactionJoueur.getChildren().addAll(vueJCour,vueJCourDesti,instructionMSG);
        interactionJoueur.setAlignment(Pos.CENTER);

        VBox v = new VBox();
        v.getChildren().addAll(contenuInteraction,interactionChoix,interactionJoueur);
        interactionJoueur.setSpacing(-100);
        this.setBottom(v);
        v.setSpacing(10);


        final int[] j = {0};
        switchCarte.setOnAction(actionEvent -> {
            if(j[0] ==1){
                vueJCourDesti.setVisible(false);
                vueJCour.setVisible(true);
                j[0] =0;
            }
            else{
                vueJCourDesti.setVisible(true);
                vueJCour.setVisible(false);
                j[0] =1;
            }
        });
        toutCarte.setOnAction(actionEvent -> {
            vueJCourDesti.setVisible(true);
            vueJCour.setVisible(true);
        });
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
                        interactionChoix.getChildren().set(2,listeDestinations);
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
                        if(listeDestinations.getChildren().isEmpty()){
                            interactionChoix.getChildren().set(2,cartesVisibles);
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
