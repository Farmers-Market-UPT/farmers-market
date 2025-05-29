package farmersmarket;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
    Scene scene = new Scene(vbox, 800, 800);
    stage.setScene(scene);
    Label menu = new Label("Welcome " + loggedUser.getName());
    menu.setFont(new Font(20));
    Button searchFarmer = new Button("Search Farmers");
    Button searchProduct = new Button("Search Products");
    Button exit = new Button("Exit");
    searchFarmer.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        displayFarmerChoiceMenu();
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

  public static void displayFarmerChoiceMenu() {

    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 800, 800);
    stage.setScene(scene);

    Label farmerLabel = new Label("Choose Farmer");

    ComboBox<String> farmers = new ComboBox<>();

    ArrayList<Farmer> farmerList = manager.getFarmerListAlphabetically();
    for (Farmer farmer : farmerList) {
      farmers.getItems().add(farmer.getName());
    }

    Button search = new Button("Show Profile");
    Button back = new Button("Back");

    search.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        // tbd
        for (Farmer farmer : farmerList) {
          if (farmer.getName() == farmers.getValue()) {
            displayFarmerProfile(farmer);
          }
        }

      }
    });
    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        clientMenu();
      }
    });

    vbox.getChildren().addAll(farmerLabel, farmers, search, back);
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
    Scene scene = new Scene(vbox, 800, 800);
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
    Scene scene = new Scene(vbox, 800, 800);
    stage.setScene(scene);
    Label menu = new Label("Welcome " + loggedUser.getName());
    menu.setFont(new Font(20));
    Button addProduct = new Button("Add Product");
    Button addTechnique = new Button("Add Sustainable Agriculture Technique");
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
   * This method allows for the option to add sustainable agriculture technique's
   * to be displayed on the screen and used
   *
   */
  public static void farmerAddTechnique() {
    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 800, 800);
    stage.setScene(scene);
    Label techName = new Label("Technique Name");
    TextField techniqueName = new TextField();
    Label techDesc = new Label("Technique Description");
    TextArea techniqueDescription = new TextArea();
    techniqueDescription.setMinHeight(100);
    Button addTechnique = new Button("Add");
    Button back = new Button("Back");
    Region spacer1 = new Region();
    spacer1.setMinHeight(20);
    Region spacer2 = new Region();
    spacer2.setMinHeight(20);
    VBox buttons = new VBox();
    buttons.getChildren().addAll(addTechnique, back);
    buttons.setAlignment(Pos.CENTER);
    buttons.setSpacing(10);

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

    vbox.getChildren().addAll(techName, techniqueName, spacer1, techDesc, techniqueDescription, spacer2, buttons);
    vbox.setSpacing(5);
    vbox.setAlignment(Pos.TOP_LEFT);
    stage.show();

  }

  /**
   * This method displays the farmer's register product option on the screen
   */
  public static void farmerRegisterProduct() {

    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 800, 800);
    stage.setScene(scene);
    Label product = new Label("Product Name");
    TextField productText = new TextField();
    ComboBox<Category> category = new ComboBox<>();
    Label categoryLabel = new Label("Category");
    category.getItems().addAll(Category.FRUIT, Category.VEGETABLE, Category.CEREAL);
    Label price = new Label("Price");
    TextField priceValue = new TextField();
    Label stock = new Label("Stock");
    TextField stockValue = new TextField();
    Region spacer1 = new Region();
    spacer1.setMinHeight(20);
    Region spacer2 = new Region();
    spacer2.setMinHeight(20);
    Region spacer3 = new Region();
    spacer3.setMinHeight(20);
    Region spacer4 = new Region();
    spacer4.setMinHeight(20);

    Button add = new Button("Add");
    Button back = new Button("Back");
    VBox buttons = new VBox();
    buttons.getChildren().addAll(add, back);
    buttons.setAlignment(Pos.CENTER);
    buttons.setSpacing(10);

    String formattedName = (productText.getText() != null && !productText.getText().isEmpty())
        ? productText.getText().substring(0, 1).toUpperCase() + productText.getText().substring(1).toLowerCase()
        : "";

    add.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        manager.registerProduct(loggedUser.getEmail(), formattedName, Float.parseFloat(priceValue.getText()),
            Integer.parseInt(stockValue.getText()), category.getValue());
      }
    });

    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        farmerMenu();
      }
    });

    vbox.getChildren().addAll(product, productText, spacer1, categoryLabel, category, spacer2, price, priceValue,
        spacer3, stock, stockValue, spacer4, buttons);
    vbox.setSpacing(5);
    vbox.setAlignment(Pos.TOP_LEFT);

  }

  /**
   * This method displays the administrator menu and gives actions to its buttons
   *
   */
  public static void adminMenu() {
    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 800, 800);
    stage.setScene(scene);
    Label warning = new Label("Admins do not yet have any options");
    Button exit = new Button("Exit");
    warning.setFont(new Font(20));

    exit.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        System.exit(0);
      }
    });

    vbox.getChildren().addAll(warning, exit);
    vbox.setAlignment(Pos.CENTER);
    vbox.setSpacing(20);
    stage.show();

  }

  /**
   * This method allows an user to be logged in and depending o which user for
   * their respective menu screen to be shown
   *
   */
  public static void login() {

    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 800, 800);
    stage.setScene(scene);
    Label email = new Label("Email");
    TextField emailText = new TextField();
    Label password = new Label("Password");
    PasswordField passField = new PasswordField();
    Button loginButton = new Button("Login");
    Button back = new Button("Back");
    Region spacer1 = new Region();
    spacer1.setMinHeight(20);
    Region spacer2 = new Region();
    spacer2.setMinHeight(40);

    VBox buttons = new VBox();
    buttons.getChildren().addAll(loginButton, back);
    buttons.setAlignment(Pos.CENTER);
    buttons.setSpacing(10);

    loginButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        User user = manager.searchUser(emailText.getText());
        if (user == null) {
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

    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        loginScreen();
      }
    });

    vbox.setSpacing(0);
    vbox.setAlignment(Pos.CENTER_LEFT);
    vbox.getChildren().addAll(email, emailText, spacer1, password, passField, spacer2, buttons);
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
    Region spacer = new Region();
    spacer.setMinHeight(70);
    vbox.getChildren().addAll(welcome, spacer, login, create, exit);
    Scene startScene = new Scene(vbox, 800, 800);
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

  public static void displayFarmerProfile(Farmer farmer) {

    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 800, 800);
    stage.setScene(scene);

    Label farmerInfo = new Label("Farmer Information: ");
    farmerInfo.setFont(new Font(17));
    Label farmerName = new Label("Name: " + farmer.getName());
    Label farmerLoc = new Label("Location: " + farmer.getLocation());

    Region spacer1 = new Region();
    spacer1.setMinHeight(20);
    Region spacer2 = new Region();
    spacer2.setMinHeight(20);

    Label productLabel = new Label("Products: ");
    productLabel.setFont(new Font(17));
    ObservableList<FarmerProduct> farmerProducts = FXCollections.observableArrayList(farmer.getFarmerProducts());
    ListView<FarmerProduct> farmerProductsView = new ListView<>(farmerProducts);
    Button back = new Button("Back");

    Label techniqueLabel = new Label("Sustainable Agriculture Techniques: ");
    techniqueLabel.setFont(new Font(17));
    ObservableList<String> bioTechniques = FXCollections.observableArrayList(farmer.getTechniqueList());
    ListView<String> bioTechniquesView = new ListView<>(bioTechniques);
    bioTechniquesView.setPrefHeight(200);

    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        clientMenu();
      }
    });

    vbox.setSpacing(5);
    vbox.setAlignment(Pos.TOP_LEFT);
    vbox.getChildren().addAll(farmerInfo, farmerName, farmerLoc, spacer1, productLabel, farmerProductsView, spacer2,
        techniqueLabel, bioTechniquesView, back);
    stage.show();
  }

  public static boolean adminVerify(String code) {
    if (code.equals("Lasagna")) {
      return true;
    }
    return false;
  }

  public static void adminCreate(String name, String email,
      LocalDate date, String password,
      String location, SecurityQuestion question, String answer,
      String accountType) {

    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 800, 800);
    stage.setScene(scene);
    Label adminCode = new Label("What is the admin code?");
    PasswordField secretCode = new PasswordField();
    Button create = new Button("Create admin account");
    Button back = new Button("Back");

    create.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        if (adminVerify(secretCode.getText())) {
          manager.registerUser(name, email, date, password, location, question, answer, accountType);
          login();
        } else {
          System.out.println("Wrong code!!! Returning...");
          loginScreen();
        }
      }
    });

    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        loginScreen();
      }
    });

    vbox.setSpacing(0);
    vbox.setAlignment(Pos.TOP_LEFT);
    vbox.getChildren().addAll(adminCode, secretCode, create, back);
    stage.show();

  }

  /**
   * This method creates a new account and writes it to the respective csv file
   *
   */
  public static void createAccount() {
    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 800, 800);
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
    Button back = new Button("Back");
    Region spacer1 = new Region();
    spacer1.setMinHeight(20);
    Region spacer2 = new Region();
    spacer2.setMinHeight(20);
    Region spacer3 = new Region();
    spacer3.setMinHeight(20);
    Region spacer4 = new Region();
    spacer4.setMinHeight(20);
    Region spacer5 = new Region();
    spacer5.setMinHeight(20);
    Region spacer6 = new Region();
    spacer6.setMinHeight(20);
    Region spacer7 = new Region();
    spacer7.setMinHeight(20);
    Region spacer8 = new Region();
    spacer8.setMinHeight(20);
    VBox buttons = new VBox();
    buttons.getChildren().addAll(create, back);
    buttons.setAlignment(Pos.CENTER);
    buttons.setSpacing(10);

    create.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        if (accountTypes.getValue().equals(Admin.class.getSimpleName())) {
          adminCreate(nameText.getText(), emailText.getText().toLowerCase(),
              date.getValue(), passField.getText(),
              locationText.getText(), questions.getValue(), answerText.getText(),
              accountTypes.getValue());
        } else {
          manager.registerUser(nameText.getText(), emailText.getText().toLowerCase(),
              date.getValue(), passField.getText(),
              locationText.getText(), questions.getValue(), answerText.getText(),
              accountTypes.getValue());
          loginScreen();
        }
      }
    });

    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        loginScreen();
      }
    });

    vbox.setSpacing(0);
    vbox.setAlignment(Pos.TOP_LEFT);
    vbox.getChildren().addAll(accountType, accountTypes, spacer1, name, nameText, spacer2, email, emailText, spacer3,
        password, passField, spacer4,
        birthdate, date, spacer5, location, locationText, spacer6, question, questions, spacer7, answer, answerText,
        spacer8, buttons);
    stage.show();
  }
}
