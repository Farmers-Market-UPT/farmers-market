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
  private String passWord;
  private HashMap<Question, String> secretQuestion;

  /**
   * Constructor for objects of class user
   *
   * @param name 
   * @param email 
   * @param birthdate 
   * @param passWord 
   * @param secretQuestion 
   */
  public User(String name, String email, LocalDate birthdate, String passWord, HashMap secretQuestion) {
    this.name = name;
    this.email = email;
    this.birthdate = birthdate;
    this.passWord = passWord;
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
	return passWord;
}

/**
 * @param passWord the passWord to set
 */
public void setPassWord(String passWord) {
	this.passWord = passWord;
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
	return "User name= " + name + "\n email = " + email + "\n birthdate = " + birthdate + "\n passWord = " + passWord;
}

@Override
public String toString() {
    return "User name = " + name + 
           "\nemail = " + email + 
           "\nbirthdate = " + birthdate;
    //password was intentionally not shown here
}
  
  
  
  
  

}
