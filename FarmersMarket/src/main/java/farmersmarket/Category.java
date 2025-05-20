package farmersmarket;

/**
 * Lists the different categories of products
 */
public enum Category {
  FRUIT("Fruit"),
  VEGETABLE("Vegetable");
  
  private final String category;

  Category(String category) {
    this.category = category;
  }

  public String getCategory() {
    return category;

  }  
}
