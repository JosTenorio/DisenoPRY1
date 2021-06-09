package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class ConnectionManager {
   
    private static String Database;
    private static String Port;
    private static String Ip;
    private static String Login;
    private static String Password;
    
    
    public static Connection connection;
    private static Statement statement;
    
    public static void setLogIn(String username, String password){
        Login = username;
        Password = password;
    }
    
    public static void setConnString(String ip, String port, String database){
        Ip = ip;
        Database = database;
        Port = port;
    }
    
    public static void connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        XMLUtil.readEnvConfig();
        if (Arrays.asList(Database, Port, Ip, Login, Password).contains(null))
            return;   
        String url = "jdbc:sqlserver://" + Ip  + ":" + Port + ";databaseName=" + Database + ";user=" + Login + ";password=" + Password;
        Proxy.openProxy();
        connection = DriverManager.getConnection(url);
    }
    
    public static void disconnect() throws SQLException {
        if (connection != null)
            connection.close();
        Proxy.closeProxy();
    }
}
  

  