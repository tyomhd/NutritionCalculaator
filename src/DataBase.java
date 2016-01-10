import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBase {
    static Connection conn = null;

//------------------------------------MAIN CONSTRUCTOR----------------------------------------

    public DataBase() {
        connectToDataBase();
        createLoginTable();
    }

//-------------------------------------MAIN METHODS--------------------------------------------

    public static void connectToDataBase() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:FOODDB.sqlite");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
      System.out.println("Database connection opened");
    }

    public static void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
       System.out.println("Database connection closed");

    }

    private void updateDataBase(String sql) {
        try {
            Statement stat = conn.createStatement();
            stat.executeUpdate(sql);
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registerUser(String username, String password, String fullname, String gender, int age, double weight, double height) {
        String sql = "INSERT INTO USERS (USERNAME, PASSWORD, FULLNAME, GENDER, AGE, WEIGHT, HEIGHT, PLAN) " +
                "VALUES ('"+username+"','"+password+"','"+fullname+"','"+gender+"','"+age  +"','"+weight+"','"+height+"', 'EMPTY')";

        String sql2 = "CREATE TABLE IF NOT EXISTS '"+ username +"' (Id INTEGER PRIMARY KEY  NOT NULL , Date TEXT, FoodTime TEXT, ToidunimiEST TEXT, " +
                "Weight INTEGER, ToidunimiLAT TEXT, Sunonuumid TEXT, Toidugrupp TEXT, Allikas TEXT, EnergiashkiudainedkJ DOUBLE, " +
                "Energiashkiudainedkcal DOUBLE, Susivesikudimenduvadg DOUBLE, Rasvadg DOUBLE, Valgudg DOUBLE, Alkoholg DOUBLE, " +
                "Vesig DOUBLE, Tuhkg DOUBLE, Susivesikudkokkug DOUBLE, Kiudainedg DOUBLE, Tarklisg DOUBLE, Sahharoosg DOUBLE, " +
                "Laktoosg DOUBLE, Maltoosg DOUBLE, Glukoosg DOUBLE, Fruktoosg DOUBLE, Galaktoosg DOUBLE, Rasvhappedkokkug DOUBLE, " +
                "Kullastunudrasvhappedg DOUBLE, Monokullastumatarasvhappedg DOUBLE, Polukullastumatarasvhappedg DOUBLE, " +
                "Transrasvhappedg DOUBLE, PalmitiinhapeC16g DOUBLE, SteariinhapeC18g DOUBLE, LinoolhapeC182g DOUBLE, " +
                "LinoleenhapeC183g DOUBLE, Kolesteroolmg DOUBLE, Naatriummg DOUBLE, Kaaliummg DOUBLE, Kaltsiummg DOUBLE, " +
                "Magneesiummg DOUBLE, Fosformg DOUBLE, Raudmg DOUBLE, Tsinkmg DOUBLE, Vaskmg DOUBLE, Mangaanmg DOUBLE, "+
                "Joodug DOUBLE, Seleenug DOUBLE, Kroomug DOUBLE, Nikkelug DOUBLE, VitamiinARE DOUBLE, Retinoolug DOUBLE, "+
                "BeetakaroteeniekvivalentBCE DOUBLE, VitamiinDug DOUBLE, VitamiinD3ug DOUBLE, VitamiinEaTE DOUBLE, "+
                "VitamiinKug DOUBLE, VitamiinB1mg DOUBLE, VitamiinB2mg DOUBLE, NiatsiiniekvivalentkokkuNE DOUBLE, "+
                "Niatsiinmg DOUBLE, Niatsiiniekvivtruptofaanistmg DOUBLE, Pantoteenhapemg DOUBLE, VitamiinB6mg DOUBLE, " +
                "Biotiinug DOUBLE, Folaadidug DOUBLE, VitamiinB12ug DOUBLE, VitamiinCmg DOUBLE, Soolaekvivalentmg DOUBLE);";

        String sql3 = "CREATE TABLE IF NOT EXISTS userPhysicalActivity (ID INTEGER PRIMARY KEY NOT NULL, USERNAME TEXT, DATE TEXT, PA DOUBLE);";

        updateDataBase(sql);
        updateDataBase(sql2);
        updateDataBase(sql3);
    }

    public boolean login(String username, String password) {
        try {
            System.out.println(TestScene.getCurrentUser());
            System.out.println("Login/"+username+"/"+password+"");
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM USERS WHERE USERNAME = '" + username + "' LIMIT 1;";
            ResultSet rs = stat.executeQuery(sql);
            String dbPassword = rs.getString("password");
            rs.close();
            stat.close();
            return password.equals(dbPassword);
        } catch (SQLException e) {
            new AlertBox("login");
            System.out.println("Login Error");
            e.printStackTrace();

        }
        return false;
    }
//------------------------------------------------TABLE SETUPS------------------------------------------------------
    public void createLoginTable() {
        String sql = "CREATE TABLE IF NOT EXISTS USERS (ID INTEGER PRIMARY KEY  AUTOINCREMENT, USERNAME TEXT, " +
                "PASSWORD TEXT, FULLNAME TEXT, GENDER INTEGER, AGE INTEGER, WEIGHT DOUBLE, HEIGHT DOUBLE, PLAN TEXT);";
        updateDataBase(sql);
    }

//-----------------------------------------------DATABASE GETTERS---------------------------------------------------

    public double getDoubleFromDB(String name, String what) {
        try {
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM DB WHERE ToidunimiEST = '" + name + "' LIMIT 1;";
            ResultSet rs = stat.executeQuery(sql);
            Double result = rs.getDouble(what);
            rs.close();
            stat.close();
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return 0.0;
    }
    public String getStringFromDB(int fID, String what) {
        try {
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM DB WHERE ID = '" + fID + "' LIMIT 1;";
            ResultSet rs = stat.executeQuery(sql);
            String result = rs.getString(what);
            rs.close();
            stat.close();
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
    public String getStringFromDB(String name, String what) {
        try {
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM DB WHERE ToidunimiEST = '" + name + "' LIMIT 1;";
            ResultSet rs = stat.executeQuery(sql);
            String result = rs.getString(what);
            rs.close();
            stat.close();
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
//-----------------------------SET USER DAY ACTIVITY--------------------------

    public void setUserActivity(String username, String date, double PA) {

        String sql1 = "DELETE FROM userPhysicalActivity WHERE USERNAME = '" + username + "' AND DATE = '" + date + "';";
        String sql2 = "INSERT INTO userPhysicalActivity (USERNAME, DATE, PA) " +
                "VALUES ('"+username+"','"+date+"','"+PA+"')";
        updateDataBase(sql1);
        updateDataBase(sql2);
        System.out.println(username);
        System.out.println(date);
        System.out.println(PA);

    }
    public double getUserActivity(String username, String date) {
        Double result;
        try {
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM userPhysicalActivity WHERE USERNAME = '" + username + "' AND DATE = '" + date + "' LIMIT 1;";
            ResultSet rs = stat.executeQuery(sql);
            result = rs.getDouble("PA");
            rs.close();
            stat.close();
            return result;

        } catch (SQLException e) {
             result = 1.0;
        }
        return result;
    }

//-----------------------------GET USER PLAN BY DATE---------------------------

    public static List<String> getUserPlan(String user, String date) {
        try {
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM '" + user + "' WHERE Date = '" + date + "';";
            ResultSet rs = stat.executeQuery(sql);
            List<String> userDayPlan = new ArrayList<String>();
            while(rs.next()) {
                userDayPlan.add(rs.getString("Id"));
                userDayPlan.add(rs.getString("FoodTime"));
                userDayPlan.add(rs.getString("ToidunimiEST"));
                userDayPlan.add(String.format("%.2f", rs.getDouble("Weight"))+" g");
                userDayPlan.add(String.format("%.2f", rs.getDouble("Energiashkiudainedkcal"))+" kcal");
            }
            rs.close();
            stat.close();
            return userDayPlan;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("database error");
        }
        return null;
    }
//-----------------------------------------------------------------------------------------
    public static String getUserSex(String username){
        try {
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM USERS WHERE USERNAME = '" + username + "' LIMIT 1;";
            ResultSet rs = stat.executeQuery(sql);
            String result = rs.getString("GENDER");
            rs.close();
            stat.close();
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

//-----------------------------------ADD TO USERPLAN---------------------------------------

    public void addToUsersPlan(String kasutajanimi, String kuupaev, String toiduNimetus, String aeg, double kaal ){

        String ToidunimiLAT = getStringFromDB(toiduNimetus, "ToidunimiLAT");
        String Sunonuumid = getStringFromDB(toiduNimetus, "Sunonuumid");
        String Toidugrupp = getStringFromDB(toiduNimetus, "Toidugrupp");
        String Allikas = getStringFromDB(toiduNimetus, "Allikas");

        //in gramms
        double EnergiashkiudainedkJ = (getDoubleFromDB(toiduNimetus, "EnergiashkiudainedkJ")*kaal)/100;
        double Energiashkiudainedkcal = (getDoubleFromDB(toiduNimetus, "Energiashkiudainedkcal")*kaal)/100;
        double Susivesikudimenduvadg = (getDoubleFromDB(toiduNimetus, "Susivesikudimenduvadg")*kaal)/100;
        double Rasvadg = (getDoubleFromDB(toiduNimetus, "Rasvadg")*kaal)/100;
        double Valgudg = (getDoubleFromDB(toiduNimetus, "Valgudg")*kaal)/100;
        double Alkoholg = (getDoubleFromDB(toiduNimetus, "Alkoholg")*kaal)/100;
        double Vesig = (getDoubleFromDB(toiduNimetus, "Vesig")*kaal)/100;
        double Tuhkg = (getDoubleFromDB(toiduNimetus, "Tuhkg")*kaal)/100;
        double Susivesikudkokkug = (getDoubleFromDB(toiduNimetus, "Susivesikudkokkug")*kaal)/100;
        double Kiudainedg = (getDoubleFromDB(toiduNimetus, "Kiudainedg")*kaal)/100;
        double Tarklisg = (getDoubleFromDB(toiduNimetus, "Tarklisg")*kaal)/100;
        double Sahharoosg = (getDoubleFromDB(toiduNimetus, "Sahharoosg")*kaal)/100;
        double Laktoosg = (getDoubleFromDB(toiduNimetus, "Laktoosg")*kaal)/100;
        double Maltoosg = (getDoubleFromDB(toiduNimetus, "Maltoosg")*kaal)/100;
        double Glukoosg = (getDoubleFromDB(toiduNimetus, "Glukoosg")*kaal)/100;
        double Fruktoosg = (getDoubleFromDB(toiduNimetus, "Fruktoosg")*kaal)/100;
        double Galaktoosg = (getDoubleFromDB(toiduNimetus, "Galaktoosg")*kaal)/100;
        double Rasvhappedkokkug = (getDoubleFromDB(toiduNimetus, "Rasvhappedkokkug")*kaal)/100;
        double Kullastunudrasvhappedg = (getDoubleFromDB(toiduNimetus, "Kullastunudrasvhappedg")*kaal)/100;
        double Monokullastumatarasvhappedg = (getDoubleFromDB(toiduNimetus, "Monokullastumatarasvhappedg")*kaal)/100;
        double Polukullastumatarasvhappedg = (getDoubleFromDB(toiduNimetus, "Polukullastumatarasvhappedg")*kaal)/100;
        double Transrasvhappedg = (getDoubleFromDB(toiduNimetus, "Transrasvhappedg")*kaal)/100;
        double PalmitiinhapeC16g = (getDoubleFromDB(toiduNimetus, "PalmitiinhapeC16g")*kaal)/100;
        double SteariinhapeC18g = (getDoubleFromDB(toiduNimetus, "SteariinhapeC18g")*kaal)/100;
        double LinoolhapeC182g = (getDoubleFromDB(toiduNimetus, "LinoolhapeC182g")*kaal)/100;
        double LinoleenhapeC183g = (getDoubleFromDB(toiduNimetus, "LinoleenhapeC183g")*kaal)/100;
        //in miligramms
        double Kolesteroolmg = (getDoubleFromDB(toiduNimetus, "Kolesteroolmg")*kaal)/100;
        double Naatriummg = (getDoubleFromDB(toiduNimetus, "Naatriummg")*kaal)/100;
        double Kaaliummg = (getDoubleFromDB(toiduNimetus, "Kaaliummg")*kaal)/100;
        double Kaltsiummg = (getDoubleFromDB(toiduNimetus, "Kaltsiummg")*kaal)/100;
        double Magneesiummg = (getDoubleFromDB(toiduNimetus, "Magneesiummg")*kaal)/100;
        double Fosformg = (getDoubleFromDB(toiduNimetus, "Fosformg")*kaal)/100;
        double Raudmg = (getDoubleFromDB(toiduNimetus, "Raudmg")*kaal)/100;
        double Tsinkmg = (getDoubleFromDB(toiduNimetus, "Tsinkmg")*kaal)/100;
        double Vaskmg = (getDoubleFromDB(toiduNimetus, "Vaskmg")*kaal)/100;
        double Mangaanmg = (getDoubleFromDB(toiduNimetus, "Mangaanmg")*kaal)/100;
        double VitamiinB1mg = (getDoubleFromDB(toiduNimetus, "VitamiinB1mg")*kaal)/100;
        double VitamiinB2mg = (getDoubleFromDB(toiduNimetus, "VitamiinB2mg")*kaal)/100;
        double Niatsiinmg = (getDoubleFromDB(toiduNimetus, "Niatsiinmg")*kaal)/100;
        double Niatsiiniekvivtruptofaanistmg = (getDoubleFromDB(toiduNimetus, "Niatsiiniekvivtruptofaanistmg")*kaal)/100;
        double Pantoteenhapemg = (getDoubleFromDB(toiduNimetus, "Pantoteenhapemg")*kaal)/100;
        double VitamiinB6mg = (getDoubleFromDB(toiduNimetus, "VitamiinB6mg")*kaal)/100;
        double VitamiinCmg = (getDoubleFromDB(toiduNimetus, "VitamiinCmg")*kaal)/100;
        double Soolaekvivalentmg = (getDoubleFromDB(toiduNimetus, "Soolaekvivalentmg")*kaal)/100;
        //-in nanogramms
        double Joodug = (getDoubleFromDB(toiduNimetus, "Joodug")*kaal)/100;
        double Seleenug = (getDoubleFromDB(toiduNimetus, "Seleenug")*kaal)/100;
        double Kroomug = (getDoubleFromDB(toiduNimetus, "Kroomug")*kaal)/100;
        double Nikkelug = (getDoubleFromDB(toiduNimetus, "Nikkelug")*kaal)/100;
        double Retinoolug = (getDoubleFromDB(toiduNimetus, "Retinoolug")*kaal)/100;
        double VitamiinDug = (getDoubleFromDB(toiduNimetus, "VitamiinDug")*kaal)/100;
        double VitamiinD3ug = (getDoubleFromDB(toiduNimetus, "VitamiinD3ug")*kaal)/100;
        double VitamiinKug = (getDoubleFromDB(toiduNimetus, "VitamiinKug")*kaal)/100;
        double Biotiinug = (getDoubleFromDB(toiduNimetus, "Biotiinug")*kaal)/100;
        double Folaadidug = (getDoubleFromDB(toiduNimetus, "Folaadidug")*kaal)/100;
        double VitamiinB12ug = (getDoubleFromDB(toiduNimetus, "VitamiinB12ug")*kaal)/100;
        //Ekvivalentid
        double VitamiinARE = (getDoubleFromDB(toiduNimetus, "VitamiinARE")*kaal)/100;
        double BeetakaroteeniekvivalentBCE = (getDoubleFromDB(toiduNimetus, "BeetakaroteeniekvivalentBCE")*kaal)/100;
        double VitamiinEaTE = (getDoubleFromDB(toiduNimetus, "VitamiinEaTE")*kaal)/100;
        double NiatsiiniekvivalentkokkuNE = (getDoubleFromDB(toiduNimetus, "NiatsiiniekvivalentkokkuNE")*kaal)/100;

        String sql = "INSERT INTO '"+kasutajanimi+"' (Date, FoodTime, ToidunimiEST, Weight, ToidunimiLAT, Sunonuumid, Toidugrupp, " +
                "Allikas, EnergiashkiudainedkJ, Energiashkiudainedkcal, Susivesikudimenduvadg, Rasvadg, Valgudg, Alkoholg, Vesig, Tuhkg, " +
                "Susivesikudkokkug, Kiudainedg, Tarklisg, Sahharoosg, Laktoosg, Maltoosg, Glukoosg, Fruktoosg, Galaktoosg, " +
                "Rasvhappedkokkug, Kullastunudrasvhappedg, Monokullastumatarasvhappedg, Polukullastumatarasvhappedg, Transrasvhappedg, " +
                "PalmitiinhapeC16g, SteariinhapeC18g, LinoolhapeC182g, LinoleenhapeC183g, Kolesteroolmg, Naatriummg, Kaaliummg, " +
                "Kaltsiummg, Magneesiummg, Fosformg, Raudmg, Tsinkmg, Vaskmg, Mangaanmg, Joodug, Seleenug, Kroomug, Nikkelug, " +
                "VitamiinARE, Retinoolug, BeetakaroteeniekvivalentBCE, VitamiinDug, VitamiinD3ug, VitamiinEaTE, VitamiinKug, " +
                "VitamiinB1mg, VitamiinB2mg, NiatsiiniekvivalentkokkuNE, Niatsiinmg, Niatsiiniekvivtruptofaanistmg, Pantoteenhapemg, " +
                "VitamiinB6mg, Biotiinug, Folaadidug, VitamiinB12ug, VitamiinCmg, Soolaekvivalentmg) " +
                "VALUES ('"+kuupaev+"','"+aeg+"','"+toiduNimetus+"','"+kaal+"','"+ToidunimiLAT+"','"+Sunonuumid+"','"+Toidugrupp+"'," +
                "'"+Allikas+"','"+EnergiashkiudainedkJ+"','"+Energiashkiudainedkcal+"','"+Susivesikudimenduvadg+"','"+Rasvadg+"','"+Valgudg+"'," +
                "'"+Alkoholg+"','"+Vesig+"','"+Tuhkg+"','"+Susivesikudkokkug+"','"+Kiudainedg+"','"+Tarklisg+"','"+Sahharoosg+"','"+Laktoosg+"'," +
                "'"+Maltoosg+"','"+Glukoosg+"','"+Fruktoosg+"','"+Galaktoosg+"','"+Rasvhappedkokkug+"','"+Kullastunudrasvhappedg+"'," +
                "'"+Monokullastumatarasvhappedg+"','"+Polukullastumatarasvhappedg+"','"+Transrasvhappedg+"','"+PalmitiinhapeC16g+"'," +
                "'"+SteariinhapeC18g+"','"+LinoolhapeC182g+"','"+LinoleenhapeC183g+"','"+Kolesteroolmg+"','"+Naatriummg+"','"+Kaaliummg+"'," +
                "'"+Kaltsiummg+"','"+Magneesiummg+"','"+Fosformg+"','"+Raudmg+"','"+Tsinkmg+"','"+Vaskmg+"','"+Mangaanmg+"','"+Joodug+"'," +
                "'"+Seleenug+"','"+Kroomug+"','"+Nikkelug+"','"+VitamiinARE+"','"+Retinoolug+"','"+BeetakaroteeniekvivalentBCE+"'," +
                "'"+VitamiinDug+"','"+VitamiinD3ug+"','"+VitamiinEaTE+"','"+VitamiinKug+"','"+VitamiinB1mg+"','"+VitamiinB2mg+"'," +
                "'"+NiatsiiniekvivalentkokkuNE+"','"+Niatsiinmg+"', '"+Niatsiiniekvivtruptofaanistmg+"','"+Pantoteenhapemg+"','"+VitamiinB6mg+"'," +
                "'"+Biotiinug+"','"+Folaadidug+"','"+VitamiinB12ug+"','"+VitamiinCmg+"','"+Soolaekvivalentmg+"')";
        updateDataBase(sql);
    }

//-----------------------------DELETE FROM USERPLAN----------------------------

    public void removeFromUsersPlan(String kasutajanimi, String id){

        String sql = "DELETE FROM '" + kasutajanimi + "' WHERE Id = '" + id + "';";
        updateDataBase(sql);
    }
//-----------------------------SUM OF COLUMN------------------------------------

    public static HashMap<String, Double> getUserDayElementsSum(String user, String date) {
        try {

            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM '" + user + "' WHERE Date = '" + date + "';";
            ResultSet rs = stat.executeQuery(sql);

            Double sumEnergiashkiudainedkJ = 0.0;
            Double sumEnergiashkiudainedkcal = 0.0;
            Double sumSusivesikudimenduvadg = 0.0;
            Double sumRasvadg = 0.0;
            Double sumValgudg = 0.0;
            Double sumAlkoholg = 0.0;
            Double sumVesig = 0.0;
            Double sumTuhkg = 0.0;
            Double sumSusivesikudkokkug = 0.0;
            Double sumKiudainedg = 0.0;
            Double sumTarklisg = 0.0;
            Double sumSahharoosg = 0.0;
            Double sumLaktoosg = 0.0;
            Double sumMaltoosg = 0.0;
            Double sumGlukoosg = 0.0;
            Double sumFruktoosg = 0.0;
            Double sumGalaktoosg = 0.0;
            Double sumRasvhappedkokkug = 0.0;
            Double sumKullastunudrasvhappedg = 0.0;
            Double sumMonokullastumatarasvhappedg = 0.0;
            Double sumPolukullastumatarasvhappedg = 0.0;
            Double sumTransrasvhappedg = 0.0;
            Double sumPalmitiinhapeC16g = 0.0;
            Double sumSteariinhapeC18g = 0.0;
            Double sumLinoolhapeC182g = 0.0;
            Double sumLinoleenhapeC183g = 0.0;
            Double sumKolesteroolmg = 0.0;
            Double sumNaatriummg = 0.0;
            Double sumKaaliummg = 0.0;
            Double sumKaltsiummg = 0.0;
            Double sumMagneesiummg = 0.0;
            Double sumFosformg = 0.0;
            Double sumRaudmg = 0.0;
            Double sumTsinkmg = 0.0;
            Double sumVaskmg = 0.0;
            Double sumMangaanmg = 0.0;
            Double sumJoodug = 0.0;
            Double sumSeleenug = 0.0;
            Double sumKroomug = 0.0;
            Double sumNikkelug = 0.0;
            Double sumVitamiinARE = 0.0;
            Double sumRetinoolug = 0.0;
            Double sumBeetakaroteeniekvivalentBCE = 0.0;
            Double sumVitamiinDug = 0.0;
            Double sumVitamiinD3ug = 0.0;
            Double sumVitamiinEaTE = 0.0;
            Double sumVitamiinKug = 0.0;
            Double sumVitamiinB1mg = 0.0;
            Double sumVitamiinB2mg = 0.0;
            Double sumNiatsiiniekvivalentkokkuNE = 0.0;
            Double sumNiatsiinmg = 0.0;
            Double sumNiatsiiniekvivtruptofaanistmg = 0.0;
            Double sumPantoteenhapemg = 0.0;
            Double sumVitamiinB6mg = 0.0;
            Double sumBiotiinug = 0.0;
            Double sumFolaadidug = 0.0;
            Double sumVitamiinB12ug = 0.0;
            Double sumVitamiinCmg = 0.0;
            Double sumSoolaekvivalentmg = 0.0;

            while (rs.next()) {
                Double EnergiashkiudainedkJ = rs.getDouble("EnergiashkiudainedkJ");
                Double Energiashkiudainedkcal = rs.getDouble("Energiashkiudainedkcal");
                Double Susivesikudimenduvadg = rs.getDouble("Susivesikudimenduvadg");
                Double Rasvadg = rs.getDouble("Rasvadg");
                Double Valgudg = rs.getDouble("Valgudg");
                Double Alkoholg = rs.getDouble("Alkoholg");
                Double Vesig = rs.getDouble("Vesig");
                Double Tuhkg = rs.getDouble("Tuhkg");
                Double Susivesikudkokkug = rs.getDouble("Susivesikudkokkug");
                Double Kiudainedg = rs.getDouble("Kiudainedg");
                Double Tarklisg = rs.getDouble("Tarklisg");
                Double Sahharoosg = rs.getDouble("Sahharoosg");
                Double Laktoosg = rs.getDouble("Laktoosg");
                Double Maltoosg = rs.getDouble("Maltoosg");
                Double Glukoosg = rs.getDouble("Glukoosg");
                Double Fruktoosg = rs.getDouble("Fruktoosg");
                Double Galaktoosg = rs.getDouble("Galaktoosg");
                Double Rasvhappedkokkug = rs.getDouble("Rasvhappedkokkug");
                Double Kullastunudrasvhappedg = rs.getDouble("Kullastunudrasvhappedg");
                Double Monokullastumatarasvhappedg = rs.getDouble("Monokullastumatarasvhappedg");
                Double Polukullastumatarasvhappedg = rs.getDouble("Polukullastumatarasvhappedg");
                Double Transrasvhappedg = rs.getDouble("Transrasvhappedg");
                Double PalmitiinhapeC16g = rs.getDouble("PalmitiinhapeC16g");
                Double SteariinhapeC18g = rs.getDouble("SteariinhapeC18g");
                Double LinoolhapeC182g = rs.getDouble("LinoolhapeC182g");
                Double LinoleenhapeC183g = rs.getDouble("LinoleenhapeC183g");
                Double Kolesteroolmg = rs.getDouble("Kolesteroolmg");
                Double Naatriummg = rs.getDouble("Naatriummg");
                Double Kaaliummg = rs.getDouble("Kaaliummg");
                Double Kaltsiummg = rs.getDouble("Kaltsiummg");
                Double Magneesiummg = rs.getDouble("Magneesiummg");
                Double Fosformg = rs.getDouble("Fosformg");
                Double Raudmg = rs.getDouble("Raudmg");
                Double Tsinkmg = rs.getDouble("Tsinkmg");
                Double Vaskmg = rs.getDouble("Vaskmg");
                Double Mangaanmg = rs.getDouble("Mangaanmg");
                Double Joodug = rs.getDouble("Joodug");
                Double Seleenug = rs.getDouble("Seleenug");
                Double Kroomug = rs.getDouble("Kroomug");
                Double Nikkelug = rs.getDouble("Nikkelug");
                Double VitamiinARE = rs.getDouble("VitamiinARE");
                Double Retinoolug = rs.getDouble("Retinoolug");
                Double BeetakaroteeniekvivalentBCE = rs.getDouble("BeetakaroteeniekvivalentBCE");
                Double VitamiinDug = rs.getDouble("VitamiinDug");
                Double VitamiinD3ug = rs.getDouble("VitamiinD3ug");
                Double VitamiinEaTE = rs.getDouble("VitamiinEaTE");
                Double VitamiinKug = rs.getDouble("VitamiinKug");
                Double VitamiinB1mg = rs.getDouble("VitamiinB1mg");
                Double VitamiinB2mg = rs.getDouble("VitamiinB2mg");
                Double NiatsiiniekvivalentkokkuNE = rs.getDouble("NiatsiiniekvivalentkokkuNE");
                Double Niatsiinmg = rs.getDouble("Niatsiinmg");
                Double Niatsiiniekvivtruptofaanistmg = rs.getDouble("Niatsiiniekvivtruptofaanistmg");
                Double Pantoteenhapemg = rs.getDouble("Pantoteenhapemg");
                Double VitamiinB6mg = rs.getDouble("VitamiinB6mg");
                Double Biotiinug = rs.getDouble("Biotiinug");
                Double Folaadidug = rs.getDouble("Folaadidug");
                Double VitamiinB12ug = rs.getDouble("VitamiinB12ug");
                Double VitamiinCmg = rs.getDouble("VitamiinCmg");
                Double Soolaekvivalentmg = rs.getDouble("Soolaekvivalentmg");

                sumEnergiashkiudainedkJ = sumEnergiashkiudainedkJ + EnergiashkiudainedkJ;
                sumEnergiashkiudainedkcal = sumEnergiashkiudainedkcal + Energiashkiudainedkcal;
                sumSusivesikudimenduvadg = sumSusivesikudimenduvadg + Susivesikudimenduvadg;
                sumRasvadg = sumRasvadg + Rasvadg;
                sumValgudg = sumValgudg + Valgudg;
                sumAlkoholg = sumAlkoholg + Alkoholg;
                sumVesig = sumVesig + Vesig;
                sumTuhkg = sumTuhkg + Tuhkg;
                sumSusivesikudkokkug = sumSusivesikudkokkug + Susivesikudkokkug;
                sumKiudainedg = sumKiudainedg + Kiudainedg;
                sumTarklisg = sumTarklisg + Tarklisg;
                sumSahharoosg = sumSahharoosg + Sahharoosg;
                sumLaktoosg = sumLaktoosg + Laktoosg;
                sumMaltoosg = sumMaltoosg + Maltoosg;
                sumGlukoosg = sumGlukoosg + Glukoosg;
                sumFruktoosg = sumFruktoosg + Fruktoosg;
                sumGalaktoosg = sumGalaktoosg + Galaktoosg;
                sumRasvhappedkokkug = sumRasvhappedkokkug + Rasvhappedkokkug;
                sumKullastunudrasvhappedg = sumKullastunudrasvhappedg + Kullastunudrasvhappedg;
                sumMonokullastumatarasvhappedg = sumMonokullastumatarasvhappedg + Monokullastumatarasvhappedg;
                sumPolukullastumatarasvhappedg = sumPolukullastumatarasvhappedg + Polukullastumatarasvhappedg;
                sumTransrasvhappedg = sumTransrasvhappedg + Transrasvhappedg;
                sumPalmitiinhapeC16g = sumPalmitiinhapeC16g + PalmitiinhapeC16g;
                sumSteariinhapeC18g = sumSteariinhapeC18g + SteariinhapeC18g;
                sumLinoolhapeC182g = sumLinoolhapeC182g + LinoolhapeC182g;
                sumLinoleenhapeC183g = sumLinoleenhapeC183g + LinoleenhapeC183g;
                sumKolesteroolmg = sumKolesteroolmg + Kolesteroolmg;
                sumNaatriummg = sumNaatriummg + Naatriummg;
                sumKaaliummg = sumKaaliummg + Kaaliummg;
                sumKaltsiummg = sumKaltsiummg + Kaltsiummg;
                sumMagneesiummg = sumMagneesiummg + Magneesiummg;
                sumFosformg = sumFosformg + Fosformg;
                sumRaudmg = sumRaudmg + Raudmg;
                sumTsinkmg = sumTsinkmg + Tsinkmg;
                sumVaskmg = sumVaskmg + Vaskmg;
                sumMangaanmg = sumMangaanmg + Mangaanmg;
                sumJoodug = sumJoodug + Joodug;
                sumSeleenug = sumSeleenug + Seleenug;
                sumKroomug = sumKroomug + Kroomug;
                sumNikkelug = sumNikkelug + Nikkelug;
                sumVitamiinARE = sumVitamiinARE + VitamiinARE;
                sumRetinoolug = sumRetinoolug + Retinoolug;
                sumBeetakaroteeniekvivalentBCE = sumBeetakaroteeniekvivalentBCE + BeetakaroteeniekvivalentBCE;
                sumVitamiinDug = sumVitamiinDug + VitamiinDug;
                sumVitamiinD3ug = sumVitamiinD3ug + VitamiinD3ug;
                sumVitamiinEaTE = sumVitamiinEaTE + VitamiinEaTE;
                sumVitamiinKug = sumVitamiinKug + VitamiinKug;
                sumVitamiinB1mg = sumVitamiinB1mg + VitamiinB1mg;
                sumVitamiinB2mg = sumVitamiinB2mg + VitamiinB2mg;
                sumNiatsiiniekvivalentkokkuNE = sumNiatsiiniekvivalentkokkuNE + NiatsiiniekvivalentkokkuNE;
                sumNiatsiinmg = sumNiatsiinmg + Niatsiinmg;
                sumNiatsiiniekvivtruptofaanistmg = sumNiatsiiniekvivtruptofaanistmg + Niatsiiniekvivtruptofaanistmg;
                sumPantoteenhapemg = sumPantoteenhapemg + Pantoteenhapemg;
                sumVitamiinB6mg = sumVitamiinB6mg + VitamiinB6mg;
                sumBiotiinug = sumBiotiinug + Biotiinug;
                sumFolaadidug = sumFolaadidug + Folaadidug;
                sumVitamiinB12ug = sumVitamiinB12ug + VitamiinB12ug;
                sumVitamiinCmg = sumVitamiinCmg + VitamiinCmg;
                sumSoolaekvivalentmg = sumSoolaekvivalentmg + Soolaekvivalentmg;
            }
                HashMap<String, Double> elementsSum = new HashMap<String, Double>();
                elementsSum.put("EnergiashkiudainedkJ", sumEnergiashkiudainedkJ);
                elementsSum.put("Energiashkiudainedkcal", sumEnergiashkiudainedkcal);
                elementsSum.put("Susivesikudimenduvadg", sumSusivesikudimenduvadg);
                elementsSum.put("Rasvadg", sumRasvadg);
                elementsSum.put("Valgudg", sumValgudg);
                elementsSum.put("Alkoholg", sumAlkoholg);
                elementsSum.put("Vesig", sumVesig);
                elementsSum.put("Tuhkg", sumTuhkg);
                elementsSum.put("Susivesikudkokkug", sumSusivesikudkokkug);
                elementsSum.put("Kiudainedg", sumKiudainedg);
                elementsSum.put("Tarklisg", sumTarklisg);
                elementsSum.put("Sahharoosg", sumSahharoosg);
                elementsSum.put("Laktoosg", sumLaktoosg);
                elementsSum.put("Maltoosg", sumMaltoosg);
                elementsSum.put("Glukoosg", sumGlukoosg);
                elementsSum.put("Fruktoosg", sumFruktoosg);
                elementsSum.put("Galaktoosg", sumGalaktoosg);
                elementsSum.put("Rasvhappedkokkug", sumRasvhappedkokkug);
                elementsSum.put("Kullastunudrasvhappedg", sumKullastunudrasvhappedg);
                elementsSum.put("Monokullastumatarasvhappedg", sumMonokullastumatarasvhappedg);
                elementsSum.put("Polukullastumatarasvhappedg", sumPolukullastumatarasvhappedg);
                elementsSum.put("Transrasvhappedg", sumTransrasvhappedg);
                elementsSum.put("PalmitiinhapeC16g", sumPalmitiinhapeC16g);
                elementsSum.put("SteariinhapeC18g", sumSteariinhapeC18g);
                elementsSum.put("LinoolhapeC182g", sumLinoolhapeC182g);
                elementsSum.put("LinoleenhapeC183g", sumLinoleenhapeC183g);
                elementsSum.put("Kolesteroolmg", sumKolesteroolmg);

                elementsSum.put("Naatriummg", sumNaatriummg);
                elementsSum.put("Kaaliummg", sumKaaliummg);
                elementsSum.put("Kaltsiummg", sumKaltsiummg);
                elementsSum.put("Magneesiummg", sumMagneesiummg);
                elementsSum.put("Fosformg", sumFosformg);
                elementsSum.put("Raudmg", sumRaudmg);
                elementsSum.put("Tsinkmg", sumTsinkmg);
                elementsSum.put("Vaskmg", sumVaskmg);
                elementsSum.put("Mangaanmg", sumMangaanmg);
                elementsSum.put("Joodug", sumJoodug);
                elementsSum.put("Seleenug", sumSeleenug);
                elementsSum.put("Kroomug", sumKroomug);
                elementsSum.put("Nikkelug", sumNikkelug);

                elementsSum.put("VitamiinARE", sumVitamiinARE);
                elementsSum.put("Retinoolug", sumRetinoolug);
                elementsSum.put("BeetakaroteeniekvivalentBCE", sumBeetakaroteeniekvivalentBCE);
                elementsSum.put("VitamiinDug", sumVitamiinDug);
                elementsSum.put("VitamiinD3ug", sumVitamiinD3ug);
                elementsSum.put("VitamiinEaTE", sumVitamiinEaTE);
                elementsSum.put("VitamiinKug", sumVitamiinKug);
                elementsSum.put("VitamiinB1mg", sumVitamiinB1mg);
                elementsSum.put("VitamiinB2mg", sumVitamiinB2mg);
                elementsSum.put("NiatsiiniekvivalentkokkuNE", sumNiatsiiniekvivalentkokkuNE);
                elementsSum.put("Niatsiinmg", sumNiatsiinmg);
                elementsSum.put("Niatsiiniekvivtruptofaanistmg", sumNiatsiiniekvivtruptofaanistmg);
                elementsSum.put("Pantoteenhapemg", sumPantoteenhapemg);
                elementsSum.put("VitamiinB6mg", sumVitamiinB6mg);
                elementsSum.put("Biotiinug", sumBiotiinug);
                elementsSum.put("Folaadidug", sumFolaadidug);
                elementsSum.put("VitamiinB12ug", sumVitamiinB12ug);
                elementsSum.put("VitamiinCmg", sumVitamiinCmg);

                elementsSum.put("Soolaekvivalentmg", sumSoolaekvivalentmg);

                rs.close();
                stat.close();
                return elementsSum;

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
//---------------------------------------------------------------------------------------------------------
        public static double getSumFromUserDB(String user, String date, String what) {
            try {

                Statement stat = conn.createStatement();
                String sql = "SELECT * FROM '" + user + "' WHERE Date = '" + date + "';";
                ResultSet rs = stat.executeQuery(sql);
                Double sum = 0.0;
                while (rs.next()) {
                    Double i = rs.getDouble(what);
                    sum = sum + i;
                }
                rs.close();
                stat.close();
                return sum;
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(0);
            }
            return 0.0;
}
//---------------------------------------------------------------------------------------------------------
    public static HashMap<String, String> getUser(String username) {
    HashMap<String, String> userData = new HashMap<String, String>();
    try {
        Statement stat = conn.createStatement();
        String sql = "SELECT * FROM USERS WHERE USERNAME = '" + username + "' LIMIT 1;";

        ResultSet rs = stat.executeQuery(sql);
        userData.put("ID", rs.getString("ID"));
        userData.put("USERNAME", rs.getString("USERNAME"));
        userData.put("PASSWORD", rs.getString("PASSWORD"));
        userData.put("FULLNAME", rs.getString("FULLNAME"));
        userData.put("GENDER", rs.getString("GENDER"));
        userData.put("AGE", String.valueOf(rs.getString("AGE")));
        userData.put("WEIGHT", String.valueOf(rs.getString("WEIGHT")));
        userData.put("HEIGHT", String.valueOf(rs.getString("HEIGHT")));
        userData.put("PLAN", rs.getString("PLAN"));


        rs.close();
        stat.close();
        return userData;
    } catch (SQLException e) {
        e.printStackTrace();
        System.exit(0);
    }
    return userData;
    }
//-----------------------------------------------------------------------------------------------------
    public void updateUserData(String username, String password, String fullname, String gender, int age, double weight, double height) {
        
        try {
            Statement stat = conn.createStatement();
            String sql = String.format("UPDATE USERS SET USERNAME = '"+username+"', PASSWORD = '"+password+"', FULLNAME = '"+fullname+"', " +
                    "GENDER = '"+gender+"', AGE = '"+age+"', WEIGHT = '"+weight+"', HEIGHT = '"+height+"' WHERE USERNAME = '"+username+"';");
            System.out.println(username+" "+password+" "+fullname+" "+gender+" "+age+" "+weight+" "+height);
            stat.executeUpdate(sql);
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);

        }
    }
//--------------------------------------------------------------------------------------------------------

    public  HashMap<String, Double> getUserRV(String gender, int age) {
        int ageIndex;

        if(age>=0 && age<1){ageIndex = 1;}
        else if(age>=1 && age<=2){ageIndex = 2;}
        else if(age>=3 && age<=5){ageIndex = 3;}
        else if(age>=6 && age<=9){ageIndex = 4;}
        else if(age>=10 && age<=13){ageIndex = 5;}
        else if(age>=14 && age<=17){ageIndex = 6;}
        else if(age>=18 && age<=30){ageIndex = 7;}
        else if(age>=31 && age<=60){ageIndex = 8;}
        else if(age>=61 && age<=75){ageIndex = 9;}
        else{ageIndex = 10;}

        HashMap<String, Double> userDRV = new HashMap<String, Double>();

        try {
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM DRValues WHERE Id = '" + ageIndex + "' LIMIT 1;";

            ResultSet rs = stat.executeQuery(sql);
            userDRV.put("Naatriummg", rs.getDouble("Naatriummg"));
            userDRV.put("Kaaliummg", rs.getDouble("Kaaliummg"));
            userDRV.put("Kaltsiummg", rs.getDouble("Kaltsiummg"));
            userDRV.put("Magneesiummg", rs.getDouble("Magneesiummg"));
            userDRV.put("Fosformg", rs.getDouble("Fosformg"));
            userDRV.put("Raudmg", rs.getDouble("Raudmg"));
            userDRV.put("Tsinkmg", rs.getDouble("Tsinkmg"));
            userDRV.put("Vaskmg", rs.getDouble("Vaskmg"));
            userDRV.put("Mangaanmg", rs.getDouble("Mangaanmg"));
            userDRV.put("Joodug", rs.getDouble("Joodug"));
            userDRV.put("Seleenug", rs.getDouble("Seleenug"));
            userDRV.put("Kroomug", rs.getDouble("Kroomug"));

            userDRV.put("VitamiinARE", rs.getDouble("VitamiinARE"));
            userDRV.put("VitamiinDug", rs.getDouble("VitamiinDug"));
            userDRV.put("VitamiinEaTE", rs.getDouble("VitamiinEaTE"));
            userDRV.put("VitamiinKug", rs.getDouble("VitamiinKug"));
            userDRV.put("VitamiinB1mg", rs.getDouble("VitamiinB1mg"));
            userDRV.put("VitamiinB2mg", rs.getDouble("VitamiinB2mg"));
            userDRV.put("NiatsiiniekvivalentkokkuNE", rs.getDouble("NiatsiiniekvivalentkokkuNE"));
            userDRV.put("Niatsiinmg", rs.getDouble("Niatsiinmg"));
            userDRV.put("Pantoteenhapemg", rs.getDouble("Pantoteenhapemg"));
            userDRV.put("VitamiinB6mg", rs.getDouble("VitamiinB6mg"));
            userDRV.put("Biotiinug", rs.getDouble("Biotiinug"));
            userDRV.put("Folaadidug", rs.getDouble("Folaadidug"));
            userDRV.put("VitamiinB12ug", rs.getDouble("VitamiinB12ug"));
            userDRV.put("VitamiinCmg", rs.getDouble("VitamiinCmg"));

            rs.close();
            stat.close();
            return userDRV;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return userDRV;
    }





//------------------------------------------UNUSED YET-----------------------------------------------------

    public String getStringFromUserPlan(String user, String searchBy, String what) {
        try {
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM '" + user + "' WHERE '" + searchBy + "' = '" + what + "';";
            ResultSet rs = stat.executeQuery(sql);
            String result = rs.getString(what);
            rs.close();
            stat.close();
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    public double getDoubleFromUserPlan(String user, String searchBy, String what) {
        try {
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM '" + user + "' WHERE ToidunimiEST = '" + searchBy + "' LIMIT 1;";
            ResultSet rs = stat.executeQuery(sql);
            Double result = rs.getDouble(what);
            rs.close();
            stat.close();
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return 0.0;
    }
            public int getCountByDate(String user, String date) {
                try {
                    Statement stat = conn.createStatement();
                    String sql = "SELECT COUNT (*) AS RowCount FROM '" + user + "' WHERE Date = '" + date + "';";
                    ResultSet rs = stat.executeQuery(sql);
                    rs.next();
                    int count = rs.getInt("RowCount") ;
                    rs.close() ;
                    //System.out.println("MyTable has " + count + " row(s).");
                    stat.close();
                    return count;

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return 0;
            }
//-----------------------------------------





}//END OF CLASS
