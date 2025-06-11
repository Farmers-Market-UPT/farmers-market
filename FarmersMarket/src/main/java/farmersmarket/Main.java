package farmersmarket;

import java.io.File;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

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
    scene.setFill(Color.rgb(248, 236, 215));
    stage.setScene(scene);
    Label menu = new Label("Welcome " + loggedUser.getName());
    menu.setFont(new Font(20));
    Button searchFarmer = new Button("Search Farmers");
    Button searchProduct = new Button("Search Products");
    Button cartButton = new Button("Shopping Cart");
    Button orderHistory = new Button("Order History");
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

    cartButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        displayCart();
      }
    });

    orderHistory.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        displayClientOrders();
      }
    });

    logout.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        loggedUser = null;
        loginScreen();
      }
    });

    vbox.getChildren().addAll(imageView, spacer, menu, searchFarmer, searchProduct, cartButton, orderHistory, logout);
    vbox.setSpacing(20);
    vbox.setAlignment(Pos.CENTER);
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");
  }

  public static void displayFarmerSales() {
    String path = System.getProperty("user.dir") + "/images/farmersmarketvieworders.png";
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
    scene.setFill(Color.rgb(248, 236, 215));
    stage.setScene(scene);
    ObservableList<Order> orders = FXCollections
        .observableArrayList(((Farmer) loggedUser).getSales());
    ListView<Order> ordersView = new ListView<>(orders);
    ordersView.setMaxHeight(270);
    Button view = new Button("View");
    Button back = new Button("Back");

    view.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        VBox popupVbox = new VBox();
        popupVbox.setMinWidth(400);
        Label farmerLabel = new Label(
            "Buyer: " + ordersView.getSelectionModel().getSelectedItem().getClient().getName());
        farmerLabel.setFont(new Font(20));
        ObservableList<CartItem> items = FXCollections
            .observableArrayList(ordersView.getSelectionModel().getSelectedItem().getItems());
        ListView<CartItem> itemsView = new ListView<>(items);
        popupVbox.getChildren().addAll(farmerLabel, itemsView);
        Popup popup = new Popup();
        popup.getContent().addAll(popupVbox);
        popup.setWidth(700);
        popup.setAutoHide(true);
        popup.show(stage);
      }
    });

    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        farmerMenu();
      }
    });

    vbox.getChildren().addAll(imageView, spacer, ordersView, view, back);
    vbox.setSpacing(10);
    vbox.setAlignment(Pos.CENTER);
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");
  }

  public static void viewFarmerProducts() {
    String path = System.getProperty("user.dir") + "/images/farmersmarketviewproducts.png";
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
    scene.setFill(Color.rgb(248, 236, 215));
    stage.setScene(scene);
    ObservableList<FarmerProduct> products = FXCollections
        .observableArrayList(((Farmer) loggedUser).getFarmerProducts());
    ListView<FarmerProduct> productsView = new ListView<>(products);
    productsView.setMaxHeight(270);
    Button editQuant = new Button("Change quantity");
    Button editPrice = new Button("Change price");
    Button back = new Button("Back");
    HBox buttons = new HBox();
    buttons.getChildren().addAll(editQuant, editPrice, back);
    buttons.setAlignment(Pos.CENTER);
    buttons.setSpacing(30);

    editQuant.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        FarmerProduct productToChange = productsView.getSelectionModel().getSelectedItem();

        if (productToChange == null) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID INPUT");
          alert.setHeaderText(null);
          alert.setContentText("Select an item to change the stock!");
          alert.showAndWait();
          return;
        }

        TextInputDialog amount = new TextInputDialog(String.valueOf(productToChange.getStock()));
        amount.setTitle("Choose Quantity");
        amount.setHeaderText("Enter Quantity for: " + productToChange.getProductName());
        amount.setContentText("Quantity:");
        Optional<String> result = amount.showAndWait();

        TextField amountText = amount.getEditor();
        int quant;
        try {
          quant = Integer.parseInt(amountText.getText());

        } catch (NumberFormatException e2) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID INPUT");
          alert.setHeaderText(null);
          alert.setContentText("Invalid Quantity!");
          alert.showAndWait();
          return;
        }

        if (!result.isPresent()) {
          return;
        }

        if (quant < 0) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID INPUT");
          alert.setHeaderText(null);
          alert.setContentText("The stock cannot be under zero!");
          alert.showAndWait();
          return;
        }
        manager.changeStockProduct((Farmer) loggedUser, productToChange, quant);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCCESS");
        alert.setHeaderText(null);
        alert.setContentText("Stock updated with success!");
        alert.showAndWait();
        productsView.refresh();
      }
    });

    editPrice.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        FarmerProduct productToChange = productsView.getSelectionModel().getSelectedItem();

        if (productToChange == null) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID INPUT");
          alert.setHeaderText(null);
          alert.setContentText("Select an item to change the price!");
          alert.showAndWait();
          return;
        }

        TextInputDialog amount = new TextInputDialog(String.valueOf(productToChange.getPrice()));
        amount.setTitle("Choose Price");
        amount.setHeaderText("Enter Quantity for: " + productToChange.getProductName());
        amount.setContentText("Price:");
        Optional<String> result = amount.showAndWait();

        TextField amountText = amount.getEditor();
        double price;
        try {
          price = Double.parseDouble(amountText.getText());

        } catch (NumberFormatException e2) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID INPUT");
          alert.setHeaderText(null);
          alert.setContentText("Invalid Price!");
          alert.showAndWait();
          return;
        }

        if (!result.isPresent()) {
          return;
        }

        if (price < 0) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID INPUT");
          alert.setHeaderText(null);
          alert.setContentText("The price cannot be under zero!");
          alert.showAndWait();
          return;
        }
        manager.changePriceProduct((Farmer) loggedUser, productToChange, price);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCCESS");
        alert.setHeaderText(null);
        alert.setContentText("Price updated with success!");
        alert.showAndWait();
        productsView.refresh();
      }
    });

    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        farmerMenu();
      }
    });

    vbox.getChildren().addAll(imageView, spacer, productsView, buttons);
    vbox.setSpacing(10);
    vbox.setAlignment(Pos.CENTER);
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");

  }

  public static void displayClientOrders() {
    String path = System.getProperty("user.dir") + "/images/farmersmarketvieworders.png";
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
    scene.setFill(Color.rgb(248, 236, 215));
    stage.setScene(scene);
    ObservableList<Order> orders = FXCollections
        .observableArrayList(((Client) loggedUser).getOrderHistory());
    ListView<Order> ordersView = new ListView<>(orders);
    ordersView.setMaxHeight(270);
    Button view = new Button("View");
    Button back = new Button("Back");

    view.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        VBox popupVbox = new VBox();
        popupVbox.setMinWidth(400);
        Label farmerLabel = new Label(
            "Seller: " + ordersView.getSelectionModel().getSelectedItem().getFarmer().getName());
        farmerLabel.setFont(new Font(20));
        ObservableList<CartItem> items = FXCollections
            .observableArrayList(ordersView.getSelectionModel().getSelectedItem().getItems());
        ListView<CartItem> itemsView = new ListView<>(items);
        popupVbox.getChildren().addAll(farmerLabel, itemsView);
        Popup popup = new Popup();
        popup.getContent().addAll(popupVbox);
        popup.setWidth(700);
        popup.setAutoHide(true);
        popup.show(stage);
      }
    });

    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        clientMenu();
      }
    });

    vbox.getChildren().addAll(imageView, spacer, ordersView, view, back);
    vbox.setSpacing(10);
    vbox.setAlignment(Pos.CENTER);
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");
  }

  public static void displayCart() {
    String path = System.getProperty("user.dir") + "/images/farmersmarketshopcart.png";
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
    ObservableList<CartItem> cartItems = FXCollections
        .observableArrayList(((Client) loggedUser).getCurrentCart());
    ListView<CartItem> cart = new ListView<>(cartItems);
    cart.setMaxHeight(200);

    VBox total = new VBox();
    Label totalPrice = new Label("Total: " + String.format("%.2f", ((Client) loggedUser).getCurrentCartTotal()) + "€");
    totalPrice.setFont(new Font(25));
    total.setAlignment(Pos.CENTER_RIGHT);
    total.getChildren().addAll(totalPrice);
    total.setPadding(new Insets(0, 30, 0, 0));

    Button buy = new Button("Buy");
    Button edit = new Button("Edit");
    Button removeItem = new Button("Remove Item");
    Button removeAll = new Button("Remove All");
    Button back = new Button("Back");
    HBox buttons = new HBox();
    buttons.getChildren().addAll(buy, edit, removeItem, removeAll, back);
    buttons.setSpacing(30);
    buttons.setAlignment(Pos.CENTER);

    buy.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        if (cartItems.isEmpty()) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID INPUT");
          alert.setHeaderText(null);
          alert.setContentText("Your cart is empty!");
          alert.showAndWait();
          return;
        }

        Dialog<String> method = new Dialog<>();
        method.setTitle("Payment Method");
        method.setHeaderText("Choose Payment Method");
        method.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        ComboBox<String> methods = new ComboBox<>();
        methods.getItems().addAll("Visa", "Paypal", "Bank Transfer");
        method.getDialogPane().setContent(methods);

        method.setResultConverter(new Callback<ButtonType, String>() {
          public String call(ButtonType button) {
            if (button == ButtonType.OK) {
              return methods.getValue();
            }
            return null;
          }
        });

        Optional<String> result = method.showAndWait();
        if (result.isPresent()) {
          if (methods.getValue().equals("Visa")) {
            payWithVisa();
          } else if (methods.getValue().equals("Paypal")) {
            payWithPaypal();
          } else {
            payWithBankTransfer();
          }
        }
      }
    });

    edit.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        CartItem selectedItem = cart.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID INPUT");
          alert.setHeaderText(null);
          alert.setContentText("Select an item to edit!");
          alert.showAndWait();
          return;
        }
        TextInputDialog amount = new TextInputDialog(String.valueOf(selectedItem.getQuantity()));
        amount.setTitle("Choose Quantity");
        amount.setHeaderText("Enter Quantity for: " + selectedItem.getProduct().getProductName());
        amount.setContentText("Quantity:");
        Optional<String> result = amount.showAndWait();

        TextField amountText = amount.getEditor();
        int quant;
        try {
          quant = Integer.parseInt(amountText.getText());
        } catch (NumberFormatException e2) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID INPUT");
          alert.setHeaderText(null);
          alert.setContentText("Invalid Quantity!");
          alert.showAndWait();
          return;
        }

        if (!result.isPresent()) {
          return;
        }

        if (quant < 0 || quant > selectedItem.getProduct().getStock()) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID INPUT");
          alert.setHeaderText(null);
          alert.setContentText(
              "The quantity for " + selectedItem.getProduct().getProductName() + " has to be between 1 and "
                  + selectedItem.getProduct().getStock() + "!");
          alert.showAndWait();
          return;
        }
        manager.editCartItem((Client) loggedUser, selectedItem, quant);
        displayCart();
      }
    });

    removeItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        CartItem selectedItem = cart.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID INPUT");
          alert.setHeaderText(null);
          alert.setContentText("Select an item to edit!");
          alert.showAndWait();
          return;
        }
        manager.editCartItem((Client) loggedUser, selectedItem, 0);
        displayCart();
      }
    });


    removeAll.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        manager.clearClientCart((Client) loggedUser);
        displayCart();
      }
    });

    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        clientMenu();
      }
    });

    vbox.getChildren().addAll(imageView, spacer, cart, total, buttons);
    vbox.setSpacing(20);
    vbox.setAlignment(Pos.CENTER);
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");
  }

  public static void payWithBankTransfer() {
    String path = System.getProperty("user.dir") + "/images/farmersmarketbanktransfer.png";
    Image image = new Image(new File(path).toURI().toString());
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(600);
    imageView.setPreserveRatio(true);
    Region spacer = new Region();
    spacer.setMinHeight(100);
    Region spacer1 = new Region();
    spacer1.setMinHeight(100);
    Region spacer2 = new Region();
    spacer2.setMinHeight(40);

    int entity = 10000 + (int) (Math.random() * ((99999 - 10000) + 1));
    Label entityLabel = new Label("Entity: " + entity);
    entityLabel.setFont(new Font(14));
    int reference = 100000000 + (int) (Math.random() * ((999999999 - 100000000) + 1));
    Label referenceLabel = new Label("Reference: " + reference);
    referenceLabel.setFont(new Font(14));
    Label price = new Label("Amount: " + String.format("%.2f", ((Client) loggedUser).getCurrentCartTotal()) + "€");
    price.setFont(new Font(14));

    Button confirm = new Button("Confirm");
    Button cancel = new Button("Cancel");
    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 820, 820);
    stage.setScene(scene);

    confirm.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        processPayment();
      }
    });

    cancel.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        clientMenu();
      }
    });

    vbox.getChildren().addAll(spacer, imageView, spacer1, entityLabel, referenceLabel, price, spacer2, confirm, cancel);
    vbox.setSpacing(20);
    vbox.setAlignment(Pos.TOP_CENTER);
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");
  }

  public static void payWithVisa() {
    String path = System.getProperty("user.dir") + "/images/farmersmarketvisa.png";
    Image image = new Image(new File(path).toURI().toString());
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(600);
    imageView.setPreserveRatio(true);
    Region spacer = new Region();
    spacer.setMinHeight(100);
    Region spacer1 = new Region();
    spacer1.setMinHeight(100);

    VBox info = new VBox();
    Label name = new Label("Name:");
    TextField nameText = new TextField();
    nameText.setMaxWidth(300);
    Label card = new Label("Card Number:");
    TextField cardNumber = new TextField();
    cardNumber.setMaxWidth(300);
    cardNumber.setTextFormatter(new TextFormatter<>(change -> {
      String cardNum = change.getControlNewText();
      if (cardNum.matches("\\d{0,16}")) {
        return change;
      }
      return null;
    }));

    ComboBox<String> monthBox = new ComboBox<>();
    for (int i = 1; i <= 12; i++) {
      monthBox.getItems().add(String.format("%02d", i));
    }
    monthBox.setMaxWidth(80);

    ComboBox<String> yearBox = new ComboBox<>();
    int currentYear = LocalDate.now().getYear();

    for (int i = 0; i <= 10; i++) {
      yearBox.getItems().add(String.valueOf(currentYear + i));
    }

    yearBox.setMaxWidth(80);

    Label date = new Label("Expiration Date:");
    TextField dateNumber = new TextField();
    dateNumber.setMaxWidth(80);
    Label slash = new Label("/");
    HBox dateBox = new HBox(10, date, monthBox, slash, yearBox);
    dateBox.setAlignment(Pos.CENTER_LEFT);
    Label ccv = new Label("CCV:");
    TextField ccvNumber = new TextField();

    ccvNumber.setMaxWidth(50);
    ccvNumber.setTextFormatter(new TextFormatter<>(change -> {
      String ccvNum = change.getControlNewText();
      if (ccvNum.matches("\\d{0,3}")) {
        return change;
      }
      return null;
    }));

    Region spacer3 = new Region();
    spacer3.setMinHeight(15);
    Region spacer4 = new Region();
    spacer4.setMinHeight(15);
    Region spacer5 = new Region();
    spacer5.setMinHeight(15);
    info.getChildren().addAll(name, nameText, spacer3, card, cardNumber, spacer4, dateBox, spacer5, ccv,
        ccvNumber);
    info.setAlignment(Pos.TOP_LEFT);
    info.setPadding(new Insets(0, 0, 0, 30));

    Label price = new Label("Amount: " + String.format("%.2f", ((Client) loggedUser).getCurrentCartTotal()) + "€");
    price.setFont(new Font(20));

    Button confirm = new Button("Confirm");
    Button cancel = new Button("Cancel");
    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 820, 820);
    stage.setScene(scene);

    confirm.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {

        if (nameText.getText().isBlank() || cardNumber.getText().length() < 16 || ccvNumber.getText().length() < 3
            || monthBox.getValue().isEmpty() || yearBox.getValue().isEmpty()) {

          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID INPUT");
          alert.setHeaderText(null);
          alert.setContentText("All fields are required!");
          alert.showAndWait();
          return;
        }
        processPayment();
      }
    });

    cancel.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        clientMenu();
      }
    });

    vbox.getChildren().addAll(spacer, imageView, spacer1, info, price, confirm, cancel);
    vbox.setSpacing(20);
    vbox.setAlignment(Pos.TOP_CENTER);
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");
  }

  public static void processPayment() {
    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 820, 820);
    stage.setScene(scene);

    Label process = new Label("Processing your payment");
    Label wait = new Label("Please do not close this window");

    ProgressIndicator progress = new ProgressIndicator();
    vbox.setAlignment(Pos.CENTER);

    PauseTransition delay = new PauseTransition(Duration.seconds(3));
    delay.setOnFinished(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        clientMenu();
        manager.finalizePurchase((Client) loggedUser);
        Platform.runLater(new Runnable() {
          public void run() {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUCCESS");
            alert.setHeaderText(null);
            alert.setContentText("Order completed with success!");
            alert.showAndWait();
          }
        });
      }
    });
    delay.play();

    vbox.getChildren().addAll(process, wait, progress);
    vbox.setSpacing(20);
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");

  }

  public static void payWithPaypal() {
    String path = System.getProperty("user.dir") + "/images/farmersmarketpaypal.png";
    Image image = new Image(new File(path).toURI().toString());
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(600);
    imageView.setPreserveRatio(true);
    Region spacer = new Region();
    spacer.setMinHeight(100);
    Region spacer1 = new Region();
    spacer1.setMinHeight(200);
    Region spacer2 = new Region();
    spacer2.setMinHeight(20);
    Region spacer3 = new Region();
    spacer3.setMinHeight(20);
    Region spacer4 = new Region();
    spacer4.setMinHeight(10);

    Label email = new Label("Email:");
    TextField emailText = new TextField();
    emailText.setMaxWidth(350);
    Label password = new Label("Password:");
    PasswordField passwordText = new PasswordField();
    passwordText.setMaxWidth(350);

    Label price = new Label("Amount: " + String.format("%.2f", ((Client) loggedUser).getCurrentCartTotal()) + "€");
    price.setFont(new Font(20));

    Button confirm = new Button("Confirm");
    Button cancel = new Button("Cancel");
    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 820, 820);
    stage.setScene(scene);

    confirm.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {

        if (emailText.getText().isBlank() || passwordText.getText().isEmpty()) {

          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("INVALID INPUT");
          alert.setHeaderText(null);
          alert.setContentText("All fields are required!");
          alert.showAndWait();
          return;
        }
        processPayment();
      }
    });

    cancel.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        clientMenu();
      }
    });

    vbox.getChildren().addAll(spacer, imageView, spacer1, email, emailText, spacer2, password, passwordText, spacer3,
        confirm, spacer4, cancel);
    vbox.setSpacing(5);
    vbox.setAlignment(Pos.TOP_CENTER);
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");
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
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");
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
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");

  }

  public static void displayProducts(Category category) {
    VBox vbox = new VBox();
    Scene scene = new Scene(vbox, 820, 820);
    stage.setScene(scene);
    ObservableList<FarmerProduct> productsCategory = FXCollections
        .observableArrayList(manager.getCategoryProducts(category, true));
    ListView<FarmerProduct> productsView = new ListView<>(productsCategory);
    ComboBox<String> sorter = new ComboBox<>();
    sorter.getItems().addAll("Price: Ascending", "Price: Descending", "Name: Ascending", "Name: Descending");

    Button cartButton = new Button("Add to cart");
    VBox cart = new VBox();
    cart.getChildren().addAll(cartButton);
    cart.setAlignment(Pos.CENTER_RIGHT);

    cartButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        FarmerProduct selectedItem = productsView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("SUCCESS");
          alert.setHeaderText(null);
          alert.setContentText("Please select an item to add to your cart!");
          alert.showAndWait();
          return;
        } else {
          TextInputDialog amount = new TextInputDialog("1");
          amount.setTitle("Choose Quantity");
          amount.setHeaderText("Enter Quantity for: " + selectedItem.getProductName());
          amount.setContentText("Quantity:");
          Optional<String> result = amount.showAndWait();

          TextField stockText = amount.getEditor();
          int stock;
          try {
            stock = Integer.parseInt(stockText.getText());
          } catch (NumberFormatException e2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("INVALID INPUT");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Quantity!");
            alert.showAndWait();
            return;
          }

          if (!result.isPresent()) {
            return;
          }

          if (stock < 1 || stock > selectedItem.getStock()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("INVALID INPUT");
            alert.setHeaderText(null);
            alert.setContentText("The quantity for " + selectedItem.getProductName() + " has to be between 1 and "
                + selectedItem.getStock() + "!");
            alert.showAndWait();
            return;

          }
          manager.addProductToCart(selectedItem, (Client) loggedUser, stock);
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
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");

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
    Button displayProducts = new Button("View items");
    Button displaySales = new Button("View Orders");
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

    displayProducts.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        viewFarmerProducts();
      }
    });

    displaySales.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        displayFarmerSales();
      }
    });

    logout.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        loggedUser = null;
        loginScreen();
      }
    });

    vbox.getChildren().addAll(imageView, spacer, menu, addProduct, addTechnique, displayProducts, displaySales, logout);
    vbox.setSpacing(20);
    vbox.setAlignment(Pos.CENTER);
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");

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
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");

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
          manager.registerProduct(loggedUser.getEmail(), formattedName,
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
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");

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
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");

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
    emailText.setMaxWidth(350);
    Label password = new Label("Password");
    PasswordField passField = new PasswordField();
    passField.setMaxWidth(350);
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
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");
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
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");

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
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");

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
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");
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
    Scene scene = new Scene(vbox, 820, 820);
    vbox.setSpacing(17);
    vbox.setAlignment(Pos.CENTER);
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");
    stage.setScene(scene);

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
    ObservableList<FarmerProduct> farmerProducts = FXCollections
        .observableArrayList(manager.getFarmerAvailableItems(farmer));
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
        FarmerProduct selectedItem = farmerProductsView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("SUCCESS");
          alert.setHeaderText(null);
          alert.setContentText("Please select an item to add to your cart!");
          alert.showAndWait();
          return;
        } else {
          TextInputDialog amount = new TextInputDialog("1");
          amount.setTitle("Choose Quantity");
          amount.setHeaderText("Enter Quantity for: " + selectedItem.getProductName());
          amount.setContentText("Quantity:");
          Optional<String> result = amount.showAndWait();

          TextField stockText = amount.getEditor();
          int stock;
          try {
            stock = Integer.parseInt(stockText.getText());
          } catch (NumberFormatException e2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("INVALID INPUT");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Quantity!");
            alert.showAndWait();
            return;
          }

          if (!result.isPresent()) {
            return;
          }

          if (stock < 1 || stock > selectedItem.getStock()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("INVALID INPUT");
            alert.setHeaderText(null);
            alert.setContentText("The quantity for " + selectedItem.getProductName() + " has to be between 1 and "
                + selectedItem.getStock() + "!");
            alert.showAndWait();
            return;

          }
          manager.addProductToCart(selectedItem, (Client) loggedUser, stock);
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setTitle("SUCCESS");
          alert.setHeaderText(null);
          alert.setContentText("Item(s) added to cart successfully!");
          alert.showAndWait();
          return;
        }
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
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");
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
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");

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
    vbox.setStyle("-fx-background-color: rgb(247, 242, 234);");
  }
}
