package farmersmarket;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;

/**
 * The systems main managing class
 *
 */
public class FarmersMarket {

  private HashSet<User> users;
  private HashSet<Product> products;

  public FarmersMarket() {
    users = new HashSet<>();
    products = new HashSet<>();
  }

  /**
   * Registers a new user
   *
   * @param name 
   * @param email 
   * @param birthdate 
   * @param password 
   * @param secretQuestion 
   * @param class 
   */
  public void registerUser(String name, String email, LocalDate birthdate, String password, HashMap<SecurityQuestion, String> secretQuestion, Class<?> c) {
    if (c == Farmer.class) {
      users.add(new Farmer(name, email, birthdate, password, secretQuestion));
    } else if (c == Client.class) {
      users.add(new Client(name, email, birthdate, password, secretQuestion));
    } // admin to be added later
  }

  /**
   * Registers a new product
   *
   * @param productName 
   * @param category 
   */
  public void registerProduct(String productName, Category category) {
    int id = products.size();
    products.add(new Product(productName, id, category));
  }
}
