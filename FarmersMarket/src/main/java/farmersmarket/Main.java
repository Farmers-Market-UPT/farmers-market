package farmersmarket;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.Scanner;

/**
 * @author Diogo Ferreira (51084)
 * @author Sara Canelas (51297)
 * @author
 * @author
 * 
 */
public class Main {

  public static void main(String[] args) {

    FarmersMarket manager = new FarmersMarket();
    manager.readData();

    User loggedUser = null;
    VBox vbox = new VBox(8);
    vbox.getChildren().addAll(new Button("Log In with existing account"),new Button("Create a new account"))

    /* System.out.println("Welcome to Farmers Market! Please choose an option!");
    System.out.println("1- Login with an existing account");
    System.out.println("2- Create a new account");
    System.out.println("3- Close the program");


    switch (userChoice) {
      case 1:
        loggedUser = manager.login();
        while (loggedUser == null) {
          loggedUser = manager.login();
        }
        break;

      case 2:
        manager.createAccount();
        loggedUser = manager.login();
        while (loggedUser == null) {
          loggedUser = manager.login();
        }
        break;

      case 3:
        System.exit(0);

      default:
        System.out.println("Please pick a valid option");
    }

  }*/
  }
}
