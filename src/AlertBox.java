import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public AlertBox(String boxType) {
        if (boxType.equals("login")){
            windowBox("", "Wrong username / password");
        }
    }

    public static void windowBox(String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinHeight(100);
        window.setMinWidth(200);

        Label label = new Label();
        label.setText(message);
        label.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 18));

        Button okButton = new Button("ok");
        okButton.setPrefWidth(50);
        okButton.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 18));
        okButton.setOnAction(event -> window.close());

        VBox layout = new VBox();
        layout.setSpacing(15);
        layout.setPrefSize(300,100);
        layout.getChildren().addAll(label, okButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
