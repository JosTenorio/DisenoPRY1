
package Controller;

import Controller.Abstract.CategoryController;
import Model.Dish;
import Model.Managers.FoodManager;
import java.io.File;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MenuController extends CategoryController implements Initializable {
    
    private boolean menuOpen;
    private boolean dishCardOpen;
    private boolean editMode;
    private boolean isNewDish;
    private String currentCategory;

    private String dishImgPath;
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
    private TextField dishSidesInput;
    @FXML
    private TextField dishPriceInput;
    @FXML
    private ImageView dishImageInput;
    @FXML
    private Button save;
    
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
        else if (event.getSource() == save){
            if (isNewDish && validInputs()){
                Dish newDish = setDishInfo();
                FoodManager.insertFood(newDish);
                // catch error wrong info
                FoodManager.categorizeFood(newDish.name, currentCategory);
                // catch error wrong info
                setFoodCategories(currentCategory, menuGrid, 3, 210.0, true, true);
                addButtonFunction(categoryButtons);
                addButtonFunction(itemButtons);
                Dish dish = FoodManager.getFoodDetails(newDish.name);
                // catch error dish not found
                populateDishCard(dish);
            } else if (!isNewDish && validInputs()) {
                Dish updateDish = setDishInfo();
                FoodManager.updateFood(dishName.getText(), updateDish);
                // catch error dish not found or wrong info
                setFoodCategories(currentCategory, menuGrid, 3, 210.0, true, true);
                addButtonFunction(categoryButtons);
                addButtonFunction(itemButtons);
                Dish dish = FoodManager.getFoodDetails(updateDish.name);
                // catch error dish not found
                populateDishCard(dish);
            }
        }
        else if (event.getSource() == archive){
            FoodManager.toggleFood(dishName.getText());
            // catch error not found
            setFoodCategories(currentCategory, menuGrid, 3, 210.0, true, true);
            addButtonFunction(categoryButtons);
            addButtonFunction(itemButtons);
            Dish dish = FoodManager.getFoodDetails(dishName.getText());
            // catch error dish not found
            toggleArchiveText(dish);
        }
        else if (event.getSource() == dishImageInput){
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(filter);
            chooser.showOpenDialog(null);
            if (chooser.getSelectedFile() != null) {
                File f = chooser.getSelectedFile();
                String path = f.getPath().replace("\\", "\\\\");
                String base = System.getProperty("user.dir").replace("\\", "\\\\");;
                String relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();
                dishImgPath = relative;
            }
        }
        for (Button item : categoryButtons){
            if (event.getSource() == item){
                setFoodCategories(item.getText(), menuGrid, 3, 210.0, true, true);
                addButtonFunction(categoryButtons);
                addButtonFunction(itemButtons);
                setFlow(item.getText());
            }
        }
        for (Button item : itemButtons){
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
                    Dish dish = FoodManager.getFoodDetails(item.getText());
                    // catch error dish not found
                    populateDishCard(dish);
                    populateDishCardEdit(dish);
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
        
    }
    
    private void populateDishCard(Dish dish){
        dishName.setText(dish.name);
        try {
            System.out.println(dish.imgPath);
            dishImage.setImage(new Image(getClass().getResourceAsStream(dish.imgPath)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        dishDesc.setText(dish.description);
        dishSides.setText("Inlcuye " + dish.sideDishes + " acompañamientos");
        dishPrice.setText("₡" + dish.price);
        toggleArchiveText(dish);
    }
    
    private void toggleArchiveText(Dish dish){
        if (dish.isArchived)
            archive.setText("Desarchivar");
        else
            archive.setText("Archivar");
    }
    
    private void populateDishCardEdit(Dish dish){
        dishNameInput.setText(dish.name);
        try {
            dishImageEdit.setImage(new Image(getClass().getResourceAsStream(dish.imgPath)));
        } catch (Exception e) {
        }
        dishDescInput.setText(dish.description);
        dishIsSide.setSelected(dish.isSideDish);
        dishSidesInput.setText(String.valueOf(dish.sideDishes));
        dishPriceInput.setText(String.valueOf(dish.price));
        dishImgPath = dish.imgPath;
    }
    
    private void emptyDishCardEdit(){
        dishNameInput.setText("");
        dishNameInput.setPromptText("Nombre del plato...");
        dishImageEdit.setImage(null);
        dishDescInput.setText("");
        dishDescInput.setPromptText("Descripción...");
        dishIsSide.setSelected(false);
        dishSidesInput.setText("");
        dishPriceInput.setText("");
        dishImgPath = "";
    }
    
    private Dish setDishInfo(){
        String name = dishNameInput.getText();
        String path = dishImgPath;
        String desc = dishDescInput.getText();
        boolean isSide = dishIsSide.isSelected();
        int sides = Integer.valueOf(dishSidesInput.getText());
        double price = Double.valueOf(dishPriceInput.getText());  
        if (isSide)
            return new Dish(name, path, desc, isSide, price, false);
        else
            return new Dish(name, path, desc, isSide, sides, price, false);
    }
    
    private boolean validInputs(){
        if ("".equals(dishNameInput.getText()))
            return false;
        try {
            Integer.valueOf(dishSidesInput.getText());
            Double.valueOf(dishPriceInput.getText());
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
    
    private void setFlow(String category){
        // set flow menu to navigate
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
    public void initialize(URL url, ResourceBundle rb) {
        slideClose(slider);
        menuOpen = false;
        editMode = false;
        dishCardOpen = false;
        dishCard.setVisible(false);
        dishCardEdit.setVisible(false);
        addDish.setVisible(false);
        setFoodCategories(null, menuGrid, 3, 210.0, true, true);
        addButtonFunction(categoryButtons);
        addButtonFunction(itemButtons);
        editMenu.setImage(new Image(getClass().getResourceAsStream("/Images/Edit.png")));
        addPane.setPickOnBounds(false);
        setFlow(null);
    }     
}
