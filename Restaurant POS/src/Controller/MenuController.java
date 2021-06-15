
package Controller;

import Controller.Abstract.CategoryController;
import Model.Dish;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class MenuController extends CategoryController implements Initializable {
    
    private boolean menuOpen;
    private boolean dishCardOpen;
    private boolean editMode;
    private boolean isNewDish;

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
    private HBox flowContainer;
    @FXML
    private Label dishName;
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
    private ImageView addDish;
    @FXML
    private AnchorPane dishCardEdit;
    @FXML
    private TextField dishNameInput;
    @FXML
    private AnchorPane addPane;
    @FXML
    private Button archive;
    @FXML
    private ImageView dishImageEdit;
    @FXML
    private TextField dishDescInput;
    @FXML
    private CheckBox dishIsSide;
    @FXML
    private Spinner<Integer> dishSidesInput;
    @FXML
    private Spinner<Double> dishPriceInput;
    @FXML
    private ImageView dishImageInput;
    
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
        else if (event.getSource() == editMenu){
            if (editMode){
                editMode = false;
                editMenu.setImage(new Image(getClass().getResourceAsStream("/Images/Edit.png")));
                addDish.setVisible(false);
                if (dishCardOpen){
                    dishCard.setVisible(true);
                    dishCardEdit.setVisible(false);
                }
            } else {
                editMode = true;
                editMenu.setImage(new Image(getClass().getResourceAsStream("/Images/ConfirmEdit.png")));
                addDish.setVisible(true);
                if (dishCardOpen){
                    dishCard.setVisible(false);
                    dishCardEdit.setVisible(true);
                }
            }
        }
        else if (event.getSource() == addDish){
            isNewDish = true;
            dishCardOpen = true;
            dishCardEdit.setVisible(true);
            emptyDishCardEdit();
        }
        else if (event.getSource() == confirm){
            if (isNewDish){
                Dish newDish = setDishInfo();
            } else {
                //send info to dishName
            }
        }
        else if (event.getSource() == archive){
            //send info and reset items
        }
        for (Button item : categoryButtons){
            if (event.getSource() == item){
                setFoodCategories(item.getText(), menuGrid, 3, 210.0, true);
                addButtonFunction(categoryButtons);
                addButtonFunction(foodButtons);
                //set Flow
            }
        }
        for (Button item : foodButtons){
            if (event.getSource() == item){
                if (dishName.getText().equals(item.getText()) && dishCardOpen){
                    dishCardOpen = false;
                    dishCard.setVisible(false);
                    dishCardEdit.setVisible(false);
                } else {
                    isNewDish = false;
                    dishCardOpen = true;
                    if (editMode){
                        dishCardEdit.setVisible(true);
                    } else {
                        dishCard.setVisible(true);
                    }
                    populateDishCard(item.getText());
                    populateDishCardEdit(item.getText());
                }
            }
        }
        // SIDE MENU
        if (event.getSource() == sideMenuTable)
            tablesShow(event);
        else if (event.getSource() == sideMenuMenu)
            menuShow(event);
        
    }
    
    private void populateDishCard(String name){
        dishName.setText(name);
        //get info for dish or sidedish
    }
    
    private void populateDishCardEdit(String name){
        dishNameInput.setText(name);
        //get info for dish or sidedish
    }
    
    private void emptyDishCardEdit(){
        dishNameInput.setText("");
        dishNameInput.setPromptText("Nombre del plato...");
        dishImageEdit.setImage(null);
        dishDescInput.setText("");
        dishDescInput.setPromptText("Descripción...");
        dishIsSide.setSelected(false);
        dishSidesInput.getValueFactory().setValue(0);
        dishPriceInput.getValueFactory().setValue(0.00);
    }
    
    private Dish setDishInfo(){
        String name = dishNameInput.getText();
        String path = "";
        String desc = dishDescInput.getText();
        boolean isSide = dishIsSide.isSelected();
        int sides = dishSidesInput.getValueFactory().getValue();
        double price = dishPriceInput.getValueFactory().getValue();
        if (isSide)
            return new Dish(name, path, desc, isSide, price);
        else
            return new Dish(name, path, desc, isSide, sides, price);
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
    public void initialize(URL url, ResourceBundle rb) {
        slideClose(slider);
        menuOpen = false;
        editMode = false;
        dishCardOpen = false;
        dishCard.setVisible(false);
        dishCardEdit.setVisible(false);
        addDish.setVisible(false);
        setFoodCategories(null, menuGrid, 3, 210.0, true);
        addButtonFunction(categoryButtons);
        addButtonFunction(foodButtons);
        editMenu.setImage(new Image(getClass().getResourceAsStream("/Images/Edit.png")));
        addPane.setPickOnBounds(false);
        setSpinners();
        //set Flow
    }    
    
    private void setSpinners(){
        SpinnerValueFactory<Integer> valueFactoryInt = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        dishSidesInput.setValueFactory(valueFactoryInt);
        SpinnerValueFactory<Double> valueFactoryDoub = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 100000.00);
        dishPriceInput.setValueFactory(valueFactoryDoub);
    }

    
}
