
package Controller.Abstract;

import Controller.Items.MenuItemController;
import Model.Managers.FoodCtgrManager;
import Model.Managers.FoodManager;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.javatuples.Triplet;

public abstract class CategoryController extends SceneController {
    
    public ArrayList<Button> categoryButtons;
    public ArrayList<Button> foodButtons;
    
    public void setFoodCategories(String category, GridPane menuGrid, int cols, double size, boolean includeArchived){
        menuGrid.getChildren().clear();
        ArrayList<Triplet<String, String, Boolean>> categories;
        ArrayList<Triplet<String, String, Boolean>> food;
        if (category == null){
            categories = FoodCtgrManager.getFatherCategories();
            food = FoodManager.getUncategorizedFood(true, includeArchived);
        } else {
            categories = FoodCtgrManager.getSubCategories(category);
            food = FoodManager.getFoodByCategory(category, true, includeArchived);
        } 
        categoryButtons = setItems(categories, menuGrid, 0, cols, size);
        foodButtons = setItems(food, menuGrid, categories.size() % cols, cols, size);
    }
    
    private ArrayList<Button> setItems(ArrayList<Triplet<String, String, Boolean>> items, GridPane menuGrid, int col, int cols, double size){   
        ArrayList<Button> itemButtons = new ArrayList<>();
        int row = 0;
        for (int i = 0; i < items.size(); i++){
            FXMLLoader loader = new FXMLLoader();
            if (col == cols){
                col = 0;
                row++;
            }
            try{
                loader.setLocation(getClass().getResource("/View/Items/MenuItemView.fxml"));
                AnchorPane pane = loader.load();
                MenuItemController itemController = loader.getController();
                Triplet<String, String, Boolean> item = items.get(i);
                itemController.setData(item.getValue0(), item.getValue1(), item.getValue2());
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
    
    public abstract void addButtonFunction(ArrayList<Button> buttons);
    
}
