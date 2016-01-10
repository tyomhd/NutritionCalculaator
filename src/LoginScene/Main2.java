package LoginScene;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;



public class Main2 extends Application {

    Stage window;
    Scene scene1, scene2, scene3, scene4;

    public Parent createContent(){
        Pane root = new Pane();
        //new


        root.setPrefSize(800, 600);
        try (InputStream in = Files.newInputStream(Paths.get("res/bg.jpg"))) {
            ImageView img = new ImageView(new Image(in));
            img.setFitWidth(810);
            img.setFitHeight(610);
            root.getChildren().add(img);
        }
        catch (IOException e) {
            System.out.println("No image");
        }

        Title title = new Title("F O O D", Color.WHITE);
        title.setTranslateX(300);
        title.setTranslateY(200);

        TextField username = new TextField();
        TextField password = new TextField();


        MenuLine login = new MenuLine("Log in");
        login.setOnMouseClicked(event1 -> {
            window.setScene(scene2);
        });


        MenuLine lineExit = new MenuLine("Exit");
        lineExit.setOnMouseClicked(event ->
                System.exit(0)
        );


        MenuBox menu = new MenuBox(
                login,
                new MenuLine("Register"),
                lineExit);
        menu.setTranslateX(325);
        menu.setTranslateY(300);
        root.getChildren().addAll(title,menu);

        return root;
    }

/*
    private Parent createScene1(){
        Pane root = new Pane();

        root.setPrefSize(860, 600);
        /*try (InputStream in = Files.newInputStream(Paths.get("bg.jpg"))) {
            ImageView img = new ImageView(new Image(in));
            img.setFitWidth(860);
            img.setFitHeight(600);
            root.getChildren().add(img);
        }
        catch (IOException e) {
            System.out.println("No image");
        }

        Title title = new Title("F O O D", Color.BLACK);
        title.setTranslateX(300);
        title.setTranslateY(200);


        root.getChildren().addAll(title);

        return root;
    }
*/


    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        scene1 = new Scene(createContent());

        scene2 = new Scene(Scene1.createScene1());

        window.setTitle("i200 - Food");
        window.setScene(scene1);
        window.setResizable(false);
        window.show();
    }

    private static class Title extends StackPane {
        public Title(String name,javafx.scene.paint.Color x) {
            Rectangle rtg = new Rectangle(250, 60);
            rtg.setStroke(x);
            rtg.setStrokeWidth(2);
            rtg.setFill(null);

            Text text = new Text(name);
            text.setFill(x);
            text.setFont(Font.font("TW Cen MT Condensed", FontWeight.SEMI_BOLD, 50));

            setAlignment(Pos.CENTER);
            getChildren().addAll(rtg, text);
        }
    }

    private static class MenuBox extends VBox {
        public MenuBox(MenuLine... items) {
            getChildren().add(createSeparator());

            for (MenuLine item : items) {
                getChildren().addAll(item, createSeparator());
            }
        }

        private Line createSeparator() {
            Line sep = new Line();
            sep.setEndX(200);
            sep.setStroke(Color.DARKGRAY);
            return sep;

        }

    }
    private static class MenuLine extends StackPane{
        public MenuLine(String name){
            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[]{
                    new Stop(0, Color.DARKVIOLET),
                    new Stop(0.1, Color.BLACK),
                    new Stop(0.9, Color.BLACK),
                    new Stop(1, Color.DARKVIOLET)
            });

            Rectangle bg = new Rectangle(200, 30);
            bg.setOpacity(0.4);

            Text text = new Text(name);
            text.setFill(Color.WHITE);
            text.setFont(Font.font("TW Cen MT Condensed", FontWeight.SEMI_BOLD, 22));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);

            setOnMouseEntered(event -> {
                bg.setFill(gradient);
                text.setFill(Color.VIOLET);
            });

            setOnMouseExited(event -> {
                bg.setFill(Color.BLACK);
                text.setFill(Color.WHITE);
            });

            setOnMousePressed(event -> {
                bg.setFill(Color.DARKVIOLET);
            });

            setOnMouseReleased(event -> {
                bg.setFill(gradient);
            });
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
