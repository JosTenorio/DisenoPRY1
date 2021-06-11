
package Controller.Abstract;

import Controller.Items.MenuItemController;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public abstract class CategoryController extends SceneController {
    
    public ArrayList<Button> setMenuCategories(String category, GridPane menuGrid, int cols, double size){
        ArrayList<Pair<String, String>> categories = new ArrayList<>();
        if (category == null){
            //TEMP
            categories.add(new Pair("", "Prueba 1"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
        } else {
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
        } 
        return setCategories(categories, menuGrid, cols, size);
    }
    
    private ArrayList<Button> setCategories(ArrayList<Pair<String, String>> categories, GridPane menuGrid, int cols, double size){   
        menuGrid.getChildren().clear();
        ArrayList<Button> itemButtons = new ArrayList<>();
        int col = 0;
        int row = 0;
        for (int i = 0; i < categories.size(); i++){
            FXMLLoader loader = new FXMLLoader();
            if (col == cols){
                col = 0;
                row++;
            }
            try{
                loader.setLocation(getClass().getResource("/View/Items/MenuItemView.fxml"));
                AnchorPane pane = loader.load();
                MenuItemController itemController = loader.getController();
                itemController.setData(categories.get(i).getKey(), categories.get(i).getValue());
                pane.setPrefSize(size, size);
                menuGrid.add(pane, col++, row);
                GridPane.setMargin(pane, new Insets(10));
                itemButtons.add(itemController.getButton());
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
        return itemButtons;
    }
    
    public abstract void setGridButtons();
    
}
