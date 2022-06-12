package fr.umontpellier.iut.vues;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class VueChoixJoueur extends Pane {

    private HBox box;
    private Button b;
    private TextField t;
    private int nb;

    public VueChoixJoueur(int i,VueChoixJoueurs vue){
        nb = i;
        b = new Button("change");
        box = new HBox();
        t = new TextField("Joueur " +String.valueOf(i+1));
        box.setAlignment(Pos.CENTER);
        ImageView view;
        this.setMinWidth(400);
        this.setMinHeight(125);
        this.setMaxWidth(400);
        this.setMaxHeight(125);
        Rectangle rect = new Rectangle(1000,1000);
        rect.setArcHeight(300.0);
        rect.setArcWidth(150.0);
        this.setShape(rect);
        switch (i)
        {
            case 0:
            {
                view = new ImageView("/images/avatar/avatar-ROUGE.png");
                view.setFitHeight(100);
                view.setFitWidth(100);
                box.getChildren().add(view);
                this.setStyle("-fx-background-color:#cb3939 " );
                break;
            }
            case 1:
            {
                view = new ImageView("/images/avatar/avatar-BLEU.png");
                view.setFitHeight(100);
                view.setFitWidth(100);
                box.getChildren().add(view);
                this.setStyle("-fx-background-color:#4a4ad7 " );
                break;
            }
            case 2:
            {
                view = new ImageView("/images/avatar/avatar-VERT.png");
                view.setFitHeight(100);
                view.setFitWidth(100);
                box.getChildren().add(view);
                this.setStyle("-fx-background-color:#3da93d " );
                break;
            }
            case 3:
            {
                view = new ImageView("/images/avatar/avatar-JAUNE.png");
                view.setFitHeight(100);
                view.setFitWidth(100);
                box.getChildren().add(view);
                this.setStyle("-fx-background-color:#e0e03f " );
                break;
            }
            case 4:
            {
                view = new ImageView("/images/avatar/avatar-ROSE.png");
                view.setFitHeight(100);
                view.setFitWidth(100);
                box.getChildren().add(view);
                this.setStyle("-fx-background-color:pink " );
                break;
            }
        }
        b.setOnAction(actionEvent -> {
            if(!t.getText().isEmpty()){
                vue.getNomsJoueurs().set(nb,t.getText());

            }

        });
        this.getChildren().add(box);
        box.getChildren().addAll(b,t);
    }
}
