package farmersmarket;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class describes the Administrators from the platform FarmersMarket and
 * inherits from class User
 */

public class Admin extends User {

  public Admin(String name, String email, LocalDate birthdate, String password, String location,
      SecurityQuestion question, String answer) {
    super(name, email, birthdate, password, location, question, answer);
  }

  /**
   * This is method's purpose is to allow the class Farmer to use
   * it, since User is an abstract class
   *
   * @param productName
   * @param price
   * @param stock
   */

  public void addProduct(String productName, float price, int stock) {
  }

  /**
   * This is method's whose purpose is to allow the class Farmer to use
   * it, since User is an abstract class
   *
   * @param productName
   * @param price
   * @param stock
   */
  public void addSustainableTechnique(String techniqueName, String techniqueDescription) {
  }

  /**
   * This method's purpose is to allow the class Farmer to use
   * it, since User is an abstract class
   *
   * @param productName
   */
  public boolean hasProduct(String productName) {
    return false;
  }

}
