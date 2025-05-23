package farmersmarket;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class describes a model of a Farmer
 *
 */
public class Farmer extends User {

  private ArrayList<FarmerProduct> farmerProducts;
  private ArrayList<String> bioTechniques;

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
  public Farmer(String name, String email, LocalDate birthdate, String password, SecurityQuestion question, String answer) {
    super(name, email, birthdate, password, question, answer);
    farmerProducts = new ArrayList<>();
    bioTechniques = new ArrayList<>();
  }

  /**
   * This method adds bio techniques
   *
   */
  public void addBioTechnique(String technique) {
    bioTechniques.add(technique);
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
   * @param name 
   * @param price 
   * @param stock 
   */
  public void addProduct(String productName, float price, int stock) {
    farmerProducts.add(new FarmerProduct(super.getEmail(), productName, price, stock));
  }

  public void displayProducts() {
    System.out.println(farmerProducts);
  }
}
