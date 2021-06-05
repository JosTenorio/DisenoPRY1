package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {
    private static Connection connection;
    private static Statement statement;
    private static String IP;
    private static String USERNAME;
    private static String PASSWORD;
    
    public static void logIn(String ip, String username, String password) throws ClassNotFoundException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        IP = ip;
        USERNAME = username;
        PASSWORD = password;
    }
    
    public static void connect() throws SQLException {
        String url = "jdbc:sqlserver://" + IP  + ":1433;databaseName=SmrPrototype;user=" + USERNAME + ";password=" + PASSWORD;
        connection = DriverManager.getConnection(url);
    }
    
    public static void disconnect() throws SQLException {
        if (connection != null)
            connection.close();
    }
}
  

  