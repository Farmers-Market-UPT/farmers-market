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
   * @param farmerEmail 
   * @param price 
   * @param stock 
   */
  public void addFarmer(String farmerEmail, float price, int stock) {
    productFarmers.add(new FarmerProduct(farmerEmail, productName, price, stock));
  }
  
  /**
   * @return the productName
   */

  public String getName() {
    return productName;
  }
  
  /**
   * @return the category
   */

  public Category getCategory() {
    return category;
  }


public FarmerProduct[] getFarmerProducts() {
	return null;
}

  public HashSet<FarmerProduct> getProductFarmers() {
    return productFarmers;
  }
  


}
