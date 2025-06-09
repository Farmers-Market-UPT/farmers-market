/**
 * 
 */
package farmersmarket;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class describes the objects of type Order
 */
public class Order {

  private ArrayList<CartItem> items;
  private LocalDate orderDate;
  private double total;

  /**
   * @param orderDate
   * @param total
   * @param paid
   * 
   *                  This method is the constructor from class Order
   */
  public Order() {
    items = new ArrayList<>();
  }

  /**
   * This method allows add new items to the cart or alter the quantity of
   * previously added item
   */
  public void addCartItem(FarmerProduct product, int quant) {
    for (CartItem item : items) {
      if (item.getProduct().getProductName().equals(product.getProductName())) {
        item.setQuantity(quant);
        return;
      }
    }
    items.add(new CartItem(product, quant));
  }

  public String toString() {
    if (orderDate == null) {
      return "Items: " + items + ", Total: " + total;
    } else {
      return "Date: " + orderDate + ", Items: " + items + ", Total: " + total;
    }
  }

  /**
   * This method calculates the total of an order
   */
  public void calculateTotal() {
    for (CartItem item : items) {
      total += item.getTotalPrice();
    }
  }

  /**
   * @return the items
   */
  public ArrayList<CartItem> getItems() {
    return items;
  }

  /**
   * @return the orderDate
   */
  public LocalDate getOrderDate() {
    return orderDate;
  }

  /**
   * @return the total
   */
  public double getTotal() {
    return total;
  }

  public void setDate(LocalDate date) {
    orderDate = date;
  }

}
