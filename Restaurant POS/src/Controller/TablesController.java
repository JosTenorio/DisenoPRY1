
package Controller;

import Controller.Items.TableItemController;
import Model.Item;
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
     
    private ArrayList<String> tableNames;
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
    private AnchorPane popUp;
    @FXML
    private ImageView add;
    @FXML
    private ImageView addOrder;
    @FXML
    private Button charge;
    @FXML
    private VBox itemContainer;

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
        if (event.getSource() == table1){
            if(menuOpen){
                slideClose(slider);
                menuOpen = false;
            }
            popUp.setVisible(true);
            setTableOrder(table1.getText());
        }
    }
    
    private void setTableOrder(String tableName){
        //TEMP
        ArrayList<String> subItems = new ArrayList<>();
        subItems.add("Papas Fritas");
        subItems.add("Ensalada");
        ArrayList<Item> list = new ArrayList<>();
        list.add(new Item("Carne de Res", subItems, 8000, true, null));
        list.add(new Item("Carne de Cerdo", subItems, 8000, false, null));
        Order tableOrder = new Order("Mesa 1", list);
        
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
        popUp.setVisible(false);
        // Populate table names
    }
}
