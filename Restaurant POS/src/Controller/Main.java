
package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Connection.*;
import Model.TablesManager;

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
            ConnectionManager.connect();
            System.out.println(TablesManager.getTableNames().toString());
            ConnectionManager.disconnect();
        } catch (Exception e) {
            System.out.println(e.toString());              
        }
    }
}

