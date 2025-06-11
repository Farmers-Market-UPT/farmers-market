package farmersmarket;

import java.time.LocalDate;
import java.util.ArrayList;

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
   * Constructor of objects of class User
   *
   * @param name
   * @param email
   * @param birthdate
   * @param password
   * @param location
   * @param question
   * @param answer
   */
  public User(String name, String email, LocalDate birthdate, String password, String location,
      SecurityQuestion question, String answer) {
    this.name = name;
    this.email = email;
    this.birthdate = birthdate;
    this.password = password;
    this.location = location;
    this.question = question;
    this.answer = answer;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password the password to set
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

  /**
   * @return the location
   */
  public String getLocation() {
    return location;
  }
}
