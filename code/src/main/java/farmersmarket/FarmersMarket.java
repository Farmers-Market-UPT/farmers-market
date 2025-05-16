import java.util.HashSet;

/**
 * The systems main managing class
 *
 */
public class FarmersMarket {

  private HashSet<User> users;
  private HashSet<Product> products;

  public FarmerMarket() {
    users = new HashSet<>();
    products = new HashSet<>();
  }

  /**
   * Registers a new farmer
   *
   * @param name 
   * @param email 
   * @param birthdate 
   * @param passWord 
   * @param secretQuestion 
   */
  public void registerFarmer(String name, String email, LocalDate birthdate, String passWord, HashMap<SecurityQuestion, String> secretQuestion) {
    int id = users.size();
    users.add(new Farmer(name, email, birthdate, passWord, secretQuestion, id));
  }

  /**
   * Registers a new product
   *
   * @param productName 
   * @param image 
   * @param id 
   * @param category 
   */
  public void registerProduct(String productName, String image, int id, Category category) {
    products.add(new Product(productName, image, id, category));
  }
}
