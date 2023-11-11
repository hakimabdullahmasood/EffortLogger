package asuHelloWorldJavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class MainController {

    @FXML
    private BorderPane root;

    @FXML
    private GridPane grid;

    @FXML
    private TextField idTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label warningLabel;

    @FXML
    private Button loginButton;

    @FXML
    private void initialize() {
        // Additional initialization code can go here
    }

    @FXML
    private void handleLoginButtonAction() {
        String id = idTextField.getText();
        String password = passwordTextField.getText();

        // Your login logic goes here

        if (EffortLoggerPermissions.isIdValid(id) && validInput.checkType(id) == 5) {
            // Successful login logic
        	
            // Close the window if needed
        	
        } else {
            warningLabel.setVisible(true);
        }
    }
}
