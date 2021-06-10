
package Model;

import java.util.ArrayList;

public class Item {
    
    public String name;
    public boolean isMainDish;
    public ArrayList<String> sideDishes;
    public String notes;
    
    public Item(String name, boolean isMainDish,ArrayList<String> sideDishes, String notes) {
        this.name = name;
        this.isMainDish = isMainDish;
        this.sideDishes = sideDishes;
        this.notes = notes;
    }
}
    
