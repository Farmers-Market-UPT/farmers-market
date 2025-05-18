package farmersmarket;

import java.util.ArrayList;

/**
 * This class describes a model of a Product 
 *
 */
public class Product {

  private String productName;
  private int id;
  private Category category;
  private ArrayList<FarmerProduct> productFarmer;

  /**
   * Constructor for objects of the class Product
   *
   * @param productName 
   * @param id 
   * @param category 
   */
  public Product(String productName, int id, Category category) {
    this.productName = productName;
    this.id = id;
    this.category = category;
  }

}
