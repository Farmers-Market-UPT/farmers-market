package farmersmarket;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class defines the final clients from the platform Farmers Market and
 * extends from the class User
 */
public class Client extends User {

  private ArrayList<Order> orderHistory;
  private ArrayList<CartItem> currentCart;

  /**
   * Constructor of objects of class Client
   *
   * @param name 
   * @param email 
   * @param birthdate 
   * @param password 
   * @param location 
   * @param question 
   * @param answer 
   */
  public Client(String name, String email, LocalDate birthdate, String password, String location,
      SecurityQuestion question, String answer) {
    super(name, email, birthdate, password, location, question, answer);
    orderHistory = new ArrayList<>();
    currentCart = new ArrayList<>();
  }

  /**
   * This method calculates and returns the total value of the current cart
   *
   * @return the total
   */
  public double getCurrentCartTotal() {
    double total = 0;
    for (CartItem cartItem : currentCart) {
      total += cartItem.getTotalPrice();
    }
    return total;
  }

  /**
   * This method adds the current cart to the order list
   *
   */
  public void finalizePurchase(Order order) {
    orderHistory.add(order);
  }

  /**
   * This method returns the current cart
   *
   * @return the current cart
   */
  public ArrayList<CartItem> getCurrentCart() {
    return currentCart;
  }

  /**
   * This method returns the order history of the client
   *
   * @return the orderHistory
   */
  public ArrayList<Order> getOrderHistory() {
    return orderHistory;
  }

  /**
   * This method clears the client's cart
   *
   */
  public void clearCart() {
    currentCart = new ArrayList<>();
  }

  /**
   * This method adds an item to the cart
   *
   * @param product 
   * @param quant 
   */
  public void addToCart(FarmerProduct product, int quant) {
    for (CartItem cartItem : currentCart) {
      if (cartItem.getProduct() == product) {
        cartItem.setQuantity(quant);
        return;
      }
    }
    currentCart.add(new CartItem(product, quant));
  }

}
