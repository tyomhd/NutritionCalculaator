import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Food {

    private final StringProperty id;
    private final StringProperty time;
    private final StringProperty toiduNimetus;
    private final StringProperty portion;
    private final StringProperty energy;


    public Food(String toiduNimetus) {
        this.toiduNimetus = new SimpleStringProperty(toiduNimetus);
        id = null;
        time = null;
        portion = null;
        energy = null;
    }

    public Food(String id, String time, String toiduNimetus, String portion, String energy) {
        this.id = new SimpleStringProperty(id);
        this.time = new SimpleStringProperty(time);
        this.toiduNimetus = new SimpleStringProperty(toiduNimetus);
        this.portion = new SimpleStringProperty(portion);
        this.energy = new SimpleStringProperty(energy);
    }

    public String getToiduNimetus() {
        return toiduNimetus.get();
    }
    public StringProperty toiduNimetusProperty() {
        return toiduNimetus;
    }

    public String getId() {
        if (id != null) {
            return id.get();
        }return null;
    }
    public StringProperty idProperty() {return id;}

    public String getTime() {
        if (time != null) {
            return time.get();
        }return null;
    }
    public StringProperty timeProperty() {return time;}


    public String getPortion() {
        if (portion != null) {
            return portion.get();
        }return null;
    }
    public StringProperty portionProperty() {return portion;}

    public String getEnergy() {
        if (energy != null) {
            return energy.get();
        }return null;
    }
    public StringProperty energyProperty() {return energy;}
}
