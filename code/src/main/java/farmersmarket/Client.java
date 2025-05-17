/**
 * 
 */
package farmersmarket;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * This class defines the final clients and extends from the class User
 */
public class Client extends User {

	/**
	 * Constructor from the class Client
	 */
	public Client(String name, String email, LocalDate birthdate, String passWord, HashMap secretQuestion) {
		super(name, email, birthdate, passWord, secretQuestion);
		
		//there are no specific attributes for this class
		
	}

}
