/**
 * Sample Skeleton for 'PrintoutView.fxml' Controller Class
 */

package gpacalculator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrintoutViewControl {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="gpaButton"
    private Button gpaButton; // Value injected by FXMLLoader

    @FXML // fx:id="printReportButton"
    private Button printReportButton; // Value injected by FXMLLoader

    @FXML // fx:id="studentInfoButton"
    private Button studentInfoButton; // Value injected by FXMLLoader

    @FXML // fx:id="printTranscriptButton"
    private Button printTranscriptButton; // Value injected by FXMLLoader

    @FXML
    void displayGPAAction(ActionEvent event) {
        //opens gpa window
    }

    @FXML
    void displayStudentAction(ActionEvent event) {
        //opens student window
    }

    @FXML
    void printReportAction(ActionEvent event) {
        //prints the report card formatting, need to figure this out
    }

    @FXML
    void printTranscriptAction(ActionEvent event) {
        //prints the transcript formatting, need to figure this out
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert gpaButton != null : "fx:id=\"gpaButton\" was not injected: check your FXML file 'PrintoutView.fxml'.";
        assert printReportButton != null : "fx:id=\"printReportButton\" was not injected: check your FXML file 'PrintoutView.fxml'.";
        assert studentInfoButton != null : "fx:id=\"studentInfoButton\" was not injected: check your FXML file 'PrintoutView.fxml'.";
        assert printTranscriptButton != null : "fx:id=\"printTranscriptButton\" was not injected: check your FXML file 'PrintoutView.fxml'.";

    }
}
