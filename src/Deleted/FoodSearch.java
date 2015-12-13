/*
package Deleted;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class FoodSearch {

    @FXML
    private TextField filterField;
    @FXML
    private TableView<Food> foodTable;
    @FXML
    private TableColumn<Food, String> toiduNimetusColumn;
    @FXML
    private TableColumn<Food, Rectangle> infoColumn;


    private ObservableList<Food> masterData = FXCollections.observableArrayList();


    public FoodSearch() {

        Andmebaas a = new Andmebaas();
        for (int i = 1; i < 2767; i++) {
            String food =  a.vaataDbString(i,"ToidunimiEST");
            masterData.add(new Food(food));
        }
        a.sulgeYhendus();


    }

    @FXML
    private void initialize() {



        toiduNimetusColumn.setCellValueFactory(cellData -> cellData.getValue().toiduNimetusProperty());
        //infoColumn.setCellValueFactory(cellData -> cellData.getValue().infoProperty());

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
                return false; // Does not match.
            });
        });

        SortedList<Food> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(foodTable.comparatorProperty());

        foodTable.setItems(sortedData);
    }




}
*/