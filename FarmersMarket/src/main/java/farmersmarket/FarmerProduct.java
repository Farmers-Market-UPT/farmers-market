package farmersmarket;

/**
 * Associative class between Farmer and Product
 *
 */
public class FarmerProduct {
  private String productName;
  private String farmerEmail;
  private float price;
  private int stock;

  public FarmerProduct(String farmerEmail, String productName, float price, int stock) {
    this.farmerEmail = farmerEmail;
    this.productName = productName;
    this.price = price;
    this.stock = stock;
  }
  

  public String getFarmerEmail() {
    return farmerEmail;
  }

  public String getProductName() {
    return productName;
  }

}
