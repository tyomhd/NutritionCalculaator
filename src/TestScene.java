import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



public class TestScene {
    //-----
    public  static String date;
    public static String kasutajanimi;
    //-----

    static Text headerDate;
    static LocalDate headerDay;
    static DateTimeFormatter formatter;

    public  static String outData;
    public  static double outDataWeight;
    public  static String outDataTime;


    static TableView userplan;
    static VBox firstWindowVBox;

    private Stage stage = new Stage();
    HBox hbox1;
    HBox hbox2;
    static VBox vboxMain;
    VBox vbox2;
    StackPane root = new StackPane();

    static StackPane addDialog = new StackPane();
    static StackPane addDialogStackPane = new StackPane();
    static VBox addDialogBox = new VBox();
    public static HBox mainwin = new HBox();

    private static TranslateTransition translateTransition;

//---------------------------MAIN CONSTRUCTOR-------------------------------
    public TestScene(String kasutajaSisse) {
        kasutajanimi = kasutajaSisse;
        setupStage();
    }

    private void setupStage(){

//-----------------------------STAGE MARKUP--------------------------------

        vboxMain = new VBox();
        hbox1 = new HBox();
        hbox2 = new HBox();
        vbox2 = new VBox();


//-----------------------------ADD DIALOG-----------------------------------

        addDialogBox.setMaxSize(220,140);
        Rectangle addQuestion = new Rectangle(304,154, Color.FORESTGREEN);
        Rectangle addQuestion2 = new Rectangle(300,150, Color.WHITE);
        Rectangle addBack = new Rectangle(600,600, Color.WHITE);
        addBack.setOpacity(0);
        addDialogStackPane.setAlignment(addDialogBox, Pos.CENTER);
        addDialogStackPane.getChildren().add(addDialogBox);


        addDialog.setMaxSize(600,150);
        addDialog.setAlignment(addQuestion, Pos.CENTER);
        addDialog.setAlignment(addDialogBox, Pos.CENTER);
        addDialog.getChildren().addAll(addBack, addQuestion, addQuestion2, addDialogBox);
        addDialog.setVisible(false);


//--------------------------TOP MENU BUTTON-------------------------------------
        StackPane menuButton = new StackPane();
        menuButton.setPrefSize(50,50);
        Rectangle r1 = new Rectangle(50, 50, Color.hsb(150.0, 1.0, 0.5));
        Rectangle mbr1 = new Rectangle(40, 6, Color.WHITE);
        Rectangle mbr2 = new Rectangle(40, 6, Color.WHITE);
        Rectangle mbr3 = new Rectangle(40, 6, Color.WHITE);
        menuButton.getChildren().addAll(r1,mbr1,mbr2,mbr3);
        menuButton.setAlignment(r1,Pos.CENTER);
        menuButton.setAlignment(mbr1,Pos.TOP_CENTER);
        mbr1.setTranslateY(mbr1.getY()+ 8);
        menuButton.setAlignment(mbr2,Pos.CENTER);
        menuButton.setAlignment(mbr3,Pos.BOTTOM_CENTER);
        mbr3.setTranslateY(mbr1.getY()- 8);

//----------------------------HEADER------------------------------------

        Rectangle rct11 = new Rectangle(85, 50, Color.hsb(150.0, 1.0, 0.5));
        Rectangle rct22 = new Rectangle(230, 50, Color.hsb(150.0, 1.0, 0.5));
        Rectangle rct33 = new Rectangle(135, 50, Color.hsb(150.0, 1.0, 0.5));
        StackPane header1 = new StackPane();
        header1.setMaxSize(230, 50);

                            //Date
        headerDay = LocalDate.now();
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        headerDate = new Text(headerDay.format(formatter));
        dateCheckIfToday();
        header1.setOnMouseEntered(event -> {headerDate.setFill(Color.BLACK);});
        header1.setOnMouseExited(event -> {headerDate.setFill(Color.WHITE);});
        header1.setOnMousePressed(event -> {rct22.setFill(Color.hsb(170.0, 1.0, 0.5));});
        header1.setOnMouseReleased(event -> {rct22.setFill(Color.hsb(150.0, 1.0, 0.5));});
        DatePicker datePicker = new DatePicker();
        datePicker.setMaxWidth(0);
        datePicker.setOpacity(0.0);
        header1.setOnMouseClicked(e -> {datePicker.show();});
        datePicker.setOnAction(e ->{
                    if(datePicker.getValue().equals(LocalDate.now())) {
                        headerDate.setText("Today");
                    }
                    else {
                        headerDate.setText(datePicker.getValue().format(formatter));
                    }
        });
        headerDate.setFill(Color.WHITE);
        headerDate.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 18));

                            //Arrow buttons
        StackPane hbutton1 = new StackPane();
        hbutton1.setPrefSize(50,50);
        Rectangle hbrct1 = new Rectangle(50,50,Color.hsb(150.0, 1.0, 0.5));
        Polyline polyline1 = new Polyline(new double[]{25, 35, 15, 25, 25, 15,});
        polyline1.setFill(Color.TRANSPARENT);
        polyline1.setStroke(Color.WHITE);
        polyline1.setStrokeWidth(2.5);
        hbutton1.setAlignment(polyline1,Pos.CENTER);
        hbutton1.getChildren().addAll(hbrct1, polyline1);
        StackPane hbutton2 = new StackPane();
        hbutton2.setPrefSize(50,50);
        Rectangle hbrct2 = new Rectangle(50,50,Color.hsb(150.0, 1.0, 0.5));
        Polyline polyline2 = new Polyline(new double[]{25, 35, 15, 25, 25, 15,});
        polyline2.setFill(Color.TRANSPARENT);
        polyline2.setStroke(Color.WHITE);
        polyline2.setStrokeWidth(2.5);
        polyline2.setRotate(180);
        hbutton2.setAlignment(polyline2,Pos.CENTER);
        hbutton2.getChildren().addAll(hbrct2, polyline2);
        hbutton1.setOnMouseEntered(e ->{polyline1.setStroke(Color.BLACK);});
        hbutton1.setOnMouseExited(e ->{polyline1.setStroke(Color.WHITE);});
        hbutton1.setOnMousePressed(e ->{hbrct1.setFill(Color.hsb(170.0, 1.0, 0.5));});
        hbutton1.setOnMouseReleased(e ->{
            hbrct1.setFill(Color.hsb(150.0, 1.0, 0.5));
            headerDay = headerDay.minusDays(1);
            headerDate.setText(headerDay.format(formatter));
            dateCheckIfToday();
        });
        hbutton2.setOnMouseEntered(e ->{polyline2.setStroke(Color.BLACK);});
        hbutton2.setOnMouseExited(e ->{polyline2.setStroke(Color.WHITE);});
        hbutton2.setOnMousePressed(e ->{hbrct2.setFill(Color.hsb(170.0, 1.0, 0.5));});
        hbutton2.setOnMouseReleased(e ->{
            hbrct2.setFill(Color.hsb(150.0, 1.0, 0.5));
            headerDay = headerDay.plusDays(1);
            headerDate.setText(headerDay.format(formatter));
            dateCheckIfToday();
        });


        header1.setAlignment(datePicker, Pos.CENTER_LEFT);
        header1.getChildren().addAll(datePicker, rct22, headerDate);
        HBox header = new HBox();
        header.getChildren().addAll(rct11, hbutton1, header1, hbutton2, rct33);

//----------------------------MENU BUTTONS----------------------------------------

        MenuLine ml1 = new MenuLine("Today List");
        ml1.setOnMouseClicked(event1 -> {translateTransition(Duration.seconds(0.2), mainwin, (int) mainwin.getTranslateX(), -600);});
        MenuLine ml2 = new MenuLine("My Plan");
        ml2.setOnMouseClicked(event1 -> {translateTransition(Duration.seconds(0.2), mainwin, (int) mainwin.getTranslateX(), -1200);});
        MenuLine ml3 = new MenuLine("Statistics");
        ml3.setOnMouseClicked(event1 -> {translateTransition(Duration.seconds(0.2), mainwin, (int) mainwin.getTranslateX(), -1800);});


//-----------------------------SEARCH----------------------------------------------
        hbox2.getChildren().addAll(ml1, ml2, ml3);
        hbox1.getChildren().addAll(menuButton, header);
        TableLoader.setupTable();
        TextField tF = TableLoader.getFilterField();
        tF.setMaxWidth(590);
        TableView<Food> tV = TableLoader.getFoodTable();
        tV.setMaxWidth(590);
        tV.setMinHeight(450);
        vbox2.setMinWidth(600);
        vbox2.getChildren().addAll(tF, tV);

// ----------------------------1ST WINDOW--------------------------------------------
        StackPane firstWindow = new StackPane();
        firstWindow.setMaxSize(600,500);
        firstWindowVBox = new VBox();
        firstWindowVBox.setMaxSize(600,500);

        Rectangle rct1 = new Rectangle(600, 50, Color.YELLOW);
        //Rectangle rct2 = new Rectangle(600, 450, Color.GREENYELLOW);
        userplan = TableLoader.setupUserPlanTable(kasutajanimi, getCurrentDate());

        StackPane addToListButton = addToListButton();

        firstWindowVBox.getChildren().addAll(rct1, userplan);
        firstWindow.setAlignment(firstWindowVBox, Pos.CENTER);
        firstWindow.setAlignment(addToListButton, Pos.BOTTOM_RIGHT);
        firstWindow.getChildren().addAll(firstWindowVBox, addToListButton);

//------------------------------------------------------------------


        //Rectangle mr2 = new Rectangle(600, 500, Color.RED);
        //mr2.setOnMouseClicked(e ->{mainwin.setTranslateX(0);});
        Rectangle mr3 = new Rectangle(600, 500, Color.GREEN);

        mainwin.setMaxWidth(1800);
        mainwin.setTranslateX(-600);
        mainwin.getChildren().addAll(vbox2, firstWindow, mr3);//----------------------------------------------------------------------------
        vboxMain.getChildren().addAll(hbox2, hbox1, mainwin );


        root.setAlignment(vboxMain, Pos.CENTER_LEFT);
        root.setAlignment(addDialog,Pos.CENTER_LEFT);
        root.getChildren().addAll(vboxMain, addDialog);


        Scene scene = new Scene(root);
        stage.setMaxHeight(600);
        stage.setMaxWidth(600);
        stage.setResizable(false);
        stage.setScene(scene);
        try {
            InputStream iconImage = Files.newInputStream(Paths.get("res/icon.png"));
            Image icon = new Image(iconImage);
            stage.getIcons().add(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Food Calculator");
        stage.show();
        stage.setOnCloseRequest(event -> System.exit(1));//<-------------------------------------------------------------------------------------
    }

//----------------------------MENULINE---------------------------------------------
    private static class MenuLine extends StackPane {
        public MenuLine(String name){

            Rectangle bg = new Rectangle(200, 40);
            bg.setFill(Color.hsb(150.0, 1.0, 0.5));

            Text text = new Text(name);
            text.setFill(Color.WHITE);
            text.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 18));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);

            setOnMouseEntered(event -> {text.setFill(Color.BLACK);});
            setOnMouseExited(event -> {text.setFill(Color.WHITE);});
            setOnMousePressed(event -> {bg.setFill(Color.hsb(170.0, 1.0, 0.5));});
            setOnMouseReleased(event -> {bg.setFill(Color.hsb(150.0, 1.0, 0.5));});
        }
    }
//------------------------------ADD TO LIST BUTTON---------------------------------
    private StackPane addToListButton(){
        Rectangle rct = new Rectangle(40, 40, Color.ORANGE);
        rct.setArcWidth(15);
        rct.setArcHeight(15);
        Rectangle rct2 = new Rectangle(2.5, 20, Color.WHITE);
        Rectangle rct3 = new Rectangle(20, 2.5, Color.WHITE);
        StackPane addToListButton = new StackPane();
        addToListButton.setMinSize(80, 80);
        addToListButton.setMaxSize(80, 80);
        addToListButton.getChildren().addAll(rct, rct2, rct3);
        addToListButton.setOnMouseEntered(e ->{
            rct2.setFill(Color.BLACK);
            rct3.setFill(Color.BLACK);
        });
        addToListButton.setOnMouseExited(e ->{
            rct2.setFill(Color.WHITE);
            rct3.setFill(Color.WHITE);
        });
        addToListButton.setOnMousePressed(e ->{
            rct.setFill(Color.ORANGERED);
        });
        addToListButton.setOnMouseReleased(e ->{
            rct.setFill(Color.ORANGE);
            mainwin.setTranslateX(0);
        });
        return addToListButton;
    }
//------------------------------TRANSLATE TRANSITION----------------------------------
    private static TranslateTransition translateTransition(Duration duration, Node node, Integer from, Integer to){
        translateTransition = new TranslateTransition(duration, node);
        translateTransition.setFromX(from);
        translateTransition.setToX(to);
        translateTransition.setCycleCount(1);
        translateTransition.play();
        translateTransition.setOnFinished(e -> mainwin.setTranslateX(to));
        return translateTransition;

    }
//----------------------------ADD WINDOW------------------------------------------------
    public static void showAddDialog(String toit){
        Label addLabel1 = new Label("On valitud : " + toit);
        Label addLabel2 = new Label("Kas soovid lisada oma toitumisplaani?");
        Label blankLabel = new Label("");

       // HBox kuupaevBox = new HBox();
       // Label kLabel = new Label("Kuupaev : ");
       // Label kuupaevField = new Label(getCurrentDate());
       // kuupaevBox.getChildren().addAll(kLabel, kuupaevField);

        HBox kogusBox = new HBox();
        Label addLabel3 = new Label("Kogus : ");
        TextField kogusField = new TextField();
        Label addLabel4 = new Label(" g");
        kogusBox.getChildren().addAll(addLabel3, kogusField, addLabel4);
        HBox aegBox = new HBox();
        Label addLabel5 = new Label("Aeg :     ");
        ChoiceBox aegChoiceBox = new ChoiceBox(FXCollections.observableArrayList("Hommikusöök", "Lõuna", "Õhtusöök"));
        aegBox.getChildren().addAll(addLabel5, aegChoiceBox);
        HBox buttonBox = new HBox();
        Label blankLabel2 = new Label("    ");
        Button okButton = new Button("OK");
        Button cancelButton = new Button("Cancel");
        buttonBox.getChildren().addAll(blankLabel2, okButton, cancelButton);
        okButton.setOnAction(e1 -> {

            date = getCurrentDate();
            outData = TableLoader.outData;
            outDataWeight = Double.parseDouble(kogusField.getText());
            outDataTime = String.valueOf(aegChoiceBox.getValue());

            DataBase a = new DataBase();
            a.addToUsersPlan(kasutajanimi, date, outData, outDataTime,outDataWeight );
            a.closeConnection();

           //userplan.getColumns().get(0).
            //userplan.getColumns().get(0).setVisible(true);
            //TableLoader.userPlanTable.;
            System.out.println("------------");
            refreshUserTable();

            addDialog.setVisible(false);
            vboxMain.setEffect(null);
            addDialogBox.getChildren().removeAll(addLabel1,addLabel2, blankLabel, kogusBox, aegBox, buttonBox);

            translateTransition(Duration.seconds(0.2), mainwin, (int) mainwin.getTranslateX(), -600);
            System.out.println("Added to database : " + date + ", "+ outData + ", " + outDataWeight + " g, " + outDataTime);
        });
        cancelButton.setOnAction( e2 -> {
            addDialog.setVisible(false);
            vboxMain.setEffect(null);
            addDialogBox.getChildren().removeAll(addLabel1,addLabel2, blankLabel, kogusBox, aegBox, buttonBox);
        });

        addDialogBox.getChildren().addAll(addLabel1,addLabel2, kogusBox, aegBox, buttonBox);
        addDialog.setVisible(true);
    }


    public static void showRemoveDialog(String paev, String toit, String kaal){
        Label addLabel1 = new Label("On valitud : " + toit + ", " + kaal);
        Label addLabel2 = new Label("Kas soovid kustutada oma toitumisplaanist?");
        Label blankLabel = new Label("");

        HBox buttonBox = new HBox();
        Label blankLabel2 = new Label("    ");
        Button okButton = new Button("OK");
        Button cancelButton = new Button("Cancel");
        buttonBox.getChildren().addAll(blankLabel2, okButton, cancelButton);
        okButton.setOnAction(e1 -> {


            DataBase a = new DataBase();
           // a.addToUsersPlan(kasutajanimi, date, outData, outDataTime,outDataWeight );
            a.closeConnection();


            addDialog.setVisible(false);
            vboxMain.setEffect(null);
            addDialogBox.getChildren().removeAll(addLabel1,addLabel2, blankLabel, buttonBox);


            //translateTransition(Duration.seconds(0.2), mainwin, (int) mainwin.getTranslateX(), -600);
            System.out.println("Removed from plan: " + paev + ", "+ toit + ", " + kaal);
        });
        cancelButton.setOnAction( e2 -> {
            addDialog.setVisible(false);
            vboxMain.setEffect(null);
            addDialogBox.getChildren().removeAll(addLabel1,addLabel2, blankLabel, buttonBox);
        });

        addDialogBox.getChildren().addAll(addLabel1,addLabel2, buttonBox);
        addDialog.setVisible(true);
    }


    public void dateCheckIfToday(){
        if(headerDay.equals(LocalDate.now())){
        headerDate.setText("Today");
    }
    }
//-----------------------------------------------------------------------------------------------------------------


    public static String getCurrentDate(){return headerDay.format(formatter).toString();}
    public static String getCurrentUser(){return kasutajanimi;}



//-----------------------------------REFRESH TABLE METHOD-------------------------------
    public static void refreshUserTable(){
        firstWindowVBox.getChildren().remove(userplan);
        userplan = TableLoader.setupUserPlanTable(kasutajanimi, getCurrentDate());
        firstWindowVBox.getChildren().add(userplan);
        userplan.getColumns().clear();
        userplan.getColumns().addAll(TableLoader.dotColumn, TableLoader.foodNameColumn,
                TableLoader.portionColumn, TableLoader.energyColumn, TableLoader.removeColumn);
    }
//--------------------------------------------------------------------------------------

}//END OF CLASS
