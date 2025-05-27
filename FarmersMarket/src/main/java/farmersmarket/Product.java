package farmersmarket;

import java.util.HashSet;

import javax.swing.text.StyledEditorKit.ForegroundAction;

/**
 * This class describes a model of a Product 
 *
 */
public class Product {

  private String productName;
  private Category category;
  private HashSet<FarmerProduct> productFarmers;

  /**
   * Constructor for objects of class Product
   *
   * @param productName 
   * @param category 
   */
  public Product(String productName, Category category) {
    this.productName = productName;
    this.category = category;
    productFarmers = new HashSet<>();
  }

  /**
   * Adds a new farmer that sells this product
   *
   * @param farmerEmail 
   * @param price 
   * @param stock 
   */
  public void addFarmer(String farmerEmail, float price, int stock) {
    productFarmers.add(new FarmerProduct(farmerEmail, productName, price, stock));
  }

  public String getName() {
    return productName;
  }

  public Category getCategory() {
    return category;
  }

}
