
package Controller.Items;

import Model.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class TableItemController {

    @FXML
    private Label name;
    @FXML
    private Label status;
    @FXML
    private Label cost;
    @FXML
    private ImageView checkbox;

    public void setData(Item item){
        String itemNames = item.name;
        if (item.sideDishes != null){
            for (String dish : item.sideDishes){
                itemNames += "\n\t" + dish;
            }
        }
        name.setText(itemNames);
        cost.setText("₡" + item.cost);
        if (item.prepared){
            status.setStyle("-fx-background-color: #02A730");
        } else{
            status.setStyle("-fx-background-color: #DB6C38");
        }
    }  
    
}
