package farmersmarket;

/**
 * This is an associative class between Farmer and Product
 *
 */
public class FarmerProduct {

  private String productName;
  private Farmer farmer;
  private double price;
  private int stock;

  /**
   * Constructor for objects of class FarmerProduct
   *
   * @param farmer
   * @param productName
   * @param price
   * @param stock
   */

  public FarmerProduct(Farmer farmer, String productName, float price, int stock) {
    this.farmer = farmer;
    this.productName = productName;
    this.price = price;
    this.stock = stock;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public Farmer getFarmer() {
    return farmer;
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
  public double getPrice() {
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
    return productName + " | Price: " + String.format("%.2f", price)+ "€ | Stock: " + stock + " | Seller: " + farmer.getName(); 
  }

  /**
   * This method allows for the stock to be updated by the farmers
   */
  public void setStock(int num) {
    stock = num;
  }

  public void reduceStock(int num) {
    stock -= num;
  }

}
