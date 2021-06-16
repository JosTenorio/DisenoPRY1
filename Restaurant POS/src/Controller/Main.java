
package Controller;

import Connection.ConnectionManager;
import Model.Managers.ItemManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/TablesView.fxml"));
        System.out.println(ItemManager.markReadyItem(15));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    @Override
    public void stop() {
        try {
            ConnectionManager.disconnect();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

