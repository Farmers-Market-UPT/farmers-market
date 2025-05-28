package farmersmarket;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class defines the final clients and extends from the class User
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
   * This method is only here to allow the class Farmer to have it, due to User
   * being abstract
   *
   * @param productName
   * @param price
   * @param stock
   */
  public void addProduct(String productName, float price, int stock) {
  }

  /**
   * This method is only here to allow the class Farmer to have it, due to User
   * being abstract
   *
   * @param productName
   * @param price
   * @param stock
   */
  public void addBioTechnique(String techniqueName, String techniqueDescription) {
  }

  /**
   * This method is only here to allow the class Farmer to use iThis method is only here to allow the class Farmer to use itt
   *
   * @param productName 
   */
  public boolean hasProduct(String productName) {
    return false;
  }

}
