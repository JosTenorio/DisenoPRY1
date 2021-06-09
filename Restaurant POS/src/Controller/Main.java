
package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Connection.*;

public class Main extends Application{
    
    public static void main(String[] args) {
        launch(args);
    }
    

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/TablesView.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        try {
            Proxy.openProxy();
            ConnectionManager.logIn("127.0.0.1", "sqlserver", "BigRoo85");
            ConnectionManager.connect();
            ConnectionManager.disconnect();
            Proxy.closeProxy();
        } catch (Exception e) {
            System.out.println(e.toString());              
        }
    }
}

