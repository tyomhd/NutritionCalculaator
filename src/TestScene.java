import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.util.HashMap;



public class TestScene {
    public static StackPane root = new StackPane();
    public static VBox vboxMain;
    public  static String outData;
    public  static double outDataWeight;
    public  static String outDataTime;
    public  static String date;

    static Text headerDate;
    static LocalDate headerDay;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");;
    static StackPane progressBar;
    private static String kasutajanimi;
    private static double PA;

//--------------------------------------------------
    private static TranslateTransition translateTransition;
    private StackPane menuButton;
    private HBox header;
    private static StackPane firstWindow;
    private static VBox elementsProgressPane;
    private static TilePane planDataPane;
    private static StackPane sideMenu;
    private static HBox underlineHBox;
    private static TableView userplan;
    private static VBox firstWindowVBox;
    private Stage stage = new Stage();
    private HBox hbox1;
    private HBox hbox2;
    private static VBox vbox2;
    private static StackPane dialog;
    private static StackPane addDialogStackPane;
    private static VBox activityBox;
    private static HBox mainwin = new HBox();


//---------------------------MAIN CONSTRUCTOR-------------------------------
    public TestScene(String kasutajaSisse) {
        kasutajanimi = kasutajaSisse;
        setupDialogBox();
        setupTopMenuButton();
        setupHeader();
        setupStage();
        new Getters(kasutajanimi);

    }
//-----------------------------SETUP STAGE----------------------------------
    private void setupStage(){

//-----------------------------STAGE MARKUP

        vboxMain = new VBox();
        hbox1 = new HBox();
        hbox2 = new HBox();
        vbox2 = new VBox();

//----------------------------MENU BUTTONS

        MenuLine ml1 = new MenuLine("Today List");
        ml1.setOnMouseClicked(event1 -> {translateTransition(Duration.seconds(0.2), mainwin, (int) mainwin.getTranslateX(), -600);});
        MenuLine ml2 = new MenuLine("My Plan");
        ml2.setOnMouseClicked(event1 -> {translateTransition(Duration.seconds(0.2), mainwin, (int) mainwin.getTranslateX(), -1200);});
        MenuLine ml3 = new MenuLine("Elements");
        ml3.setOnMouseClicked(event1 -> {translateTransition(Duration.seconds(0.2), mainwin, (int) mainwin.getTranslateX(), -1800);});


//--------------------------------------SEARCH
        hbox2.getChildren().addAll(menuButton, ml1, ml2, ml3);
        hbox1.getChildren().addAll(header);
        new TableLoader();
        TextField tF = TableLoader.getFilterField();
        tF.setMaxWidth(590);
        TableView<Food> tV = TableLoader.getFoodTable();
        tV.setMaxWidth(590);
        tV.setMinHeight(450);
        vbox2.setMinWidth(600);
        vbox2.getChildren().addAll(tF, tV);


// ----------------------------------1ST WINDOW--------------------------------------------
        firstWindow = new StackPane();
        firstWindow.setMaxSize(600,500);
        firstWindowVBox = new VBox();
        firstWindowVBox.setMaxSize(600,500);


        userplan = TableLoader.setupUserPlanTable(kasutajanimi, getCurrentDate());
        userplan.setMaxWidth(590);
        userplan.setMinHeight(450);




        progressBar = setupProgressBar(Getters.getTergetKcal(), Getters.getCurrentKcal(), Getters.getPAString());

        StackPane addToListButton = addToListButton();
        StackPane activityButton = activityButton();
        HBox bottomButtons = new HBox();
        bottomButtons.setMaxSize(110, 80);
        bottomButtons.getChildren().addAll(activityButton, addToListButton);


        firstWindowVBox.getChildren().addAll(progressBar, userplan);
        firstWindow.setAlignment(firstWindowVBox, Pos.CENTER);
        firstWindow.setAlignment(bottomButtons, Pos.BOTTOM_RIGHT);
        firstWindow.getChildren().addAll(firstWindowVBox, bottomButtons);

//------------------------------------------------------------------
        elementsProgressPane = setupElementsProgressPane(getCurrentUser(), getCurrentDate(), Getters.getUserSex(),Getters.getUserAge());

//-------------------
        DataBase a = new DataBase();
        Double strv1 = a.getSumFromUserDB(kasutajanimi, Getters.getCurrentDate(), "Susivesikudimenduvadg");
        Double strv2 = a.getSumFromUserDB(kasutajanimi, Getters.getCurrentDate(), "Valgudg");
        Double strv3 = a.getSumFromUserDB(kasutajanimi, Getters.getCurrentDate(), "Rasvadg");
        a.closeConnection();
        planDataPane = setupPlanPane(50,25,25,strv1,strv2,strv3);
//---------------------
        sideMenu = setupSideMenuPane();
        sideMenu.setTranslateX(-600);
//------------
        mainwin.setMaxWidth(1800);
        mainwin.setTranslateX(-600);
        mainwin.getChildren().addAll(vbox2, firstWindow, planDataPane, elementsProgressPane);
        vboxMain.getChildren().addAll(hbox2, hbox1, mainwin );


        root.setAlignment(vboxMain, Pos.CENTER_LEFT);
        root.setAlignment(dialog,Pos.CENTER_LEFT);
        root.setAlignment(sideMenu,Pos.CENTER_LEFT);
        root.getChildren().addAll(vboxMain,sideMenu,dialog);


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
        stage.setTitle("Nutrition Calculator");
        stage.show();
        stage.setOnCloseRequest(event -> System.exit(1));
    }



//-----------------------------------------------------------------------------------------------------------------






//******************************SETUP USER INTERFACE****************************

//---------------------------------- TOP MENU BUTTON-----------------------------
    private void setupTopMenuButton(){
        menuButton = new StackPane();
        menuButton.setPrefSize(50,40);
        Rectangle r1 = new Rectangle(50, 40, Color.hsb(150.0, 1.0, 0.5));
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

        menuButton.setOnMouseReleased(e->{
            translateTransition(Duration.seconds(0.2), sideMenu, (int) sideMenu.getTranslateX(), 0);
            vboxMain.setEffect(new GaussianBlur());
        });
    }
//-----------------------------------SIDE MENU PANE------------------------------
    private StackPane setupSideMenuPane(){
        StackPane sideMenu = new StackPane();
        VBox smvBox = new VBox();
        Rectangle rct1 = new Rectangle(250, 50, Color.GRAY);

        SideMenuLine line1 = new SideMenuLine("Change user data");
        SideMenuLine line2 = new SideMenuLine("Info");
        Rectangle filler1 = new Rectangle(250,450,Color.WHITE);
        Pane filler2 = new Pane();
        filler2.setMinSize(350,600);
        filler2.setMaxSize(350,600);
        filler2.setOnMouseReleased(e->{
            translateTransition(Duration.seconds(0.2), sideMenu, (int) sideMenu.getTranslateX(), -600);
            vboxMain.setEffect(null);
        });

        rct1.setOnMouseReleased(e->{
            translateTransition(Duration.seconds(0.2), sideMenu, (int) sideMenu.getTranslateX(), -600);
            vboxMain.setEffect(null);
        });
        line1.setOnMouseClicked(e->{
            RegisterScreen regScr= new RegisterScreen("Update Your Data", 500, 550);
            root.setEffect(new GaussianBlur());
        });
        smvBox.getChildren().addAll(rct1,line1,line2,filler1);

        sideMenu.setAlignment(smvBox,Pos.CENTER_LEFT);
        sideMenu.setAlignment(filler2,Pos.CENTER_RIGHT);
        sideMenu.setAlignment(Pos.TOP_CENTER);
        sideMenu.setMinSize(600, 600);
        sideMenu.setMaxSize(600, 600);
        sideMenu.getChildren().addAll(smvBox,filler2);
        return sideMenu;
    }
//--------------------------------------HEADER-----------------------------------
    private void setupHeader(){
        header = new HBox();
        Rectangle rct11 = new Rectangle(135, 50, Color.hsb(150.0, 1.0, 0.5));
        Rectangle rct22 = new Rectangle(230, 50, Color.hsb(150.0, 1.0, 0.5));
        Rectangle rct33 = new Rectangle(135, 50, Color.hsb(150.0, 1.0, 0.5));
        StackPane header1 = new StackPane();
        header1.setMaxSize(230, 50);

        //Date
        headerDay = LocalDate.now();
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
                headerDay = datePicker.getValue();
                headerDate.setText("Today");
            }
            else {
                headerDay = datePicker.getValue();
                headerDate.setText(headerDay.format(formatter));
            }
            refreshProgram();

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
            refreshProgram();
        });
        hbutton2.setOnMouseEntered(e ->{polyline2.setStroke(Color.BLACK);});
        hbutton2.setOnMouseExited(e ->{polyline2.setStroke(Color.WHITE);});
        hbutton2.setOnMousePressed(e ->{hbrct2.setFill(Color.hsb(170.0, 1.0, 0.5));});
        hbutton2.setOnMouseReleased(e ->{
            hbrct2.setFill(Color.hsb(150.0, 1.0, 0.5));
            headerDay = headerDay.plusDays(1);
            headerDate.setText(headerDay.format(formatter));
            dateCheckIfToday();
            refreshProgram();
        });


        header1.setAlignment(datePicker, Pos.CENTER_LEFT);
        header1.getChildren().addAll(datePicker, rct22, headerDate);
        header.getChildren().addAll(rct11, hbutton1, header1, hbutton2, rct33);
    }
//-------------------------------------PROGRESS BAR------------------------------
    private static StackPane setupProgressBar(Double target, Double current, String pa){

        double percent = current/target;

        Rectangle rct = new Rectangle(600, 50, Color.hsb(150.0, 1.0, 0.5));

        Rectangle prct1 = new Rectangle(550, 10, Color.DARKSEAGREEN);
        Rectangle prct2 = new Rectangle(1, 10, Color.WHITE);
        prct1.setArcHeight(10);
        prct1.setArcWidth(10);
        prct2.setArcHeight(10);
        prct2.setArcWidth(10);

        Text text1 = new Text("");
        Text kcalText = new Text("");
        Text text2 = new Text("Eaten : ");
        Text text3 = new Text(String.format("%.0f", current) + " kcal                ");
        Text text4 = new Text("Target : ");
        Text text5 = new Text(String.format("%.0f", target) + " kcal                ");
        Text text6 = new Text("Activity : ");
        Text text7 = new Text(pa);
        text1.setFill(Color.WHITE);
        text1.setFont(Font.font("TW Cen MT Condensed", FontWeight.SEMI_BOLD, 18));
        kcalText.setFill(Color.WHITE);
        kcalText.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 18));
        text2.setFill(Color.WHITE);
        text2.setFont(Font.font("TW Cen MT Condensed", FontWeight.SEMI_BOLD, 18));
        text3.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 18));
        text4.setFill(Color.WHITE);
        text4.setFont(Font.font("TW Cen MT Condensed", FontWeight.SEMI_BOLD, 18));
        text5.setFill(Color.WHITE);
        text5.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 18));
        text6.setFill(Color.WHITE);
        text6.setFont(Font.font("TW Cen MT Condensed", FontWeight.SEMI_BOLD, 18));
        text7.setFill(Color.WHITE);
        text7.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 18));

        if(current < target){
            text1.setText("You can still eat: ");
            kcalText.setText(String.format("%.0f", (target - current))+" kcal");
            text3.setFill(Color.WHITE);
            prct2.setWidth(550 *percent);
            if(percent > 0.9){
                prct2.setFill(Color.YELLOW);
                text3.setFill(Color.YELLOW);
            }
        }
        if(current == target){
            text1 = new Text("Congratulation! Target is reached");
            text3.setFill(Color.YELLOW);
            prct2.setWidth(550);
            prct2.setFill(Color.YELLOW);
        }
        if(current > target){
            text1 = new Text("Limit is reached. It's enough for today");
            prct2.setWidth(550);
            if(percent <= 1.1){
                text3.setFill(Color.YELLOW);
                prct2.setFill(Color.YELLOW);
            }else {
                text3.setFill(Color.RED);
                prct2.setFill(Color.RED);
            }
        }

        StackPane stack1 = new StackPane();
        VBox vbox = new VBox();
        HBox hBox1 = new HBox();
        StackPane stack2 = new StackPane();
        underlineHBox = new HBox();
        hBox1.setMaxSize(550,25);;
        stack2.setMaxSize(550,10);
        underlineHBox.setMaxSize(450,15);
        vbox.setMaxSize(550,50);
        stack1.setMaxSize(600,50);
        hBox1.getChildren().addAll(text1, kcalText, text2);
        stack2.setAlignment(prct1, Pos.CENTER);
        stack2.setAlignment(prct2, Pos.CENTER_LEFT);
        stack2.getChildren().addAll(prct1, prct2);
        underlineHBox.getChildren().addAll(text2,text3,text4,text5, text6, text7);
        underlineHBox.setSpacing(20);
        vbox.getChildren().addAll(hBox1, stack2, underlineHBox);

        stack1.setAlignment(rct, Pos.CENTER);
        stack1.setAlignment(vbox, Pos.CENTER);
        stack1.getChildren().addAll(rct, vbox);
        return stack1;
    }



    //-------------------------------ACTIVITY BUTTON---------------------------------
    private StackPane activityButton(){
        Rectangle rct = new Rectangle(40, 40, Color.ORANGE);
        rct.setArcWidth(15);
        rct.setArcHeight(15);

        Rectangle rct1 = new Rectangle(3,5,Color.WHITE);
        Rectangle rct2 = new Rectangle(3,10,Color.WHITE);
        Rectangle rct3 = new Rectangle(3,15,Color.WHITE);
        Rectangle rct4 = new Rectangle(10,5,Color.WHITE);
        Rectangle rct5 = new Rectangle(3,15,Color.WHITE);
        Rectangle rct6 = new Rectangle(3,10,Color.WHITE);
        Rectangle rct7 = new Rectangle(3,5,Color.WHITE);
        HBox gantl = new HBox();
        gantl.getChildren().addAll(rct1,rct2,rct3,rct4,rct5,rct6,rct7);
        gantl.setAlignment(Pos.CENTER);


        StackPane activityButton = new StackPane();
        activityButton.setMinSize(40, 80);
        activityButton.setMaxSize(40, 80);
        activityButton.getChildren().addAll(rct, gantl);
        activityButton.setOnMouseEntered(e ->{
            rct1.setFill(Color.BLACK);
            rct2.setFill(Color.BLACK);
            rct3.setFill(Color.BLACK);
            rct4.setFill(Color.BLACK);
            rct5.setFill(Color.BLACK);
            rct6.setFill(Color.BLACK);
            rct7.setFill(Color.BLACK);
        });
        activityButton.setOnMouseExited(e ->{
            rct1.setFill(Color.WHITE);
            rct2.setFill(Color.WHITE);
            rct3.setFill(Color.WHITE);
            rct4.setFill(Color.WHITE);
            rct5.setFill(Color.WHITE);
            rct6.setFill(Color.WHITE);
            rct7.setFill(Color.WHITE);
        });
        activityButton.setOnMousePressed(e ->{
            rct.setFill(Color.ORANGERED);
        });
        activityButton.setOnMouseReleased(e ->{
            rct.setFill(Color.ORANGE);
            vboxMain.setEffect(new GaussianBlur());
            setupActivityDialog();
        });

        return activityButton;

    }
    //------------------------------ADD TO LIST BUTTON---------------------------------
    private StackPane addToListButton(){
        Rectangle rct = new Rectangle(40, 40, Color.ORANGE);
        rct.setArcWidth(15);
        rct.setArcHeight(15);
        Rectangle rct2 = new Rectangle(2.5, 20, Color.WHITE);
        Rectangle rct3 = new Rectangle(20, 2.5, Color.WHITE);
        StackPane addToListButton = new StackPane();
        addToListButton.setMinSize(70, 80);
        addToListButton.setMaxSize(70, 80);
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




//--------------------------------------2ND WINDOW---------------------------------
    private static TilePane setupPlanPane(int tcarbs, int tprots, int tfats, double ccarbs, double cprots, double cfats){
        TilePane tp = new TilePane();
        Label lbl1 = new Label("Target");
        Label lbl2 = new Label("Current");
        lbl1.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 21));
        lbl2.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 21));
        lbl1.setTextFill(Color.hsb(0,0,0.3));
        lbl2.setTextFill(Color.hsb(0,0,0.3));

        DataPieChart pie1 = new DataPieChart("Carbs ("+tcarbs+"%)", tcarbs, "Proteins ("+tprots+"%)", tprots, "Fats ("+tfats+"%)", tfats);
        DataPieChart pie2 = new DataPieChart("Carbs ("+String.format("%.0f", ccarbs)+"%)", ccarbs, "Proteins ("+String.format("%.0f", cprots)+"%)", cprots, "Fats ("+String.format("%.0f", cfats)+"%)", cfats);

        ImageView bottleIV = null;
        ImageView saltIV = null;
        try {
            InputStream bottleImage = Files.newInputStream(Paths.get("res/water.png"));
            InputStream saltImage = Files.newInputStream(Paths.get("res/salt.png"));
            bottleIV = new ImageView(new Image(bottleImage));
            saltIV = new ImageView(new Image(saltImage));
            saltIV.setFitWidth(100);
            saltIV.setFitHeight(100);
            bottleIV.setFitWidth(100);
            bottleIV.setFitHeight(100);

        }catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while loading images");
        }

        VBox pieVB1 = new VBox();
        VBox pieVB2 = new VBox();
        //if(tcarbs!=0||tprots)
        Text c1 = null;
        Text p1 = null;
        Text f1 = null;
        Text c2 = null;
        Text p2 = null;
        Text f2 = null;

        if(tcarbs+tprots+tfats != 0 && ccarbs+cprots+cfats != 0 ) {
            double cc = (tcarbs * 100) / (tcarbs + tprots + tfats);
            double pp = (tprots * 100) / (tcarbs + tprots + tfats);
            double ff = (tfats * 100) / (tcarbs + tprots + tfats);
            double c = (ccarbs * 100) / (ccarbs + cprots + cfats);
            double p = (cprots * 100) / (ccarbs + cprots + cfats);
            double f = (cfats * 100) / (ccarbs + cprots + cfats);
            c1 = new Text("Carbs (" + String.format("%.0f", cc) + "%)");
            p1 = new Text("Proteins (" + String.format("%.0f", pp) + "%)");
            f1 = new Text("Fats (" + String.format("%.0f", ff) + "%)");
            c2 = new Text("Carbs (" + String.format("%.0f", c) + "%)");
            p2 = new Text("Proteins (" + String.format("%.0f", p) + "%)");
            f2 = new Text("Fats (" + String.format("%.0f", f) + "%)");
            c1.setFill(Color.GREY);
            c1.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 18));
            p1.setFill(Color.GREY);
            p1.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 18));
            f1.setFill(Color.GREY);
            f1.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 18));
            c2.setFill(Color.GREY);
            c2.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 18));
            p2.setFill(Color.GREY);
            p2.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 18));
            f2.setFill(Color.GREY);
            f2.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 18));
            pieVB1.setAlignment(Pos.CENTER_RIGHT);
            pieVB2.setAlignment(Pos.CENTER_RIGHT);
            pieVB1.getChildren().addAll(c1,p1,f1);
            pieVB2.getChildren().addAll(c2,p2,f2);
        }

        DataBase a = new DataBase();
        Double strv1 = a.getSumFromUserDB(kasutajanimi, Getters.getCurrentDate(), "Vesig");
        Double strv2 = a.getSumFromUserDB(kasutajanimi, Getters.getCurrentDate(), "Soolaekvivalentmg");
        a.closeConnection();

        Text watValue = new Text(String.format("%.0f",strv1)+" ml");
        watValue.setFill(Color.GREY);
        watValue.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 28));
        Text salValue = new Text(String.format("%.0f", strv2)+" mg");
        salValue.setFill(Color.GREY);
        salValue.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 28));

        VBox tile1 = setMyPlanCellLayout("Target", pie1, pieVB1);
        VBox tile2 = setMyPlanCellLayout("Current", pie2, pieVB2);
        VBox tile3 = setMyPlanCellLayout("Water", bottleIV, watValue);
        VBox tile4 = setMyPlanCellLayout("Salt", saltIV, salValue);

        tp.getChildren().addAll(tile1,tile2,tile3,tile4);
        tp.setAlignment(Pos.CENTER);
        tp.setHgap(15);
        tp.setVgap(55);
        tp.setMinSize(600,500);
        tp.setMaxSize(600,500);
        return tp;
    }
//-------------------------------------------------------------------------------
    private static VBox setMyPlanCellLayout(String label, Node img, Node node){
        HBox mainhb = new HBox();
        VBox mplvbox = new VBox();
        mplvbox.setPrefSize(250,170);
        StackPane imgSP = new StackPane();
        imgSP.setAlignment(Pos.CENTER);
        imgSP.getChildren().add(img);
        imgSP.setMinSize(150,150);
        imgSP.setMaxSize(150,150);
        StackPane header = new StackPane();
        Label head = new Label(label);
        head.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 22));
        header.setPrefSize(250,20);
        header.setAlignment(Pos.CENTER);
        header.getChildren().add(head);
        mplvbox.setAlignment(Pos.CENTER);
        StackPane stackk = new StackPane();
        stackk.setPrefSize(100,150);
        stackk.setAlignment(Pos.CENTER);
        stackk.getChildren().addAll(node);
        mainhb.setAlignment(Pos.CENTER);
        mainhb.setMinSize(250,150);
        mainhb.setMaxSize(250,150);
        mainhb.getChildren().addAll(imgSP, stackk);
        mplvbox.getChildren().addAll(header,mainhb);
        mplvbox.setMinSize(250,170);
        mplvbox.setMaxSize(250,170);
        return mplvbox;
    }
//-------------------------------------3RD WINDOW---------------------------------
    private static VBox setupElementsProgressPane(String username, String date, String gender, int age){

        //ScrollPane scrollPane = new ScrollPane();
        VBox elemPane = new VBox();
        StackPane headerPane= new StackPane();
        VBox elVBox = new VBox();

        StackPane header1 = new StackPane();
        StackPane header2 = new StackPane();
        Rectangle headrct1 = new Rectangle(600, 20, Color.GRAY);
        Rectangle headrct11 = new Rectangle(600, 10, Color.WHITE);
        Rectangle headrct2 = new Rectangle(600, 20, Color.GRAY);
        Rectangle headrct22 = new Rectangle(600, 10, Color.TRANSPARENT);
        Rectangle headrct33 = new Rectangle(600, 10, Color.TRANSPARENT);
        Text hText1 = new Text("       Micro Elements");
        Text hText2 = new Text("       Vitamins");
        hText1.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 18));
        hText2.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 18));
        hText1.setFill(Color.WHITE);
        hText2.setFill(Color.WHITE);
        header1.setAlignment(Pos.TOP_LEFT);
        header2.setAlignment(Pos.CENTER);
        header1.setAlignment(headrct11,Pos.BOTTOM_CENTER);
        header2.setAlignment(headrct22,Pos.BOTTOM_CENTER);
        header2.setAlignment(headrct33,Pos.TOP_CENTER);
        header2.setAlignment(hText2, Pos.CENTER_LEFT);
        header1.setMinSize(600,30);
        header2.setMinSize(600,40);
        header1.getChildren().addAll(headrct1, headrct11, hText1);
        header2.getChildren().addAll(headrct33, headrct2, headrct22, hText2);

        headerPane.setMaxSize(600, 30);
        elVBox.setMinSize(600, 470);
        elVBox.setMaxSize(600, 470);

        Rectangle hrct = new Rectangle(600, 30, Color.hsb(150.0, 1.0, 0.5));
        Text headerText = new Text("Elements Progress");
        headerText.setFill(Color.WHITE);
        headerText.setFont(Font.font("TW Cen MT Condensed", FontWeight.BOLD, 18));


        HashMap<String, Double> elementsHMap = Getters.getUserElementsSum(username, date);
        HashMap<String, Double> DRVHashMap = Getters.getUserDRV(Getters.getUserSex(), Getters.getUserAge());


        ElementProgressBar element1 = new ElementProgressBar("Naatrium", "mg", elementsHMap.get("Naatriummg"), DRVHashMap.get("Naatriummg"));
        ElementProgressBar element2 = new ElementProgressBar("Kaalium", "mg", elementsHMap.get("Kaaliummg"), DRVHashMap.get("Kaaliummg"));
        ElementProgressBar element3 = new ElementProgressBar("Kaltsium", "mg", elementsHMap.get("Kaltsiummg"), DRVHashMap.get("Kaltsiummg"));
        ElementProgressBar element4 = new ElementProgressBar("Magneesium", "mg", elementsHMap.get("Magneesiummg"), DRVHashMap.get("Magneesiummg"));
        ElementProgressBar element5 = new ElementProgressBar("Fosfor", "mg", elementsHMap.get("Fosformg"), DRVHashMap.get("Fosformg"));
        ElementProgressBar element6 = new ElementProgressBar("Raud", "mg", elementsHMap.get("Raudmg"), DRVHashMap.get("Raudmg"));
        ElementProgressBar element7 = new ElementProgressBar("Tsink", "mg", elementsHMap.get("Tsinkmg"), DRVHashMap.get("Tsinkmg"));
        ElementProgressBar element8 = new ElementProgressBar("Vask", "mg", elementsHMap.get("Vaskmg"), DRVHashMap.get("Vaskmg"));
        ElementProgressBar element9 = new ElementProgressBar("Mangaan", "mg", elementsHMap.get("Mangaanmg"), DRVHashMap.get("Mangaanmg"));
        ElementProgressBar element10 = new ElementProgressBar("Jood", "µg", elementsHMap.get("Joodug"), DRVHashMap.get("Joodug"));
        ElementProgressBar element11 = new ElementProgressBar("Seleen", "µg", elementsHMap.get("Seleenug"), DRVHashMap.get("Seleenug"));
        ElementProgressBar element12 = new ElementProgressBar("Kroom", "µg", elementsHMap.get("Kroomug"), DRVHashMap.get("Kroomug"));

        ElementProgressBar element13 = new ElementProgressBar("Vitamiin A", "a-RE", elementsHMap.get("VitamiinARE"), DRVHashMap.get("VitamiinARE"));
        ElementProgressBar element14 = new ElementProgressBar("Vitamiin B1", "mg", elementsHMap.get("VitamiinB1mg"), DRVHashMap.get("VitamiinB1mg"));
        ElementProgressBar element15 = new ElementProgressBar("Vitamiin B2", "mg", elementsHMap.get("VitamiinB2mg"), DRVHashMap.get("VitamiinB2mg"));
        ElementProgressBar element16 = new ElementProgressBar("Vitamiin B6", "mg", elementsHMap.get("VitamiinB6mg"), DRVHashMap.get("VitamiinB6mg"));
        ElementProgressBar element17 = new ElementProgressBar("Vitamiin B12", "µg", elementsHMap.get("VitamiinB12ug"), DRVHashMap.get("VitamiinB12ug"));
        ElementProgressBar element18 = new ElementProgressBar("Vitamiin C", "mg", elementsHMap.get("VitamiinCmg"), DRVHashMap.get("VitamiinCmg"));
        ElementProgressBar element19 = new ElementProgressBar("Vitamiin D", "µg", elementsHMap.get("VitamiinDug"), DRVHashMap.get("VitamiinDug"));
        ElementProgressBar element20 = new ElementProgressBar("Vitamiin E", "a-TE", elementsHMap.get("VitamiinEaTE"), DRVHashMap.get("VitamiinEaTE"));
        ElementProgressBar element22 = new ElementProgressBar("Vitamiin K", "µg", elementsHMap.get("VitamiinKug"), DRVHashMap.get("VitamiinKug"));
        ElementProgressBar element23 = new ElementProgressBar("Niatsiin", "mg", elementsHMap.get("Niatsiinmg"), DRVHashMap.get("Niatsiinmg"));
        ElementProgressBar element24 = new ElementProgressBar("Pantoteen hape", "mg", elementsHMap.get("Pantoteenhapemg"), DRVHashMap.get("Pantoteenhapemg"));
        ElementProgressBar element25 = new ElementProgressBar("Biotiin", "µg", elementsHMap.get("Biotiinug"), DRVHashMap.get("Biotiinug"));
        ElementProgressBar element26 = new ElementProgressBar("Folaadid", "µg", elementsHMap.get("Folaadidug"), DRVHashMap.get("Folaadidug"));



        elVBox.getChildren().addAll(header1, element1, element2, element3, element4, element5, element6, element7,
                element8, element9, element10, element11, element12, header2, element13, element14, element15, element16, element17, element18, element19, element20,
                element22, element23, element24, element25, element26);


        headerPane.setAlignment(headerText, Pos.CENTER);
        headerPane.getChildren().addAll(hrct, headerText);


        elemPane.setPrefSize(600, 500);
        elemPane.setMaxSize(600, 500);
        elemPane.getChildren().addAll(headerPane, elVBox);

        return elemPane;
    }




//------------------------------TRANSLATE TRANSITION----------------------------------
    private static TranslateTransition translateTransition(Duration duration, Node node, Integer from, Integer to){
        translateTransition = new TranslateTransition(duration, node);
        translateTransition.setFromX(from);
        translateTransition.setToX(to);
        translateTransition.setCycleCount(1);
        translateTransition.play();
        translateTransition.setOnFinished(e -> node.setTranslateX(to));
        return translateTransition;
    }



//------------------------------POP UP DIALOG BOXES------------------------------------------
    public void setupDialogBox(){
        dialog = new StackPane();
        addDialogStackPane = new StackPane();
        activityBox = new VBox();
        activityBox.setMaxSize(220,140);
        Rectangle addQuestion = new Rectangle(404,354, Color.FORESTGREEN);
        Rectangle addQuestion2 = new Rectangle(400,350, Color.WHITE);
        Rectangle addBack = new Rectangle(600,600, Color.WHITE);
        addBack.setOpacity(0);
        addDialogStackPane.setAlignment(activityBox, Pos.CENTER);
        addDialogStackPane.getChildren().add(activityBox);
        dialog.setMaxSize(600,600);
        dialog.setAlignment(addQuestion, Pos.CENTER);
        dialog.setAlignment(activityBox, Pos.CENTER);
        dialog.getChildren().addAll(addBack, addQuestion, addQuestion2, activityBox);
        dialog.setVisible(false);

    }
//----------------------------------ACTIVITY DIALOG--------------------------------------
    public static void setupActivityDialog(){

        Label addLabel2 = new Label("What's your activity today?");
        Label blankLabel = new Label("");

        VBox vbox = new VBox();

        ToggleGroup activityGroup = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Sedentary");
        RadioButton rb2 = new RadioButton("Low Active");
        RadioButton rb3 = new RadioButton("Active");
        RadioButton rb4 = new RadioButton("Very Active");
        rb1.setToggleGroup(activityGroup);
        rb2.setToggleGroup(activityGroup);
        rb3.setToggleGroup(activityGroup);
        rb4.setToggleGroup(activityGroup);

        String usex = Getters.getUserSex();

        if(usex.equals("Man")){
            rb1.setUserData(1);
            rb2.setUserData(1.11);
            rb3.setUserData(1.25);
            rb4.setUserData(1.48);
        }else{
            rb1.setUserData(1);
            rb2.setUserData(1.12);
            rb3.setUserData(1.27);
            rb4.setUserData(1.45);
        }
        rb1.setSelected(true);

        Text rbt1= new Text("Typical daily living activities \n (e.g., household tasks, walking to the bus)");
        Text rbt2= new Text("Typical daily living activities \n PLUS 30 - 60 minutes of daily moderate activity (ex. walking at 5-7 km/h)");
        Text rbt3= new Text("Typical daily living activities \n PLUS At least 60 minutes of daily moderate activity");
        Text rbt4= new Text("Typical daily living activities \n PLUS At least 60 minutes of daily moderate activity PLUS An additional 60 \n minutes of vigorous activity or 120 minutes of moderate activity");
        vbox.getChildren().addAll(rb1,rbt1,rb2,rbt2,rb3,rbt3,rb4,rbt4);



        HBox buttonBox = new HBox();
        Button okButton = new Button("OK");
        Button cancelButton = new Button("Cancel");
        buttonBox.getChildren().addAll(okButton, cancelButton);
        okButton.setOnAction(e1 -> {
            PA = Double.parseDouble(activityGroup.getSelectedToggle().getUserData().toString());
            DataBase a = new DataBase();
            a.setUserActivity(kasutajanimi, getCurrentDate(), PA);
            a.closeConnection();

            refreshProgram();


            dialog.setVisible(false);
            vboxMain.setEffect(null);
            activityBox.getChildren().removeAll(addLabel2, blankLabel, vbox, buttonBox);


            //translateTransition(Duration.seconds(0.2), mainwin, (int) mainwin.getTranslateX(), -600);
            //System.out.println("Removed from plan: " + paev + ", "+ toit + ", " + kaal);
        });
        cancelButton.setOnAction( e2 -> {
            dialog.setVisible(false);
            vboxMain.setEffect(null);
            activityBox.getChildren().removeAll(addLabel2, blankLabel, vbox, buttonBox);
        });

        activityBox.getChildren().addAll(addLabel2,blankLabel,vbox, buttonBox);
        dialog.setVisible(true);
    }
//----------------------------ADD WINDOW------------------------------------------------
    public static void showAddDialog(String toit){
        Label addLabel1 = new Label("On valitud : " + toit);
        Label addLabel2 = new Label("Kas soovid lisada oma toitumisplaani?");
        Label blankLabel = new Label("");


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
            refreshProgram();

            dialog.setVisible(false);
            vboxMain.setEffect(null);
            activityBox.getChildren().removeAll(addLabel1,addLabel2, blankLabel, kogusBox, aegBox, buttonBox);

            translateTransition(Duration.seconds(0.2), mainwin, (int) mainwin.getTranslateX(), -600);
            System.out.println("Added to database : " + date + ", "+ outData + ", " + outDataWeight + " g, " + outDataTime);
        });
        cancelButton.setOnAction( e2 -> {
            dialog.setVisible(false);
            vboxMain.setEffect(null);
            activityBox.getChildren().removeAll(addLabel1,addLabel2, blankLabel, kogusBox, aegBox, buttonBox);
        });

        activityBox.getChildren().addAll(addLabel1,addLabel2, kogusBox, aegBox, buttonBox);
        dialog.setVisible(true);
    }
//---------------------------------REMOVE BUTTON--------------------------------------------
    public static void showRemoveDialog(String removeId){
        // Label addLabel1 = new Label("On valitud : " + toit + ", " + kaal);
        Label addLabel2 = new Label("Kas soovid kustutada ?");
        Label blankLabel = new Label("");

        HBox buttonBox = new HBox();
        Label blankLabel2 = new Label("    ");
        Button okButton = new Button("OK");
        Button cancelButton = new Button("Cancel");
        buttonBox.getChildren().addAll(blankLabel2, okButton, cancelButton);
        okButton.setOnAction(e1 -> {

            DataBase a = new DataBase();
            a.removeFromUsersPlan(kasutajanimi, removeId);
            a.closeConnection();
            refreshProgram();

            dialog.setVisible(false);
            vboxMain.setEffect(null);
            activityBox.getChildren().removeAll(addLabel2, blankLabel, buttonBox);

            //System.out.println("Removed from plan: " + paev + ", "+ toit + ", " + kaal);
        });
        cancelButton.setOnAction( e2 -> {
            dialog.setVisible(false);
            vboxMain.setEffect(null);
            activityBox.getChildren().removeAll(addLabel2, blankLabel, buttonBox);
        });

        activityBox.getChildren().addAll(addLabel2, buttonBox);
        dialog.setVisible(true);
    }






//*********************************UGLY REFRESH METHOD************************************

    public static void refreshProgram(){
        firstWindowVBox.getChildren().remove(userplan);
        userplan = TableLoader.setupUserPlanTable(kasutajanimi, getCurrentDate());
        firstWindowVBox.getChildren().add(userplan);
        userplan.getColumns().clear();
        userplan.getColumns().addAll(TableLoader.idColumn, TableLoader.aegColumn, TableLoader.foodNameColumn, TableLoader.portionColumn, TableLoader.energyColumn, TableLoader.removeColumn);
        firstWindowVBox.getChildren().removeAll(progressBar, userplan);
        progressBar = setupProgressBar(Getters.getTergetKcal(), Getters.getCurrentKcal(), Getters.getPAString());
        firstWindowVBox.getChildren().addAll(progressBar,userplan);
        Double maiwinXPos = mainwin.getTranslateX();

        mainwin.getChildren().removeAll(vbox2, firstWindow, planDataPane, elementsProgressPane);
        elementsProgressPane = setupElementsProgressPane(getCurrentUser(), getCurrentDate(), Getters.getUserSex(),Getters.getUserAge());
        DataBase a = new DataBase();
        Double strv1 = a.getSumFromUserDB(kasutajanimi, Getters.getCurrentDate(), "Susivesikudimenduvadg");
        Double strv2 = a.getSumFromUserDB(kasutajanimi, Getters.getCurrentDate(), "Valgudg");
        Double strv3 = a.getSumFromUserDB(kasutajanimi, Getters.getCurrentDate(), "Rasvadg");
        a.closeConnection();
        planDataPane = setupPlanPane(50,25,25,strv1,strv2,strv3);
        mainwin.getChildren().addAll(vbox2, firstWindow, planDataPane, elementsProgressPane);
        mainwin.setTranslateX(maiwinXPos);
    }







//**************************************GETTERS***************************************
    public static String getCurrentDate(){return headerDay.format(formatter).toString();}
    public static String getCurrentUser(){return kasutajanimi;}
    public static double getPA(){return PA;}


//-----------------------------------DATE CHECK--------------------------------------
    public void dateCheckIfToday(){
        if(headerDay.equals(LocalDate.now())){
            headerDate.setText("Today");
        }
    }




//***********************************INNER CLASSES************************************


//-----------------------------------SIDE MENULINE------------------------------------
    private class SideMenuLine extends StackPane{
        public SideMenuLine(String text) {
            Text menuLineText = new Text(text);
            menuLineText.setFill(Color.BLACK);
            menuLineText.setFont(Font.font("TW Cen MT Condensed", FontWeight.NORMAL, 18));
            Rectangle rct1 = new Rectangle(250, 48, Color.WHITE);
            Rectangle rct2 = new Rectangle(250, 2, Color.DIMGREY);
            setMaxSize(250, 50);
            setAlignment(rct1, Pos.TOP_CENTER);
            setAlignment(rct2, Pos.BOTTOM_CENTER);
            setAlignment(menuLineText, Pos.CENTER);
            getChildren().addAll(rct1,rct2,menuLineText);

            setOnMouseEntered(e->{menuLineText.setFill(Color.GREY);});
            setOnMouseExited(e->{menuLineText.setFill(Color.BLACK);});
            setOnMousePressed(e->{rct1.setFill(Color.DIMGREY);});
            setOnMouseReleased(e->{rct1.setFill(Color.WHITE);});
        }
    }
//-------------------------------------MENULINE----------------------------------------
    private class MenuLine extends StackPane {
        public MenuLine(String name){

            Rectangle bg = new Rectangle(184, 40);
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




}//END OF CLASS
