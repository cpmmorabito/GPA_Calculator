/**
 * Sample Skeleton for 'OverallView.fxml' Controller Class
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
* This class is responsible for OverallView
* It connects to StudentView and GPAView and PrintoutView
* It contains no functionality for any entity
* It does not use any database table
**/

public class OverallViewControl {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="gpaButton"
    private Button gpaButton; // Value injected by FXMLLoader

    @FXML // fx:id="printoutButton"
    private Button printoutButton; // Value injected by FXMLLoader

    @FXML // fx:id="studentInfoButton"
    private Button studentInfoButton; // Value injected by FXMLLoader
    
    private int studentID;

    // opens gpa view
    @FXML
    void displayGPAAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GPAView.fxml"));
            Parent secondRoot = loader.load();

            // getting the controller from FXLoader
            GPAViewControl secondController =  loader.getController();
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

    // Opens printout view
    @FXML
    void displayPrintoutAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PrintoutView.fxml"));
            Parent secondRoot = loader.load();

            // getting the controller from FXLoader
            PrintoutViewControl secondController =  loader.getController();
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

    // Opens student view with selected student
    @FXML
    void displayStudentAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentView.fxml"));
            Parent secondRoot = loader.load();

            // getting the controller from FXLoader
            StudentViewControl secondController =  loader.getController();
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
        assert gpaButton != null : "fx:id=\"gpaButton\" was not injected: check your FXML file 'OverallView.fxml'.";
        assert printoutButton != null : "fx:id=\"printoutButton\" was not injected: check your FXML file 'OverallView.fxml'.";
        assert studentInfoButton != null : "fx:id=\"studentInfoButton\" was not injected: check your FXML file 'OverallView.fxml'.";
        
        studentID = stuID;
    }
}
