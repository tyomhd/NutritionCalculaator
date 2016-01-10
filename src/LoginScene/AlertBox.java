package LoginScene;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.VBox;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class AlertBox {

    public static void windowBox(String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);

        Button yesButton = new Button("yes");
        Button noButton = new Button("no");
        noButton.setOnAction(event -> window.close());
        yesButton.setOnAction(event -> System.exit(0));

        VBox layout = new VBox();
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }




}
