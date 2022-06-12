package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.RailsIHM;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableListValue;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.SetChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe correspond à une nouvelle fenêtre permettant de choisir le nombre et les noms des joueurs de la partie.
 *
 * Sa présentation graphique peut automatiquement être actualisée chaque fois que le nombre de joueurs change.
 * Lorsque l'utilisateur a fini de saisir les noms de joueurs, il demandera à démarrer la partie.
 */
public class VueChoixJoueurs extends Stage {

    private ObservableList<String> nomsJoueurs;
    private VBox vBox;
    private VBox joueurEntrerNom;

    private Button dec;
    private Button inc;

    private Button goPlay;
    private HBox button;

    private IntegerProperty nb;
    private boolean launch = false;
    private int nb2;
    public ObservableList<String> nomsJoueursProperty() {
        return nomsJoueurs;
    }


    public List<String> getNomsJoueurs() {
        return nomsJoueurs;
    }

    public VueChoixJoueurs() {
        this.setMaximized(true);
        this.setFullScreen(true);
        nomsJoueurs = FXCollections.observableArrayList();
        nomsJoueurs.add("joueur 1");
        nomsJoueurs.add("joueur 2");
        nomsJoueurs.add("joueur 3");
        nomsJoueurs.add("joueur 4");
        nomsJoueurs.add("joueur 5");
        nb = new SimpleIntegerProperty(5);
        Image view = new Image("/images/fondJeuChoix.png");


        vBox = new VBox();
        goPlay = new Button("Lancer");
        goPlay.setOnAction(actionEvent -> {
            launch = true;
            nomsJoueurs.add("a");

        });
        joueurEntrerNom = new VBox();
        vBox.setBackground(new Background(new BackgroundImage(view, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));
        Scene scene = new Scene(vBox);
        this.setScene(scene);
        button = new HBox();
        dec = new Button("retirer (-)");
        dec.setStyle("-fx-background-color: red");
        inc = new Button("ajouter (+)");
        inc.setStyle("-fx-background-color: green");
        dec.setOnAction(actionEvent -> {
            if(nb.getValue() > 2){

                nb.setValue(nb.getValue() - 1);

            }
        });
        inc.setOnAction(actionEvent -> {
            if(nb.getValue() < 5){
                nb.setValue(nb.getValue() + 1);
            }
        });
        vBox.setPadding(new Insets(100,100,100,100));

        //---paramètres visuels boutons----
        HBox hbutton = new HBox(dec,inc);
        hbutton.setSpacing(10);
        hbutton.setPadding(new Insets(5,0,0,0));
        VBox vbutton = new VBox(hbutton,goPlay);
        vbutton.setSpacing(100);
        vbutton.setAlignment(Pos.CENTER);

        goPlay.setMaxHeight(100);
        goPlay.setMaxWidth(150);
        goPlay.setStyle("-fx-background-color: #adadad;-fx-font-size: 20");
        //button.setSpacing(10);
        //button.setPadding(new Insets(10,10,10,10));
        button.getChildren().add(vbutton);



        vBox.getChildren().add(joueurEntrerNom);
        vBox.getChildren().add(button);

        joueurEntrerNom.setAlignment(Pos.CENTER);
        button.setAlignment(Pos.CENTER);
        creerBindings();

    }

    /**
     * Définit l'action à exécuter lorsque la liste des participants est correctement initialisée
     */

    private ChangeListener<Integer> tkt = new ChangeListener<Integer>() {
        @Override
        public void changed(ObservableValue<? extends Integer> observableValue, Integer oldValue, Integer newValue) {

            throw new RuntimeException("coucou");
        }
    };



    private ListChangeListener<String> listener = new ListChangeListener<String>() {
        @Override
        public void onChanged(Change<? extends String> change) {
            Platform.runLater(() -> {
                while (change.next()) {
                    if(change.wasUpdated()){

                    }
                }
            });
        }
    };

    public boolean getLaunch(){
        return launch;
    }
    public void setNomsDesJoueursDefinisListener(ListChangeListener<String> quandLesNomsDesJoueursSontDefinis) {
        nomsJoueurs.addListener(quandLesNomsDesJoueursSontDefinis);
    }

    /**
     * Définit l'action à exécuter lorsque le nombre de participants change
     */
    protected void setChangementDuNombreDeJoueursListener(ChangeListener<Integer> quandLeNombreDeJoueursChange) {

    }

    /**
     * Vérifie que tous les noms des participants sont renseignés
     * et affecte la liste définitive des participants
     */
    protected void setListeDesNomsDeJoueurs() {
        ArrayList<String> tempNamesList = new ArrayList<>();
        for (int i = 1; i <= getNombreDeJoueurs() ; i++) {
            String name = getJoueurParNumero(i);
            if (name == null || name.equals("")) {
                tempNamesList.clear();
                break;
            }
            else
                tempNamesList.add(name);
        }
        if (!tempNamesList.isEmpty()) {
            hide();
            nomsJoueurs.clear();
            nomsJoueurs.addAll(tempNamesList);
        }
    }

    /**
     * Retourne le nombre de participants à la partie que l'utilisateur a renseigné
     */
    protected int getNombreDeJoueurs() {
        return nb2;
    }

    /**
     * Retourne le nom que l'utilisateur a renseigné pour le ième participant à la partie
     * @param playerNumber : le numéro du participant
     */
    protected String getJoueurParNumero(int playerNumber) {
        return nomsJoueurs.get(playerNumber-1);
    }

    public void creerBindings(){

        for(int i = 0 ; i < 5 ; i++){
            joueurEntrerNom.getChildren().add(new VueChoixJoueur(i,this));
        }

        nb.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {

                if(oldValue.intValue() > newValue.intValue()){
                    int i = oldValue.intValue() - 1;
                    while(i >= newValue.intValue()){
                        joueurEntrerNom.getChildren().remove(i);
                        nomsJoueurs.remove(i);
                        i--;
                    }

                }
                if(oldValue.intValue() < newValue.intValue()){
                    int i = oldValue.intValue();
                    while(i < newValue.intValue()){
                        TextField t = new TextField();

                        joueurEntrerNom.getChildren().add(new VueChoixJoueur(i,VueChoixJoueurs.this));
                        nomsJoueurs.add("Joueur " + String.valueOf(i+1));
                        i++;
                    }
                }
            }
        });

    }

}
