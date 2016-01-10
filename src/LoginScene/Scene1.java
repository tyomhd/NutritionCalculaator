package LoginScene;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Scene1 {

    public static  Pane createScene1(){
        Pane root = new Pane();

        root.setPrefSize(800, 600);
        try (InputStream in = Files.newInputStream(Paths.get("res/bg.jpg"))) {
            ImageView img = new ImageView(new Image(in));
            img.setFitWidth(810);
            img.setFitHeight(60);
            root.getChildren().add(img);
        }
        catch (IOException e) {
            System.out.println("No image");
        }

        //Label label = new Label(text);

        Button backButton = new Button("Back");

        backButton.setOnAction(event -> System.out.println("hello"));


        BorderPane layout = new BorderPane( );
        layout.getChildren().addAll(backButton);

        return root;
    }
}
