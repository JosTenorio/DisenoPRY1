
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
}
