import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class TestScene {

    Stage stage = new Stage();
    HBox hbox1;
    HBox hbox2;

    VBox vboxMain;
    VBox vbox2;
    private HBox mainwin = new HBox();
    private TranslateTransition translateTransition;

    public TestScene() {
        setupStage();
    }

    private void setupStage() {

//-----------------------STAGE MARKUP---------------------------------
        vboxMain = new VBox();


        hbox1 = new HBox();
        hbox2 = new HBox();
        vbox2 = new VBox();

        Rectangle r1 = new Rectangle(50, 50, Color.hsb(150.0, 1.0, 0.5));
        Rectangle r2 = new Rectangle(550, 50, Color.hsb(150.0, 1.0, 0.5));
        Rectangle r3 = new Rectangle(200, 50, Color.OLIVE);
        Rectangle r4 = new Rectangle(200, 50, Color.GREEN);
        Rectangle r5 = new Rectangle(200, 50, Color.OLIVE);
        Rectangle r6 = new Rectangle(600, 350, Color.WHITE);
        AnchorPane r66 = new AnchorPane();



        MenuLine ml1 = new MenuLine("Menu1");
        ml1.setOnMouseClicked(event1 -> {
            translateTransition(Duration.seconds(0.2), mainwin, (int) mainwin.getTranslateX(), 0);

        });


        MenuLine ml2 = new MenuLine("Menu2");
        ml2.setOnMouseClicked(event1 -> {
            translateTransition(Duration.seconds(0.2), mainwin, (int) mainwin.getTranslateX(), -600);


        });
        MenuLine ml3 = new MenuLine("Menu3");
        ml3.setOnMouseClicked(event1 -> {
            translateTransition(Duration.seconds(0.2), mainwin, (int) mainwin.getTranslateX(), -1200);


        });
        hbox2.getChildren().addAll(ml1, ml2, ml3);

        hbox1.getChildren().addAll(r1, r2);

        TableLoader.setupTable();
        TextField tf = TableLoader.getFilterField();
        tf.setMaxWidth(590);
        TableView<Food> tv = TableLoader.getFoodTable();
        tv.setMaxWidth(590);
        tv.setMinHeight(450);
        tv.setVisible(false);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(tv);
        vbox2.setMinWidth(600);
        vbox2.getChildren().addAll(tf, stackPane);



        // Rectangle mr1 = new Rectangle(600, 500, Color.YELLOW);
        Rectangle mr2 = new Rectangle(600, 500, Color.RED);
        Rectangle mr3 = new Rectangle(600, 500, Color.GREEN);
        mainwin.setMaxWidth(1800);
        mainwin.getChildren().addAll(vbox2, mr2, mr3);



        vboxMain.getChildren().addAll(hbox1, hbox2, mainwin );
        Scene scene = new Scene(vboxMain);
        stage.setMaxHeight(600);
        stage.setMaxWidth(600);
        stage.setResizable(false);
        stage.setScene(scene);

        stage.show();


    }


    private static class MenuLine extends StackPane {
        public MenuLine(String name){

            Rectangle bg = new Rectangle(200, 40);
            bg.setFill(Color.hsb(150.0, 1.0, 0.5));

            Text text = new Text(name);
            text.setFill(Color.WHITE);
           text.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 18));
            /*final DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(2.2);
            text.setEffect(dropShadow);*/


            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);

            setOnMouseEntered(event -> {
                text.setFill(Color.BLACK);
            });

            setOnMouseExited(event -> {
                text.setFill(Color.WHITE);
            });

            setOnMousePressed(event -> {
                bg.setFill(Color.hsb(170.0, 1.0, 0.5));

            });

            setOnMouseReleased(event -> {
                bg.setFill(Color.hsb(150.0, 1.0, 0.5));

            });
        }
    }

    private TranslateTransition translateTransition(Duration duration, Node node, Integer from, Integer to){
        translateTransition = new TranslateTransition(duration, node);
        translateTransition.setFromX(from);
        translateTransition.setToX(to);
        translateTransition.setCycleCount(1);
        translateTransition.play();
        translateTransition.setOnFinished(e -> mainwin.setTranslateX(to));
        return translateTransition;

    }




}
