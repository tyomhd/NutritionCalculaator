import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ElementProgressBar extends HBox {
    public ElementProgressBar(String element, String units, double current, double target) {

        StackPane stack1 = new StackPane();
        StackPane stack2 = new StackPane();

        Text elementText = new Text("  "+element);
        elementText.setFill(Color.BLACK);

        double percent = current/target;
        Rectangle prct1 = new Rectangle(450, 10, Color.WHITE);
        Rectangle prct2 = new Rectangle(0, 10, Color.GREENYELLOW);
        prct2.setVisible(false);
        prct1.setArcHeight(10);
        prct1.setArcWidth(10);
        prct2.setArcHeight(10);
        prct2.setArcWidth(10);
        prct1.setStrokeWidth(1);
        prct2.setStrokeWidth(1);
        prct1.setStroke(Color.GRAY);
        prct2.setStroke(Color.GRAY);

        Text currentText = new Text();
        Text targetText = new Text();
        currentText.setText(String.format("%.0f", current)+" "+units);
        currentText.setFill(Color.BLACK);
        targetText.setText(String.format("%.0f", target)+" "+units);
        targetText.setFill(Color.BLACK);

        if(current < target && current != 0){
            prct2.setVisible(true);
            prct2.setWidth(450*percent);
            if(percent > 0.9){
                prct2.setFill(Color.GREEN);
            }
        }
        if(current > target){
            prct2.setVisible(true);
            prct2.setWidth(450);
            prct2.setFill(Color.GREEN);
        }


        stack1.setMinSize(120, 15);
        stack1.setAlignment(elementText, Pos.CENTER_LEFT);
        stack1.getChildren().addAll(elementText);

        stack2.setMinSize(480, 15);
        stack2.setAlignment(prct1, Pos.CENTER_LEFT);
        stack2.setAlignment(prct2, Pos.CENTER_LEFT);
        stack2.getChildren().addAll(prct1,prct2);

        setAlignment(Pos.CENTER);
        setMinSize(600, 15);
        setMaxSize(600, 15);
        getChildren().addAll(stack1,stack2);

    }//Constructor End
  
    
}//END
