package asuHelloWorldJavaFX;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class ASUHelloWorldJavaFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage primaryStage) {
    	System.out.println("Abhijit Girish Hello World!");
    	System.out.println("It started!");
        primaryStage.setTitle("Abhijit Girish Hello World");
        Button btn = new Button();
        btn.setText("Display: 'Abhijit Girish says: Hello World!'");
        btn.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
                System.out.println("Abhijit Girish: Hello World!");
            }
        });
        
        
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
        
        
        
        
        
    }
}