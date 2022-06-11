package fr.umontpellier.iut.vues;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ReglesDuJeu extends Stage {
    private Button next;
    private Button back;
    private VBox vBox;

    public ReglesDuJeu() {
        next = new Button();
        back = new Button();

        next.setOnAction(actionEvent -> {
            this.close();
        });
        back.setOnAction(actionEvent -> {
            this.close();
        });

        Image view = new Image("/images/imgAcceuil.png");
        vBox = new VBox();
        vBox.setBackground(new Background(new BackgroundImage(view, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,true,true,true,true))));

        Scene scene = new Scene(vBox);
        this.setMaximized(true);
        this.setFullScreen(true);
        this.setScene(scene);
    }
}
