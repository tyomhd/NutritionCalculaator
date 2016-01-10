import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import java.util.List;


public class TableLoader {


    public static String outData;

    public static TextField filterField = new TextField();
    public static TableView<Food> foodTable = new TableView<>();
    public static TableColumn<Food, String> toiduNimetusColumn = new TableColumn<>("Otsing");
    public static ObservableList<Food> masterData = FXCollections.observableArrayList();
    public static ObservableList<Food> userPlan;
    public static TableView<Food> userPlanTable;
    public static TableColumn<Food, String> idColumn;
    public static TableColumn<Food, String> aegColumn;
    public static TableColumn<Food, String> foodNameColumn;
    public static TableColumn<Food, String> portionColumn;
    public static TableColumn<Food, String> energyColumn;
    public static TableColumn<Food, Boolean> removeColumn;



//---------------------------------MAIN CONSTRUCTOR-----------------------------

    public TableLoader() {
        setupTable();
    }


//---------------------------------SETUP TABLE----------------------------------
    public static void setupTable() {

                    //PUTS FOOD NAME FROM DATABSE TO TABLE
        DataBase a = new DataBase();
        for (int i = 1; i < 2767; i++) {

            String foodName =  a.getStringFromDB(i,"ToidunimiEST");
            masterData.add(new Food(foodName));
        }
        a.closeConnection();

                    //SEARCH : SORTING AND FILTERING

//-------------------------------------------------------------------------------------------------Peeked this at the StackOverFlow
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
//---------------------------------------------------------------------------------------
        SortedList<Food> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(foodTable.comparatorProperty());

                             //FOOD COLUMN CREATION

        toiduNimetusColumn.setCellValueFactory(cellData -> cellData.getValue().toiduNimetusProperty());
        toiduNimetusColumn.setMinWidth(500);


                            //PLUS ADD BUTTON

        TableColumn<Food, Boolean> actionCol = new TableColumn<>("Lisa");
        actionCol.setSortable(false);
        actionCol.setMinWidth(30);

        actionCol.setCellFactory(new Callback<TableColumn<Food, Boolean>, TableCell<Food, Boolean>>() {
            @Override public TableCell<Food, Boolean> call(TableColumn<Food, Boolean> actionCol) {
                return new AddCell(foodTable);
            }
        });

        //-------------
        foodTable.setItems(sortedData);
        foodTable.getColumns().addAll(toiduNimetusColumn, actionCol);
        foodTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


    }

//--------------------------------TABLE ADD CELL-------------------------------------
    private static class AddCell extends TableCell<Food, Boolean> {
    StackPane stack = new StackPane();


        AddCell(final TableView table) {

            Rectangle r1 = new Rectangle(15,5, Color.GRAY);
            Rectangle r2 = new Rectangle(5,15, Color.GRAY);
            stack.setPadding(new Insets(3));
            stack.getChildren().addAll(r1,r2);
            stack.setAlignment(r1, Pos.CENTER);
            stack.setAlignment(r2, Pos.CENTER);
            Rectangle backrt = new Rectangle(25,30);
            backrt.setFill(Color.ORANGE);

            stack.setOnMouseEntered(e ->{
                r1.setFill(Color.FORESTGREEN);
                r2.setFill(Color.FORESTGREEN);
            });
            stack.setOnMouseExited(e ->{
                r1.setFill(Color.GRAY);
                r2.setFill(Color.GRAY);
            });
            stack.setOnMouseExited(e ->{
                r1.setFill(Color.GRAY);
                r2.setFill(Color.GRAY);
            });
            stack.setOnMousePressed(e ->{
                r1.setFill(Color.ORANGE);
                r2.setFill(Color.ORANGE);
            });
            stack.setOnMouseReleased(e ->{
                r1.setFill(Color.FORESTGREEN);
                r2.setFill(Color.FORESTGREEN);
                addToUserTable();
            });
        }
        @Override protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(stack);
            } else {
                setGraphic(null);
            }
        }
}

//-----------------------------------SETUP USER PLAN TABLE ------------------------------------
    public static TableView setupUserPlanTable(String user, String date){

        userPlan= FXCollections.observableArrayList();
        userPlanTable = new TableView<>(userPlan);

        DataBase a = new DataBase();
        List<String> userDayPlan = a.getUserPlan(TestScene.getCurrentUser(), TestScene.getCurrentDate());
        for (int i = 0; i < (userDayPlan != null ? userDayPlan.size() : 0); i++) {
            userPlan.add(new Food(userDayPlan.get(i), userDayPlan.get(i+1), userDayPlan.get(i+2), userDayPlan.get(i+3), userDayPlan.get(i+4)));
            i=i+4;
        }
        a.closeConnection();


        idColumn = new TableColumn<>("id");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        aegColumn = new TableColumn<>("Time");
        aegColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        foodNameColumn = new TableColumn<>("Food");
        foodNameColumn.setCellValueFactory(cellData -> cellData.getValue().toiduNimetusProperty());
        portionColumn = new TableColumn<>("Portion");
        portionColumn.setCellValueFactory(cellData -> cellData.getValue().portionProperty());
        energyColumn = new TableColumn<>("Energy");
        energyColumn.setCellValueFactory(cellData -> cellData.getValue().energyProperty());
        removeColumn = new TableColumn<>("");
        removeColumn.setSortable(false);


        removeColumn.setCellFactory(new Callback<TableColumn<Food, Boolean>, TableCell<Food, Boolean>>() {
            @Override public TableCell<Food, Boolean> call(TableColumn<Food, Boolean> actionCol) {
                return new RemoveCell(foodTable);
            }
        });

        idColumn.setMaxWidth(0);
        idColumn.setVisible(false);
        aegColumn.setMaxWidth(100);
        foodNameColumn.setMaxWidth(300);
        portionColumn.setMaxWidth(80);
        energyColumn.setMaxWidth(80);
        removeColumn.setMaxWidth(30);


        userPlanTable.getColumns().setAll(idColumn, aegColumn, foodNameColumn, portionColumn, energyColumn, removeColumn);
        userPlanTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return userPlanTable;
    }

//------------------------------------REMOVE CELL-----------------------------------
    private static class RemoveCell extends TableCell<Food, Boolean> {
        StackPane stack = new StackPane();

        RemoveCell(final TableView table) {

            Rectangle r1 = new Rectangle(15, 5, Color.GRAY);
            Rectangle r2 = new Rectangle(5, 15, Color.GRAY);
            r1.setRotate(45);
            r2.setRotate(45);
            stack.setPadding(new Insets(3));
            stack.getChildren().addAll(r1, r2);
            stack.setAlignment(r1, Pos.CENTER);
            stack.setAlignment(r2, Pos.CENTER);
            Rectangle backrt = new Rectangle(25, 30);
            backrt.setFill(Color.ORANGE);

            stack.setOnMouseEntered(e -> {
                r1.setFill(Color.RED);
                r2.setFill(Color.RED);
            });
            stack.setOnMouseExited(e -> {
                r1.setFill(Color.GRAY);
                r2.setFill(Color.GRAY);
            });
            stack.setOnMouseExited(e -> {
                r1.setFill(Color.GRAY);
                r2.setFill(Color.GRAY);
            });
            stack.setOnMousePressed(e -> {
                r1.setFill(Color.ORANGE);
                r2.setFill(Color.ORANGE);
            });
            stack.setOnMouseReleased(e -> {
                r1.setFill(Color.RED);
                r2.setFill(Color.RED);
                removeFromUserTable();
            });
        }

        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(stack);
            } else {
                setGraphic(null);
            }
        }
    }

//-----------------------------DELETE FROM USER TABLE-------------------------
    public static void removeFromUserTable(){
        TestScene.vboxMain.setEffect(new GaussianBlur());
        TablePosition pos2 = userPlanTable.getSelectionModel().getSelectedCells().get(0);
        int row = pos2.getRow();
        Food item = userPlanTable.getItems().get(row);
        String removeID = item.getId();
        System.out.println("ID : " + removeID);
        TestScene.showRemoveDialog(removeID);
    }
    public static void addToUserTable(){
        TestScene.vboxMain.setEffect(new GaussianBlur());
        TablePosition pos2 = foodTable.getSelectionModel().getSelectedCells().get(0);
        int row2 = pos2.getRow();
        Food item2 = foodTable.getItems().get(row2);
        outData = (String) toiduNimetusColumn.getCellObservableValue(item2).getValue();
        TestScene.showAddDialog(outData);
    }

//--------------------------------GETTERS-------------------------------------

    public static TextField getFilterField(){return filterField;}
    public static TableView<Food>  getFoodTable(){return foodTable;}







}//CLASS END

