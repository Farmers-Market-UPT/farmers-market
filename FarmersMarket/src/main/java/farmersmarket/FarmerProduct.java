package farmersmarket;

/**
 * Associative class between Farmer and Product
 *
 */
public class FarmerProduct {
  private int productID;
  private String farmerEmail;
  private float price;
  // quant/weight

  // only added this constructor to avoid errors, this class still hasn't been done
  public FarmerProduct(int productID, String farmerEmail, float price) {
    this.productID = productID;
    this.farmerEmail = farmerEmail;
    this.price = price;
  }
  

}
