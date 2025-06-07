package farmersmarket;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.Action;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
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
    stage.show();

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

    String path = System.getProperty("user.dir") + "/images/farmersmarketmenu.png";
    Image image = new Image(new File(path).toURI().toString());
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(600);
    imageView.setPreserveRatio(true);
    DropShadow ds = new DropShadow();
    ds.setColor(Color.rgb(213, 186, 152));
    ds.setSpread(0.42);
    ds.setRadius(40);
    imageView.setEffect(ds);
    Region spacer = new Region();
    spacer.setMinHeight(20);

    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 820, 820);
    stage.setScene(scene);
    Label menu = new Label("Welcome " + loggedUser.getName());
    menu.setFont(new Font(20));
    Button searchFarmer = new Button("Search Farmers");
    Button searchProduct = new Button("Search Products");
    Button logout = new Button("Logout");
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
    logout.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        loggedUser = null;
        loginScreen();
      }
    });
    vbox.getChildren().addAll(imageView, spacer, menu, searchFarmer, searchProduct, logout);
    vbox.setSpacing(20);
    vbox.setAlignment(Pos.CENTER);
  }

  public static void displayFarmerChoiceMenu() {
    String path = System.getProperty("user.dir") + "/images/farmersmarketsearchfarmer.png";
    Image image = new Image(new File(path).toURI().toString());
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(600);
    imageView.setPreserveRatio(true);
    DropShadow ds = new DropShadow();
    ds.setColor(Color.rgb(213, 186, 152));
    ds.setSpread(0.42);
    ds.setRadius(40);
    imageView.setEffect(ds);
    Region spacer = new Region();
    spacer.setMinHeight(20);

    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 820, 820);
    stage.setScene(scene);

    Label farmerLabel = new Label("Choose Farmer");

    ComboBox<Farmer> farmers = new ComboBox<>();
    farmers.getItems().addAll(manager.getFarmerListAlphabetically());

    Button search = new Button("Show Profile");
    Button back = new Button("Back");

    search.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        if (farmers.getValue() == null) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID INPUT");
          alert.setHeaderText(null);
          alert.setContentText("Please select a Farmer!");
          alert.showAndWait();
          return;
        } else {
          displayFarmerProfile(farmers.getValue());
        }
      }
    });
    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        clientMenu();
      }
    });

    vbox.getChildren().addAll(imageView, spacer, farmerLabel, farmers, search, back);
    vbox.setSpacing(20);
    vbox.setAlignment(Pos.CENTER);
  }

  /**
   * This method allows a client to search for products by category
   *
   */
  public static void searchProductMenu() {
    String path = System.getProperty("user.dir") + "/images/farmersmarketsearchproduct.png";
    Image image = new Image(new File(path).toURI().toString());
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(600);
    imageView.setPreserveRatio(true);
    DropShadow ds = new DropShadow();
    ds.setColor(Color.rgb(213, 186, 152));
    ds.setSpread(0.42);
    ds.setRadius(40);
    imageView.setEffect(ds);
    Region spacer = new Region();
    spacer.setMinHeight(20);

    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 820, 820);
    stage.setScene(scene);
    Label categoryLabel = new Label("Category");
    ComboBox<Category> category = new ComboBox<>();
    category.getItems().addAll(Category.FRUIT, Category.VEGETABLE, Category.CEREAL);
    Button search = new Button("Search");
    Button back = new Button("Back");
    search.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        if (category.getValue() == null) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID INPUT");
          alert.setHeaderText(null);
          alert.setContentText("Please select a Category!");
          alert.showAndWait();
          return;
        } else {
          displayProducts(category.getValue());
        }
      }
    });
    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        clientMenu();
      }
    });
    vbox.getChildren().addAll(imageView, spacer, categoryLabel, category, search, back);
    vbox.setSpacing(20);
    vbox.setAlignment(Pos.CENTER);

  }

  public static void displayProducts(Category category) {
    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 820, 820);
    stage.setScene(scene);
    ObservableList<FarmerProduct> productsCategory = FXCollections
        .observableArrayList(manager.getCategoryProducts(category, true));
    ListView<FarmerProduct> productsView = new ListView<>(productsCategory);
    productsView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    ComboBox<String> sorter = new ComboBox<>();
    sorter.getItems().addAll("Price: Ascending", "Price: Descending", "Name: Ascending", "Name: Descending");

    Button cartButton = new Button("Add to cart");
    VBox cart = new VBox();
    cart.getChildren().addAll(cartButton);
    cart.setAlignment(Pos.CENTER_RIGHT);

    cartButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        if (productsView.getSelectionModel().getSelectedItems().isEmpty()) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("SUCCESS");
          alert.setHeaderText(null);
          alert.setContentText("Please select which item(s) you want to add to your cart!");
          alert.showAndWait();
          return;
        } else {
          // add to cart code
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setTitle("SUCCESS");
          alert.setHeaderText(null);
          alert.setContentText("Item(s) added to cart successfully!");
          alert.showAndWait();
          return;
        }
      }
    });

    Label sortProducts = new Label("Sort Products");
    Button sort = new Button("Sort");
    Button back = new Button("Back");

    sort.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        if (sorter.getValue() == null) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID INPUT");
          alert.setHeaderText(null);
          alert.setContentText("Please select a sorting method!");
          alert.showAndWait();
          return;
        }

        if (sorter.getValue().equals("Price: Ascending")) {
          productsCategory.clear();
          productsCategory.addAll(manager.sortedProductsByPrice(category, true));

        } else if (sorter.getValue().equals("Price: Descending")) {
          productsCategory.clear();
          productsCategory.addAll(manager.sortedProductsByPrice(category, false));

        } else if (sorter.getValue().equals("Name: Ascending")) {
          productsCategory.clear();
          productsCategory.addAll(manager.getCategoryProducts(category, true));

        } else if (sorter.getValue().equals("Name: Descending")) {
          productsCategory.clear();
          productsCategory.addAll(manager.getCategoryProducts(category, false));

        }
      }
    });
    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        searchProductMenu();
      }
    });
    vbox.getChildren().addAll(productsView, cart, sortProducts, sorter, sort, back);
    vbox.setAlignment(Pos.TOP_CENTER);
    vbox.setSpacing(15);

  }

  /**
   * This method displays the farmer's menu
   *
   */
  public static void farmerMenu() {

    String path = System.getProperty("user.dir") + "/images/farmersmarketmenu.png";
    Image image = new Image(new File(path).toURI().toString());
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(600);
    imageView.setPreserveRatio(true);
    DropShadow ds = new DropShadow();
    ds.setColor(Color.rgb(213, 186, 152));
    ds.setSpread(0.42);
    ds.setRadius(40);
    imageView.setEffect(ds);
    Region spacer = new Region();
    spacer.setMinHeight(20);

    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 820, 820);
    stage.setScene(scene);
    Label menu = new Label("Welcome " + loggedUser.getName());
    menu.setFont(new Font(20));
    Button addProduct = new Button("Add Product");
    Button addTechnique = new Button("Add Sustainable Agriculture Technique");
    Button logout = new Button("Logout");

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

    logout.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        loggedUser = null;
        loginScreen();
      }
    });

    vbox.getChildren().addAll(imageView, spacer, menu, addProduct, addTechnique, logout);
    vbox.setSpacing(20);
    vbox.setAlignment(Pos.CENTER);

  }

  /**
   * This method allows for the option to add sustainable agriculture technique's
   * to be displayed on the screen and used
   *
   */
  public static void farmerAddTechnique() {
    String path = System.getProperty("user.dir") + "/images/farmersmarketaddtech.png";
    Image image = new Image(new File(path).toURI().toString());
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(600);
    imageView.setPreserveRatio(true);
    DropShadow ds = new DropShadow();
    ds.setColor(Color.rgb(213, 186, 152));
    ds.setSpread(0.42);
    ds.setRadius(40);
    imageView.setEffect(ds);
    Region spacer = new Region();
    spacer.setMinHeight(20);

    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 820, 820);
    stage.setScene(scene);
    Label techName = new Label("Technique Name");
    TextField techniqueName = new TextField();
    techniqueName.setMaxWidth(400);
    Label techDesc = new Label("Technique Description");
    TextArea techniqueDescription = new TextArea();
    techniqueDescription.setMaxWidth(750);
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
        if (techniqueName.getText().isBlank() || techniqueDescription.getText().isBlank()) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID INPUT");
          alert.setHeaderText(null);
          alert.setContentText("All fields are required to be filled!");
          alert.showAndWait();
          return;

        } else if (techniqueDescription.getText().length() > 1000) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID INPUT");
          alert.setHeaderText(null);
          alert.setContentText("The description has to be shorter than 1000 characters!");
          alert.showAndWait();
          return;

        } else {
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setTitle("SUCCESS");
          alert.setHeaderText(null);
          alert.setContentText("Technique added with success!");
          alert.showAndWait();
          manager.addSustainableTechnique(loggedUser.getEmail(), techniqueName.getText(),
              techniqueDescription.getText());
        }
      }
    });

    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        farmerMenu();
      }
    });

    vbox.getChildren().addAll(imageView, spacer, techName, techniqueName, spacer1, techDesc, techniqueDescription,
        spacer2, buttons);
    vbox.setSpacing(5);
    vbox.setAlignment(Pos.CENTER);

  }

  /**
   * This method displays the farmer's register product option on the screen
   */
  public static void farmerRegisterProduct() {
    String path = System.getProperty("user.dir") + "/images/farmersmarketaddprod.png";
    Image image = new Image(new File(path).toURI().toString());
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(600);
    imageView.setPreserveRatio(true);
    DropShadow ds = new DropShadow();
    ds.setColor(Color.rgb(213, 186, 152));
    ds.setSpread(0.42);
    ds.setRadius(40);
    imageView.setEffect(ds);
    Region spacer = new Region();
    spacer.setMinHeight(20);

    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 820, 820);
    stage.setScene(scene);
    Label product = new Label("Product Name");
    TextField productText = new TextField();
    productText.setMaxWidth(300);
    ComboBox<Category> category = new ComboBox<>();
    Label categoryLabel = new Label("Category");
    category.getItems().addAll(Category.FRUIT, Category.VEGETABLE, Category.CEREAL);
    Label price = new Label("Price");
    TextField priceValue = new TextField();
    priceValue.setMaxWidth(50);
    Label stock = new Label("Stock");
    TextField stockValue = new TextField();
    stockValue.setMaxWidth(50);
    Region spacer1 = new Region();
    spacer1.setMinHeight(5);
    Region spacer2 = new Region();
    spacer2.setMinHeight(5);
    Region spacer3 = new Region();
    spacer3.setMinHeight(5);
    Region spacer4 = new Region();
    spacer4.setMinHeight(5);

    Button add = new Button("Add");
    Button back = new Button("Back");
    VBox buttons = new VBox();
    buttons.getChildren().addAll(add, back);
    buttons.setAlignment(Pos.CENTER);
    buttons.setSpacing(10);

    add.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        if (productText.getText().isEmpty() || category.getValue() == null || priceValue.getText().isBlank()
            || stockValue.getText().isBlank()) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID INPUT");
          alert.setHeaderText(null);
          alert.setContentText("All fields are required to be filled!");
          alert.showAndWait();
          return;
        }

        float parsedPrice;
        int parsedStock;

        try {
          parsedPrice = Float.parseFloat(priceValue.getText());
          parsedStock = Integer.parseInt(stockValue.getText());

          if (parsedPrice <= 0 || parsedStock <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("INVALID INPUT");
            alert.setHeaderText(null);
            alert.setContentText("Price and Stock have to be positive numbers!");
            alert.showAndWait();
            return;
          }
        } catch (NumberFormatException e2) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID INPUT");
          alert.setHeaderText(null);
          alert.setContentText("Please enter valid numbers for price and stock!");
          alert.showAndWait();
          return;
        }

        String text = productText.getText();
        String formattedName = "";
        if (text != null && !text.isEmpty()) {
          text = text.trim().toLowerCase();
          if (!text.isEmpty()) {
            formattedName = text.substring(0, 1).toUpperCase()
                + text.substring(1);
          }
        }

        if (!loggedUser.hasProduct(formattedName)) {
          manager.registerProduct(loggedUser.getEmail(), formattedName, loggedUser.getName(),
              parsedPrice, parsedStock, category.getValue());
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setTitle("SUCCESS");
          alert.setHeaderText(null);
          alert.setContentText("Product registered successfully!");
          alert.showAndWait();
        } else {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("PRODUCT ALREADY REGISTERED");
          alert.setHeaderText(null);
          alert.setContentText("This product is already registered!");
          alert.showAndWait();
          return;

        }
      }
    });

    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        farmerMenu();
      }
    });

    vbox.getChildren().addAll(imageView, spacer, product, productText, spacer1, categoryLabel, category, spacer2, price,
        priceValue,
        spacer3, stock, stockValue, spacer4, buttons);
    vbox.setSpacing(5);
    vbox.setAlignment(Pos.CENTER);

  }

  /**
   * This method displays the administrator menu and gives actions to its buttons
   *
   */
  public static void adminMenu() {

    String path = System.getProperty("user.dir") + "/images/farmersmarketmenu.png";
    Image image = new Image(new File(path).toURI().toString());
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(600);
    imageView.setPreserveRatio(true);
    DropShadow ds = new DropShadow();
    ds.setColor(Color.rgb(213, 186, 152));
    ds.setSpread(0.42);
    ds.setRadius(40);
    imageView.setEffect(ds);
    Region spacer = new Region();
    spacer.setMinHeight(20);

    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 820, 820);
    stage.setScene(scene);
    Label warning = new Label("Admins do not yet have any options");
    Button logout = new Button("Logout");
    warning.setFont(new Font(20));

    logout.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        loggedUser = null;
        loginScreen();
      }
    });

    vbox.getChildren().addAll(imageView, spacer, warning, logout);
    vbox.setAlignment(Pos.CENTER);
    vbox.setSpacing(20);

  }

  /**
   * This method allows an user to be logged in and depending o which user for
   * their respective menu screen to be shown
   *
   */
  public static void login() {
    String path = System.getProperty("user.dir") + "/images/farmersmarketlogin.png";
    Image image = new Image(new File(path).toURI().toString());
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(600);
    imageView.setPreserveRatio(true);
    DropShadow ds = new DropShadow();
    ds.setColor(Color.rgb(213, 186, 152));
    ds.setSpread(0.42);
    ds.setRadius(40);
    imageView.setEffect(ds);

    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 820, 820);
    stage.setScene(scene);
    Label email = new Label("Email");
    TextField emailText = new TextField();
    emailText.setMaxWidth(500);
    Label password = new Label("Password");
    PasswordField passField = new PasswordField();
    passField.setMaxWidth(300);
    Button loginButton = new Button("Login");
    Button back = new Button("Back");
    Button changePassword = new Button("Forgot Password");
    Region spacer1 = new Region();
    spacer1.setMinHeight(20);
    Region spacer2 = new Region();
    spacer2.setMinHeight(40);
    Region spacer3 = new Region();
    spacer3.setMinHeight(40);

    VBox buttons = new VBox();
    buttons.getChildren().addAll(loginButton, changePassword, back);
    buttons.setAlignment(Pos.CENTER);
    buttons.setSpacing(10);

    loginButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        User user = manager.searchUser(emailText.getText());
        if (user == null) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("USER NOT FOUND");
          alert.setHeaderText(null);
          alert.setContentText("User not found! Please try a different e-mail or create an account!");
          alert.showAndWait();
          return;
        } else if (!user.getPassword().equals(passField.getText())) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("WRONG CREDENTIALS");
          alert.setHeaderText(null);
          alert.setContentText("The password is wrong! Please try again");
          alert.showAndWait();
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

    changePassword.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        changePasswordScreen();
      }
    });

    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        loginScreen();
      }
    });

    vbox.setSpacing(1);
    vbox.setAlignment(Pos.CENTER);
    vbox.getChildren().addAll(imageView, spacer3, email, emailText, spacer1, password, passField, spacer2, buttons);
  }

  public static void changePasswordScreen() {
    String path = System.getProperty("user.dir") + "/images/farmersmarketchangepw.png";
    Image image = new Image(new File(path).toURI().toString());
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(600);
    imageView.setPreserveRatio(true);
    DropShadow ds = new DropShadow();
    ds.setColor(Color.rgb(213, 186, 152));
    ds.setSpread(0.42);
    ds.setRadius(40);
    imageView.setEffect(ds);
    Region spacer = new Region();
    spacer.setMinHeight(20);

    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 820, 820);
    stage.setScene(scene);
    Label emailLabel = new Label("Enter your email:");
    TextField email = new TextField();
    email.setMaxWidth(400);
    Button confirm = new Button("Confirm");
    Button back = new Button("Back");
    Region spacer1 = new Region();
    spacer1.setMinHeight(20);

    confirm.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        User user = manager.searchUser(email.getText());
        if (user == null) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("USER NOT FOUND");
          alert.setHeaderText(null);
          alert.setContentText("This email is not registered!");
          alert.showAndWait();
          return;
        } else {
          checkSecurityQuestion(user);
        }
      }
    });

    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        login();
      }
    });

    vbox.setSpacing(10);
    vbox.setAlignment(Pos.CENTER);
    vbox.getChildren().addAll(imageView, spacer, emailLabel, email, spacer1, confirm, back);

  }

  public static void checkSecurityQuestion(User user) {
    String path = System.getProperty("user.dir") + "/images/farmersmarketchangepw.png";
    Image image = new Image(new File(path).toURI().toString());
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(600);
    imageView.setPreserveRatio(true);
    DropShadow ds = new DropShadow();
    ds.setColor(Color.rgb(213, 186, 152));
    ds.setSpread(0.42);
    ds.setRadius(40);
    imageView.setEffect(ds);
    Region spacer = new Region();
    spacer.setMinHeight(20);

    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 820, 820);
    stage.setScene(scene);
    Label secQuestion = new Label(user.getQuestion().toString());
    TextField answer = new TextField();
    answer.setMaxWidth(400);
    Button check = new Button("Check");
    Button abort = new Button("Abort");

    check.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        if (user.getAnswer().equals(answer.getText())) {
          changePassword(user);
        } else {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("WRONG ANSWER");
          alert.setHeaderText(null);
          alert.setContentText("The answer to the security question is incorrect!");
          alert.showAndWait();
          return;
        }
      }
    });

    abort.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        login();
      }
    });

    vbox.getChildren().addAll(imageView, spacer, secQuestion, answer, check, abort);
    vbox.setSpacing(20);
    vbox.setAlignment(Pos.CENTER);

  }

  public static void changePassword(User user) {
    String path = System.getProperty("user.dir") + "/images/farmersmarketchangepw.png";
    Image image = new Image(new File(path).toURI().toString());
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(600);
    imageView.setPreserveRatio(true);
    DropShadow ds = new DropShadow();
    ds.setColor(Color.rgb(213, 186, 152));
    ds.setSpread(0.42);
    ds.setRadius(40);
    imageView.setEffect(ds);
    Region spacer = new Region();
    spacer.setMinHeight(20);
    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 820, 820);
    stage.setScene(scene);

    Label pw = new Label("Choose a new Password");
    PasswordField newPw = new PasswordField();
    newPw.setMaxWidth(400);
    Button change = new Button("Change");
    Button abort = new Button("Abort");

    change.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        if (manager.isPasswordValid(newPw.getText())) {
          manager.changePassword(user.getEmail(), newPw.getText());
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setTitle("SUCCESS");
          alert.setHeaderText(null);
          alert.setContentText("Password changed successfully!");
          alert.showAndWait();
          login();
        } else {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID PASSWORD");
          alert.setHeaderText(null);
          alert.setContentText(
              "Password must be between 8–16 characters and contain at least one number or special character!");
          alert.showAndWait();
          return;
        }
      }
    });

    abort.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        login();
      }
    });

    vbox.getChildren().addAll(imageView, spacer, pw, newPw, change, abort);
    vbox.setSpacing(20);
    vbox.setAlignment(Pos.CENTER);
  }

  /**
   * This method displays the main menu and gives actions to its buttons
   *
   */
  public static void loginScreen() {

    String path = System.getProperty("user.dir") + "/images/farmersmarket.png";
    Image image = new Image(new File(path).toURI().toString());
    ImageView imageView = new ImageView(image);
    imageView.setFitHeight(450);
    imageView.setPreserveRatio(true);
    imageView.setBlendMode(BlendMode.SRC_ATOP);
    Bloom bloom = new Bloom();
    imageView.setEffect(bloom);
    DropShadow ds = new DropShadow();
    ds.setColor(Color.rgb(38, 69, 62));
    ds.setSpread(0.42);
    ds.setRadius(40);
    imageView.setEffect(ds);

    // ImageView imageView = new ImageView();
    // imageView.setFitHeight(200);
    // imageView.setPreserveRatio(true);
    // FileChooser fileChooser = new FileChooser();
    // fileChooser.getExtensionFilters().add(
    // new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg",
    // "*.gif"));
    //
    // File file = fileChooser.showOpenDialog(stage);
    // if (file != null) {
    // Image image = new Image(file.toURI().toString());
    // imageView.setImage(image);
    // }

    VBox vbox = new VBox();
    Button login = new Button("Login");
    Button create = new Button("Create Account");
    Button exit = new Button("Exit");
    Region spacer = new Region();
    spacer.setMinHeight(8);
    vbox.getChildren().addAll(imageView, spacer, login, create, exit);
    Scene startScene = new Scene(vbox, 820, 820);
    vbox.setSpacing(17);
    vbox.setAlignment(Pos.CENTER);
    stage.setScene(startScene);

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
    Scene scene = new Scene(vbox, 820, 820);
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
    Button backButton = new Button("Back");
    VBox back = new VBox();
    back.getChildren().addAll(backButton);
    back.setAlignment(Pos.CENTER);

    Label techniqueLabel = new Label("Sustainable Agriculture Techniques: ");
    techniqueLabel.setFont(new Font(17));
    ObservableList<String> bioTechniques = FXCollections.observableArrayList(farmer.getTechniqueList());
    ListView<String> bioTechniquesView = new ListView<>(bioTechniques);
    bioTechniquesView.setPrefHeight(150);

    Button cartButton = new Button("Add to cart");
    VBox cart = new VBox();
    cart.getChildren().addAll(cartButton);
    cart.setAlignment(Pos.CENTER_RIGHT);

    cartButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCCESS");
        alert.setHeaderText(null);
        alert.setContentText("Item(s) added to cart successfully!");
        alert.showAndWait();
        return;
      }
    });

    backButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        clientMenu();
      }
    });

    vbox.setSpacing(5);
    vbox.setAlignment(Pos.TOP_LEFT);
    vbox.getChildren().addAll(farmerInfo, farmerName, farmerLoc, spacer1, productLabel, farmerProductsView, cart,
        spacer2,
        techniqueLabel, bioTechniquesView, back);
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
    Scene scene = new Scene(vbox, 820, 820);
    stage.setScene(scene);
    Label adminCode = new Label("What is the admin code?");
    PasswordField secretCode = new PasswordField();
    Button create = new Button("Create admin account");
    Button back = new Button("Back");

    create.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        if (adminVerify(secretCode.getText())) {
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setTitle("SUCCESS");
          alert.setHeaderText(null);
          alert.setContentText("Admin account created with success");
          alert.showAndWait();

          manager.registerUser(name, email, date, password, location, question, answer, accountType);
          login();
        } else {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("WRONG CODE");
          alert.setHeaderText(null);
          alert.setContentText("The Admin code is wrong! Please try again");
          alert.showAndWait();
        }
      }
    });

    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        loginScreen();
      }
    });

    vbox.setSpacing(15);
    vbox.setAlignment(Pos.CENTER);
    vbox.getChildren().addAll(adminCode, secretCode, create, back);

  }

  /**
   * This method creates a new account and writes it to the respective csv file
   *
   */
  public static void createAccount() {
    String path = System.getProperty("user.dir") + "/images/farmersmarketacc.png";
    Image image = new Image(new File(path).toURI().toString());
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(350);
    imageView.setPreserveRatio(true);
    DropShadow ds = new DropShadow();
    ds.setColor(Color.rgb(213, 186, 152));
    ds.setSpread(0.42);
    ds.setRadius(40);
    imageView.setEffect(ds);
    VBox imageBox = new VBox();
    imageBox.getChildren().addAll(imageView);
    imageBox.setAlignment(Pos.TOP_CENTER);

    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 820, 820);
    stage.setScene(scene);
    Label accountType = new Label("Account Type");
    ComboBox<String> accountTypes = new ComboBox<>();
    accountTypes.getItems().addAll("Client", "Farmer", "Admin");
    Label name = new Label("Name");
    TextField nameText = new TextField();
    nameText.setMaxWidth(400);
    Label email = new Label("Email");
    TextField emailText = new TextField();
    emailText.setMaxWidth(500);
    Label password = new Label("Password");
    PasswordField passField = new PasswordField();
    passField.setMaxWidth(350);
    Label birthdate = new Label("Birthdate");
    DatePicker date = new DatePicker();
    Label location = new Label("Location");
    TextField locationText = new TextField();
    locationText.setMaxWidth(250);
    Button create = new Button("Create Account");
    Label question = new Label("Security Question");
    ComboBox<SecurityQuestion> questions = new ComboBox<>();
    questions.getItems().addAll(SecurityQuestion.FAV_FOOD, SecurityQuestion.FAV_SONG, SecurityQuestion.FIRST_PET,
        SecurityQuestion.BIRTH_PLACE);
    Label answer = new Label("Answer");
    TextField answerText = new TextField();
    answerText.setMaxWidth(300);
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

        if (nameText.getText().isBlank() || emailText.getText().isBlank() || date.getValue().toString().isBlank()
            || locationText.getText().isBlank() || questions.getValue() == null
            || answerText.getText().isBlank() || accountTypes.getValue() == null) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID INPUT");
          alert.setHeaderText(null);
          alert.setContentText("All fields are required to be filled!");
          alert.showAndWait();
          return;
        }

        if (date.getValue().isAfter(LocalDate.now())) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID INPUT");
          alert.setHeaderText(null);
          alert.setContentText("Please be born already!");
          alert.showAndWait();
          return;

        }

        if (!manager.verifyEmail(emailText.getText())) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("USER ALREADY REGISTERED");
          alert.setHeaderText(null);
          alert.setContentText(
              "Email already registered! Please try a different e-mail or login with the existing account!");
          alert.showAndWait();
          return;
        }

        if (!manager.isPasswordValid(passField.getText())) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID PASSWORD");
          alert.setHeaderText(null);
          alert.setContentText(
              "Password must be between 8–16 characters and contain at least one number or special character!");
          alert.showAndWait();
          return;
        }

        if (accountTypes.getValue().equals(Admin.class.getSimpleName())) {
          adminCreate(nameText.getText(), emailText.getText().toLowerCase(),
              date.getValue(), passField.getText(),
              locationText.getText(), questions.getValue(), answerText.getText(),
              accountTypes.getValue());
        } else {
          if (accountTypes.getValue().equals(Client.class.getSimpleName())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUCCESS");
            alert.setHeaderText(null);
            alert.setContentText("Client account created with success");
            alert.showAndWait();

          } else if (accountTypes.getValue().equals(Farmer.class.getSimpleName())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUCCESS");
            alert.setHeaderText(null);
            alert.setContentText("Farmer account created with success");
            alert.showAndWait();
          }

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
    vbox.setPadding(new Insets(10, 0, 0, 30));
    vbox.setAlignment(Pos.TOP_LEFT);
    vbox.getChildren().addAll(imageBox, accountType, accountTypes, spacer1, name, nameText, spacer2, email, emailText,
        spacer3,
        password, passField, spacer4,
        birthdate, date, spacer5, location, locationText, spacer6, question, questions, spacer7, answer, answerText,
        spacer8, buttons);
  }
}
