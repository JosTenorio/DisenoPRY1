
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MenuController extends SceneController implements Initializable {
    
    private ArrayList<Button> itemButtons;
    private boolean menuOpen;

    @FXML
    private ImageView hambMenu;
    @FXML
    private ImageView editTables;
    @FXML
    private AnchorPane slider;
    @FXML
    private Button sideMenuTable;
    @FXML
    private Button sideMenuOrders;
    @FXML
    private Button sideMenuKitchen;
    @FXML
    private Button sideMenuMenu;
    @FXML
    private Button sideMenuInventory;
    @FXML
    private Button sideMenuSettings;
    
    @FXML
    private void btnHandle(MouseEvent event) throws IOException {
        if (event.getSource() == hambMenu){
            if (!menuOpen){
                slideOpen(slider);
                menuOpen = true;
            } else{
                slideClose(slider);
                menuOpen = false;
            }
        }
        // SIDE MENU
        if (event.getSource() == sideMenuTable)
            tablesShow(event);
        else if (event.getSource() == sideMenuMenu)
            menuShow(event);
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        slideClose(slider);
        menuOpen = false;
        itemButtons = new ArrayList<>();
    }    

    
}
