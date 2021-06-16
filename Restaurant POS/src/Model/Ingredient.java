package Model;


public class Ingredient {
    
    public String name;
    public String description;
    public int minimum;
    public int quantity;
    public String measurement;
    public String imgPath;
    
    public Ingredient (String name, String description, int minimum, int quantity, String measurement, String imgPath) {
        this.name = name;
        this.description = description;
        this.minimum = minimum;
        this.quantity = quantity;
        this.measurement = measurement;
        this.imgPath = imgPath;
    }
    
    
    
    
}
