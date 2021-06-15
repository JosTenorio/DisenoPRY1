
package Model;

import java.util.ArrayList;

public class Item {
    
    public int number;
    public String name;
    public ArrayList<String> sideDishes;
    public String notes;
    public double cost;
    public boolean prepared;
    
    public Item(int number, String name, ArrayList<String> sideDishes, double cost, boolean prepared, String notes){
        this.number = number;
        this.name = name;
        this.sideDishes = sideDishes;
        this.cost = cost;
        this.prepared = prepared;
        this.notes = notes;
    }
    
}
    
