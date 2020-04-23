/**
 * Sample Skeleton for 'CourseView.fxml' Controller Class
 */

package gpacalculator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CourseViewControl {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="courseNumField"
    private TextField courseNumField; // Value injected by FXMLLoader

    @FXML // fx:id="courseNameField"
    private TextField courseNameField; // Value injected by FXMLLoader

    @FXML // fx:id="professorField"
    private TextField professorField; // Value injected by FXMLLoader

    @FXML // fx:id="creditField"
    private TextField creditField; // Value injected by FXMLLoader

    @FXML // fx:id="gradeField"
    private TextField gradeField; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert courseNumField != null : "fx:id=\"courseNumField\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert courseNameField != null : "fx:id=\"courseNameField\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert professorField != null : "fx:id=\"professorField\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert creditField != null : "fx:id=\"creditField\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert gradeField != null : "fx:id=\"gradeField\" was not injected: check your FXML file 'CourseView.fxml'.";

    }
}
