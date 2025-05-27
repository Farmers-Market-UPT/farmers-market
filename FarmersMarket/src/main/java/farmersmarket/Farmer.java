package farmersmarket;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class describes a model of a Farmer
 *
 */
public class Farmer extends User {

  private ArrayList<FarmerProduct> farmerProducts;
  private HashMap<String, String> bioTechniques;

  /**
   * Constructor for objects of class Farmer
   *
   * @param name
   * @param email
   * @param birthdate
   * @param password
   * @param question
   * @param answer
   */
  public Farmer(String name, String email, LocalDate birthdate, String password, String location,
      SecurityQuestion question, String answer) {
    super(name, email, birthdate, password, location, question, answer);
    farmerProducts = new ArrayList<>();
    bioTechniques = new HashMap<>();
  }

  /**
   * This method adds bio techniques
   *
   */
  public void addBioTechnique(String techniqueName, String techniqueDescription) {
    if (techniqueDescription.length() > 1000) {
      System.out.println("Please provide a description shorter than 1000 characters.");
      return;
    }
    bioTechniques.put(techniqueName, techniqueDescription);
  }

  /**
   * Registers a new product and adds it to the list
   *
   * @param name
   * @param price
   * @param stock
   * @param category
   * @return
   */
  public Product registerProduct(String name, float price, int stock, Category category) {
    Product newProduct = new Product(name, category);
    farmerProducts.add(new FarmerProduct(super.getEmail(), name, price, stock));
    return newProduct;
  }

  /**
   * Adds a new product to the farmer's catalogue
   *
   * @param productName
   * @param price
   * @param stock
   */
  public void addProduct(String productName, float price, int stock) {
    farmerProducts.add(new FarmerProduct(super.getEmail(), productName, price, stock));
  }

  // public void displayProducts() {
  // System.out.println("Available products: ");
  // for (FarmerProduct product : farmerProducts) {
  // if (product.getStock() > 0) {
  // System.out.println(product);
  // }
  // }
  // }

}
