
package Controller;

import Controller.Items.TableItemController;
import Model.Item;
import Model.Managers.TableManager;
import Model.Order;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TablesController extends SceneController implements Initializable {
     
    private ArrayList<Button> tableButtons;
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
        if (event.getSource() == dimmer){
            popupTable.setVisible(false);
            popupOrder.setVisible(false);
            dimmer.setVisible(false);
        }
        if (event.getSource() == addOrder){
            popupOrder.setVisible(true);
            dimmer.setVisible(true);
        }
    }
    
    private void setTableOrder(String tableName){
        //TEMP
        ArrayList<String> subItems = new ArrayList<>();
        subItems.add("Papas Fritas");
        subItems.add("Ensalada");
        ArrayList<Item> list = new ArrayList<>();
        list.add(new Item("Carne de Res", subItems, 8000, true, "Notas notas notas"));
        list.add(new Item("Carne de Cerdo", subItems, 8000, false, null));
        Order tableOrder = new Order("Mesa 1", list);
        
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
                System.err.println(ex.toString());
            }
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        resetSlide(slider);
        menuOpen = false;
        popupTable.setVisible(false);
        popupOrder.setVisible(false);
        dimmer.setVisible(false);
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
