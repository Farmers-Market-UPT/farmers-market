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
   * This method returns the user's email
   *
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * This method returns the user's password
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * This method changes the user's password
   *
   * @param password 
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * This method returns the user's name
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * This method returns the user's birthdate
   *
   * @return the birthdate
   */
  public LocalDate getBirthdate() {
    return birthdate;
  }

  /**
   * This method returns the user's secret questions
   *
   * @return the question
   */
  public SecurityQuestion getQuestion() {
    return question;
  }

  /**
   * This method returns the user's answer to the security question
   *
   * @return the answer
   */
  public String getAnswer() {
    return answer;
  }

  /**
   * This method returns the user's location
   *
   * @return the location
   */
  public String getLocation() {
    return location;
  }
}
