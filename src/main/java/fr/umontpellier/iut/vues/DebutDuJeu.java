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

import java.util.ArrayList;


public class DebutDuJeu extends Stage {
    private VBox vBox;
    private boolean launch = false;
    private Button rule;
    private Button play;
    private Button exit;
    private Button left;
    private Button right;
    private ImageView aventurier;
    private ArrayList<ImageView> regle;
    private int nbRule;
    private Label miniRule;
    private HBox regleButton;
    private HBox menu;

    /////////

    public DebutDuJeu(){
        regle = new ArrayList<>();
        miniRule = new Label("2-5 joueur \n30-60\n8 ans et plus");
        aventurier = new ImageView("/images/titre.png");
        Image view = new Image("/images/imgAcceuil.png");
        rule = new Button();
        rule.setStyle("-fx-background-color: transparent;");
        rule.setGraphic(new ImageView("/images/option.png"));
        rule.setOnAction(actionEvent -> {
            menu.getChildren().set(0,regleButton);
            nbRule = 0;
            regleButton.getChildren().set(1,regle.get(0));
        });
        play = new Button();
        play.setStyle("-fx-background-color: transparent;");
        play.setGraphic(new ImageView("/images/play.png"));
        exit = new Button();
        exit.setStyle("-fx-background-color: transparent;");
        exit.setGraphic(new ImageView("/images/exit.png"));
        vBox = new VBox();

        ImageView view0 = new ImageView("/images/canvas.png");
        view0.setFitHeight(1200);
        view0.setFitWidth(1200);
        regle.add(view0);
        view0 = new ImageView("/images/canvas1.png");
        view0.setFitHeight(1200);
        view0.setFitWidth(1200);

        regle.add(view0);
        view0 = new ImageView("/images/canvas2.png");
        view0.setFitHeight(1200);
        view0.setFitWidth(1200);
        regle.add(view0);
        view0 = new ImageView("/images/canvas3.png");
        view0.setFitHeight(1200);
        view0.setFitWidth(1200);
        regle.add(view0);
        view0 = new ImageView("/images/canvas4.png");
        view0.setFitHeight(1200);
        view0.setFitWidth(1200);
        regle.add(view0);
        view0 = new ImageView("/images/canvas5.png");
        view0.setFitHeight(1200);
        view0.setFitWidth(1200);
        regle.add(view0);
        regleButton = new HBox();
        regleButton.setSpacing(0);
        left = new Button();
        left.setOnAction(actionEvent -> {
            if(nbRule > 0){
                nbRule--;
                regleButton.getChildren().set(1,regle.get(nbRule));
            }
            else{
                menu.getChildren().set(0,vBox);
            }

        });
        left.setGraphic(new ImageView("/images/Left.png"));
        left.setMinHeight(200);
        regleButton.getChildren().add(left);
        regleButton.getChildren().add(view0);

        right = new Button();
        right.setOnAction(actionEvent -> {
            if(nbRule < 5){
                nbRule++;
                regleButton.getChildren().set(1,regle.get(nbRule));
            }
            else{
                menu.getChildren().set(0,vBox);

            }


        });
        right.setMinHeight(200);
        right.setGraphic(new ImageView("/images/Right.png"));
        regleButton.getChildren().add(right);
        play.setOnAction(actionEvent -> {
            launch = true;
            this.close();
        });
        exit.setOnAction(actionEvent -> {

            this.close();
        });
        menu = new HBox();
        menu.setBackground(new Background(new BackgroundImage(view, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,true,true,true,true))));

        Scene scene = new Scene(menu);
        menu.setAlignment(Pos.CENTER);
        menu.getChildren().add(vBox);
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

    public boolean isLaunch(){
        return launch;
    }
}
