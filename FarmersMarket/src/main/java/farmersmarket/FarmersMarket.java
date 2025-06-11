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
import java.util.UUID;

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
   * This method returns a farmer's available items
   *
   * @param farmer
   * @return
   */
  public ArrayList<FarmerProduct> getFarmerAvailableItems(Farmer farmer) {
    ArrayList<FarmerProduct> availableItems = new ArrayList<>();
    for (FarmerProduct farmerProduct : farmer.getFarmerProducts()) {
      if (farmerProduct.getStock() > 0) {
        availableItems.add(farmerProduct);
      }
    }
    return availableItems;
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

    for (int i = 0; i < categoryProducts.size(); i++) {
      if (categoryProducts.get(i).getStock() < 1) {
        categoryProducts.remove(i);
        ;
      }
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

        addFarmerProduct(data[0], data[2], Float.valueOf(data[3]), Integer.valueOf(data[4]));

      }

      // Reading bio techniques
      path = Paths.get(System.getProperty("user.dir"), "data", "techniques.csv");
      reader = Files.newBufferedReader(path);

      while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");

        Farmer farmer = (Farmer) searchUser(data[0]);
        farmer.addSustainableTechnique(data[1], data[2]);
      }
      
   // Reading recommendations
      path = Paths.get(System.getProperty("user.dir"), "data", "instructions.csv");
      reader = Files.newBufferedReader(path);

      while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");

        Admin admin = (Admin) searchUser(data[0]);
        admin.addRecommendation(data[1], data[2]);
      }


      // Reading carts

      path = Paths.get(System.getProperty("user.dir"), "data", "carts.csv");
      reader = Files.newBufferedReader(path);

      while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");

        Farmer farmer = (Farmer) searchUser(data[2]);
        Client client = (Client) searchUser(data[0]);
        for (FarmerProduct farmerProduct : farmer.getFarmerProducts()) {
          if (farmerProduct.getProductName().equals(data[1])) {
            client.addToCart(farmerProduct, Integer.parseInt(data[3]));
            break;
          }
        }
      }

      // Reading orders
      path = Paths.get(System.getProperty("user.dir"), "data", "orders.csv");
      reader = Files.newBufferedReader(path);

      while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");
        Client client = (Client) searchUser(data[1]);
        Farmer farmer = (Farmer) searchUser(data[2]);
        FarmerProduct product = null;

        for (FarmerProduct fp : farmer.getFarmerProducts()) {
          if (fp.getProductName().equals(data[3])) {
            product = fp;
            break;
            
          }
        }

        CartItem item = new CartItem(product, Integer.parseInt(data[5]));
        item.setPriceAtPurchase(Double.parseDouble(data[4]));

        Order existingOrder = null;
        for (Order order : client.getOrderHistory()) {
          if (data[0].equals(order.getID())) {
            existingOrder = order;
          }
        }

        if (existingOrder != null) {
          existingOrder.getItems().add(item);

        } else {
          ArrayList<CartItem> items = new ArrayList<>();
          items.add(item);
          Order newOrder = new Order(data[0], items, client, farmer, LocalDate.parse(data[6]));
          client.getOrderHistory().add(newOrder);
          farmer.getSales().add(newOrder);
        }


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
  public void addFarmerProduct(String farmerEmail, String productName, float price, int stock) {
    Farmer farmer = (Farmer) searchUser(farmerEmail);
    Product product = searchProduct(productName);
    FarmerProduct productToAdd = new FarmerProduct(farmer, productName, price, stock);
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
  public boolean registerProduct(String farmerEmail, String productName, float price, int stock,
      Category category) {

    if (searchProduct(productName) == null) {
      products.add(new Product(productName, category));
    }
    Farmer farmer = (Farmer) searchUser(farmerEmail);

    if (farmer.hasProduct(productName)) {
      return false;
    }

    addFarmerProduct(farmerEmail, productName, price, stock);

    BufferedWriter writer = null;

    try {
      writer = Files.newBufferedWriter(Paths.get(System.getProperty("user.dir"), "data", "products.csv"),
          StandardOpenOption.APPEND);

      if (writer != null) {
        writer.write(
            farmer.getEmail() + "," + category.toString() + "," + productName + "," + price + "," + stock);
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
    Farmer farmer = (Farmer) searchUser(farmerEmail);

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
    client.addToCart(product, quant);

    BufferedWriter writer = null;
    Path path = Paths.get(System.getProperty("user.dir"), "data", "carts.csv");
    List<String> lines = new ArrayList<>();

    try {
      lines = Files.readAllLines(path);
      boolean existed = false;

      for (int i = 0; i < lines.size(); i++) {
        String[] data = lines.get(i).split(",");
        if (data[0].equals(client.getEmail()) && data[1].equals(product.getProductName())
            && data[2].equals(product.getFarmer().getEmail())) {
          lines.set(i, data[0] + "," + data[1] + "," + data[2] + "," + quant);
          existed = true;
        }
      }

      if (existed) {
        Files.write(path, lines);
        return;
      }

      writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND);

      if (writer != null) {
        writer.write(
            client.getEmail() + "," + product.getProductName() + "," + product.getFarmer().getEmail() + "," + quant);
        writer.newLine();
        writer.close();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void finalizePurchase(Client client) {
    HashSet<Farmer> saleFarmers = new HashSet<>();

    for (CartItem item : client.getCurrentCart()) {
      saleFarmers.add((Farmer) item.getProduct().getFarmer());
    }

    for (Farmer farmer : saleFarmers) {
      ArrayList<CartItem> farmerItems = new ArrayList<>();
      for (CartItem item : client.getCurrentCart()) {
        if (item.getProduct().getFarmer().getEmail().equals(farmer.getEmail())) {
          farmerItems.add(item);
          changeStockProduct(farmer, item.getProduct(), item.getProduct().getStock() - item.getQuantity());
        }
      }
      String orderID = UUID.randomUUID().toString();
      Order order = new Order(orderID, farmerItems, client, farmer, LocalDate.now());
      client.finalizePurchase(order);
      farmer.addSale(order);

      try {

        BufferedWriter writer = Files.newBufferedWriter(
            Paths.get(System.getProperty("user.dir"), "data", "orders.csv"),
            StandardOpenOption.APPEND);

        for (CartItem item : farmerItems) {
          writer.write(
              orderID + "," +
                  client.getEmail() + "," +
                  farmer.getEmail() + "," +
                  item.getProduct().getProductName() + "," +
                  item.getPriceAtPurchase() + "," +
                  item.getQuantity() + "," +
                  order.getOrderDate());
          writer.newLine();
        }

        writer.close();


      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    clearClientCart(client);

  }

  public void editCartItem(Client client, CartItem item, int quant) {
    Path path = Paths.get(System.getProperty("user.dir"), "data", "carts.csv");
    List<String> lines = new ArrayList<>();
    try {
      lines = Files.readAllLines(path);
      for (int i = lines.size() - 1; i >= 0; i--) {
        String[] data = lines.get(i).split(",");
        if (data[0].equals(client.getEmail()) && data[1].equals(item.getProduct().getProductName())
            && data[2].equals(item.getProduct().getFarmer().getEmail())) {
          if (quant == 0) {
            client.getCurrentCart().remove(i);
            lines.remove(i);
          } else {
            lines.set(i, data[0] + "," + data[1] + "," + data[2] + "," + quant);
          }
        }
      }

      Files.write(path, lines);
      item.setQuantity(quant);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void clearClientCart(Client client) {
    Path path = Paths.get(System.getProperty("user.dir"), "data", "carts.csv");
    List<String> lines = new ArrayList<>();
    try {
      lines = Files.readAllLines(path);
      for (int i = lines.size() - 1; i >= 0; i--) {
        String[] data = lines.get(i).split(",");
        if (data[0].equals(client.getEmail())) {
          lines.remove(i);
        } else {
          lines.set(i, data[0] + "," + data[1] + "," + data[2] + "," + data[3]);
        }
      }

      client.clearCart();
      Files.write(path, lines);

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void changeStockProduct(Farmer farmer, FarmerProduct product, int stock) {
    Path path = Paths.get(System.getProperty("user.dir"), "data", "products.csv");
    List<String> lines = new ArrayList<>();
    try {
      lines = Files.readAllLines(path);
      for (int i = lines.size() - 1; i >= 0; i--) {
        String[] data = lines.get(i).split(",");
        if (data[0].equals(farmer.getEmail()) && data[2].equals(product.getProductName())) {
          lines.set(i, data[0] + "," + data[1] + "," + data[2] + "," + data[3] + "," + stock);
        }
      }

      product.setStock(stock);
      Files.write(path, lines);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void changePriceProduct(Farmer farmer, FarmerProduct product, double price) {
    Path path = Paths.get(System.getProperty("user.dir"), "data", "products.csv");
    List<String> lines = new ArrayList<>();
    try {
      lines = Files.readAllLines(path);
      for (int i = lines.size() - 1; i >= 0; i--) {
        String[] data = lines.get(i).split(",");
        if (data[0].equals(farmer.getEmail()) && data[2].equals(product.getProductName())) {
          lines.set(i, data[0] + "," + data[1] + "," + data[2] + "," + price + "," + data[4]);
        }
      }

      product.setPrice(price);
      Files.write(path, lines);

    } catch (IOException e) {
      e.printStackTrace();
    }

  }
  
  /**
   * This method allows the administrators to add their recommendations for sustainable agriculture at home
   *
   * @param adminEmail
   * @param recommendationName
   * @param recommendationDescription
   */
  public void addRecommendation(String adminEmail, String recommendationName, String recommendationDescription) {
    Admin admin = (Admin) searchUser(adminEmail);

    admin.addRecommendation(recommendationName, recommendationDescription);

    BufferedWriter writer = null;

    try {
      writer = Files.newBufferedWriter(Paths.get(System.getProperty("user.dir"), "data", "instructions.csv"),
          StandardOpenOption.APPEND);

      if (writer != null) {
    	System.out.print("I'm here");
        writer.write(adminEmail + "," + recommendationName + "," + recommendationDescription);
        writer.newLine();
        writer.close();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  
  public ArrayList<String> getAllAdminRecommendations() {
	    ArrayList<String> allRecs = new ArrayList<>();

	    for (User user : users) {
	        if (user instanceof Admin admin) {
	            allRecs.addAll(admin.getRecommendationList());
	        }
	    }

	    return allRecs;
	}

  
  
}
