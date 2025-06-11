package farmersmarket;

import java.util.HashSet;

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
   * This method adds a new farmer who is also selling this product
   *
   * @param productFarmer
   */
  public void addFarmer(FarmerProduct productFarmer) {
    productFarmers.add(productFarmer);
  }

  /**
   * This method returns the name of the product
   *
   * @return the product name
   */
  public String getName() {
    return productName;
  }

  /**
   * This method returns the category of the product
   *
   * @return the category
   */
  public Category getCategory() {
    return category;
  }

  /**
   * This method returns this product's farmers
   *
   * @return the productFarmer
   */
  public HashSet<FarmerProduct> getProductFarmers() {
    return productFarmers;
  }
}
