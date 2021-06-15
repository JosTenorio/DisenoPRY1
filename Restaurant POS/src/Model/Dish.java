
package Model;

public class Dish {
    
    public String name;
    public String imgPath;
    public String description;
    public boolean isSideDish;
    public int sideDishes;
    public double price;
    public boolean isArchived;

    public Dish(String name, String imgPath, String description, boolean isSideDish, int sideDishes, double price, boolean isArchived) {
        this.name = name;
        this.imgPath = imgPath;
        this.description = description;
        this.isSideDish = isSideDish;
        this.sideDishes = sideDishes;
        this.price = price;
        this.isArchived = isArchived;
    }
    
    public Dish(String name, String imgPath, String description, boolean isSideDish, double price, boolean isArchived) {
        this.name = name;
        this.imgPath = imgPath;
        this.description = description;
        this.isSideDish = isSideDish;
        this.price = price;
        this.isArchived = isArchived;
    }
}
