/**
 * 
 */
package farmersmarket;

/**
 * This Class describes the details of a specific item from a purchase
 */
public class CartItem {

  private FarmerProduct product;
  private double priceAtPurchase;
  private int quantity;

  /**
   * @param price
   * @param quantity
   * 
   *                 This method is the constructor for class CartItem.
   */
  public CartItem(FarmerProduct product, int quantity) {
    this.product = product;
    this.quantity = quantity;
    priceAtPurchase = product.getPrice();
  }

  public String toString() {
    return "Product: " + product.getProductName() + " | Price: " + String.format("%.2f", priceAtPurchase)
        + "€ | Quantity: " + quantity + " | Seller: " + product.getFarmer().getName();
  }

  public double getPriceAtPurchase() {
    return priceAtPurchase;
  }

  public void setPriceAtPurchase(double price) {
    priceAtPurchase = price;
  }

  /**
   * @return the quantity
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * @param quantity the quantity to set
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * This method calculates the price per item based on quantity.
   */

  public double getTotalPrice() {
    return priceAtPurchase * quantity;
  }

  /**
   * This method allows an object FarmerProduct to be accessed in order to obtain
   * details such as name, price or category.
   */

  public FarmerProduct getProduct() {
    return product;
  }

}
