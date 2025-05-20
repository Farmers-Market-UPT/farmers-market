package farmersmarket;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 * The systems main managing class
 *
 */
public class FarmersMarket {

  private HashSet<User> users;
  private HashSet<Product> products;
  Scanner input = new Scanner(System.in);

  public FarmersMarket() {
    users = new HashSet<>();
    products = new HashSet<>();
  }

  /**
   * Registers a new user
   *
   * @param name
   * @param email
   * @param birthdate
   * @param password
   * @param secretQuestion
   * @param class
   */
  public void registerUser(String name, String email, LocalDate birthdate, String password, SecurityQuestion question,
      String answer, String accountType) {
    if (accountType.equalsIgnoreCase(Farmer.class.getSimpleName())) {
      users.add(new Farmer(name, email, birthdate, password, question, answer));
      System.out.println("Welcome new farmer");
    } else if (accountType.equalsIgnoreCase(Client.class.getSimpleName())) {
      users.add(new Client(name, email, birthdate, password, question, answer));
      System.out.println("Welcome new client");
    } // admin to be added later
  }

  public User searchUser(String email) {
    for (User user : users) {
      if (user.getEmail().equalsIgnoreCase(email)) {
        return user;
      }
    }
    System.out.println("User not found");
    return null;
  }

  /**
   * Registers a new product
   *
   * @param productName
   * @param category
   */
  public void registerProduct(String productName, Category category) {
    int id = products.size();
    products.add(new Product(productName, id, category));
  }

  public boolean verifyEmail(String email) {
    for (User user : users) {
      if (user.getEmail().equalsIgnoreCase(email)) {
        return false;
      }
    }
    return true;
  }

  public void createAccount() {
    System.out.println("Please choose the account type (Farmer or Client)");
    String accountType = input.next();
    input.nextLine();
    System.out.println("What is your name?");
    String name = input.nextLine();
    System.out.println("What is your email?");
    String email = input.next();
    input.nextLine();
    while (!verifyEmail(email)) {
      System.out.println("Email already registered! Please pick a different one");
      email = input.next();
      input.nextLine();
    }
    System.out.println("What is your birthdate? (dd/mm/yyyy)");
    String dateString = input.next();
    input.nextLine();
    LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    System.out.println("What is your password?");
    String password = input.next();
    while (password.length() < 8 || password.length() > 16) { // special character / number
      System.out.println("The password has to be between 8 and 16 characters long!");
      password = input.next();
      input.nextLine();
    }
    System.out.println("Choose a security question");
    System.out.println("1- " + SecurityQuestion.FAV_FOOD);
    System.out.println("2- " + SecurityQuestion.FAV_SONG);
    System.out.println("3- " + SecurityQuestion.BIRTH_PLACE);
    System.out.println("4- " + SecurityQuestion.FIRST_PET);
    int questionNumber = input.nextInt();
    input.nextLine();
    SecurityQuestion question;
    switch (questionNumber) {
      case 1:
        question = SecurityQuestion.FAV_FOOD;
        break;
      case 2:
        question = SecurityQuestion.FAV_SONG;
        break;
      case 3:
        question = SecurityQuestion.BIRTH_PLACE;
        break;
      case 4:
        question = SecurityQuestion.FIRST_PET;
        break;
      default:
        throw new IllegalArgumentException("Invalid question number");
    }
    System.out.println("What is the answer?");
    String answer = input.next();
    input.nextLine();
    registerUser(name, email, date, password, question, answer, accountType);

  }

  public User login() {
    System.out.println("Logging in...");
    System.out.println("What is the email?");
    String email = input.next();
    input.nextLine();
    User user = searchUser(email);
    if (user != null) {
      System.out.println("What is the password?");
      String password = input.next();
      input.nextLine();
      if (user.getPassword().equalsIgnoreCase(password)) {
        return user;
      }
    System.out.println("The credentials are wrong, please try again!");
    }
    return null;
  }
}
