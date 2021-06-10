
package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Connection.*;
import Model.*;
import Model.Managers.FoodManager;
import Model.Managers.OrderManager;
import java.util.ArrayList;

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
            ArrayList<String> sideDishes1 = new ArrayList<String>();
            sideDishes1.add("Papas fritas");
            sideDishes1.add("Papas fritas");
            ArrayList<String> sideDishes2 = new ArrayList<String>();
            Item item1 = new Item("Papas Fritas", false, sideDishes2, "Sin sal");
            Item item2 = new Item("Pasta boloñesa", true, sideDishes2, "Sin carne");
            Item item3 = new Item("Pasta alfredo", true, sideDishes1, "Sin hongos");
            ArrayList<Item> Itemes = new ArrayList<Item>();
            Itemes.add(item1);
            Itemes.add(item2);
            Itemes.add(item3);
            Order oder = new Order ("Mesa 1", Itemes);
            System.out.println(OrderManager.insertOrder(oder));
            ConnectionManager.disconnect();
        } catch (Exception e) {
            System.err.println(e.toString());              
        }
    }
}

