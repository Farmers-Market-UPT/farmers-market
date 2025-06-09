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
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
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

  private Client client;

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

    if (accountType.equalsIgnoreCase(Farmer.class.getSimpleName())) {
      users.add(new Farmer(name, email, birthdate, password, location, question, answer));
    } else if (accountType.equalsIgnoreCase(Client.class.getSimpleName())) {
      users.add(new Client(name, email, birthdate, password, location, question, answer));
    } else if (accountType.equalsIgnoreCase(Admin.class.getSimpleName())) {
      users.add(new Admin(name, email, birthdate, password, location, question, answer));
    }

    // Writing users to csv files

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
   * This method searches users by email
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
    return farmerNames.toArray(new String[] {});
  }

  /**
   * This method returns the existing products per category in an alphabetically
   * order
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
    Collections.sort(farmerNames, Comparator.comparing(Farmer::getName));
    return farmerNames;
  }

  /**
   * This method returns the existing products of a certain Category
   * Alphabetically
   *
   * @param category
   */
  public ArrayList<FarmerProduct> getCategoryProducts(Category category, boolean ascending) {

    ArrayList<Product> productsCategory = new ArrayList<>();
    for (Product product : products) {
      if (product.getCategory() == category) {
        productsCategory.add(product);
      }
    }
    if (ascending) {
      Collections.sort(productsCategory, Comparator.comparing(Product::getName));
    } else {
      Collections.sort(productsCategory, Comparator.comparing(Product::getName).reversed());
    }

    ArrayList<FarmerProduct> categoryProducts = new ArrayList<>();

    for (Product product : productsCategory) {
      categoryProducts.addAll(product.getProductFarmers());
    }

    return categoryProducts;
  }

  /**
   * This method returns the existing products of a certain Category sorted by
   * price
   *
   * @param category
   */
  public ArrayList<FarmerProduct> sortedProductsByPrice(Category category, boolean ascending) {

    ArrayList<FarmerProduct> productsCategory = getCategoryProducts(category, true);

    if (ascending) {
      Collections.sort(productsCategory, Comparator.comparing(FarmerProduct::getPrice));
    } else {
      Collections.sort(productsCategory, Comparator.comparing(FarmerProduct::getPrice).reversed());
    }

    return productsCategory;
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
   * This method reads the necessary information to create a new account and
   * creates it by writing it on the csv file
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

        addFarmerProduct(data[0], data[2], data[3], Float.valueOf(data[4]), Integer.valueOf(data[5]));

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
   * This method allows the farmers to add a new product to their catalog and adds
   * the farmer to the list of the sellers of said product
   *
   * @param farmerEmail
   * @param productName
   * @param price
   * @param stock
   */
  public void addFarmerProduct(String farmerEmail, String productName, String farmerName, float price, int stock) {
    User farmer = searchUser(farmerEmail);
    Product product = searchProduct(productName);
    FarmerProduct productToAdd = new FarmerProduct(farmerEmail, productName, farmerName, price, stock);
    farmer.addProduct(productToAdd);
    product.addFarmer(productToAdd);
  }

  /**
   * This method allows the farmers to register a new product after checking if
   * the product already exists or if the farmer sells it already
   *
   * @param farmerEmail
   * @param productName
   * @param price
   * @param stock
   * @param category
   */
  public boolean registerProduct(String farmerEmail, String productName, String farmerName, float price, int stock,
      Category category) {

    if (searchProduct(productName) == null) {
      products.add(new Product(productName, category));
    }
    User farmer = searchUser(farmerEmail);

    if (farmer.hasProduct(productName)) {
      return false;
    }

    addFarmerProduct(farmerEmail, productName, farmerName, price, stock);

    BufferedWriter writer = null;

    try {
      writer = Files.newBufferedWriter(Paths.get(System.getProperty("user.dir"), "data", "products.csv"),
          StandardOpenOption.APPEND);

      if (writer != null) {
        writer.write(
            farmerEmail + "," + category.toString() + "," + productName + "," + farmerName + "," + price + "," + stock);
        writer.newLine();
        writer.close();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  /**
   * This method allows the farmers to add their sustainable agricultural
   * techniques to their profile
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

  public void changePassword(String email, String newPassword) {
    User user = searchUser(email);
    user.setPassword(newPassword);
    try {

      List<String> lines = new ArrayList<>();
      Path path = null;
      if (user instanceof Farmer) {
        path = Paths.get(System.getProperty("user.dir"), "data", "farmers.csv");
        lines = Files.readAllLines(path);
      } else if (user instanceof Client) {
        path = Paths.get(System.getProperty("user.dir"), "data", "clients.csv");
        lines = Files.readAllLines(path);
      } else if (user instanceof Admin) {
        path = Paths.get(System.getProperty("user.dir"), "data", "admins.csv");
        lines = Files.readAllLines(path);
      }

      for (int i = 0; i < lines.size(); i++) {
        String[] data = lines.get(i).split(",");
        if (data[1].equals(email)) {
          lines.set(i, data[0] + "," + data[1] + "," + data[2] + "," + newPassword + "," + data[4] + "," + data[5] + ","
              + data[6]);
        }

      }

      Files.write(path, lines);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void addProductToCart(FarmerProduct product, Client client, int quant) {
    client.getCurrentCart().addCartItem(product, quant);
  }

  public Order getClientCart(Client client) {
    return client.getCurrentCart();
  }

  public void finalizePurchase(Client client) {
    for (CartItem item : client.getCurrentCart().getItems()) {
      ((Farmer) searchUser(item.getProduct().getFarmerEmail())).addSale(item);
      item.getProduct().reduceStock(item.getQuantity());
    }

    client.getCurrentCart().setDate(LocalDate.now());
    client.finalizePurchase();

  }

}
