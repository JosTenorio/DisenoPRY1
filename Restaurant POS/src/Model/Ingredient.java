
package Model;

public class Ingredient {
    
    public String name;
    public String description;
    public double minimum;
    public double quantity;
    public String unit;
    public String imgPath;
    
    public Ingredient (String name, String description, double minimum, double quantity, String unit, String imgPath) {
        this.name = name;
        this.description = description;
        this.minimum = minimum;
        this.quantity = quantity;
        this.unit = unit;
        this.imgPath = imgPath;
    }
    
    
    
    
}
