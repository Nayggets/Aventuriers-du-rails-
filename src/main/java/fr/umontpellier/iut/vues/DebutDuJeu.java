package fr.umontpellier.iut.vues;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DebutDuJeu extends Stage {
    private VBox vBox;
    private boolean exited = false;
    private Button rule;
    private Button play;
    private Button exit;
    private ImageView aventurier;
    private Label miniRule;

    public DebutDuJeu(){
        miniRule = new Label("2-5 joueur \n30-60\n8 ans et plus");
        miniRule.setAlignment(Pos.BOTTOM_LEFT);
        aventurier = new ImageView("/images/titre.png");


        Image view = new Image("/images/imgAcceuil.png");
        rule = new Button();
        rule.setGraphic(new ImageView("/images/option.png"));
        play = new Button();
        play.setGraphic(new ImageView("/images/play.png"));
        exit = new Button();
        exit.setGraphic(new ImageView("/images/exit.png"));
        vBox = new VBox();
        play.setOnAction(actionEvent -> {
            this.close();
        });
        exit.setOnAction(actionEvent -> {
            exited = true;
            this.close();
        });

        vBox.setBackground(new Background(new BackgroundImage(view, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,true,true,true,true))));
        Scene scene = new Scene(vBox);
        this.setMaximized(true);
        this.setFullScreen(true);


        rule.setAlignment(Pos.TOP_RIGHT);
        play.setAlignment(Pos.CENTER);
        exit.setAlignment(Pos.BOTTOM_RIGHT);
        vBox.getChildren().addAll(rule,aventurier,play,exit,miniRule);
        vBox.setAlignment(Pos.CENTER);
        this.setScene(scene);

    }

    public boolean isExited(){
        return exited;
    }
}
