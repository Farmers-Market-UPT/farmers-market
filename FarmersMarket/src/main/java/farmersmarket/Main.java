package farmersmarket;

import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Main class of Farmers Market
 *
 * @author Diogo Ferreira (51084)
 * @author Sara Canelas (51297)
 * @author Tomás Falcão (51258)
 * @author Francisca Silva (51765)
 */
public class Main extends Application {

  private static FarmersMarket manager = new FarmersMarket();
  private static Stage stage;
  private static User loggedUser = null;
  Scanner input = new Scanner(System.in);

  public void start(Stage stage) {

    manager.readData();
    Main.stage = stage;
    stage.setTitle("Farmers Market");

    loginScreen();

    if (loggedUser != null && loggedUser instanceof Client) {
      clientMenu();
    }

  }

  public static void main(String[] args) {
    launch(args);
  }

  /**
   * This method displays the client's menu
   *
   */
  public static void clientMenu() {
    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 500, 300);
    stage.setScene(scene);
    Label menu = new Label("Welcome " + loggedUser.getName());
    Button searchFarmer = new Button("Search Farmers");
    Button searchProduct = new Button("Search Products");
    Button exit = new Button("Exit");
    searchFarmer.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        manager.displayFarmersAlphabetically();
      }
    });
    searchProduct.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        searchProductMenu();
      }
    });
    exit.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        System.exit(0);
      }
    });
    vbox.getChildren().addAll(menu, searchFarmer, searchProduct, exit);
    vbox.setSpacing(20);
    vbox.setAlignment(Pos.CENTER);
    stage.show();
  }
  
  /**
   * This method allows a client to search for products by category
   *
   */

  public static void searchProductMenu() {
    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 500, 300);
    stage.setScene(scene);
    Label categoryLabel = new Label("Category");
    ComboBox<Category> category = new ComboBox<>();
    category.getItems().addAll(Category.FRUIT, Category.VEGETABLE, Category.CEREAL);
    Button search = new Button("Search");
    Button back = new Button("Back");
    search.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        manager.displayProductsByCategory(category.getValue());
      }
    });
    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        clientMenu();
      }
    });
    vbox.getChildren().addAll(categoryLabel, category, search, back);
    vbox.setSpacing(20);
    vbox.setAlignment(Pos.CENTER);
    stage.show();

  }

  /**
   * This method displays the farmer's menu
   *
   */
  public static void farmerMenu() {
    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 500, 300);
    stage.setScene(scene);
    Label menu = new Label("Welcome " + loggedUser.getName());
    Button addProduct = new Button("Add Product");
    Button addTechnique = new Button("Add Bio Technique");
    Button exit = new Button("Exit");

    addProduct.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        farmerRegisterProduct();
      }
    });

    addTechnique.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        farmerAddTechnique();
      }
    });

    exit.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        System.exit(0);
      }
    });

    vbox.getChildren().addAll(menu, addProduct, addTechnique, exit);
    vbox.setSpacing(20);
    vbox.setAlignment(Pos.CENTER);
    stage.show();

  }

  /**
   * This method allows for the option to add sustainable agriculture technique's to be displayed on the screen and used
   *
   */
  public static void farmerAddTechnique() {
    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 500, 300);
    stage.setScene(scene);
    Label techName = new Label("Technique Name");
    TextField techniqueName = new TextField();
    Label techDesc = new Label("Technique Description");
    TextField techniqueDescription = new TextField();
    techniqueDescription.setMinHeight(100);
    Button addTechnique = new Button("Add");
    Button back = new Button("Back");

    addTechnique.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        manager.addSustainableTechnique(loggedUser.getEmail(), techniqueName.getText(), techniqueDescription.getText());
      }
    });

    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        farmerMenu();
      }
    });

    vbox.getChildren().addAll(techName, techniqueName, techDesc, techniqueDescription, addTechnique,
        back);
    vbox.setSpacing(10);
    vbox.setAlignment(Pos.CENTER);
    stage.show();

  }

  /**
   * This method displays the farmer's register product option on the screen
   */
  public static void farmerRegisterProduct() {

    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 500, 300);
    stage.setScene(scene);
    Label product = new Label("Add Product");
    TextField productText = new TextField();
    ComboBox<Category> category = new ComboBox<>();
    Label categoryLabel = new Label("Category");
    category.getItems().addAll(Category.FRUIT, Category.VEGETABLE, Category.CEREAL);
    Label price = new Label("Price");
    TextField priceValue = new TextField();
    Label stock = new Label("Stock");
    TextField stockValue = new TextField();
    Button add = new Button("Add");
    Button back = new Button("Back");

    add.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        manager.registerProduct(loggedUser.getEmail(), productText.getText(), Float.parseFloat(priceValue.getText()),
            Integer.parseInt(stockValue.getText()), category.getValue());
      }
    });

    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        farmerMenu();
      }
    });

    vbox.getChildren().addAll(product, productText, categoryLabel, category, price, priceValue, stock, stockValue, add, back);
    vbox.setSpacing(5);
    vbox.setAlignment(Pos.CENTER);

  }

  /**
   * This method displays the administrator menu and gives actions to its buttons
   *
   */
  public static void adminMenu() {

  }

  /**
   * LThis method allows an user to be logged in and depending o which user for their respective menu screen to be shown
   *
   */
  public static void login() {

    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 500, 300);
    stage.setScene(scene);
    Label email = new Label("Email");
    TextField emailText = new TextField();
    Label password = new Label("Password");
    PasswordField passField = new PasswordField();
    Button loginButton = new Button("Login");

    loginButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        User user = manager.searchUser(emailText.getText());
        if (user == null) {
          System.out.println("User not found!");
          return;
        } else if (!user.getPassword().equals(passField.getText())) {
          System.out.println("Wrong credentials!");
          return;
        }
        loggedUser = user;

        if (loggedUser instanceof Client) {
          clientMenu();
        } else if (loggedUser instanceof Farmer) {
          farmerMenu();
        } else if (loggedUser instanceof Admin) {
          adminMenu();
        }
      }
    });

    vbox.setSpacing(20);
    vbox.setAlignment(Pos.TOP_LEFT);
    vbox.getChildren().addAll(email, emailText, password, passField, loginButton);
    stage.show();
  }

  /**
   * This method displays the main menu and gives actions to its buttons
   *
   */
  public static void loginScreen() {
    VBox vbox = new VBox();
    Label welcome = new Label("Welcome to Farmers Market");
    welcome.setFont(new Font(25));
    Button login = new Button("Login");
    Button create = new Button("Create Account");
    Button exit = new Button("Exit");
    vbox.getChildren().addAll(welcome, login, create, exit);
    Scene startScene = new Scene(vbox, 500, 300);
    vbox.setSpacing(20);
    vbox.setAlignment(Pos.CENTER);
    stage.setScene(startScene);
    stage.show();

    login.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent a) {
        login();
      }
    });

    create.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        createAccount();
      }
    });

    exit.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        System.exit(0);
      }
    });
  }

  /**
   * This method creates a new account and writes it to the respective csv file
   *
   */
  public static void createAccount() {
    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 500, 500);
    stage.setScene(scene);
    Label accountType = new Label("Account Type");
    ComboBox<String> accountTypes = new ComboBox<>();
    accountTypes.getItems().addAll("Client", "Farmer", "Admin");
    Label name = new Label("Name");
    TextField nameText = new TextField();
    Label email = new Label("Email");
    TextField emailText = new TextField();
    Label password = new Label("Password");
    PasswordField passField = new PasswordField();
    Label birthdate = new Label("Birthdate");
    DatePicker date = new DatePicker();
    Label location = new Label("Location");
    TextField locationText = new TextField();
    Button create = new Button("Create Account");
    Label question = new Label("Security Question");
    ComboBox<SecurityQuestion> questions = new ComboBox<>();
    questions.getItems().addAll(SecurityQuestion.FAV_FOOD, SecurityQuestion.FAV_SONG, SecurityQuestion.FIRST_PET,
        SecurityQuestion.BIRTH_PLACE);
    Label answer = new Label("Answer");
    TextField answerText = new TextField();

    create.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        manager.registerUser(nameText.getText(), emailText.getText(),
            date.getValue(), passField.getText(),
            locationText.getText(), questions.getValue(), answerText.getText(),
            accountTypes.getValue());
        loginScreen();
      }
    });
    vbox.setSpacing(0);
    vbox.setAlignment(Pos.TOP_LEFT);
    vbox.getChildren().addAll(accountType, accountTypes, name, nameText, email, emailText, password, passField,
        birthdate, date, location, locationText, question, questions, answer, answerText, create);
    stage.show();
  }

}
