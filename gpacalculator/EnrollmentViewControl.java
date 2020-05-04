/**
 * Sample Skeleton for 'EnrollmentView.fxml' Controller Class
 */

package gpacalculator;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
* This class is responsible for EnrollmentView
* It connects to SemesterView and TranscriptView
* It contains update and delete functionality for Enrollment entity
* It uses the Enrollment database table
**/

public class EnrollmentViewControl {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="detailTab"
    private Tab detailTab; // Value injected by FXMLLoader

    @FXML // fx:id="detailCourseField"
    private TextField detailCourseField; // Value injected by FXMLLoader

    @FXML // fx:id="detailSemesterField"
    private TextField detailSemesterField; // Value injected by FXMLLoader

    @FXML // fx:id="detailGradeField"
    private TextField detailGradeField; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="updateTab"
    private Tab updateTab; // Value injected by FXMLLoader

    @FXML // fx:id="updateCourseField"
    private TextField updateCourseField; // Value injected by FXMLLoader

    @FXML // fx:id="semesterChoiceBox"
    private ChoiceBox<Integer> semesterChoiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="updateGradeField"
    private TextField updateGradeField; // Value injected by FXMLLoader

    @FXML // fx:id="updateButton"
    private Button updateButton; // Value injected by FXMLLoader

    @FXML // fx:id="deleteTab"
    private Tab deleteTab; // Value injected by FXMLLoader

    @FXML // fx:id="deleteCourseField"
    private TextField deleteCourseField; // Value injected by FXMLLoader

    @FXML // fx:id="deleteSemesterField"
    private TextField deleteSemesterField; // Value injected by FXMLLoader

    @FXML // fx:id="deleteGradeField"
    private TextField deleteGradeField; // Value injected by FXMLLoader

    @FXML // fx:id="deleteButton"
    private Button deleteButton; // Value injected by FXMLLoader

    EntityManager manager;
    Enrollment eInfo = new Enrollment();
    Alert dAlert = new Alert(AlertType.CONFIRMATION);
    Alert uAlert = new Alert(AlertType.CONFIRMATION);   
    Scene previousScene;
    
    // Deletes the selected Enrollment
    @FXML
    void deleteEnrollmentAction(ActionEvent event) {
        // delete confirmation dialog
        dAlert.setTitle("Confirmation Dialog");
        dAlert.setHeaderText("Delete this enrollment?");
        dAlert.setContentText("Are you sure you want to delete " + eInfo.getCourseID() + " from your record?");
        Optional<ButtonType> result = dAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // Deletes the enrollment
            delete(eInfo);
            // Tells the user the enrollment was removed
            dAlert.setTitle("Confirmation Dialog");
            dAlert.setHeaderText("Deletion Successful");
            dAlert.setContentText("Window will now return to PrintoutView.");
            dAlert.showAndWait();
            closeAction(event);
        }
    }

    // Takes user to the previous view (either SemesterView or TranscriptView)
    @FXML
    void showBackAction(ActionEvent event) {
        Stage stage = (Stage)backButton.getScene().getWindow();
        if (previousScene != null) {
            stage.setScene(previousScene);
        }
    }

    @FXML
    void tabDeleteChanged(Event event) {
        // Has no elements that require updating when the tabs are changed
    }

    @FXML
    void tabDetailChanged(Event event) {
        // Has no elements that require updating when the tabs are changed
    }

    @FXML
    void tabUpdateChanged(Event event) {
        // Has no elements that require updating when the tabs are changed
    }

    // Checks for the elements to update the selected enrollment
    @FXML
    void updateEnrollmentAction(ActionEvent event) {
        try {
            // Makes sure value isn't null and updates value in Enrollment variable
            if (semesterChoiceBox.getSelectionModel().getSelectedItem() != null) {
                eInfo.setSemesterID(semesterChoiceBox.getSelectionModel().getSelectedItem());
            }
            if (updateGradeField.getText() != null) {
                eInfo.setGrade(Float.parseFloat(updateGradeField.getText()));
            }      
            // Updates database      
            update(eInfo);
            // Tells user the update was successful
            uAlert.setTitle("Confirmation Dialog");
            uAlert.setHeaderText("Update Successful");
            uAlert.setContentText("Window will now return to PrintoutView.");
            uAlert.showAndWait();
            closeAction(event);
        } catch (NumberFormatException | NullPointerException ex) {
            // Tells user the update was not successful
            System.out.println(ex.getMessage());
            uAlert.setHeaderText("Update Failed");
            uAlert.setContentText("This enrollment cannot be updated at this time. Please hit the Back button to return to the previous view, or close the window return to Printout menu.");
            uAlert.showAndWait();
        }
    }

    // Closes the window entirely so that the data can refresh in SemesterView and TranscriptView
    void closeAction(ActionEvent event) {
        Stage stage = (Stage)backButton.getScene().getWindow();
        stage.close();
    }

    // Saves previous view as a variable for future access
    public void setPreviousScene(Scene scene) {
        previousScene = scene;
        backButton.setDisable(false);
    }
    
    // updates model in database
    public void update(Enrollment model) {
        try {
            Enrollment dbModel = manager.find(Enrollment.class, model.getId());

            if (dbModel != null) {
                manager.getTransaction().begin();
                // update
                dbModel.setGrade(model.getGrade());
                dbModel.setSemesterID(model.getSemesterID());
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Deletes model from database
    public void delete(Enrollment model) {
        try {
            Enrollment dbModel = manager.find(Enrollment.class, model.getId());

            if (dbModel != null) {
                manager.getTransaction().begin();
                //remove model
                manager.remove(dbModel);
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // Flls in all text fields
    public void loadData(){
        detailCourseField.setText(eInfo.getCourseID());
        detailSemesterField.setText(Integer.toString(eInfo.getSemesterID()));
        detailGradeField.setText(Float.toString(eInfo.getGrade()));
        updateCourseField.setText(eInfo.getCourseID());
        deleteCourseField.setText(eInfo.getCourseID());
        deleteSemesterField.setText(Integer.toString(eInfo.getSemesterID()));
        deleteGradeField.setText(Float.toString(eInfo.getGrade()));
    }
    
    // Initializes controller
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize(Enrollment enroll) {
        assert detailTab != null : "fx:id=\"detailTab\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert detailCourseField != null : "fx:id=\"detailCourseField\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert detailSemesterField != null : "fx:id=\"detailSemesterField\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert detailGradeField != null : "fx:id=\"detailGradeField\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert updateTab != null : "fx:id=\"updateTab\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert updateCourseField != null : "fx:id=\"updateCourseField\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert semesterChoiceBox != null : "fx:id=\"semesterChoiceBox\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert updateGradeField != null : "fx:id=\"updateGradeField\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert updateButton != null : "fx:id=\"updateButton\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert deleteTab != null : "fx:id=\"deleteTab\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert deleteCourseField != null : "fx:id=\"deleteCourseField\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert deleteSemesterField != null : "fx:id=\"deleteSemesterField\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert deleteGradeField != null : "fx:id=\"deleteGradeField\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'EnrollmentView.fxml'.";

        manager = (EntityManager) Persistence.createEntityManagerFactory("IST311ProjectD3").createEntityManager();

        eInfo = enroll;
        semesterChoiceBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        loadData();
    }
}


