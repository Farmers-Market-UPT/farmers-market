package farmersmarket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

/**
 * This class describes Farmers Market's system's main management
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
  public void registerUser(String name, String email, LocalDate birthdate, String password, String location,
      SecurityQuestion question,
      String answer, String accountType) {
	  if (!verifyEmail(email)) {
		    System.out.println("Email already registered. Registration aborted.");
		    return;
	  }
	  if (!isPasswordValid(password)) {
		    System.out.println("Password must be between 8–16 characters and contain at least one number or special character.");
		    return;
		  }
	  if (accountType.equalsIgnoreCase(Farmer.class.getSimpleName())) {
      users.add(new Farmer(name, email, birthdate, password, location, question, answer));
      System.out.println("Welcome new farmer");
    } else if (accountType.equalsIgnoreCase(Client.class.getSimpleName())) {
      users.add(new Client(name, email, birthdate, password, location, question, answer));
      System.out.println("Welcome new client");
    } else if (accountType.equalsIgnoreCase(Admin.class.getSimpleName())) {
      System.out.println("");
      users.add(new Admin(name, email, birthdate, password, location, question, answer));
      System.out.println("Welcome new admin");
    }
    
    //this allows to write in our cvs files

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
            .write(
                name + "," + email + "," + birthdate + "," + password + "," + location + "," + question + "," + answer);
        writer.newLine();
        writer.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  

  /**
   * This method searches users per email
   *
   * @param email
   * @return user
   */
  public User searchUser(String email) {
    for (User user : users) {
      if (user.getEmail().equalsIgnoreCase(email)) {
        return user;
      }
    }
    return null;
  }

  /**
   * This method searches a product by its name
   *
   * @param productName
   * @return product
   */
  public Product searchProduct(String productName) {
    for (Product product : products) {
      if (product.getName().equalsIgnoreCase(productName)) {
        return product;
      }
    }
    return null;
  }

  /**
   * This method returns the registered farmers in alphabetically order
   *
   */
  public String[] getFarmersAlphabetically() {
    ArrayList<String> farmerNames = new ArrayList<String>();
    for (User user : users) {
      if (user instanceof Farmer) {
        farmerNames.add(user.getName());
      }
    }
    Collections.sort(farmerNames);
    for (String farmer : farmerNames) {
      System.out.println(farmer);
    }
    return farmerNames.toArray(new String[]{});
  }

  /**
   * This method returns the existing products per category in an alphabetically order
   *
   */
  public ArrayList<Farmer> getFarmerListAlphabetically() {
    ArrayList<Farmer> farmerNames = new ArrayList<Farmer>();
    for (User user : users) {
      if (user instanceof Farmer) {
        Farmer farmer = (Farmer) user;
        farmerNames.add(farmer);
      }
    }
    return farmerNames;
  }
  
  /**
   * Print Product by Category Alphabetically
   *
   * @param category
   */
  public void displayProductsByCategory(Category category) {
    if (category == null) {
      System.out.println("Category unavailable");
      return;
    }
    ArrayList<String> productsCategory = new ArrayList<String>();
    for (Product product : products) {
      if (product.getCategory() == category) {
        productsCategory.add(product.getName());
      }
    }
    Collections.sort(productsCategory);
    for (String product : productsCategory) {
      System.out.println(product);
        
    }
  }

  /**
   * This method verifies if the email already exists in the system
   *
   * @param email
   * @return true if the email is already in the system or false otherwise
   */
  
  public boolean verifyEmail(String email) {
    for (User user : users) {
      if (user.getEmail().equalsIgnoreCase(email)) {
        return false;
      }
    }
    return true;
  }
  
  /**
   * This method verifies if the password matches the expected requirements
   *
   */

  public boolean isPasswordValid(String password) {
    if (password.length() < 8 || password.length() > 16) {
      return false;
    }
    return password.matches(".*[!?.,:;|\"@#$%^&()_\\-+='0-9].*");
  }

  /**
   * This method reads the necessary information to create a new account and creates it by writing it on the csv file
   *
   * @throws throw new IllegalArgumentException("Invalid question number");
   */
  public void readData() {
    try {

      // Reading farmers accounts
      Path path = Paths.get(System.getProperty("user.dir"), "data", "farmers.csv");
      BufferedReader reader = Files.newBufferedReader(path);
      String line;

      while ((line = reader.readLine()) != null) {

        String[] data = line.split(",");

        users.add(new Farmer(data[0], data[1], LocalDate.parse(data[2]), data[3], data[4],
            SecurityQuestion.fromString(data[5]),
            data[6]));

      }

      // Reading clients accounts
      path = Paths.get(System.getProperty("user.dir"), "data", "clients.csv");
      reader = Files.newBufferedReader(path);

      while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");

        users.add(new Client(data[0], data[1], LocalDate.parse(data[2]), data[3], data[4],
            SecurityQuestion.fromString(data[5]),
            data[6]));
      }

      // Reading admin accounts
      path = Paths.get(System.getProperty("user.dir"), "data", "admins.csv");
      reader = Files.newBufferedReader(path);

      while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");

        users.add(new Admin(data[0], data[1], LocalDate.parse(data[2]), data[3], data[4],
            SecurityQuestion.fromString(data[5]),
            data[6]));

      }

      // Reading products
      path = Paths.get(System.getProperty("user.dir"), "data", "products.csv");
      reader = Files.newBufferedReader(path);

      while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");

        if (searchProduct(data[2]) == null) {
          products.add(new Product(data[2], Category.fromString(data[1])));
        }

        addFarmerProduct(data[0], data[2], Float.valueOf(data[3]), Integer.valueOf(data[4]));

      }

      // Reading bio techniques
      path = Paths.get(System.getProperty("user.dir"), "data", "techniques.csv");
      reader = Files.newBufferedReader(path);

      while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");

        User farmer = searchUser(data[0]);
        farmer.addSustainableTechnique(data[1], data[2]);
      }

      reader.close();

    } catch (IOException e) {
      e.printStackTrace();
    }

  }
  
  

  /**
   * This method allows the farmers to add a new product to their catalog and adds the farmer to the list of the sellers of said product
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
   * This method allows the farmers to register a new product after checking if the product already exists or if the farmer sells it already 
   *
   * @param farmerEmail
   * @param productName
   * @param price
   * @param stock
   * @param category
   */
  public void registerProduct(String farmerEmail, String productName, float price, int stock, Category category) {

    if (searchProduct(productName) == null) {
      products.add(new Product(productName, category));
    }
    User farmer = searchUser(farmerEmail);

    if (farmer.hasProduct(productName)) {
      System.out.println("The product is already registered!");
      return;
    }

    addFarmerProduct(farmerEmail, productName, price, stock);
    System.out.println("Product added with success!");

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

  /**
   * This method allows the farmers to add their sustainable agricultural techniques to their profile
   *
   * @param farmerEmail
   * @param techniqueName
   * @param techniqueDescription
   */
  public void addSustainableTechnique(String farmerEmail, String techniqueName, String techniqueDescription) {
    User farmer = searchUser(farmerEmail);

    farmer.addSustainableTechnique(techniqueName, techniqueDescription);

    BufferedWriter writer = null;

    try {
      writer = Files.newBufferedWriter(Paths.get(System.getProperty("user.dir"), "data", "techniques.csv"),
          StandardOpenOption.APPEND);

      if (writer != null) {
        writer.write(farmerEmail + "," + techniqueName + "," + techniqueDescription);
        writer.newLine();
        writer.close();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
