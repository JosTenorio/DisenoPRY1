
package Model;

import java.util.ArrayList;

public class Order {
    
    public String tableName;
    public ArrayList<Item> items;
    public String age;

    public Order(String tableName, ArrayList<Item> items){
        this.tableName = tableName;
        this.items = items;
    }
    
    public Order(String tableName, ArrayList<Item> items, String age){
        this.tableName = tableName;
        this.items = items;
        this.age = age;
    }
    
    public Order(String tableName){
        this.tableName = tableName;
        this.items = new ArrayList<>();
    }
    
    public void addItem(String name, String notes){
        items.add(new Item(name, notes));
    }
    
    public void addSide(String name){
        if (items.get(items.size()-1).sideDishes == null){
            items.get(items.size()-1).sideDishes = new ArrayList<>();
        }
        items.get(items.size()-1).sideDishes.add(name);
    }
}
