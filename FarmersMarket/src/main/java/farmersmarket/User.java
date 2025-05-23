package farmersmarket;

import java.time.LocalDate;

/**
 * This class describes a model of a user
 *
 */

public abstract class User {

  private String name;
  private String email;
  private LocalDate birthdate;
  private String password;
  private String location;
  private SecurityQuestion question;
  private String answer;
  
  
  /**
   * Constructor for objects of class User
   *
   * @param name 
   * @param email 
   * @param birthdate 
   * @param password 
   * @param location
   * @param question 
   * @param answer 
   */
  public User(String name, String email, LocalDate birthdate, String password, String location, SecurityQuestion question, String answer) {
    this.name = name;
    this.email = email;
    this.birthdate = birthdate;
    this.password = password;
    this.location = location;
    this.question = question;
    this.answer = answer;
  }

  public abstract void addProduct(String name, float price, int stock);
  public abstract void addBioTechnique(String techniqueName, String techniqueDescription);

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
public String getPassword() {
	return password;
}

/**
 * @param passWord the password to set
 * @param password 
 */
public void setPassword(String password) {
	this.password = password;
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

/**
 * @return the question
 */
public SecurityQuestion getQuestion() {
	return question;
}

/**
 * @return the answer
 */
public String getAnswer() {
	return answer;
}
  
  
 
  

}
