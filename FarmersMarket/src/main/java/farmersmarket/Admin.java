package farmersmarket;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class describes the Administrators from the platform FarmersMarket and
 * inherits from class User
 */
public class Admin extends User {

  private HashMap<String, String> recommendations;

  /**
   * Constructor for objects of class Admin
   *
   * @param name
   * @param email
   * @param birthdate
   * @param password
   * @param location
   * @param question
   * @param answer
   */
  public Admin(String name, String email, LocalDate birthdate, String password, String location,
      SecurityQuestion question, String answer) {
    super(name, email, birthdate, password, location, question, answer);
    recommendations = new HashMap<>();
  }

  /**
   * This method allows the adminstators to add their agricultural recommendations
   *
   */
  public void addRecommendation(String recommendationName, String recommendationDescription) {
    recommendations.put(recommendationName, recommendationDescription);
  }

  /**
   * This method returns the recommendations that this admin added
   *
   * @return the recommendations
   */
  public ArrayList<String> getRecommendationNames() {
    ArrayList<String> recommendationNames = new ArrayList<>();
    for (String name : recommendations.keySet()) {
      recommendationNames.add(name);
    }
    return recommendationNames;
  }

  /**
   * Returns a recommendation description based on its name
   *
   * @param name 
   * @return 
   */
  public String getRecommendation(String name) {
    return recommendations.get(name);
  }
}
