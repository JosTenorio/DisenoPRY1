
package Controller.Items;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class MenuItemController {

    @FXML
    private ImageView image;
    @FXML
    private Button button;
    
    public void setData(String text, String path){
        button.setText(text);
        //path can be null
    }
    
    public Button getButton(){
        return button;
    }
    
}
