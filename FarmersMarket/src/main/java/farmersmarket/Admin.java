package farmersmarket;

import java.time.LocalDate;

public class Admin extends User {

  public Admin(String name, String email, LocalDate birthdate, String password, String location,
      SecurityQuestion question, String answer) {
    super(name, email, birthdate, password, location, question, answer);
  }

  /**
   * This method is only here to allow the class Farmer to have it, due to User being abstract
   *
   * @param productName 
   * @param price 
   * @param stock 
   */
  public void addProduct(String productName, float price, int stock) {
  }

  /**
   * This method is only here to allow the class Farmer to have it, due to User being abstract
   *
   * @param productName 
   * @param price 
   * @param stock 
   */
  public void addBioTechnique(String techniqueName, String techniqueDescription) {
  }

}
