
package Model;

public class Dish {
    
    public String name;
    public String imgPath;
    public String description;
    public boolean isSideDish;
    public int sideDishes;
    public double price;

    public Dish(String name, String imgPath, String description, boolean isSideDish, int sideDishes, double price) {
        this.name = name;
        this.imgPath = imgPath;
        this.description = description;
        this.isSideDish = isSideDish;
        this.sideDishes = sideDishes;
        this.price = price;
    }
    
    public Dish(String name, String imgPath, String description, boolean isSideDish, double price) {
        this.name = name;
        this.imgPath = imgPath;
        this.description = description;
        this.isSideDish = isSideDish;
        this.price = price;
    }
}
