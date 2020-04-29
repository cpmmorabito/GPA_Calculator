/**
 * Sample Skeleton for 'StudentInfoGView.fxml' Controller Class
 */

package gpacalculator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class StudentInfoGViewControl {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="nameField"
    private TextField nameField; // Value injected by FXMLLoader

    @FXML // fx:id="yearField"
    private TextField yearField; // Value injected by FXMLLoader

    @FXML // fx:id="programField"
    private TextField programField; // Value injected by FXMLLoader

    @FXML // fx:id="onTrackField"
    private TextField onTrackField; // Value injected by FXMLLoader

    @FXML // fx:id="closeButton"
    private Button closeButton; // Value injected by FXMLLoader

    @FXML
    void closeAction(ActionEvent event) {
        //closes window
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'StudentInfoGView.fxml'.";
        assert yearField != null : "fx:id=\"yearField\" was not injected: check your FXML file 'StudentInfoGView.fxml'.";
        assert programField != null : "fx:id=\"programField\" was not injected: check your FXML file 'StudentInfoGView.fxml'.";
        assert onTrackField != null : "fx:id=\"onTrackField\" was not injected: check your FXML file 'StudentInfoGView.fxml'.";
        assert closeButton != null : "fx:id=\"closeButton\" was not injected: check your FXML file 'StudentInfoGView.fxml'.";

    }
}
