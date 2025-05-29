package farmersmarket;

/**
 * This class describes the the different categories of products sold at Farmers Market
 */

public enum Category {
	
  FRUIT("Fruit"),
  VEGETABLE("Vegetable"),
  CEREAL("Cereal");

  private final String category;
  
  /**
   * Constructor of class Category
   */

  Category(String category) {
    this.category = category;
  }

  public String toString() {
    return category;
  }
  

  /**
   * This method converts a given string into the corresponding Category enum constant, by comparing the input with each category's name.
   */
  public static Category fromString(String text) {
    for (Category c : Category.values()) {
      if (c.category.equalsIgnoreCase(text)) {
        return c;
      }
    }
    return null;
  }

}
