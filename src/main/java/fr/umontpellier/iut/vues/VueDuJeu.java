package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.rails.Destination;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private VBox listeDestinations;
    private VueJoueurCourant vueJCour;

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        plateau = new VuePlateau();
        listeDestinations = new VBox();
        passer = new Button("Passer");
        vueJCour = new VueJoueurCourant(jeu);

        getChildren().add(listeDestinations);
        getChildren().add(passer);
        getChildren().add(vueJCour);


        //getChildren().add(plateau);
    }

    public IJeu getJeu() {
        return jeu;
    }

    public Label trouveLabelDestination(IDestination id){//pas sûre
        for (int i = 0; i < listeDestinations.getChildren().size() ; i++) {
            Label label = new Label(""+listeDestinations.getChildren().get(i));
            if (label.getText().equals(id.getNom())){
                return label;
            }
        }
        return null;
    }

    public void creerBindings() {
        getJeu().destinationsInitialesProperty().addListener(listener);
        vueJCour.creerBindings();

        passer.setOnAction(actionEvent -> {
            getJeu().passerAEteChoisi();
        });

    }

    ListChangeListener<Destination> listener = new ListChangeListener<Destination>() {
        @Override
        public void onChanged(Change<? extends Destination> change ) {
            Platform.runLater(() -> {
                while (change.next()) {
                    if (change.wasAdded()) {
                        for (int i = 0; i < change.getAddedSize(); i++) {
                            listeDestinations.getChildren().add(new Label(change.getAddedSubList().get(i).getNom()));
                        }
                    }
                    if(change.wasRemoved()){
                        for (int i = 0; i < change.getRemovedSize(); i++) {
                            listeDestinations.getChildren().remove(trouveLabelDestination(change.getRemoved().get(i)));
                        }
                    }
                }
            });
        }
    };
}