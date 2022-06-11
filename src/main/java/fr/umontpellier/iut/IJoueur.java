package fr.umontpellier.iut;

import fr.umontpellier.iut.rails.CouleurWagon;
import fr.umontpellier.iut.rails.Destination;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

import java.util.List;

public interface IJoueur {

    ObservableList<CouleurWagon> cartesWagonProperty();

    public static enum Couleur {
        JAUNE(Color.YELLOW),
        ROUGE(Color.RED),
        BLEU(Color.BLUE),
        VERT(Color.GREEN),
        ROSE(Color.PINK);

        private final Color javaFxColor;

        Couleur(Color javaFxColor) {
            this.javaFxColor = javaFxColor;
        }

        public Color getJavaFxColor() {
            return javaFxColor;
        }
    }

    List<CouleurWagon> getCartesWagon();
    List<Destination> getDestinations();
    int getNbWagons();
    String getNom();
    Couleur getCouleur();
    int getNbGares();
    int getScore();
}