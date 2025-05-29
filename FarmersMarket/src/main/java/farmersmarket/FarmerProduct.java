package farmersmarket;

/**
 * This is an associative class between Farmer and Product
 *
 */
public class FarmerProduct {
	
  private String productName;
  private String farmerEmail;
  private float price;
  private int stock;
  
  /**
   * Constructor for objects of class FarmerProduct
   *
   * @param farmerEmail
   * @param productName
   * @param price
   * @param stock
   */

  public FarmerProduct(String farmerEmail, String productName, float price, int stock) {
    this.farmerEmail = farmerEmail;
    this.productName = productName;
    this.price = price;
    this.stock = stock;
  }
  
  
  /**
   * @return farmerEmail
   */

  public String getFarmerEmail() {
    return farmerEmail;
  }
  
  /**
   * @return productName
   */

  public String getProductName() {
    return productName;
  }
  
  /**
   * @return price
   */

  public float getPrice() {
    return price;
  }
  
  /**
   * @return stock
   */

  public int getStock() {
    return stock;
  }

  @Override
  public String toString() {
    return "FarmerProduct [productName=" + productName + ", price=" + price + ", stock=" + stock + "]";
  }
  
  /**
   * This method allows for the stock to be updated by the farmers
   */

  public void addStock(int num) {
    stock += num;
  }

}
