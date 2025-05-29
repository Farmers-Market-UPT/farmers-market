package farmersmarket;

/**
 * This enum class lists all the possible security questions
 *
 */
public enum SecurityQuestion {
	
  FIRST_PET("What was the name of your first pet?"),
  FAV_FOOD("What is your favourite food?"),
  BIRTH_PLACE("What city were you born in?"),
  FAV_SONG("What is your favourite song?");

  private final String question;
  
  /**
   * Constructor from the security question
   *
   */

  SecurityQuestion(String question) {
    this.question = question;
  }
  
  /**
   * @return the security question
<<<<<<< HEAD
  */
=======
   *
   */
>>>>>>> origin/main

  public String toString() {
    return question;
  }

  /**
   * This method reads a string and returns the respective SecurityQuestion object
   *
   * @param text 
   * @return 
   */
  public static SecurityQuestion fromString(String text) {
    for (SecurityQuestion q: SecurityQuestion.values()) {
      if (q.question.equals(text)) {
        return q;
      }
    }
    return null;
  }
}
