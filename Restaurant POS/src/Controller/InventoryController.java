
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class InventoryController extends CategoryController implements Initializable {
    
    private boolean menuOpen;
    private boolean ingCardOpen;
    private boolean editMode;
    private boolean isNewIng;
    private String currentCategory;

    @FXML
    private ImageView hambMenu;
    @FXML
    private ImageView editInventory;
    @FXML
    private HBox flowContainer;
    @FXML
    private Label ingName;
    @FXML
    private ImageView ingImage;
    @FXML
    private Button update;
    @FXML
    private Label ingDesc;
    @FXML
    private Label ingNotify;
    @FXML
    private Label ingCuantity;
    @FXML
    private TextField ingNameInput;
    @FXML
    private ImageView ingImageInput;
    @FXML
    private ImageView ingImageEdit;
    @FXML
    private Button confirm;
    @FXML
    private TextField ingDescInput;
    @FXML
    private TextField ingNotifyInput;
    @FXML
    private TextField ingCuantityInput;
    @FXML
    private TextField ingUnitInput;
    @FXML
    private GridPane ingGrid;
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
    private AnchorPane addPane;
    @FXML
    private ImageView addIng;
    @FXML
    private AnchorPane ingCard;
    @FXML
    private AnchorPane ingCardEdit;

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
        else if (event.getSource() == sideMenuInventory)
            inventoryShow(event);
    }
    
    private void setFlow(String category){
        // set flow menu to navigate
        currentCategory = category;
    }

    @Override
    public void addButtonFunction(ArrayList<Button> buttons) {
        for (Button item : buttons){
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
    public void initialize(URL arg0, ResourceBundle arg1) {
        slideClose(slider);
        menuOpen = false;
        editMode = false;
        ingCardOpen = false;
        ingCard.setVisible(false);
        ingCardEdit.setVisible(false);
        addIng.setVisible(false);
        //change
        setFoodCategories(null, ingGrid, 3, 210.0, true);
        addButtonFunction(categoryButtons);
        addButtonFunction(itemButtons);
        editInventory.setImage(new Image(getClass().getResourceAsStream("/Images/Edit.png")));
        addPane.setPickOnBounds(false);
        setFlow(null);
    }
    
}
