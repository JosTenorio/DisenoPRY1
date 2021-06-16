
package Controller;

import Controller.Abstract.SceneController;
import Controller.Items.OrderItemController;
import Model.Managers.OrderManager;
import Model.Order;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class KitchenController extends SceneController implements Initializable {

    private boolean menuOpen;    
    
    @FXML
    private ImageView hambMenu;
    @FXML
    private HBox flowContainer;
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
    private HBox itemContainer;
    
    @FXML
    private void btnHandle(MouseEvent event) {
        if (event.getSource() == hambMenu){
            if (!menuOpen){
                slideOpen(slider);
                menuOpen = true;
            } else{
                slideClose(slider);
                menuOpen = false;
            }
        }
    }
    
    private void setOrders(){
        ArrayList<Order> orders = OrderManager.getUnreadyOrders();
        itemContainer.getChildren().clear();
        for (int i = 0; i < orders.size(); i++){
            FXMLLoader loader = new FXMLLoader();
            try{
                loader.setLocation(getClass().getResource("/View/Items/OrderItemView.fxml"));
                AnchorPane pane = loader.load();
                OrderItemController itemController = loader.getController();
                itemController.setData(orders.get(i));
                itemContainer.getChildren().add(pane);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        slideClose(slider);
        menuOpen = false;
        setOrders();
    }    
    
}
