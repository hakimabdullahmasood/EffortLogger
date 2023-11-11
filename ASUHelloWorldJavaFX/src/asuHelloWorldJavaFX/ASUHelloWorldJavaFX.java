package asuHelloWorldJavaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ASUHelloWorldJavaFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ASUHelloWorldJavaFX.fxml"));

        Scene scene = new Scene(root, 300, 250);
        scene.setFill(Color.BLACK);

        primaryStage.setTitle("Effort Logger Prototype");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
    }
}
