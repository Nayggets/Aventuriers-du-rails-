package fr.umontpellier.iut.vues;

import javafx.geometry.Insets;
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
        aventurier = new ImageView("/images/titre.png");
        Image view = new Image("/images/imgAcceuil.png");
        rule = new Button();
        rule.setStyle("-fx-background-color: transparent;");
        rule.setGraphic(new ImageView("/images/option.png"));
        play = new Button();
        play.setStyle("-fx-background-color: transparent;");
        play.setGraphic(new ImageView("/images/play.png"));
        exit = new Button();
        exit.setStyle("-fx-background-color: transparent;");
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
        this.setScene(scene);


        //---partie visuelle---
        BorderPane root = new BorderPane();
        play.setPadding(new Insets(100,0,0,0));
        VBox v = new VBox(aventurier,play);
        v.setAlignment(Pos.CENTER);
        v.setPadding(new Insets(0,0,0,100));
        root.setCenter(v);

        BorderPane bottom = new BorderPane();
        HBox exith = new HBox(exit);
        exith.setPadding(new Insets(75,0,0,0));
        bottom.setRight(exith);
        miniRule.setStyle("-fx-font-size: 40;-fx-text-fill: white; -fx-font-weight: bold");
        bottom.setLeft(miniRule);
        bottom.setPadding(new Insets(275,0,0,0));
        //top droite bottom gauche
        root.setBottom(bottom);

        BorderPane top = new BorderPane();
        top.setRight(rule);
        root.setTop(top);

        vBox.getChildren().addAll(root);
    }

    public boolean isExited(){
        return exited;
    }
}
