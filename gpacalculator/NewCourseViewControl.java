/**
 * Sample Skeleton for 'NewCourseView.fxml' Controller Class
 */

package gpacalculator;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
* This class is responsible for NewCourseView 
* It connects to SemesterView and TranscriptView
* It contains create functionality for Course entity
* It uses the Course database table
**/

public class NewCourseViewControl {

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

    @FXML // fx:id="addCourseButton"
    private Button addCourseButton; // Value injected by FXMLLoader
    
    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    EntityManager manager;
    private int studentID;
    Alert alert = new Alert(AlertType.CONFIRMATION);
    Scene previousScene;
    

    // Creates new course variable and adds it to the database if it doesn't already exist
    @FXML
    void addCourseAction(ActionEvent event) {
        try {
            // Checks to see that the fields aren't empty
            if (!courseNumField.getText().isBlank() && !courseNameField.getText().isBlank() && !professorField.getText().isBlank() && !creditField.getText().isBlank()) {
                // Creates new course from user input
                String id = courseNumField.getText();
                String name = courseNameField.getText();
                String prof = professorField.getText();
                int credit = Integer.parseInt(creditField.getText());
                Course course = new Course();
                course.setCourseID(id);
                course.setCourseName(name);
                course.setProfessor(prof);
                course.setCredit(credit);
                // Makes sure course doesn't already exist
                if (!doesExist(id)) {
                    // Creates course in database
                    create(course);
                    // Tells user the course was made
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Creation Successful");
                    alert.setContentText("Window will now return to PrintoutView.");
                    alert.showAndWait();
                    closeAction(event);
                } else {
                    // Tells user the course already exists
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("New Course Creation Failed");
                    alert.setContentText("Please check that course number does not already exist and try again.");
                    alert.showAndWait();
                }
            } else {
                // Tells user the course could not be created cause values were null
                alert.setTitle("Error Dialog");
                alert.setHeaderText("New Course Creation Failed");
                alert.setContentText("Please check that all fields are filled in and try again.");
                alert.showAndWait();
            }
        } catch (NumberFormatException | NullPointerException ex) {
            System.out.println(ex.getMessage());
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
    
    // Cycles through all courses to see if the course id already exists
    public boolean doesExist(String id) {
        Query query = manager.createNamedQuery("Course.findAll");
        List<Course> data = query.getResultList();
        for(Course c: data) {
            if (id.equals(c.getCourseID())) {
                return true;
            }
        }
        return false;
    }

    // Creates course entity in database
    public void create(Course course) {
        try {
            manager.getTransaction().begin();
            if (course.getCourseID() != null) {
                // create model
                manager.persist(course);
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Initializes controller
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize(int stuID) {
        assert courseNumField != null : "fx:id=\"courseNumField\" was not injected: check your FXML file 'NewCourseView.fxml'.";
        assert courseNameField != null : "fx:id=\"courseNameField\" was not injected: check your FXML file 'NewCourseView.fxml'.";
        assert professorField != null : "fx:id=\"professorField\" was not injected: check your FXML file 'NewCourseView.fxml'.";
        assert creditField != null : "fx:id=\"creditField\" was not injected: check your FXML file 'NewCourseView.fxml'.";
        assert addCourseButton != null : "fx:id=\"addCourseButton\" was not injected: check your FXML file 'NewCourseView.fxml'.";
        
        manager = (EntityManager) Persistence.createEntityManagerFactory("IST311ProjectD3").createEntityManager();
        
        backButton.setDisable(true);
        
        studentID = stuID;
    }
}
