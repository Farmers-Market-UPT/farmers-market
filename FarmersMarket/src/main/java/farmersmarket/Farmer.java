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
  public Farmer(String name, String email, LocalDate birthdate, String password, HashMap<SecurityQuestion, String> secretQuestion) {
    super(name, email, birthdate, password, secretQuestion);
    farmerProducts = new ArrayList<>();
  }

}
