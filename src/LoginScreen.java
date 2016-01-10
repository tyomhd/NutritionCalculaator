import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LoginScreen {
    private TextField kasutajanimi;
    private PasswordField parool;
    private Button loginButton;
    private Button registerButton;
    private Stage stage = new Stage();
    private ImageView bg;

    LoginScreen() {
        setupScene();
        setupLogin();
        setupRegister();
    }

    private void setupScene() {
        StackPane root = new StackPane();
        VBox vbox = new VBox();
        Scene scene = new Scene(root);

        try {
            InputStream iconImage = Files.newInputStream(Paths.get("res/icon.png"));
            Image icon = new Image(iconImage);
            stage.getIcons().add(icon);
            InputStream backGround = Files.newInputStream(Paths.get("res/background.png"));
            bg = new ImageView(new Image(backGround));
            bg.setFitWidth(600);
            bg.setFitHeight(600);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Label l1 = new Label("User Name");
        l1.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 25));
        kasutajanimi = new TextField();
        Label l2 = new Label("Password");
        l2.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 25));
        parool = new PasswordField();
        loginButton = new Button("Sign In");
        loginButton.setPrefWidth(100);
        loginButton.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 18));
        registerButton = new Button("Sign Up");
        registerButton.setPrefWidth(100);
        registerButton.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 18));
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(15);
        vbox.setMaxSize(300,300);
        vbox.getChildren().addAll(l1, kasutajanimi, l2, parool, loginButton, registerButton);

        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(bg, vbox);
        stage.setScene(scene);
        stage.setMaxHeight(600);
        stage.setMaxWidth(600);
        try {
            InputStream iconImage = Files.newInputStream(Paths.get("res/icon.png"));
            Image icon = new Image(iconImage);
            stage.getIcons().add(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Nutrition Calculator");
        stage.show();
    }

    private void setupLogin() {
        loginButton.setOnAction(event -> {
            String nimi = kasutajanimi.getText();
            String p = parool.getText();
            DataBase a = new DataBase();
            boolean result = a.login(nimi, p);
            a.closeConnection();

            if (result) {
                TestScene ts = new TestScene(nimi);
                System.out.println("You have logged in");
                //RegisterScreen ud = new RegisterScreen(nimi);
                stage.close();
            }
            else {
                new AlertBox("login");
            }
        });
    }

    private void setupRegister() {
        registerButton.setOnAction(event -> {
            stage.close();
            new RegisterScreen("Create Account", 600, 600);
        });
    }
}

    //RegisterScreen(String kasutajaSisse)