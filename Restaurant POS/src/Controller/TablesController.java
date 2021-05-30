
package Controller;

import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

public class TablesController {

    @FXML
    private Button table1;
    @FXML
    private ImageView hambMenu;
    @FXML
    private ImageView edit;

    @FXML
    private void btnHandle(MouseEvent event) {
        if (event.getSource() == table1){
            tableHandle(event);
        }
    }
    
    private void tableHandle(MouseEvent event){
        if (event.getButton() == MouseButton.PRIMARY){
            System.out.println("BTN 1");
        }
        else if (event.getButton() == MouseButton.SECONDARY){
            System.out.println("BTN 2");
        }
    }
}
