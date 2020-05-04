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

/**
* This class is responsible for GPAView
* It connects to no other views
* It displays GPA details
* It does not use any database table
**/

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
    
    // Displays selected semester gpa
    @FXML
    void displaySemesterGPAAction(ActionEvent event) {
        try {
            // Gets selected semsester
            int semester = semesterChoiceBox.getSelectionModel().getSelectedItem();
            // calculates gpa
            gpa.calcSemesterGPA(semester, studentID); 
            // formats gpa if it has no data
            if (Double.isNaN(gpa.getSemesterGPA())) {
                gpa.setSemesterGPA(0.0);
            }
            // displays gpa
            semesterGPAField.setText(String.format("%.2f", gpa.getSemesterGPA()));
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Displays overall gpa
    void displayOverallGPA() {
        // calculates gpa
        gpa.calcOverallGPA(studentID);
        // formats gpa if it has no data
        if (Double.isNaN(gpa.getOverallGPA())) {
            gpa.setOverallGPA(0.0);
        }
        // displays gpa
        overallGPAField.setText(String.format("%.2f", gpa.getOverallGPA()));
    }

    // Initializes controller
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize(int stuID) {
        assert overallGPAField != null : "fx:id=\"overallGPAField\" was not injected: check your FXML file 'GPAView.fxml'.";
        assert semesterChoiceBox != null : "fx:id=\"semesterChoiceBox\" was not injected: check your FXML file 'GPAView.fxml'.";
        assert semesterGPAField != null : "fx:id=\"semesterGPAField\" was not injected: check your FXML file 'GPAView.fxml'.";
        assert semesterButton != null : "fx:id=\"semesterButton\" was not injected: check your FXML file 'GPAView.fxml'.";
        studentID = stuID;
        semesterChoiceBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        displayOverallGPA();
    }
}
