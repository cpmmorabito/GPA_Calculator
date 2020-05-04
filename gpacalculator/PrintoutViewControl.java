/**
 * Sample Skeleton for 'PrintoutView.fxml' Controller Class
 */

package gpacalculator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
* This class is responsible for PrintoutView
* It connects to SemesterView and TranscriptView
* It contains no functionality for any entity
* It does not use any database table
**/

public class PrintoutViewControl {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="semesterButton"
    private Button semesterButton; // Value injected by FXMLLoader

    @FXML // fx:id="transcriptButton"
    private Button transcriptButton; // Value injected by FXMLLoader
    
    private int studentID;

    // Opens SemesterView
    @FXML
    void displaySemesterAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SemesterView.fxml"));
            Parent secondRoot = loader.load();

            // getting the controller from FXLoader
            SemesterViewControl secondController =  loader.getController();
            //secondController.displayMessage(messageInput.getText());
            secondController.initialize(studentID);
            // Show Second FXML in new a window
            Stage stage = new Stage();
            stage.setScene(new Scene(secondRoot));
            stage.setTitle("Second Window");
            stage.show();
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
    }

    // Opens TranscriptView
    @FXML
    void displayTranscriptAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TranscriptView.fxml"));
            Parent secondRoot = loader.load();

            // getting the controller from FXLoader
            TranscriptViewControl secondController =  loader.getController();
            //secondController.displayMessage(messageInput.getText());
            secondController.initialize(studentID);
            // Show Second FXML in new a window
            Stage stage = new Stage();
            stage.setScene(new Scene(secondRoot));
            stage.setTitle("Second Window");
            stage.show();
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
    }

    // Initializes controller
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize(int stuID) {
        assert semesterButton != null : "fx:id=\"semesterButton\" was not injected: check your FXML file 'PrintoutView.fxml'.";
        assert transcriptButton != null : "fx:id=\"transcriptButton\" was not injected: check your FXML file 'PrintoutView.fxml'.";
        
        studentID = stuID;
    }
}
