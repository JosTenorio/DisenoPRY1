
package Controller.Items;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenuItemController {

    @FXML
    private ImageView image;
    @FXML
    private Button button;
    
    public void setData(String text, String path, boolean isArchived){
        button.setText(text);
        if (!"".equals(path)){
            try {
                image.setImage(new Image(getClass().getResourceAsStream(path)));
            } catch (Exception e) {
                button.setStyle("-fx-background-color: gray;");
            }
        } else 
            button.setStyle("-fx-background-color: gray;");
        if (isArchived){
            //make black and white
        } 
            
    }
    
    public Button getButton(){
        return button;
    }
    
}
