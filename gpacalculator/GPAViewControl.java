/**
 * Sample Skeleton for 'GPAView.fxml' Controller Class
 */

package gpacalculator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class GPAViewControl {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="overallGPAField"
    private TextField overallGPAField; // Value injected by FXMLLoader

    @FXML // fx:id="semesterChoiceBox"
    private ChoiceBox<Integer> semesterChoiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="semesterGPAField"
    private TextField semesterGPAField; // Value injected by FXMLLoader

    @FXML // fx:id="semesterButton"
    private Button semesterButton; // Value injected by FXMLLoader

    private GPA gpa = new GPA();
    private int studentID;
    
    void displayOverallGPA() {
        gpa.calcOverallGPA(studentID);
        overallGPAField.setText(String.format("%.2f", gpa.getOverallGPA()));
    }
    
    @FXML
    void displaySemesterGPAAction(ActionEvent event) {
        int semester = semesterChoiceBox.getSelectionModel().getSelectedItem();
        gpa.calcSemesterGPA(semester, studentID);        
        
        semesterGPAField.setText(String.format("%.2f", gpa.getSemesterGPA()));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize(int stuID) {
        assert overallGPAField != null : "fx:id=\"overallGPAField\" was not injected: check your FXML file 'GPAView.fxml'.";
        assert semesterChoiceBox != null : "fx:id=\"semesterChoiceBox\" was not injected: check your FXML file 'GPAView.fxml'.";
        assert semesterGPAField != null : "fx:id=\"semesterGPAField\" was not injected: check your FXML file 'GPAView.fxml'.";
        assert semesterButton != null : "fx:id=\"semesterButton\" was not injected: check your FXML file 'GPAView.fxml'.";
        System.out.println(stuID);
        studentID = stuID;
        System.out.println(studentID);
        semesterChoiceBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        displayOverallGPA();
    }
}
