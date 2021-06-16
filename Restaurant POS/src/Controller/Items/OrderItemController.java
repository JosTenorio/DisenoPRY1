
package Controller.Items;

import Model.Item;
import Model.Order;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.javatuples.Pair;

public class OrderItemController {
    
    private ArrayList<Pair<Integer,ImageView>> checkBoxes;

    @FXML
    private Label tableName;
    @FXML
    private Label orderAge;
    @FXML
    private Button selectAll;
    @FXML
    private VBox itemContainer;

    public void setData(Order order){
        this.tableName.setText(order.tableName);
        this.orderAge.setText(order.age); 
        setDetails(order.items);
    }
    
    private void setDetails(ArrayList<Item> items){
        itemContainer.getChildren().clear();
        this.checkBoxes = new ArrayList<>();
        for (int i = 0; i < items.size(); i++){
            FXMLLoader loader = new FXMLLoader();
            try{
                loader.setLocation(getClass().getResource("/View/Items/OrderDetailItemView.fxml"));
                HBox hbox = loader.load();
                OrderDetailItemController itemController = loader.getController();
                itemController.setData(items.get(i));
                itemContainer.getChildren().add(hbox);
                this.checkBoxes.add(new Pair(items.get(i).number, itemController.getCheckBox()));
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
    
    public ArrayList<Pair<Integer, ImageView>> getCheckBoxes(){
        return checkBoxes;
    }
    
}
