package farmersmarket;

import java.util.ArrayList;
import java.util.Locale.Category;

/**
 * This class describes a model of a Product 
 *
 */
public class Product {

  private String productName;
  private String image; // idk what type to use here yet
  private int id;
  private Category category;
  private ArrayList<FarmerProduct> productFarmer;

  /**
   * Constructor for objects of the class Product
   *
   * @param productName 
   * @param image 
   * @param id 
   * @param category 
   */
  public Product(String productName, String image, int id, Category category) {
    this.productName = productName;
    this.image = image;
    this.id = id;
    this.category = category;
  }

}
