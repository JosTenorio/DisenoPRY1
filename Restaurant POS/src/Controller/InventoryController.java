
package Controller;

import Controller.Abstract.CategoryController;
import Model.Ingredient;
import Model.Managers.IngredientManager;
import Model.Managers.TableManager;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class InventoryController extends CategoryController implements Initializable {
    
    private boolean menuOpen;
    private boolean ingCardOpen;
    private boolean editMode;
    private boolean isNewIng;
    private String currentCategory;

    private String ingImgPath;
    private String ingUnitValue;
    private double ingQuantityValue;
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
    private TextField ingNameInput;
    @FXML
    private ImageView ingImageInput;
    @FXML
    private ImageView ingImageEdit;
    @FXML
    private TextField ingDescInput;
    @FXML
    private TextField ingNotifyInput;
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
    private Label ingQuantity;
    @FXML
    private TextField ingQuantityInput;
    @FXML
    private Button save;
    @FXML
    private AnchorPane ingCardUpdate;
    @FXML
    private Label ingNameUpdate;
    @FXML
    private ImageView ingImageUpdate;
    @FXML
    private Button updateSave;
    @FXML
    private Label ingDescUpdate;
    @FXML
    private TextField ingQuantityUpdate;
    @FXML
    private Label ingNotifyUpdate;
    @FXML
    private Label ingUnitUpdate;

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
        else if (event.getSource() == editInventory){
            if (editMode){
                editMode = false;
                editInventory.setImage(new Image(getClass().getResourceAsStream("/Images/Edit.png")));
                addIng.setVisible(false);
                if (ingCardOpen){
                    ingCard.setVisible(true);
                    ingCardEdit.setVisible(false);
                }
            } else {
                ingCardUpdate.setVisible(false);
                editMode = true;
                editInventory.setImage(new Image(getClass().getResourceAsStream("/Images/ConfirmEdit.png")));
                addIng.setVisible(true);
                if (ingCardOpen){
                    ingCard.setVisible(false);
                    ingCardEdit.setVisible(true);
                }
            }
        }
        else if (event.getSource() == addIng){
            isNewIng = true;
            ingCardOpen = true;
            ingCardEdit.setVisible(true);
            emptyIngCardEdit();
        }
        else if (event.getSource() == save){
            if (isNewIng && validInputs()){
                Ingredient newIng = setIngInfo();
                IngredientManager.insertIngredient(newIng);
                // catch error wrong info
                IngredientManager.categorizeIng(newIng.name, currentCategory);
                // catch error wrong info
                setIngCategories(currentCategory, ingGrid, 3, 210.0);
                addButtonFunction(categoryButtons);
                addButtonFunction(itemButtons);
                Ingredient ingredient = IngredientManager.getIngredientDetails(newIng.name);
                // catch error ingredient not found
                populateIngCard(ingredient);
            } else if (!isNewIng && validInputs()) {
                Ingredient updateIng = setIngInfo();
                IngredientManager.updateIngredient(ingName.getText(), updateIng);
                // catch error ingredient not found or wrong info
                setIngCategories(currentCategory, ingGrid, 3, 210.0);
                addButtonFunction(categoryButtons);
                addButtonFunction(itemButtons);
                Ingredient ingredient = IngredientManager.getIngredientDetails(updateIng.name);
                // catch error ingredient not found
                populateIngCard(ingredient);
            }
        }
        else if (event.getSource() == update){
            ingCard.setVisible(false);
            ingCardUpdate.setVisible(true);
            populateIngCardUpdate();
        }
        else if (event.getSource() == updateSave){
            if (validUpdate()){
                IngredientManager.updateIngQuantity(ingName.getText(), Double.valueOf(ingQuantityUpdate.getText()));
                // catch error ingredient not found
                Ingredient ingredient = IngredientManager.getIngredientDetails(ingName.getText());
                // catch error ingredient not found
                populateIngCard(ingredient);
                ingCard.setVisible(true);
                ingCardUpdate.setVisible(false);
            }
        }
        else if (event.getSource() == ingImageInput){
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(filter);
            chooser.showOpenDialog(null);
            if (chooser.getSelectedFile() != null) {
                File f = chooser.getSelectedFile();
                String path = f.getPath().replace("\\", "\\\\");
                String base = System.getProperty("user.dir").replace("\\", "\\\\");;
                String relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();
                ingImgPath = relative;
            }
        }
        for (Button item : categoryButtons){
            if (event.getSource() == item){
                setIngCategories(item.getText(), ingGrid, 3, 210.0);
                addButtonFunction(categoryButtons);
                addButtonFunction(itemButtons);
                setFlow(item.getText());
            }
        }
        for (Button item : itemButtons){
            if (event.getSource() == item){
                ingCardUpdate.setVisible(false);
                if (ingName.getText().equals(item.getText()) && ingCardOpen){
                    ingCardOpen = false;
                    ingCard.setVisible(false);
                    ingCardEdit.setVisible(false);
                } else {
                    isNewIng = false;
                    ingCardOpen = true;
                    if (editMode){
                        ingCardEdit.setVisible(true);
                    } else {
                        ingCard.setVisible(true);
                    }
                    Ingredient ingredient = IngredientManager.getIngredientDetails(item.getText());
                    // catch error ingredient not found
                    populateIngCard(ingredient);
                    populateIngCardEdit(ingredient);
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
    }
    
    private void populateIngCard(Ingredient ingredient){
        ingName.setText(ingredient.name);
        try {
            Image img = new Image(new File(ingredient.imgPath).toURI().toURL().toString());
            ingImage.setImage(img);
        } catch (Exception e) {
        }
        ingDesc.setText(ingredient.description);
        ingNotify.setText("Notificar con: " + ingredient.minimum + ingredient.unit);
        ingQuantity.setText("Cantidad: " + ingredient.quantity + ingredient.unit);
        ingQuantityValue = ingredient.quantity;
        ingUnitValue = ingredient.unit; 
    }
    
    private void populateIngCardEdit(Ingredient ingredient){
        ingNameInput.setText(ingredient.name);
        try {
            Image img = new Image(new File(ingredient.imgPath).toURI().toURL().toString());
            ingImageEdit.setImage(img);
        } catch (Exception e) {
        }
        ingDescInput.setText(ingredient.description);
        ingUnitInput.setText(ingredient.unit);
        ingNotifyInput.setText(String.valueOf(ingredient.minimum));
        ingQuantityInput.setText(String.valueOf(ingredient.quantity));
        ingImgPath = ingredient.imgPath;
    }
    
    private void populateIngCardUpdate(){
        ingNameUpdate.setText(ingName.getText());
        try {
            ingImageUpdate.setImage(ingImage.getImage());
        } catch (Exception e) {
        }
        ingDescUpdate.setText(ingDesc.getText());
        ingNotifyUpdate.setText(ingNotify.getText());
        ingQuantityUpdate.setText(String.valueOf(ingQuantityValue));
        ingUnitUpdate.setText(ingUnitValue);
    }
    
   private void emptyIngCardEdit(){
        ingNameInput.setText("");
        ingNameInput.setPromptText("Nombre del ingrediente...");
        ingImageEdit.setImage(null);
        ingDescInput.setText("");
        ingDescInput.setPromptText("Descripción...");
        ingUnitInput.setText("");
        ingNotifyInput.setText("");
        ingQuantityInput.setText("");
        ingImgPath = "";
    }
   
    private Ingredient setIngInfo(){
        String name = ingNameInput.getText();
        String path = ingImgPath;
        String desc = ingDescInput.getText();
        String unit = ingUnitInput.getText();
        double minimum = Double.valueOf(ingNotifyInput.getText());
        double quantity = Double.valueOf(ingQuantityInput.getText()); 
        return new Ingredient(name, desc, minimum, quantity, unit, path);
    }
   
    private boolean validInputs(){
        if ("".equals(ingNameInput.getText()))
            return false;
        try {
            Double.valueOf(ingNotifyInput.getText());
            Double.valueOf(ingQuantityInput.getText());
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
    
    private boolean validUpdate(){
        try {
            Double.valueOf(ingQuantityUpdate.getText());
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
        ingCardUpdate.setVisible(false);
        addIng.setVisible(false);
        setIngCategories(null, ingGrid, 3, 210.0);
        addButtonFunction(categoryButtons);
        addButtonFunction(itemButtons);
        editInventory.setImage(new Image(getClass().getResourceAsStream("/Images/Edit.png")));
        addPane.setPickOnBounds(false);
        setFlow(null);
    }
    
}
