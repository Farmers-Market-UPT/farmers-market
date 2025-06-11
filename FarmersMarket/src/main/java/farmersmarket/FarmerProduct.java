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

  /**
   * This method changes the price of the product
   *
   * @param price 
   */
  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * This method returns the product's seller
   *
   * @return the farmer
   */
  public Farmer getFarmer() {
    return farmer;
  }

  /**
   * This method returns the name of the product
   *
   * @return the product name
   */
  public String getProductName() {
    return productName;
  }

  /**
   * This method returns the price of the product
   *
   * @return the product price
   */
  public double getPrice() {
    return price;
  }

  /**
   * This method returns the stock of the product
   *
   * @return the product stock
   */
  public int getStock() {
    return stock;
  }

  @Override
  public String toString() {
    return productName + " | Price: " + String.format("%.2f", price)+ "€ | Stock: " + stock;
  }

  /**
   * This method allows for the stock to be updated by the farmers
   *
   */
  public void setStock(int num) {
    stock = num;
  }

  /**
   * This method reduces the stock of a product
   *
   * @param num 
   */
  public void reduceStock(int num) {
    stock -= num;
  }
  

}
