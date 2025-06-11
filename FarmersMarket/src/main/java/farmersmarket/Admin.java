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

}
