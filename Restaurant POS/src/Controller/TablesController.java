
package Controller;

import Controller.Items.MenuItemController;
import Controller.Items.TableItemController;
import Model.Item;
import Model.Managers.OrderManager;
import Model.Managers.TableManager;
import Model.Order;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class TablesController extends SceneController implements Initializable {
     
    private ArrayList<Button> tableButtons;
    private ArrayList<Button> itemButtons;
    private boolean menuOpen;

    @FXML
    private ImageView hambMenu;
    @FXML
    private Button table1;
    @FXML
    private Button table2;
    @FXML
    private Button table3;
    @FXML
    private Button table4;
    @FXML
    private Button table5;
    @FXML
    private Button table6;
    @FXML
    private Button table7;
    @FXML
    private Button table8;
    @FXML
    private Button table9;
    @FXML
    private Button table10;
    @FXML
    private Button table11;
    @FXML
    private Button table12;
    @FXML
    private Button table13;
    @FXML
    private AnchorPane slider;
    @FXML
    private ImageView editTables;
    @FXML
    private Label tableName;
    @FXML
    private ImageView editOrder;
    @FXML
    private Button selectAll;
    @FXML
    private AnchorPane popupTable;
    @FXML
    private ImageView addOrder;
    @FXML
    private Button charge;
    @FXML
    private VBox itemContainer;
    @FXML
    private AnchorPane dimmer;
    @FXML
    private ImageView add;
    @FXML
    private AnchorPane popupOrder;
    @FXML
    private ImageView search;
    @FXML
    private HBox flowContainer;
    @FXML
    private Label tableNameBuild;
    @FXML
    private ImageView editOrderBuild;
    @FXML
    private GridPane menuGrid;
    @FXML
    private Button confirm;
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
        else if (event.getSource() == dimmer){
            popupTable.setVisible(false);
            popupOrder.setVisible(false);
            dimmer.setVisible(false);
        }
        else if (event.getSource() == addOrder){
            popupOrder.setVisible(true);
            dimmer.setVisible(true);
            setCategories(null);
            setFood(null);
            setFlow(); 
        }
        for (Button table : tableButtons){
            if (event.getSource() == table){
                if(menuOpen){
                    slideClose(slider);
                    menuOpen = false;
                }
                popupTable.setVisible(true);
                dimmer.setVisible(true);
                setTableOrder(table.getText());
            }
        }
        for (Button item : itemButtons){
            if (event.getSource() == item){
                setCategories(item.getText());
                setFood(item.getText());
            }
        }
        // SIDE MENU
        if (event.getSource() == sideMenuTable)
            tablesShow(event);
        else if (event.getSource() == sideMenuMenu)
            menuShow(event);
    }
    
    private void setTableOrder(String tableName){
        Order tableOrder = OrderManager.getTableOrders(tableName, false);
        // catch error wrong name
        
        itemContainer.getChildren().clear();
        this.tableName.setText(tableOrder.tableName);
        for (int i = 0; i < tableOrder.items.size(); i++){
            FXMLLoader loader = new FXMLLoader();
            try{
                loader.setLocation(getClass().getResource("/View/Items/TableItemView.fxml"));
                HBox hbox = loader.load();
                TableItemController itemController = loader.getController();
                itemController.setData(tableOrder.items.get(i));
                itemContainer.getChildren().add(hbox);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
    
    private void setCategories(String category){
        ArrayList<Pair<String, String>> categories = new ArrayList<>();
        if (category == null){
            //TEMP
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
        } else {
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
            categories.add(new Pair("", "Prueba"));
        }     
        menuGrid.getChildren().clear();
        itemButtons = new ArrayList<>();
        this.tableNameBuild.setText(tableName.getText());
        int col = 0;
        int row = 0;
        for (int i = 0; i < categories.size(); i++){
            FXMLLoader loader = new FXMLLoader();
            if (col == 4){
                col = 0;
                row++;
            }
            try{
                loader.setLocation(getClass().getResource("/View/Items/MenuItemView.fxml"));
                AnchorPane pane = loader.load();
                MenuItemController itemController = loader.getController();
                itemController.setData(categories.get(i).getKey(), categories.get(i).getValue());
                menuGrid.add(pane, col++, row);
                GridPane.setMargin(pane, new Insets(10));
                itemController.getButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            btnHandle(event);
                        } catch (IOException ex) {
                            System.err.println(ex);
                        }
                    }
                });
                itemButtons.add(itemController.getButton());
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }

    }
    
    private void setFood(String category){
    }
    
    private void setFlow(){   
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        slideClose(slider);
        menuOpen = false;
        popupTable.setVisible(false);
        popupOrder.setVisible(false);
        dimmer.setVisible(false);
        itemButtons = new ArrayList<>();
        tableButtons = new ArrayList<>();
        tableButtons.add(table1);
        tableButtons.add(table2);
        tableButtons.add(table3);
        tableButtons.add(table4);
        tableButtons.add(table5);
        tableButtons.add(table6);
        tableButtons.add(table7);
        tableButtons.add(table8);
        tableButtons.add(table9);
        tableButtons.add(table10);
        tableButtons.add(table11);
        tableButtons.add(table12);
        tableButtons.add(table13);
        
        for (int i = 0; i < tableButtons.size(); i++){
            tableButtons.get(i).setVisible(false);
        }
        
        ArrayList<String> tableNames = TableManager.getTableNames();
        for (int i = 0; i < tableNames.size(); i++){
            tableButtons.get(i).setText(tableNames.get(i));
            tableButtons.get(i).setVisible(true);
        }
        
    }
}
