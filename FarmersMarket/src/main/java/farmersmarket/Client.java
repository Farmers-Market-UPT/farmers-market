package farmersmarket;

import java.time.LocalDate;

/**
 * This class defines the final clients from the platform Farmers Market and extends from the class User
 */
public class Client extends User {

  /**
   * Constructor from the class Client
   */
  public Client(String name, String email, LocalDate birthdate, String password, String location,
      SecurityQuestion question, String answer) {
    super(name, email, birthdate, password, location, question, answer);

    // there are no specific attributes for this class
  }

  /**
   * This method's purpose is to allow the class Farmer to use it, since User is an abstract class
   *
   * @param productName
   * @param price
   * @param stock
   */
  public void addProduct(String productName, float price, int stock) {
  }

  /**
   * This method's purpose is to allow the class Farmer to use it, since User is an abstract class
   *
   * @param techniqueName
   * @param techniqueDescription
   */
  public void addSustainableTechnique(String techniqueName, String techniqueDescription) {
  }

  /**
   * This method's purpose is to allow the class Farmer to use it, since User is an abstract class
   *
   * @param productName 
   */
  public boolean hasProduct(String productName) {
    return false;
  }
}
