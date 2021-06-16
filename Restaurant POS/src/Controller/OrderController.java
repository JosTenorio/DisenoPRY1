
package Controller;

import Controller.Abstract.SceneController;
import Controller.Items.OrderItemController;
import Model.Managers.ItemManager;
import Model.Managers.OrderManager;
import Model.Order;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.javatuples.Pair;

public class OrderController extends SceneController implements Initializable {

    private boolean menuOpen;    
    private ArrayList<Pair<Integer, ImageView>> checkBoxes;
    
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
        for (Pair<Integer, ImageView> box : checkBoxes){
            if (event.getSource() == box.getValue1()){
                ItemManager.markDeliveredItem(box.getValue0());
                setOrders();
                addCheckBoxFunction(checkBoxes);
            }
        }
        // SIDE MENU
        if (event.getSource() == sideMenuTable)
            tablesShow(event);
        else if (event.getSource() == sideMenuMenu)
            menuShow(event);
        else if (event.getSource() == sideMenuInventory)
            inventoryShow(event);
        else if (event.getSource() == sideMenuKitchen)
            kitchenShow(event);
        else if (event.getSource() == sideMenuOrders)
            ordersShow(event);
    }
    
    private void setOrders(){
        this.checkBoxes = new ArrayList<>();
        ArrayList<Order> orders = OrderManager.getReadyOrders();
        itemContainer.getChildren().clear();
        for (int i = 0; i < orders.size(); i++){
            FXMLLoader loader = new FXMLLoader();
            try{
                loader.setLocation(getClass().getResource("/View/Items/OrderItemView.fxml"));
                AnchorPane pane = loader.load();
                OrderItemController itemController = loader.getController();
                itemController.setData(orders.get(i));
                itemContainer.getChildren().add(pane);
                ArrayList<Pair<Integer, ImageView>> newBoxes = itemController.getCheckBoxes();
                for (Pair<Integer, ImageView> box : newBoxes){
                    checkBoxes.add(box);
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
    
    public void addCheckBoxFunction(ArrayList<Pair<Integer, ImageView>> checkBoxes){
        for (Pair<Integer, ImageView> item : checkBoxes){
            item.getValue1().setOnMouseClicked(new EventHandler<MouseEvent>() {
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
        setOrders();
        addCheckBoxFunction(checkBoxes);
    }    
    
}
