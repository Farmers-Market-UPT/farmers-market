package farmersmarket;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class describes a model of a Farmer
 *
 */
public class Farmer extends User {

  private ArrayList<FarmerProduct> farmerProducts;
  private ArrayList<String> bioTechniques;

  /**
   * Constructor for objects of the class Farmer
   *
   * @param name 
   * @param email 
   * @param birthdate 
   * @param password 
   * @param secretQuestion 
   */
  public Farmer(String name, String email, LocalDate birthdate, String password, SecurityQuestion question, String answer) {
    super(name, email, birthdate, password, question, answer);
    farmerProducts = new ArrayList<>();
  }

  public void registerTechniques() {

  }

}
