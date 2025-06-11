package farmersmarket;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class describes the Administrators from the platform FarmersMarket and
 * inherits from class User
 */
public class Admin extends User {

  /**
   * Constructor for objects of class Admin
   *
   * @param name 
   * @param email 
   * @param birthdate 
   * @param password 
   * @param location 
   * @param question 
   * @param answer 
   */
  public Admin(String name, String email, LocalDate birthdate, String password, String location,
      SecurityQuestion question, String answer) {
    super(name, email, birthdate, password, location, question, answer);
  }

}
