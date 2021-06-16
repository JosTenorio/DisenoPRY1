
package Controller.Items;

import Model.Item;
import Model.Order;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class OrderItemController {

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
        
    }
    
}
