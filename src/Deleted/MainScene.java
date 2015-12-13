/*
package Deleted;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class MainScene {
    TextField toidunimetus;
    Button kcalButton;
    Stage stage = new Stage();

    MainScene() {
        setupScene();
        getData();
    }

    private void setupScene() {
        VBox vbox = new VBox();
        Scene scene = new Scene(vbox);

        Label l1 = new Label("Toidu nimetus");
        toidunimetus = new TextField();
        kcalButton = new Button("Vaata Kcal");
        Label l2 = new Label("Toidu otsing");

        vbox.getChildren().addAll(l1, toidunimetus, l2, kcalButton);

        stage.setScene(scene);
        stage.show();
    }

    private void getData() {
        kcalButton.setOnAction(event -> {
            String nimi = toidunimetus.getText();
            Andmebaas a = new Andmebaas();
            double kcal =  a.vaataDbDouble(nimi,"Energiashkiudainedkcal");
            System.out.println(kcal + " kcal in 100g");
            a.sulgeYhendus();

        });
    }

}
*/