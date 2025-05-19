package farmersmarket;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * This class describes a model of a user
 *
 */
public class User {
  private String name;
  private String email;
  private LocalDate birthdate;
  private String password;
  private HashMap<SecurityQuestion, String> secretQuestion;

  /**
   * Constructor for objects of class user
   *
   * @param name 
   * @param email 
   * @param birthdate 
   * @param password 
   * @param secretQuestion 
   */
  public User(String name, String email, LocalDate birthdate, String password, HashMap<SecurityQuestion, String> secretQuestion) {
    this.name = name;
    this.email = email;
    this.birthdate = birthdate;
    this.password = password;
    this.secretQuestion = secretQuestion;
  }

/**
 * @return the email
 */
public String getEmail() {
	return email;
}

/**
 * @param email the email to set
 */
public void setEmail(String email) {
	this.email = email;
}

/**
 * @return the passWord
 */
public String getPassWord() {
	return password;
}

/**
 * @param passWord the passWord to set
 */
public void setPassword(String passWord) {
	this.password = password;
}

/**
 * @return the secretQuestion
 */
public HashMap<Question, String> getSecretQuestion() {
	return secretQuestion;
}

/**
 * @param secretQuestion the secretQuestion to set
 */
public void setSecretQuestion(HashMap<Question, String> secretQuestion) {
	this.secretQuestion = secretQuestion;
}

/**
 * @return the name
 */
public String getName() {
	return name;
}

/**
 * @return the birthdate
 */
public LocalDate getBirthdate() {
	return birthdate;
}


@Override
public String toString() {
    return "User name = " + name + 
           "\nemail = " + email + 
           "\nbirthdate = " + birthdate;
    //password was intentionally not shown here
}
  
  
  
  
  

}
