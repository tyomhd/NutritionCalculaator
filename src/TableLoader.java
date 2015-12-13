import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.lang.reflect.Array;
import java.util.List;


public class TableLoader {

    private static TextField filterField = new TextField();
    private static TableView<Food> foodTable = new TableView<>();
    private static TableColumn<Food, String> toiduNimetusColumn = new TableColumn<>();
    private static ObservableList<Food> masterData = FXCollections.observableArrayList();


    public TableLoader() {

        setupTable();
        getSelected();
    }

    public static void setupTable() {


//----------------PUTS FOOD NAME FROM DATABSE TO TABLE----------------
        Andmebaas a = new Andmebaas();
        for (int i = 1; i < 2767; i++) {
            String foodName =  a.vaataDbString(i,"ToidunimiEST");
            masterData.add(new Food(foodName));
        }
        a.sulgeYhendus();


//------------------SEARCH : SORTING AND FILTERING -------------------


        FilteredList<Food> filteredData = new FilteredList<>(masterData, p -> true);
         filterField.textProperty().addListener((observable, oldValue, newValue) -> {
          filteredData.setPredicate(food -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (food.getToiduNimetus().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });


        SortedList<Food> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(foodTable.comparatorProperty());
        foodTable.setItems(sortedData);

        toiduNimetusColumn.setCellValueFactory(cellData -> cellData.getValue().toiduNimetusProperty());
        toiduNimetusColumn.setMinWidth(500);
        foodTable.getColumns().add(toiduNimetusColumn);

//--------------------------------------------------------------------

        /*
        foodTable.getSelectionModel().selectedItemProperty().addListener( new ChangeListener() {

            public void changed(ObservableValue ov, Object t, Object t1) {

                TableView.TableViewSelectionModel<Food> selectionModel = foodTable.getSelectionModel();
                ObservableList<TablePosition> selectedCells = selectionModel.getSelectedCells();
                TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                int rowIndex = tablePosition.getRow(); // yields the row that the currently selected cell is in

                // I Want to get the cell's text in the row using the row_index or the selected row one

                List<Food> selected = selectionModel.getSelectedItems();

                foodTable.setOnMouseClicked(event -> {
                    System.out.println(selected.toString());
                });
            }
        });
*/
        /*
        foodTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Food>() {
            @Override
            public void changed(ObservableValue<? extends Food> observable, Food oldValue, Food newValue) {
                TableView.TableViewSelectionModel<Food> selectionModel = foodTable.getSelectionModel();
                List<Food> selected = selectionModel.getSelectedItems();

                foodTable.setOnMouseClicked(event -> {
                    System.out.println(selected.toString());
                });
            }
        });
*/


        foodTable.setOnMouseClicked(event -> {


            TablePosition pos = foodTable.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            Food item = foodTable.getItems().get(row);
            TableColumn col = pos.getTableColumn();
            String data = (String) col.getCellObservableValue(item).getValue();
            System.out.println("Valitud : " + data);


            String[] elements = new String[]{
                    "ID", "Toidukood" ,"ToidunimiEST","ToidunimiENG","ToidunimiLAT","Sunonuumid,Toidugrupp","Allikas","EnergiashkiudainedkJ",
                    "Energiashkiudainedkcal","Susivesikudimenduvadg","Rasvadg","Valgudg","Alkoholg","Vesig","Tuhkg","Susivesikudkokkug","Kiudainedg","Tarklisg",
                    "Sahharoosg","Laktoosg","Maltoosg","Glukoosg","Fruktoosg","Galaktoosg","Rasvhappedkokkug","Kullastunudrasvhappedg","Monokullastumatarasvhappedg",
                    "Polukullastumatarasvhappedg","Transrasvhappedg","PalmitiinhapeC16g","SteariinhapeC18g","LinoolhapeC182g","LinoleenhapeC183g","Kolesteroolmg",
                    "Naatriummg", "Kaaliummg","Kaltsiummg","Magneesiummg","Fosformg","Raudmg","Tsinkmg","Vaskmg","Mangaanmg","Joodug","Seleenug","Kroomug","Nikkelug",
                    "VitamiinARE", "Retinoolug", "BeetakaroteeniekvivalentBCE", "VitamiinDug", "VitamiinD3ug", "VitamiinEaTE", "VitamiinKug", "VitamiinB1mg", "VitamiinB2mg",
                    "NiatsiiniekvivalentkokkuNE", "Niatsiinmg", "Niatsiiniekvivtruptofaanistmg", "Pantoteenhapemg", "VitamiinB6mg", "Biotiinug", "Folaadidug",
                    "VitamiinB12ug", "VitamiinCmg", "Soolaekvivalentmg"
            };

            a.looYhendus();

            double energia1 = a.vaataDbDouble(data, elements[7]);
            double energia2 = a.vaataDbDouble(data, elements[8]);
            double energi3 = a.vaataDbDouble(data, elements[7]);




                //    str = str.substring(0, str.length() - 2);




            double kcal =  a.vaataDbDouble(data,"Energiashkiudainedkcal");
            System.out.println(kcal + " kcal in 100g");
            a.sulgeYhendus();

        });







    }

    public static TextField getFilterField(){

        return filterField;
    }
    public static TableView<Food>  getFoodTable(){

        return foodTable;
    }



    private void getSelected() {





    }


}