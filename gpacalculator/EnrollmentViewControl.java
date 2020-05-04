/**
 * Sample Skeleton for 'EnrollmentView.fxml' Controller Class
 */

package gpacalculator;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

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

    @FXML // fx:id="updateSemesterField"
    private TextField updateSemesterField; // Value injected by FXMLLoader

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
    Enrollment eInfo;
    Scene previousScene;
    
    @FXML
    void deleteEnrollmentAction(ActionEvent event) {
        // delete confirmation dialog
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete this enrollment?");
        alert.setContentText("Are you sure you want to delete " + eInfo.getCourseID() + " from your record?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            delete(eInfo);
            closeAction(event);
        }
    }

    @FXML
    void showBackAction(ActionEvent event) {
        Stage stage = (Stage)backButton.getScene().getWindow();
        if (previousScene != null){
            stage.setScene(previousScene);
        }
    }
    
    void closeAction(ActionEvent event) {
        Stage stage = (Stage)backButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void tabDeleteChanged(Event event) {
        
    }

    @FXML
    void tabDetailChanged(Event event) {
        
    }

    @FXML
    void tabUpdateChanged(Event event) {
        
    }

    @FXML
    void updateEnrollmentAction(ActionEvent event) {
        eInfo.setCourseID(updateCourseField.getText());
        eInfo.setSemesterID(Integer.parseInt(updateSemesterField.getText()));
        eInfo.setGrade(Float.parseFloat(updateGradeField.getText()));
        update(eInfo);
        closeAction(event);
    }

    public void setPreviousScene(Scene scene) {
        previousScene = scene;
        backButton.setDisable(false);
    }
    
    public void update(Enrollment model) {
        try {

            Enrollment dbModel = manager.find(Enrollment.class, model.getId());

            if (dbModel != null) {
                manager.getTransaction().begin();
                // update
                dbModel.setGrade(model.getGrade());
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

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
    
    public void loadData(){
        detailCourseField.setText(eInfo.getCourseID());
        detailSemesterField.setText(Integer.toString(eInfo.getSemesterID()));
        detailGradeField.setText(Float.toString(eInfo.getGrade()));
        updateCourseField.setText(eInfo.getCourseID());
        updateSemesterField.setText(Integer.toString(eInfo.getSemesterID()));
        updateGradeField.setText(Float.toString(eInfo.getGrade()));
        deleteCourseField.setText(eInfo.getCourseID());
        deleteSemesterField.setText(Integer.toString(eInfo.getSemesterID()));
        deleteGradeField.setText(Float.toString(eInfo.getGrade()));
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize(Enrollment enroll) {
        assert detailTab != null : "fx:id=\"detailTab\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert detailCourseField != null : "fx:id=\"detailCourseField\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert detailSemesterField != null : "fx:id=\"detailSemesterField\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert detailGradeField != null : "fx:id=\"detailGradeField\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert updateTab != null : "fx:id=\"updateTab\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert updateCourseField != null : "fx:id=\"updateCourseField\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert updateSemesterField != null : "fx:id=\"updateSemesterField\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert updateGradeField != null : "fx:id=\"updateGradeField\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert updateButton != null : "fx:id=\"updateButton\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert deleteTab != null : "fx:id=\"deleteTab\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert deleteCourseField != null : "fx:id=\"deleteCourseField\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert deleteSemesterField != null : "fx:id=\"deleteSemesterField\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert deleteGradeField != null : "fx:id=\"deleteGradeField\" was not injected: check your FXML file 'EnrollmentView.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'EnrollmentView.fxml'.";

        manager = (EntityManager) Persistence.createEntityManagerFactory("IST311ProjectD3").createEntityManager();

        eInfo = enroll;
        System.out.println(enroll);
        System.out.println(eInfo);
        
        loadData();
    }
}


