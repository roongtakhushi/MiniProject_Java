package java_mini_project;
import java.sql.Connection;
import java.sql.DriverManager;

public class connect {
    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/snackdb",
                    "root",
                    "root");
            System.out.println("Database Connected Successfully");
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
    public static void main(String[] args) {
        getConnection(); 
    }
}
