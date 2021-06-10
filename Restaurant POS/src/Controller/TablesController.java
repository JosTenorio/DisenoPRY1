
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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
    private void btnHandle(MouseEvent event) throws IOException {
        if (event.getSource() == hambMenu){
            if (!menuOpen){
                slideOpen(slider);
                menuOpen = true;
            }
            else{
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
