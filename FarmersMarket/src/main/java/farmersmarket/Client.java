package farmersmarket;

import java.time.LocalDate;

/**
 * This class defines the final clients and extends from the class User
 */
public class Client extends User {

	/**
	 * Constructor from the class Client
	 */
	public Client(String name, String email, LocalDate birthdate, String password, String location, SecurityQuestion question, String answer) {
		super(name, email, birthdate, password, location, question, answer);
		
		//there are no specific attributes for this class
		
	}

  public void addProduct(String productName, float price, int stock) {
  }
  public void addBioTechnique(String techniqueName, String techniqueDescription) {
  }
}

