package farmersmarket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * @author Diogo Ferreira (51084)
 * @author Sara Canelas (51297)
 * @author Tomás Falcão (51258)
 * @author Francisca Silva (51765)
 * 
 */
public class Main {

  // Global instance of manager from FarmersMarket
  private static FarmersMarket manager;
  private static Scanner input;

  public static void main(String[] args) {

    input = new Scanner(System.in);
    manager = new FarmersMarket();
    manager.readData();
    User loggedUser = null;

    System.out.println("Welcome to Farmers Market! Please choose an option!");
    System.out.println("1- Login with an existing account");
    System.out.println("2- Create a new account");
    System.out.println("3- Close the program");
    // we need to make it return to the menu adter creating account rather than forcing a login

    int userChoice = input.nextInt();
    input.nextLine();

    switch (userChoice) {
      case 1:
        loggedUser = login();
        while (loggedUser == null) {
          loggedUser = login();
        }
        break;

      case 2:
        createAccount();
        loggedUser = login();
        while (loggedUser == null) {
          loggedUser = login();
        }
        break;

      case 3:
        System.exit(0);

      default:
        System.out.println("Please pick a valid option");
    }
  }

  public static void createAccount() {

    System.out.println("Please choose the account type (Farmer or Client)");
    String accountType = input.next();
    if (accountType.equals("Admin")) {
      System.out.println("What is the admin code?");
      if (input.next().equals("Lasagna")) {
        input.nextLine();
      } else {
        System.out.println("Wrong code!!");
        input.nextLine();
        return;
      }
    input.nextLine();
    System.out.println("What is your name?");
    String name = input.nextLine();
    System.out.println("What is your email?");
    String email = input.next();
    input.nextLine();
    while (!manager.verifyEmail(email)) {
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
    while (!manager.isPasswordValid(password)) {
      System.out.println(
          "The password has to be between 8 and 16 characters long and include special characters or numbers!");
      password = input.next();
      input.nextLine();
    }
    System.out.println("What is your location?");
    String location = input.nextLine();
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
    manager.registerUser(name, email, date, password, location, question, answer, accountType);

    try {
      BufferedWriter writer = null;

      if (accountType.equals("Farmer")) {
        writer = Files.newBufferedWriter(Paths.get(System.getProperty("user.dir"), "data", "farmers.csv"),
            StandardOpenOption.APPEND);
      } else if (accountType.equals("Client")) {
        writer = Files.newBufferedWriter(Paths.get(System.getProperty("user.dir"), "data", "clients.csv"),
            StandardOpenOption.APPEND);
      } else if (accountType.equals("Admin")) {
        writer = Files.newBufferedWriter(Paths.get(System.getProperty("user.dir"), "data", "admins.csv"),
            StandardOpenOption.APPEND);
      } else {
        System.out.println("Invalid code!");
      }

      if (writer != null) {
        writer
            .write(name + "," + email + "," + date + "," + password + "," + location + "," + question + "," + answer);
        writer.newLine();
        writer.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  }

  /**
   * This method verifies the credentials and logs the user in
   *
   * @return the user
   */
  public static User login() {

    System.out.println("Logging in...");
    System.out.println("What is the email?");
    String email = input.next();
    input.nextLine();
    User user = manager.searchUser(email);
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
