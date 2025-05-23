package farmersmarket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
   * This method registers new users
   *
   * @param name
   * @param email
   * @param birthdate
   * @param password
   * @param question
   * @param answer
   * @param accountType
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

  /**
   * This method searches user by email
   *
   * @param email
   * @return
   */
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
   * Searches a product by name
   *
   * @param productName 
   * @return 
   */
  public Product searchProduct(String productName) {
    for (Product product : products) {
      if (product.getName().equalsIgnoreCase(productName)) {
        return product;
      }
    }
    System.out.println("Product not found");
    return null;
  }

  /**
   * Registers a new product
   *
   * @param productName
   * @param category
   */
  public void registerProduct(String productName, Category category) {
    products.add(new Product(productName, category));
  }

  /**
   * This method verifies if the email is already in the system
   *
   * @param email
   * @return true if the email is already in the system, false otherwise
   */
  public boolean verifyEmail(String email) {
    for (User user : users) {
      if (user.getEmail().equalsIgnoreCase(email)) {
        return false;
      }
    }
    return true;
  }
  
  //this method verifies if the password matches the expected requirements
  public static boolean isPasswordValid (String password) {
	  if (password.length() < 8 || password.length() > 16) {
		  return false;
	  }
	  return password.matches(".*!?.,:;|\"@#$%^&*()_-+='1234567890.*");
  }

  /**
   * This method reads the necessary information and creates accounts
   *
   * @throws throw new IllegalArgumentException("Invalid question number");
   */
  public void createAccount() { // switch to main
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
    while (!isPasswordValid(password)) {
      System.out.println("The password has to be between 8 and 16 characters long and include special characters or numbers!");
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

    try {
      BufferedWriter writer = null;

      if (accountType.equals("Farmer")) {
        writer = Files.newBufferedWriter(Paths.get(System.getProperty("user.dir"), "data", "farmers.csv"),
            StandardOpenOption.APPEND);
      } else if (accountType.equals("Client")) {
        writer = Files.newBufferedWriter(Paths.get(System.getProperty("user.dir"), "data", "clients.csv"),
            StandardOpenOption.APPEND);
      }

      if (writer != null) {
        writer.write(name + "," + email + "," + date + "," + password + "," + question + "," + answer);
        writer.newLine();
        writer.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * This method verifies the credentials and logs the user in
   *
   * @return the user
   */
  public User login() { // switch to main
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

  /**
   * This method reads the users and products data
   *
   */
  public void readData() {
    try {

      Path path = Paths.get(System.getProperty("user.dir"), "data", "farmers.csv");
      BufferedReader reader = Files.newBufferedReader(path);
      String line;

      while ((line = reader.readLine()) != null) {

        String[] data = line.split(",");

        users.add(new Farmer(data[0], data[1], LocalDate.parse(data[2]), data[3], SecurityQuestion.fromString(data[4]),
            data[5]));

      }

      path = Paths.get(System.getProperty("user.dir"), "data", "clients.csv");
      reader = Files.newBufferedReader(path);

      while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");

        users.add(new Client(data[0], data[1], LocalDate.parse(data[2]), data[3], SecurityQuestion.fromString(data[4]),
            data[5]));

      }

      path = Paths.get(System.getProperty("user.dir"), "data", "products.csv");
      reader = Files.newBufferedReader(path);

      while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");

        if (searchProduct(data[2]) == null) {
          products.add(new Product(data[2], Category.fromString(data[1])));
        }

        addFarmerProduct(data[0], data[2], Float.valueOf(data[3]), Integer.valueOf(data[4]));

      }

      reader.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds a new product to a farmer's catalogue and a new farmer to the sellers of a product
   *
   * @param farmerEmail
   * @param productName
   * @param price
   * @param stock
   */
  public void addFarmerProduct(String farmerEmail, String productName, float price, int stock) {
    User farmer = searchUser(farmerEmail);
    Product product = searchProduct(productName);
    farmer.addProduct(productName, price, stock);
    product.addFarmer(farmerEmail, price, stock);
  }

  /**
   * Registers a new product
   *
   * @param farmerEmail
   * @param productName
   * @param price
   * @param stock
   * @param category
   */
  public void registerProduct(String farmerEmail, String productName, float price, int stock, Category category) {

    products.add(new Product(productName, category));
    addFarmerProduct(farmerEmail, productName, price, stock);

    BufferedWriter writer = null;

    try {
      writer = Files.newBufferedWriter(Paths.get(System.getProperty("user.dir"), "data", "products.csv"),
          StandardOpenOption.APPEND);

      if (writer != null) {
        writer.write(farmerEmail + "," + category.toString() + "," + productName + "," + price + "," + stock);
        writer.newLine();
        writer.close();
      }

    } catch (IOException e) {
      e.printStackTrace();

    }
  }

}
