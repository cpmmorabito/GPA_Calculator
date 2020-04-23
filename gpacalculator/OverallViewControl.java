/**
 * Sample Skeleton for 'OverallView.fxml' Controller Class
 */

package gpacalculator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class OverallViewControl {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="gpaButton"
    private Button gpaButton; // Value injected by FXMLLoader

    @FXML // fx:id="transcriptButton"
    private Button transcriptButton; // Value injected by FXMLLoader

    @FXML // fx:id="printoutButton"
    private Button printoutButton; // Value injected by FXMLLoader

    @FXML // fx:id="studentInfoButton"
    private Button studentInfoButton; // Value injected by FXMLLoader

    @FXML // fx:id="semesterButton"
    private Button semesterButton; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert gpaButton != null : "fx:id=\"gpaButton\" was not injected: check your FXML file 'OverallView.fxml'.";
        assert transcriptButton != null : "fx:id=\"transcriptButton\" was not injected: check your FXML file 'OverallView.fxml'.";
        assert printoutButton != null : "fx:id=\"printoutButton\" was not injected: check your FXML file 'OverallView.fxml'.";
        assert studentInfoButton != null : "fx:id=\"studentInfoButton\" was not injected: check your FXML file 'OverallView.fxml'.";
        assert semesterButton != null : "fx:id=\"semesterButton\" was not injected: check your FXML file 'OverallView.fxml'.";

    }
}
