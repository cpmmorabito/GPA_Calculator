/**
 * Sample Skeleton for 'StudentView.fxml' Controller Class
 */

package gpacalculator;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
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

/**
* This class is responsible for StudentView
* It connects to no other views
* It contains update and delete functionality for Student entity
* It uses the Student and Enrollment database tables
**/

public class StudentViewControl {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="detailTab"
    private Tab detailTab; // Value injected by FXMLLoader

    @FXML // fx:id="detailNameField"
    private TextField detailNameField; // Value injected by FXMLLoader

    @FXML // fx:id="detailMajorField"
    private TextField detailMajorField; // Value injected by FXMLLoader

    @FXML // fx:id="detailIDField"
    private TextField detailIDField; // Value injected by FXMLLoader
    
    @FXML // fx:id="updateTab"
    private Tab updateTab; // Value injected by FXMLLoader

    @FXML // fx:id="updateNameField"
    private TextField updateNameField; // Value injected by FXMLLoader

    @FXML // fx:id="updateMajorField"
    private TextField updateMajorField; // Value injected by FXMLLoader

    @FXML // fx:id="updateIDField"
    private TextField updateIDField; // Value injected by FXMLLoader

    @FXML // fx:id="updateButton"
    private Button updateButton; // Value injected by FXMLLoader

    @FXML // fx:id="deleteTab"
    private Tab deleteTab; // Value injected by FXMLLoader

    @FXML // fx:id="deleteNameField"
    private TextField deleteNameField; // Value injected by FXMLLoader

    @FXML // fx:id="deleteMajorField"
    private TextField deleteMajorField; // Value injected by FXMLLoader

    @FXML // fx:id="deleteIDField"
    private TextField deleteIDField; // Value injected by FXMLLoader

    @FXML // fx:id="deleteButton"
    private Button deleteButton; // Value injected by FXMLLoader

    private int studentID;
    private Student sInfo = new Student();
    Alert dAlert = new Alert(AlertType.CONFIRMATION);
    Alert uAlert = new Alert(AlertType.CONFIRMATION);
    EntityManager manager;
    
    // deletes student from the program
    @FXML
    void deleteStudentAction(ActionEvent event) {
        // delete confirmation dialog
        dAlert.setTitle("Confirmation Dialog");
        dAlert.setHeaderText("Delete this student?");
        dAlert.setContentText("Are you sure you want to delete " + sInfo.getStudentName() + "?");
        Optional<ButtonType> result = dAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
                // deletes from database
                delete();  
                // Tells user deletion was successful
                dAlert.setHeaderText("Deletion Complete");
                dAlert.setContentText("Program will now exit.");
                dAlert.showAndWait();
        }
        // exits program
        Platform.exit();
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


    // creates student model to update database with new values
    @FXML
    void updateStudentAction(ActionEvent event) {
        // makes new student with same values
        Student nStu = new Student();
        nStu.setStudentId(studentID);
        nStu.setMajor(sInfo.getMajor());
        nStu.setStudentName(sInfo.getStudentName());

        // fills new student with user input values if they are entered
        if (updateMajorField.getText() != null) {
            nStu.setMajor(updateMajorField.getText());
        }
        if (updateNameField.getText() != null) {
            nStu.setStudentName(updateNameField.getText());
        }
        // updates database
        update(nStu);
        // tells user the update was successful
        uAlert.setHeaderText("Update Complete");
        uAlert.setContentText("Window will now close.");
        uAlert.showAndWait();
        Stage stage = (Stage) updateButton.getScene().getWindow();
        // closes window
        stage.close();
    }
    
    // updates student in database
    public void update(Student nStu) {
        try {

            Student dbModel = manager.find(Student.class, sInfo.getStudentId());

            if (dbModel != null) {
                manager.getTransaction().begin();
                dbModel.setMajor(nStu.getMajor());
                dbModel.setStudentName(nStu.getStudentName());
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Deletes student from database and all instances of student in enrollment
    public void delete() {
        try {
            Student dbModel = manager.find(Student.class, sInfo.getStudentId());
            Query query = manager.createNamedQuery("Enrollment.findByStudentID");
            query.setParameter("studentID", studentID);
            List<Enrollment> dbData = query.getResultList();

            
            if (dbModel != null) {
                manager.getTransaction().begin();
                //remove model
                manager.remove(dbModel);
                if(dbData != null){
                    for (Enrollment d : dbData) {
                        manager.remove(d);
                    }
                }
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Loads data and fills in all of the fields
    public void loadData() {
        Query query = manager.createNamedQuery("Student.findByStudentId");
        query.setParameter("studentId", studentID); //first is variable name second is the actual value being searched
        Student data = (Student) query.getSingleResult();
        sInfo.setMajor(data.getMajor());
        sInfo.setStudentId(studentID);
        sInfo.setStudentName(data.getStudentName());
        detailNameField.setText(data.getStudentName());
        detailMajorField.setText(data.getMajor());
        detailIDField.setText(Integer.toString(data.getStudentId()));
        updateIDField.setText(Integer.toString(data.getStudentId()));
        deleteNameField.setText(data.getStudentName());
        deleteMajorField.setText(data.getMajor());
        deleteIDField.setText(Integer.toString(data.getStudentId()));
    }
    
    // Initializes controller
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize(int stuID) {
        assert detailTab != null : "fx:id=\"detailTab\" was not injected: check your FXML file 'StudentInfoUView.fxml'.";
        assert detailNameField != null : "fx:id=\"detailNameField\" was not injected: check your FXML file 'StudentInfoUView.fxml'.";
        assert detailMajorField != null : "fx:id=\"detailMajorField\" was not injected: check your FXML file 'StudentInfoUView.fxml'.";
        assert detailIDField != null : "fx:id=\"detailIDField\" was not injected: check your FXML file 'StudentInfoUView.fxml'.";
        assert updateTab != null : "fx:id=\"updateTab\" was not injected: check your FXML file 'StudentView.fxml'.";
        assert updateNameField != null : "fx:id=\"updateNameField\" was not injected: check your FXML file 'StudentInfoUView.fxml'.";
        assert updateMajorField != null : "fx:id=\"updateMajorField\" was not injected: check your FXML file 'StudentInfoUView.fxml'.";
        assert updateIDField != null : "fx:id=\"updateIDField\" was not injected: check your FXML file 'StudentInfoUView.fxml'.";
        assert updateButton != null : "fx:id=\"updateButton\" was not injected: check your FXML file 'StudentInfoUView.fxml'.";
        assert deleteTab != null : "fx:id=\"deleteTab\" was not injected: check your FXML file 'StudentInfoUView.fxml'.";
        assert deleteNameField != null : "fx:id=\"deleteNameField\" was not injected: check your FXML file 'StudentInfoUView.fxml'.";
        assert deleteMajorField != null : "fx:id=\"deleteMajorField\" was not injected: check your FXML file 'StudentInfoUView.fxml'.";
        assert deleteIDField != null : "fx:id=\"deleteIDField\" was not injected: check your FXML file 'StudentInfoUView.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'StudentInfoUView.fxml'.";

        manager = (EntityManager) Persistence.createEntityManagerFactory("IST311ProjectD3").createEntityManager();
        
        studentID = stuID;
        
        loadData();
    }
}
