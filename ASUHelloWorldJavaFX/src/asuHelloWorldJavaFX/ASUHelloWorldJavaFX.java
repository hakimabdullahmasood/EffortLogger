package asuHelloWorldJavaFX;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ASUHelloWorldJavaFX extends Application {
    public static void main(String[] args) {
    	launch(args);
        
    }

    public void start(Stage primaryStage) {
        System.out.println("Effort Logger Console Started!");
        primaryStage.setTitle("Effort Logger Prototype");
        InputValidator validInput = new InputValidator("");
        // Create text fields for ID and password with black text and white background
        TextField idTextField = new TextField();
        idTextField.setPromptText("ID");
        idTextField.setStyle("-fx-text-fill: white; -fx-control-inner-background: black; -fx-border-color: white;");

        TextField passwordTextField = new TextField();
        passwordTextField.setPromptText("Password");
        passwordTextField.setStyle("-fx-text-fill: white; -fx-control-inner-background: black; -fx-border-color: white;");

        // Create labels for text fields with white text
        Label idLabel = new Label("ID:");
        Label passwordLabel = new Label("Password:");
        idLabel.setTextFill(Color.WHITE);
        passwordLabel.setTextFill(Color.WHITE);

        Label warningLabel = new Label("Invalid ID");
        warningLabel.setTextFill(Color.RED);
        warningLabel.setVisible(false); // Initially, hide the warning label

        // Create a grid pane to organize the elements with padding
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));
        grid.add(idLabel, 0, 0);
        grid.add(idTextField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordTextField, 1, 1);
        grid.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY))); // Set the background to black
  
        // Create a layout for centering
        BorderPane root = new BorderPane();
        root.setCenter(grid);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        
        // Add the warning label to the GridPane
        grid.add(warningLabel, 1, 2);
        
        Button loginButton = new Button("Login");
        loginButton.setMinWidth(Button.USE_PREF_SIZE);
        loginButton.setStyle("-fx-text-fill: white; -fx-background-color: black; -fx-border-color: white;");
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	
                String id = idTextField.getText();
                
                
                String password = passwordTextField.getText();
                
                if(EffortLoggerPermissions.isIdValid(id)&&validInput.checkType(id) == 5)
                {
                	System.out.println("ID: " + id + ", Password: " + password);
                
                	// Close the previous window
                	Stage oldStage = (Stage) loginButton.getScene().getWindow();
                	oldStage.close();
                	
                	if(EffortLoggerPermissions.isSupervisor(id))
                	{
                		// Create a new stage for the new window
                		Stage newStage = new Stage();
                		newStage.setTitle("Supervisor Landing"); // Set the title of the new window

                		// Create the content for the new window (e.g., a label)
                		Label newLabel = new Label("Welcome to the new window!");
                		newLabel.setTextFill(Color.WHITE);
                
                		// Create a layout for the content
                		BorderPane newRoot = new BorderPane();
                		newRoot.setCenter(newLabel);
                	
                		// Create a scene for the new window
                		Scene newScene = new Scene(newRoot, 400, 300);
                		newScene.setFill(Color.BLACK);

                		// Set the scene for the new stage
                		newStage.setScene(newScene);
                	
                		// Show the new window
                		newStage.show();
                	}
                	else
                	{
                		// Create a new stage for the new window
                		Stage newStage = new Stage();
                		newStage.setTitle("Employee Landing"); // Set the title of the new window

                		// Create the content for the new window (e.g., a label)
                		Label newLabel = new Label("Welcome to the new window!");
                		newLabel.setTextFill(Color.WHITE);
                
                		// Create a layout for the content
                		BorderPane newRoot = new BorderPane();
                		newRoot.setCenter(newLabel);
                	
                		// Create a scene for the new window
                		Scene newScene = new Scene(newRoot, 400, 300);
                		newScene.setFill(Color.BLACK);

                		// Set the scene for the new stage
                		newStage.setScene(newScene);
                	
                		// Show the new window
                		newStage.show();
                	}
            	}
                else
                {
                    warningLabel.setVisible(true);
                }
                
            }
        });

        // Create a label for the title with custom font and white text
        Label titleLabel = new Label("Effort Logger");
        titleLabel.setFont(new Font("Lucida Console", 20)); // Set the font to Lucida Console with size 20
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setPadding(new Insets(20));
        
        // Create a layout for centering
        root.setTop(titleLabel); // Add the title label at the top
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        
        // Place the "Login" button in the bottom right corner
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.getChildren().add(loginButton);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        root.setBottom(buttonBox);

        // Set constraints to make text fields take up 50% of the window
        idTextField.setMaxWidth(Double.MAX_VALUE);
        passwordTextField.setMaxWidth(Double.MAX_VALUE);

        // Set the stage to not be resizable
        primaryStage.setResizable(false);

        // Set the background color for the entire scene to black
        Scene scene = new Scene(root, 300, 250);
        scene.setFill(Color.BLACK);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}