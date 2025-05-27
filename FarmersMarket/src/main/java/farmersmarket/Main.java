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

  public static void clientMenu() {
    VBox clientVbox = new VBox();
    Scene clientScene = new Scene(clientVbox, 500, 300);
    stage.setScene(clientScene);
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
    clientVbox.getChildren().addAll(menu, searchFarmer, searchProduct, exit);
    clientVbox.setSpacing(20);
    clientVbox.setAlignment(Pos.CENTER);
    stage.show();
  }

  public static void searchProductMenu() {
    ComboBox<Category> category = new ComboBox<>();

  }

  public static void farmerMenu() {
    VBox farmerVbox = new VBox();
    Scene farmerScene = new Scene(farmerVbox, 500, 300);
    stage.setScene(farmerScene);
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

    farmerVbox.getChildren().addAll(menu, addProduct, addTechnique, exit);
    farmerVbox.setSpacing(20);
    farmerVbox.setAlignment(Pos.CENTER);
    stage.show();

  }

  public static void farmerAddTechnique() {
    VBox farmerVbox = new VBox();
    Scene farmerScene = new Scene(farmerVbox, 500, 300);
    stage.setScene(farmerScene);
    Label techName = new Label("Technique Name");
    TextField techniqueName = new TextField();
    Label techDesc = new Label("Technique Description");
    TextField techniqueDescription = new TextField();
    techniqueDescription.setMinHeight(100);
    Button addTechnique = new Button("Add");
    Button returnButton = new Button("Return");

    addTechnique.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        manager.addBioTechnique(loggedUser.getEmail(), techniqueName.getText(), techniqueDescription.getText());
      }
    });

    returnButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        farmerMenu();
      }
    });

    farmerVbox.getChildren().addAll(techName, techniqueName, techDesc, techniqueDescription, addTechnique, returnButton);
    farmerVbox.setSpacing(10);
    farmerVbox.setAlignment(Pos.CENTER);
    stage.show();

  }

  public static void farmerRegisterProduct() {

    VBox farmerVbox = new VBox();
    Scene farmerScene = new Scene(farmerVbox, 500, 300);
    stage.setScene(farmerScene);
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
    Button returnButton = new Button("Return");

    add.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        manager.registerProduct(loggedUser.getEmail(), productText.getText(), Float.parseFloat(priceValue.getText()),
            Integer.parseInt(stockValue.getText()), category.getValue());
      }
    });

    returnButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        farmerMenu();
      }
    });

    farmerVbox.getChildren().addAll(product, productText, categoryLabel, category, price, priceValue, stock, stockValue, add,
        returnButton);
    farmerVbox.setSpacing(5);
    farmerVbox.setAlignment(Pos.CENTER);

  }

  public static void adminMenu() {

  }

  public static void login() {

    VBox loginVbox = new VBox();
    Scene loginScene = new Scene(loginVbox, 500, 300);
    stage.setScene(loginScene);
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

    loginVbox.setSpacing(20);
    loginVbox.setAlignment(Pos.TOP_LEFT);
    loginVbox.getChildren().addAll(email, emailText, password, passField, loginButton);
    stage.show();
  }

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
   * Creates a new account and writes it to the respective csv file
   *
   */
  public static void createAccount() {
    VBox createVbox = new VBox();
    Scene createScene = new Scene(createVbox, 500, 500);
    stage.setScene(createScene);
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
    createVbox.setSpacing(0);
    createVbox.setAlignment(Pos.TOP_LEFT);
    createVbox.getChildren().addAll(accountType, accountTypes, name, nameText, email, emailText, password, passField,
        birthdate, date, location, locationText, question, questions, answer, answerText, create);
    stage.show();
  }

}
