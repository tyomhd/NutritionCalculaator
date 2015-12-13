import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;



public class Food {

    private final StringProperty toiduNimetus;


    public Food(String toiduNimetus) {
        this.toiduNimetus = new SimpleStringProperty(toiduNimetus);

    }

    public String getToiduNimetus() {
        return toiduNimetus.get();
    }

    public StringProperty toiduNimetusProperty() {
        return toiduNimetus;
    }
}
