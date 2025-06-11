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
   * Constructor from the class Client
   */
  public Client(String name, String email, LocalDate birthdate, String password, String location,
      SecurityQuestion question, String answer) {
    super(name, email, birthdate, password, location, question, answer);
    orderHistory = new ArrayList<>();
    currentCart = new ArrayList<>();
  }

  public double getCurrentCartTotal() {
    double total = 0;
    for (CartItem cartItem : currentCart) {
      total += cartItem.getTotalPrice();
    }
    return total;
  }

  /**
   * This method adds the current cart to the order list and creates a new one 
   *
   */
  public void finalizePurchase(Order order) {
    orderHistory.add(order);
  }

  /**
   * @return the currentCart
   */
  public ArrayList<CartItem> getCurrentCart() {
    return currentCart;
  }

  /**
   * @return the orderHistory
   */
  public ArrayList<Order> getOrderHistory() {
    return orderHistory;
  }

  public void clearCart() {
    currentCart = new ArrayList<>();
  }

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
