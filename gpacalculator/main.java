package gpacalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    @Override 
    public void start(Stage stage) throws Exception {
        // loading from FXML
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml")); 
        Scene scene = new Scene(root);
        stage.setScene(scene);    
        stage.show();
      
        
        
    }
}