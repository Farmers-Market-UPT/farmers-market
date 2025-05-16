package farmersmarket;

/**
 * This enum holds all the possible security questions
 *
 */
public enum SecurityQuestion {
  FIRST_PET("What was the name of your first pet?"),
  FAV_FOOD("What is your favourite food?"),
  BIRTH_PLACE("What city were you born in?"),
  FAV_SONG("What is your favourite song?");
  
  private final String question;

  SecurityQuestion(String question) {
  this.question = question;
  }

  public String getQuestion() {
    return question;
}
}
