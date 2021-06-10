
package Model;

import java.util.ArrayList;

public class Item {
    
    public String name;
    public ArrayList<String> sideDishes;
    public String notes;
    public double cost;
    public boolean prepared;
    
    public Item(String name, ArrayList<String> sideDishes, double cost, boolean prepared, String notes){
        this.name = name;
        this.sideDishes = sideDishes;
        this.cost = cost;
        this.prepared = prepared;
        this.notes = notes;
    }
    
}
