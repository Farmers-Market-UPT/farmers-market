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
  private String passWord;
  private HashMap<SecurityQuestion, String> secretQuestion;

  /**
   * Constructor for objects of class user
   *
   * @param name 
   * @param email 
   * @param birthdate 
   * @param passWord 
   * @param secretQuestion 
   */
  public User(String name, String email, LocalDate birthdate, String passWord, HashMap<SecurityQuestion, String> secretQuestion) {
    this.name = name;
    this.email = email;
    this.birthdate = birthdate;
    this.passWord = passWord;
    this.secretQuestion = secretQuestion;
  }

}
