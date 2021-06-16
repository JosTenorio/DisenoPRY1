
package Model;

public class Ingredient {
    
    public String name;
    public String description;
    public int minimum;
    public int quantity;
    public String unit;
    public String imgPath;
    
    public Ingredient (String name, String description, int minimum, int quantity, String unit, String imgPath) {
        this.name = name;
        this.description = description;
        this.minimum = minimum;
        this.quantity = quantity;
        this.unit = unit;
        this.imgPath = imgPath;
    }
    
    
    
    
}
