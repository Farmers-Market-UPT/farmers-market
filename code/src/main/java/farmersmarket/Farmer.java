package farmersmarket;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class describes a model of a Farmer
 *
 */
public class Farmer extends User {

  private int id;
  private ArrayList<FarmerProduct> farmerProducts;
  private ArrayList<String> bioTechniques;

  /**
   * Constructor for objects of class Farmer
   *
   * @param id 
   */
  public Farmer(String name, String email, LocalDate birthdate, String passWord, HashMap<SecurityQuestion, String> secretQuestion, ArrayList<String> bioTechniques, int id) {
    super(name, email, birthdate, passWord, secretQuestion);
    farmerProducts = new ArrayList<>();
    this.id = id;
    this.bioTechniques = bioTechniques;
  }



}
