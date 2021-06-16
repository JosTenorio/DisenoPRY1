
package Controller.Items;

import Model.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class OrderBuildItemController {

    @FXML
    private Label name;
    @FXML
    private ImageView addNote;

    public void setData(Item item){
        String itemNames = "  " + item.name;
        if (item.sideDishes != null){
            for (String dish : item.sideDishes){
                itemNames += "\n\t" + dish;
            }
        }
        if (!"".equals(item.notes)){
            itemNames += "\n\tNota: " + item.notes;
        }
        name.setText(itemNames);
    }    
    
}
