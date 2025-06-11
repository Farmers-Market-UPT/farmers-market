/**
 * 
 */
package farmersmarket;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class describes the model of an Order
 *
 */
public class Order {

  String orderID;
  private ArrayList<CartItem> items;
  private LocalDate orderDate;
  private double total;
  private Farmer farmer;
  private Client client;

  /**
   * Constructor for objects of class Order
   *
   * @param orderID
   * @param orderItems
   * @param client
   * @param farmer
   * @param date
   */
  public Order(String orderID, ArrayList<CartItem> orderItems, Client client, Farmer farmer, LocalDate date) {
    this.orderID = orderID;
    items = orderItems;
    orderDate = date;
    calculateTotal();
    this.client = client;
    this.farmer = farmer;
  }

  /**
   * This method allows add new items to the cart or alter the quantity of
   * previously added item
   *
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

  /**
   * This method returns the order ID
   *
   * @return the orderID
   */
  public String getID() {
    return orderID;
  }

  @Override
  public String toString() {
    return "Date: " + orderDate + " | Total: " + String.format("%.2f", total) + "€";
  }

  /**
   * This method calculates the total of an order
   *
   */
  public void calculateTotal() {
    for (CartItem item : items) {
      total += item.getTotalPrice();
    }
  }

  /**
   * This method returns the items of the order
   *
   * @return the items
   */
  public ArrayList<CartItem> getItems() {
    return items;
  }

  /**
   * This method returns the order's date
   *
   * @return the orderDate
   */
  public LocalDate getOrderDate() {
    return orderDate;
  }

  /**
   * This method returns the total price of the order
   *
   * @return the order total
   */
  public double getTotal() {
    return total;
  }

  /**
   * This method sets the order's date (used for csv reading)
   *
   * @param date 
   */
  public void setDate(LocalDate date) {
    orderDate = date;
  }

  /**
   * This method returns the order's farmer
   *
   * @return the farmer
   */
  public Farmer getFarmer() {
    return farmer;
  }

  /**
   * This method returns the order's client
   *
   * @return the client
   */
  public Client getClient() {
    return client;
  }

}
