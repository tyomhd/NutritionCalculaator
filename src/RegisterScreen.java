import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;

public class RegisterScreen {

    private Stage stage;
    private Button registerButton;
    private Button cancelButton;
   /*
    private String username;
    private String password;
    private String fullname;
    private String sex;
    private int age;
    private double weight;
    private double height;
    */

    public RegisterScreen() {
        setupStage();
    }

    private void setupStage() {
        stage = new Stage();
        VBox vbox = new VBox();
        TilePane tile = new TilePane();
        Scene scene = new Scene(vbox);

        Label l2 = new Label("Username");
        Label l3 = new Label("Password");
        Label l4 = new Label("Full name");
        Label l5 = new Label("Sex");
        Label l6 = new Label("Age");
        Label l7 = new Label("Weight");
        Label l8 = new Label("Height");

        TextField kasutajanimiField = new TextField();
        PasswordField paroolField = new PasswordField();
        TextField fullnameField = new TextField();
        ChoiceBox sexField = new ChoiceBox(FXCollections.observableArrayList("Man", "Woman"));
        TextField ageField = new TextField();
        TextField weightField = new TextField();
        TextField heightField = new TextField();

        registerButton = new Button("Register");
        cancelButton = new Button("Cancel");




        registerButton.setOnAction(event -> {
            if(kasutajanimiField.getText().equals("")||paroolField.getText().equals("")||fullnameField.getText().equals("")
                    ||String.valueOf(sexField.getValue()).equals("")||ageField.getText().equals("")||weightField.getText().equals("")
                    ||heightField.getText().equals("")){
                AlertBox.windowBox("Error", "Fields can't be blank");

            }else {
                DataBase a = new DataBase();
                a.registerUser(kasutajanimiField.getText(), paroolField.getText(), fullnameField.getText(), String.valueOf(sexField.getValue()),
                        Integer.parseInt(ageField.getText()), Double.parseDouble(weightField.getText()), Double.parseDouble(heightField.getText()));
                a.closeConnection();
                stage.close();
                new LoginScreen();
            }

        });

        cancelButton.setOnAction(event -> {
            stage.close();
            new LoginScreen();
        });

        tile.setPrefColumns(2);
        tile.getChildren().addAll(l2, kasutajanimiField, l3, paroolField, l4, fullnameField, l5, sexField,
                l6, ageField, l7, weightField, l8, heightField);
        vbox.setMinSize(600,600);
        vbox.getChildren().addAll(tile,registerButton,cancelButton);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
