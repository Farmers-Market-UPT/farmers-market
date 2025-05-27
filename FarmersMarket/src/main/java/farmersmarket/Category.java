package farmersmarket;

/**
 * Lists the different categories of products
 */
public enum Category {
  FRUIT("Fruit"),
  VEGETABLE("Vegetable"),
  CEREAL("Cereal");

  private final String category;

  Category(String category) {
    this.category = category;
  }

  public String toString() {
    return category;
  }

  public static Category fromString(String text) {
    for (Category c : Category.values()) {
      if (c.category.equals(text)) {
        return c;
      }
    }
    return null;
  }

}
