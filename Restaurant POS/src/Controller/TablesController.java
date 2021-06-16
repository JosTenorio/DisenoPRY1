
package Controller;

import Controller.Abstract.CategoryController;
import Controller.Items.OrderBuildItemController;
import Controller.Items.TableItemController;
import Model.Managers.FoodManager;
import Model.Managers.OrderManager;
import Model.Managers.TableManager;
import Model.Order;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TablesController extends CategoryController implements Initializable {
     
    private ArrayList<Button> tableButtons;
    private boolean menuOpen;
    private Order orderBuild;
    private String selectedTable;
    private int sidesQuantity;
    private int sidesAdded;
    private String currentCategory;
    private Stack<String> categorySequence;

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
    private AnchorPane popupOrder;
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
    private VBox orderContainer;
    @FXML
    private ImageView back;

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
            orderBuild = new Order(selectedTable);
            popupOrder.setVisible(true);
            dimmer.setVisible(true);
            setFoodCategories(null, menuGrid, 4, 182.0, false, true);
            addButtonFunction(categoryButtons);
            addButtonFunction(itemButtons);
            sidesAdded = 0;
            sidesQuantity = 0;
            categorySequence = new Stack<>();
            setFlow(null, true);
        }
        else if (event.getSource() == confirm){
            OrderManager.insertOrder(orderBuild);
            popupTable.setVisible(true);
            popupOrder.setVisible(false);
            setTableOrder(selectedTable);
        }
        else if (event.getSource() == back){
            String newCategory;
            try {
                newCategory = categorySequence.pop();
                newCategory = categorySequence.pop();
            } catch (Exception e) {
                newCategory = null;
            }
            if (sidesAdded < sidesQuantity){
                setFoodCategories(newCategory, menuGrid, 4, 182.0, false, false);
                addButtonFunction(categoryButtons);
                addButtonFunction(itemButtons);
            } else {
                setFoodCategories(newCategory, menuGrid, 4, 182.0, false, true);
                addButtonFunction(categoryButtons);
                addButtonFunction(itemButtons);
            }
            setFlow(newCategory, false);
        }
        for (Button table : tableButtons){
            if (event.getSource() == table){
                if(menuOpen){
                    slideClose(slider);
                    menuOpen = false;
                }
                popupTable.setVisible(true);
                dimmer.setVisible(true);
                selectedTable = table.getText();
                setTableOrder(selectedTable);
            }
        }
        for (Button item : categoryButtons){
            if (event.getSource() == item){
                if (sidesAdded < sidesQuantity){
                    setFoodCategories(item.getText(), menuGrid, 4, 182.0, false, false);
                    addButtonFunction(categoryButtons);
                    addButtonFunction(itemButtons);
                } else {
                    setFoodCategories(item.getText(), menuGrid, 4, 182.0, false, true);
                    addButtonFunction(categoryButtons);
                    addButtonFunction(itemButtons);
                }
                setFlow(item.getText(), true);
            }
        }
        for (Button item : itemButtons){
            if (event.getSource() == item){
                if (sidesAdded < sidesQuantity){
                    orderBuild.addSide(item.getText());
                    setBuildOrder(orderBuild);
                    sidesAdded++;
                } else {
                    // get notes from button
                    orderBuild.addItem(item.getText(), "");
                    setBuildOrder(orderBuild);
                    sidesAdded = 0;
                    sidesQuantity = FoodManager.getFoodSideDishes(item.getText());
                }
                if (sidesAdded < sidesQuantity){
                    setFoodCategories(currentCategory, menuGrid, 4, 182.0, false, false);
                    addButtonFunction(categoryButtons);
                    addButtonFunction(itemButtons);
                } else {
                    setFoodCategories(currentCategory, menuGrid, 4, 182.0, false, true);
                    addButtonFunction(categoryButtons);
                    addButtonFunction(itemButtons);
                }
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
    
    private void setTableOrder(String tableName){
        Order tableOrder = OrderManager.getTableOrder(tableName, false);
        // catch error wrong name
        
        itemContainer.getChildren().clear();
        this.tableName.setText(tableOrder.tableName);
        this.tableNameBuild.setText(tableOrder.tableName);
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
    
    private void setBuildOrder(Order orderBuild){
        orderContainer.getChildren().clear();
        for (int i = 0; i < orderBuild.items.size(); i++){
            FXMLLoader loader = new FXMLLoader();
            try{
                loader.setLocation(getClass().getResource("/View/Items/OrderBuildItemView.fxml"));
                HBox hbox = loader.load();
                OrderBuildItemController itemController = loader.getController();
                itemController.setData(orderBuild.items.get(i));
                orderContainer.getChildren().add(hbox);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
    
    private void setFlow(String category, boolean forward){
        if (forward) {
            categorySequence.push(category);
        }
        currentCategory = category;
    }
    
    @Override
    public void addButtonFunction(ArrayList<Button> buttons){
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
        popupTable.setVisible(false);
        popupOrder.setVisible(false);
        dimmer.setVisible(false);
        categoryButtons = new ArrayList<>();
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
        setTableNames();
    }
    
    private void setTableNames(){
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
