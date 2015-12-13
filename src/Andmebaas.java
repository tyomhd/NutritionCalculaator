import java.sql.*;
import java.util.HashMap;

public class Andmebaas {
    Connection conn = null;


    public Andmebaas() {
        looYhendus();
    }

    public void looYhendus() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:DB.sqlite");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
      //  System.out.println("Opened database successfully");
    }

    public void sulgeYhendus() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
       // System.out.println("Ühendus suletud");
    }

    public double vaataDbDouble(String name, String what) {
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
    public String vaataDbString(int id, String what) {
        try {
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM DB WHERE ID = '" + id + "' LIMIT 1;";
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



}
