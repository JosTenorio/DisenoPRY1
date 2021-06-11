
package Controller;

import Controller.Abstract.CategoryController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class MenuController extends CategoryController implements Initializable {
    
    private ArrayList<Button> itemButtons;
    private boolean menuOpen;

    @FXML
    private ImageView hambMenu;
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
    private ImageView editMenu;
    @FXML
    private ImageView editMenu1;
    @FXML
    private HBox flowContainer;
    @FXML
    private Label dishName;
    @FXML
    private ImageView editDish;
    @FXML
    private ImageView dishImage;
    @FXML
    private Button confirm;
    @FXML
    private Label dishDesc;
    @FXML
    private Label dishSides;
    @FXML
    private Label dishPrice;
    @FXML
    private GridPane menuGrid;
    @FXML
    private AnchorPane dishCard;
    
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
        for (Button item : itemButtons){
            if (event.getSource() == item){
                itemButtons = setMenuCategories(item.getText(), menuGrid, 3, 210.0);
                setGridButtons();
                //set Food
                //set Flow
            }
        }
        // SIDE MENU
        if (event.getSource() == sideMenuTable)
            tablesShow(event);
        else if (event.getSource() == sideMenuMenu)
            menuShow(event);
        
    }
    
    @Override
    public void setGridButtons(){
        for (Button item : itemButtons){
            item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        btnHandle(event);
                    } catch (IOException ex) {
                        System.err.println(ex);
                    }
                }
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        slideClose(slider);
        menuOpen = false;
        dishCard.setVisible(false);
        itemButtons = setMenuCategories(null, menuGrid, 3, 210.0);
        setGridButtons();
        //set Food
        //set Flow
    }    

    
}
