package farmersmarket;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class defines the farmers who sell their products at Farmers Market and
 * extends from the class User
 *
 */
public class Farmer extends User {

  private ArrayList<FarmerProduct> farmerProducts;
  private ArrayList<CartItem> farmerSales;
  private HashMap<String, String> sustainableTechniques;

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
    farmerSales = new ArrayList<>();
    sustainableTechniques = new HashMap<>();
  }

  public void addSale(CartItem item) {
    farmerSales.add(item);
  }

  /**
   * This method allows the farmers to add their sustainable agricultural
   * practices and their descriptions
   *
   */
  public void addSustainableTechnique(String techniqueName, String techniqueDescription) {
    if (techniqueDescription.length() > 1000) {
      return;
    }
    sustainableTechniques.put(techniqueName, techniqueDescription);
  }

  /**
   * This method adds a new product to the farmer's catalog
   *
   * @param productName
   * @param price
   * @param stock
   */
  public void addProduct(FarmerProduct product) {
    farmerProducts.add(product);
  }

  /**
   * This method verifies if a farmer already has a product for sale
   *
   * @param productName
   * @return
   */
  public boolean hasProduct(String productName) {
    for (FarmerProduct farmerProduct : farmerProducts) {
      if (farmerProduct.getProductName().equalsIgnoreCase(productName)) {
        return true;
      }
    }
    return false;
  }

  public ArrayList<FarmerProduct> getFarmerProducts() {
    return farmerProducts;
  }

  public ArrayList<CartItem> getSales() {
    return farmerSales;
  }

  public ArrayList<String> getTechniqueList() {
    ArrayList<String> techniqueList = new ArrayList<>();
    for (String name : sustainableTechniques.keySet()) {
      String formatedTechnique = name + ": " + sustainableTechniques.get(name);
      techniqueList.add(formatedTechnique);
    }
    return techniqueList;
  }

  /**
   * This toString method returns only the name to use on the ComboBox
   *
   * @return 
   */
  @Override
  public String toString() {
    return getName(); 
  }


}
