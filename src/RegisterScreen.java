import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;


public class RegisterScreen {

    private Stage stage;
    private CustmButton registerButton;
    private CustmButton cancelButton;
    private ImageView im1;
    private ImageView im2;
    private Image man;
    private Image woman;
    private Text gender;

    public RegisterScreen(String text, int rootwidth, int rootheight) {
        setupStage(text, rootwidth, rootheight);
    }

    private void setupStage(String text, int rootwidth, int rootheight) {
        stage = new Stage();
        StackPane window = new StackPane();
        VBox vbox = new VBox();
        HBox sbox = new HBox();
        HBox bbox = new HBox();

        window.setAlignment(Pos.CENTER);
        window.setMinSize(rootwidth,rootheight);
        window.setMaxSize(rootwidth,rootheight);
        vbox.setAlignment(Pos.CENTER);
        vbox.setMaxSize(300,500);
        sbox.setMinSize(300,100);
        sbox.setMaxSize(300,100);
        sbox.setSpacing(40);
        sbox.setAlignment(Pos.CENTER);
        bbox.setMaxSize(300,41.5);
        bbox.setAlignment(Pos.CENTER);
        bbox.setSpacing(100);

        Text header1 = new Text(text);
        header1.setFont(Font.font("TW Cen MT Condensed", FontWeight.EXTRA_BOLD, 36));
        header1.setFill(Color.hsb(150.0, 1.0, 0.5));
        Text header2 = new Text("Personal Information");
        header2.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 30));
        header2.setFill(Color.GREY);

        Rectangle rct1 = new Rectangle(1,20, Color.TRANSPARENT);
        Rectangle rct2 = new Rectangle(200, 20, Color.TRANSPARENT);
        Rectangle rct3 = new Rectangle(200, 10, Color.TRANSPARENT);
        Rectangle rct4 = new Rectangle(200, 10, Color.TRANSPARENT);
        Rectangle rct5 = new Rectangle(200, 35, Color.TRANSPARENT);

        TextField usernameField = new TextField();
        PasswordField passField = new PasswordField();
        TextField fullnameField = new TextField();
        TextField ageField = new TextField();
        TextField weightField = new TextField();
        TextField heightField = new TextField();

        RegForm username = new RegForm("User Name",usernameField);
        RegForm pass = new RegForm("Password",passField);
        RegForm fullname = new RegForm("Full Name",fullnameField);
        RegForm age = new RegForm("Age",ageField);
        RegForm weight = new RegForm("Weight",weightField);
        RegForm height = new RegForm("Height",heightField);

        try {
            InputStream manImage = Files.newInputStream(Paths.get("res/man.png"));
            InputStream womanImage = Files.newInputStream(Paths.get("res/woman.png"));
            man = new Image(manImage);
            woman = new Image(womanImage);
            im1 = new ImageView(man);
            im2 = new ImageView(woman);
            im1.setFitHeight(100);
            im1.setFitWidth(100);
            im2.setFitHeight(100);
            im2.setFitWidth(100);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while loading images");
        }
        gender = new Text();
        if (text.equals("Create Account")) {
            registerButton = new CustmButton("Sign Up", Color.ORANGE);
        }else{
            registerButton = new CustmButton("Update", Color.ORANGE);
        }
        cancelButton = new CustmButton("Cancel", Color.GRAY);
        ColorAdjust darken = new ColorAdjust();
        darken.setBrightness(-0.7);
        im1.setEffect(darken);
        im2.setEffect(darken);
        im1.setOnMouseClicked(e->{
            im1.setEffect(null);
            im2.setEffect(darken);
            gender.setText("Man");
        });
        im2.setOnMouseClicked(e->{
            im2.setEffect(null);
            im1.setEffect(darken);
            gender.setText("Woman");
        });


        registerButton.setOnMouseClicked(event -> {

            if(passField.getText().equals("")||fullnameField.getText().equals("")
                    ||gender.getText().equals("")||ageField.getText().equals("")||weightField.getText().equals("")
                    ||heightField.getText().equals("")){
                AlertBox.windowBox("Error", "Please fill the all fields, and choose your gender!");

            }else {
                if(text.equals("Create Account") && usernameField.getText().equals("")){
                    AlertBox.windowBox("Error", "Username, not set!");
                }else{
                if (text.equals("Create Account")) {
                    registerButton = new CustmButton("Sign Up", Color.ORANGE);
                    DataBase a = new DataBase();
                    a.registerUser(usernameField.getText(), passField.getText(), fullnameField.getText(), gender.getText(),
                            Integer.parseInt(ageField.getText()), Double.parseDouble(weightField.getText()), Double.parseDouble(heightField.getText()));
                    a.closeConnection();
                    stage.close();
                    new LoginScreen();
                }else{

                    DataBase a = new DataBase();
                    a.updateUserData(Getters.getCurrentUser(), passField.getText(), fullnameField.getText(), gender.getText(),
                            Integer.parseInt(ageField.getText()), Double.parseDouble(weightField.getText()), Double.parseDouble(heightField.getText()));
                    a.closeConnection();
                    TestScene.root.setEffect(null);
                    stage.close();
                }
            }
            }
        });

        cancelButton.setOnMouseClicked(event -> {
            stage.close();
            if (text.equals("Create Account")) {
                new LoginScreen();
            } else {
                TestScene.root.setEffect(null);
            }
        });


        sbox.getChildren().addAll(im1,im2);
        bbox.getChildren().addAll(registerButton, cancelButton);
        if (text.equals("Update Your Data")) {
            stage.initStyle(StageStyle.UNDECORATED);
            username.setVisible(false);
            username.setMaxSize(0,0);
        }
        window.getChildren().add(vbox);
        Scene scene = new Scene(window);
        vbox.getChildren().addAll(header1, rct1, username, pass, rct2, header2, rct3, sbox, rct4, fullname, age, weight, height, rct5, bbox);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }



    private class RegForm extends HBox{
        public RegForm(String label, TextField tf) {
            Label text = new Label(label);
            text.setMinWidth(80);
            text.setMaxWidth(80);
            tf.setMinSize(220,29);
            tf.setMaxSize(220,29);
            setMinSize(300,29);
            setMaxSize(300,29);
            setAlignment(Pos.CENTER_LEFT);
            getChildren().addAll(text,tf);


        }
        public RegForm(String label, PasswordField pf) {
            Label text = new Label(label);
            text.setMinWidth(80);
            text.setMaxWidth(80);
            pf.setMinSize(220,29);
            pf.setMaxSize(220,29);
            setMinSize(300,29);
            setMaxSize(300,29);
            setAlignment(Pos.CENTER_LEFT);
            getChildren().addAll(text,pf);

        }
    }
    private class CustmButton extends StackPane{
        public CustmButton(String text, Color color) {
            Rectangle rct1 = new Rectangle(100, 25, color);
            Label btxt = new Label(text);
            btxt.setFont(Font.font(btxt.getFont().toString(), FontWeight.SEMI_BOLD, 16));
            setAlignment(rct1,Pos.CENTER);
            setAlignment(btxt,Pos.CENTER);
            setMaxSize(100,25);
            getChildren().addAll(rct1,btxt);
        }
    }
}
