package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IRoute;
import fr.umontpellier.iut.IVille;
import fr.umontpellier.iut.rails.Joueur;
import fr.umontpellier.iut.rails.Route;
import fr.umontpellier.iut.rails.Ville;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.List;

/**
 * Cette classe présente les routes et les villes sur le plateau.
 *
 * On y définit le listener à exécuter lorsque qu'un élément du plateau a été choisi par l'utilisateur
 * ainsi que les bindings qui mettront ?à jour le plateau après la prise d'une route ou d'une ville par un joueur
 */
public class VuePlateau extends Pane {

    public VuePlateau() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/plateau.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void choixRouteOuVille(MouseEvent e) {
        IJeu jeu = ((VueDuJeu) getScene().getRoot()).getJeu();
        Node n = (Node)e.getSource();
        jeu.uneVilleOuUneRouteAEteChoisie(n.getId());

        lastRouteOuVilleClicked = n;
    }

    @FXML
    ImageView image;
    @FXML
    private Group villes;
    @FXML
    private Group routes;

    private Node lastRouteOuVilleClicked;

    private IJeu jeu;

    public void creerBindings() {
        jeu = ((VueDuJeu) getScene().getRoot()).getJeu();

        bindRedimensionPlateau();

        for(Object o : jeu.getVilles()){
            ((IVille) o).proprietaireProperty().addListener(priseDeVille);
        }

        for (Object o : jeu.getRoutes()) {
            ((IRoute) o).proprietaireProperty().addListener(priseDeRoute);
        }
    }

    private final ChangeListener<Joueur> priseDeVille = (observableValue, oldJoueur, newJoueur) -> {
        // on colore la ville
        ((Circle) lastRouteOuVilleClicked).setFill(newJoueur.getCouleur().getJavaFxColor());
    };

    public final ChangeListener<Joueur> priseDeRoute = (observableValue, oldJoueur, newJoueur) -> {
        for (Node node : ((Group) lastRouteOuVilleClicked).getChildren()) {
            Rectangle r = ((Rectangle) node);

            r.setFill(newJoueur.getCouleur().getJavaFxColor());
            r.setStroke(Color.BLACK);
            r.setStrokeWidth(5d);
        }
    };

    private final ChangeListener<Joueur> ecouteVille = new ChangeListener<Joueur>() {
        @Override
        public void changed(ObservableValue<? extends Joueur> observableValue, Joueur joueur, Joueur t1) {
            for (Node nVille : villes.getChildren()){
                Circle cVille = (Circle) nVille;
                List<IVille> listeVilles = (List<IVille>) ((VueDuJeu)getScene().getRoot()).getJeu().getVilles();
                IVille ville = listeVilles.stream().filter(v -> v.getNom().equals(nVille.getId())).findAny().orElse(null);
            }
        }
    };

    private void bindRedimensionPlateau() {
        bindRoutes();
        bindVilles();
        //Les dimensions de l'image varient avec celle de la scène
//        image.fitWidthProperty().bind(getScene().widthProperty());
//        image.fitHeightProperty().bind(getScene().heightProperty());
    }

    private void bindRectangle(Rectangle rect, double layoutX, double layoutY) {
//        Liste des propriétés à lier
//        rect.widthProperty()
//        rect.heightProperty()
//        rect.layoutXProperty()
//        rect.xProperty()
//        rect.layoutYProperty()
//        rect.yProperty()
    }

    private void bindRoutes() {
        for (Node nRoute : routes.getChildren()) {
            Group gRoute = (Group) nRoute;
            int numRect = 0;
            for (Node nRect : gRoute.getChildren()) {
                Rectangle rect = (Rectangle) nRect;
                bindRectangle(rect, DonneesPlateau.getRoute(nRoute.getId()).get(numRect).getLayoutX(), DonneesPlateau.getRoute(nRoute.getId()).get(numRect).getLayoutY());
                numRect++;
            }
        }
    }

    private void bindVilles() {
        for (Node nVille : villes.getChildren()) {
            Circle ville = (Circle) nVille;
            bindVille(ville, DonneesPlateau.getVille(ville.getId()).getLayoutX(), DonneesPlateau.getVille(ville.getId()).getLayoutY());
        }
    }

    private void bindVille(Circle ville, double layoutX, double layoutY) {
        ville.layoutXProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }
            @Override
            protected double computeValue() {
                return layoutX * image.getLayoutBounds().getWidth()/ DonneesPlateau.largeurInitialePlateau;
            }
        });
        ville.layoutYProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }
            @Override
            protected double computeValue() {
                return layoutY * image.getLayoutBounds().getHeight()/ DonneesPlateau.hauteurInitialePlateau;
            }
        });
        ville.radiusProperty().bind(new DoubleBinding() {
            { super.bind(image.fitWidthProperty(), image.fitHeightProperty());}
            @Override
            protected double computeValue() {
                return DonneesPlateau.rayonInitial * image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });
    }

}