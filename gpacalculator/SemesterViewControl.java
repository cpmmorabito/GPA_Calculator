/**
 * Sample Skeleton for 'SemesterView.fxml' Controller Class
 */

package gpacalculator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SemesterViewControl {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="modelTable"
    private TableView<?> modelTable; // Value injected by FXMLLoader

    @FXML // fx:id="modelColumnID"
    private TableColumn<?, ?> modelColumnID; // Value injected by FXMLLoader

    @FXML // fx:id="modelColumnName"
    private TableColumn<?, ?> modelColumnName; // Value injected by FXMLLoader

    @FXML // fx:id="modelColumnCredit"
    private TableColumn<?, ?> modelColumnCredit; // Value injected by FXMLLoader

    @FXML // fx:id="modelColumnGrade"
    private TableColumn<?, ?> modelColumnGrade; // Value injected by FXMLLoader

    @FXML // fx:id="courseButton"
    private Button courseButton; // Value injected by FXMLLoader

    @FXML // fx:id="gpaButton"
    private Button gpaButton; // Value injected by FXMLLoader

    @FXML // fx:id="studentButton"
    private Button studentButton; // Value injected by FXMLLoader

    @FXML // fx:id="selectButton"
    private Button selectButton; // Value injected by FXMLLoader

    @FXML // fx:id="semesterChoiceBox"
    private ChoiceBox<?> semesterChoiceBox; // Value injected by FXMLLoader

    @FXML
    void displayCourseAction(ActionEvent event) {
        //opens course window
    }

    @FXML
    void displayGPAAction(ActionEvent event) {
        //opens gpa window
    }

    @FXML
    void displaySemesterAction(ActionEvent event) {
        //updates view to display the selected semester
    }

    @FXML
    void displayStudentAction(ActionEvent event) {
        //opens student window
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert modelTable != null : "fx:id=\"modelTable\" was not injected: check your FXML file 'SemesterView.fxml'.";
        assert modelColumnID != null : "fx:id=\"modelColumnID\" was not injected: check your FXML file 'SemesterView.fxml'.";
        assert modelColumnName != null : "fx:id=\"modelColumnName\" was not injected: check your FXML file 'SemesterView.fxml'.";
        assert modelColumnCredit != null : "fx:id=\"modelColumnCredit\" was not injected: check your FXML file 'SemesterView.fxml'.";
        assert modelColumnGrade != null : "fx:id=\"modelColumnGrade\" was not injected: check your FXML file 'SemesterView.fxml'.";
        assert courseButton != null : "fx:id=\"courseButton\" was not injected: check your FXML file 'SemesterView.fxml'.";
        assert gpaButton != null : "fx:id=\"gpaButton\" was not injected: check your FXML file 'SemesterView.fxml'.";
        assert studentButton != null : "fx:id=\"studentButton\" was not injected: check your FXML file 'SemesterView.fxml'.";
        assert selectButton != null : "fx:id=\"selectButton\" was not injected: check your FXML file 'SemesterView.fxml'.";
        assert semesterChoiceBox != null : "fx:id=\"semesterChoiceBox\" was not injected: check your FXML file 'SemesterView.fxml'.";

    }
}
