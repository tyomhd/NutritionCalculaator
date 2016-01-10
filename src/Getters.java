import java.util.HashMap;

public class Getters {
    private static String username;


    public Getters(String user) {
       username = user;
    }

    public static int getUserId(){
        DataBase a = new DataBase();
        HashMap<String, String> userData = DataBase.getUser(getCurrentUser());
        int id = Integer.parseInt(userData.get("ID"));
        a.closeConnection();
        return id;
    }
    public static String getUserFullname(){
        DataBase a = new DataBase();
        HashMap<String, String> userData = DataBase.getUser(getCurrentUser());
        String fullname = userData.get("FULLNAME");
        a.closeConnection();
        return fullname;
    }
    public static String getUserSex(){
        DataBase a = new DataBase();
        HashMap<String, String> userData = DataBase.getUser(getCurrentUser());
        String gender = userData.get("GENDER");
        a.closeConnection();
        return gender;
    }
    public static int getUserAge(){
        DataBase a = new DataBase();
        HashMap<String, String> userData = DataBase.getUser(getCurrentUser());
        int age = Integer.parseInt(userData.get("AGE"));
        a.closeConnection();
        return age;
    }
    public static double getUserWeight() {
        DataBase a = new DataBase();
        HashMap<String, String> userData = DataBase.getUser(getCurrentUser());
        double weight = Double.parseDouble(userData.get("WEIGHT"));
        a.closeConnection();
        return weight;
    }
    public static double getUserHeight() {
        DataBase a = new DataBase();
        HashMap<String, String> userData = DataBase.getUser(getCurrentUser());
        double height = Double.parseDouble(userData.get("HEIGHT"));
        a.closeConnection();
        return height;
    }
    public static double getTergetKcal(){
       DataBase a = new DataBase();
       HashMap<String, String> userData = a.getUser(getCurrentUser());
       String gender = userData.get("GENDER");
        int age = Integer.parseInt(userData.get("AGE"));
        double weight = Double.parseDouble(userData.get("WEIGHT"));
        double height = Double.parseDouble(userData.get("HEIGHT"));
        double pa = a.getUserActivity(getCurrentUser(), getCurrentDate());
       a.closeConnection();

       double targetKcal = DietaryReferenceValues.getEER(gender, age, weight, height, pa);

       return targetKcal;
    }
    public static String getCurrentDate(){String date = TestScene.getCurrentDate(); return date;}
    public static String getCurrentUser(){String username = TestScene.getCurrentUser(); return username;}
    public static double getCurrentKcal(){
        DataBase a = new DataBase();
        double currentKcal = a.getSumFromUserDB(getCurrentUser(), getCurrentDate(), "Energiashkiudainedkcal");
        a.closeConnection();
        return currentKcal;
    }
    public static double getPA(){
        DataBase a = new DataBase();
        double currentPA = a.getUserActivity(getCurrentUser(), getCurrentDate());
        a.closeConnection();
        return currentPA;
    }
    public static String getPAString(){
        DataBase a = new DataBase();
        double currentPA = a.getUserActivity(getCurrentUser(), getCurrentDate());
        a.closeConnection();
        String PAText = null;
        double dPA = currentPA*100;
        int intPA = (int) dPA;
        switch (intPA){
            case 100:
                PAText ="Sedentary";
                break;
            case 111:
                PAText = "Low Active";
                break;
            case 112:
                PAText = "Low Active";
                break;
            case 125:
                PAText = "Active";
                break;
            case 127:
                PAText = "Active";
                break;
            case 145:
                PAText = "Very Active";
                break;
            case 148:
                PAText = "Very Active";
                break;
        }
        return PAText;
    }

    public static HashMap<String, Double> getUserElementsSum(String user, String date){
        DataBase a = new DataBase();
        HashMap<String, Double>  userElementsSum = a.getUserDayElementsSum(user, date);
        a.closeConnection();
        return userElementsSum;
    }
    public static HashMap<String, Double> getUserDRV(String gender, int age){
        DataBase a = new DataBase();
        HashMap<String, Double>  userDRV= a.getUserRV(gender, age);
        a.closeConnection();
        return userDRV;
    }





}
