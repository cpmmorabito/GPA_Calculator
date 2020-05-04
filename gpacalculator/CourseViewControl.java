/**
 * Sample Skeleton for 'CourseView.fxml' Controller Class
 */

package gpacalculator;

import java.net.URL;
import java.util.List;
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

/**
* This class is responsible for CourseView
* It connects to SemesterView and TranscriptView
* It contains update and delete functionality for Course entity
* It uses the Enrollment and Course database tables
*/

public class CourseViewControl {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="detailTab"
    private Tab detailTab; // Value injected by FXMLLoader

    @FXML // fx:id="detailNumField"
    private TextField detailNumField; // Value injected by FXMLLoader

    @FXML // fx:id="detailNameField"
    private TextField detailNameField; // Value injected by FXMLLoader

    @FXML // fx:id="detailProfField"
    private TextField detailProfField; // Value injected by FXMLLoader

    @FXML // fx:id="detailCreditField"
    private TextField detailCreditField; // Value injected by FXMLLoader

    @FXML // fx:id="gradeField"
    private TextField gradeField; // Value injected by FXMLLoader
    
    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="updateTab"
    private Tab updateTab; // Value injected by FXMLLoader

    @FXML // fx:id="updateNumField"
    private TextField updateNumField; // Value injected by FXMLLoader

    @FXML // fx:id="updateNameField"
    private TextField updateNameField; // Value injected by FXMLLoader

    @FXML // fx:id="updateProfField"
    private TextField updateProfField; // Value injected by FXMLLoader

    @FXML // fx:id="updateCreditField"
    private TextField updateCreditField; // Value injected by FXMLLoader

    @FXML // fx:id="updateButton"
    private Button updateButton; // Value injected by FXMLLoader

    @FXML // fx:id="deleteTab"
    private Tab deleteTab; // Value injected by FXMLLoader

    @FXML // fx:id="deleteNumField"
    private TextField deleteNumField; // Value injected by FXMLLoader

    @FXML // fx:id="deleteNameField"
    private TextField deleteNameField; // Value injected by FXMLLoader

    @FXML // fx:id="deleteProfField"
    private TextField deleteProfField; // Value injected by FXMLLoader

    @FXML // fx:id="deleteCreditField"
    private TextField deleteCreditField; // Value injected by FXMLLoader

    @FXML // fx:id="deleteButton"
    private Button deleteButton; // Value injected by FXMLLoader
    
    EntityManager manager;
    private Course cInfo = new Course();
    private int studentID;
    private String cGrade;
    private Alert uAlert = new Alert(AlertType.CONFIRMATION);
    private Alert dAlert = new Alert(AlertType.CONFIRMATION);       
    Scene previousScene;

    // Deletes the selected course
    @FXML
    void deleteCourseAction(ActionEvent event) {
        // Delete confirmation dialog
        dAlert.setTitle("Confirmation Dialog");
        dAlert.setHeaderText("Delete this course?");
        dAlert.setContentText("Are you sure you want to delete " + cInfo.getCourseID() + "?");
        Optional<ButtonType> result = dAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (canDelete()) {
                // Deletes course
                delete(cInfo);
                // Tells user deletion was successful
                dAlert.setHeaderText("Deletion Complete");
                dAlert.setContentText("Window will now return to PrintoutView.");
                dAlert.showAndWait();
                // Closes window
                closeAction(event);
            } else {
                // If course cannot be deleted, program tells the user
                dAlert.setHeaderText("Deletion Failed");
                dAlert.setContentText("This course cannot be deleted at this time. Please hit the Back button to return to the previous view, or close the window return to Printout menu.");
                dAlert.showAndWait();
            }
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

    // Updates the selected course in the database
    @FXML
    void updateCourseAction(ActionEvent event) {
        try {
            Course nCourse = new Course();
            // Fills the course with dummy values in case the user didn't fill in a field
            nCourse.setCourseID(cInfo.getCourseID());
            nCourse.setCourseName(cInfo.getCourseName());
            nCourse.setProfessor(cInfo.getProfessor());
            nCourse.setCredit(cInfo.getCredit());

            // Checks which fields have been filled in and assigns them to their respective attribute
            if (updateNameField.getText() != null) {
                nCourse.setCourseName(updateNameField.getText());
            }
            if (updateProfField.getText() != null) {
                nCourse.setProfessor(updateProfField.getText());
            }
            if (updateCreditField.getText() != null) {
                nCourse.setCredit(Integer.parseInt(updateCreditField.getText()));
            }

            // Updates course
            update(nCourse);

            // Tells user that the operation was successful
            uAlert.setTitle("Confirmation Dialog");
            uAlert.setHeaderText("Course Updated");
            uAlert.setContentText(cInfo.getCourseID() + " successfully updated. Window will now return to PrintoutView.");
            uAlert.showAndWait();
            closeAction(event);
        } catch (NumberFormatException | NullPointerException ex) {
            // Tells user that the operation was not successful
            System.out.println(ex.getMessage());
            uAlert.setHeaderText("Update Failed");
            uAlert.setContentText("This course cannot be updated at this time. Please hit the Back button to return to the previous view, or close the window return to Printout menu.");
            uAlert.showAndWait();
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
    
    // Takes the grade attribute from the Course table and formats it to a letter grade
    public String formatGrade(Float grade) {
        int letterGrade = grade.intValue();
        if (letterGrade >= 94) {
            return "A";
        } else if (letterGrade >= 90) {
            return "A-";
        } else if (letterGrade >= 87) {
            return "B+";
        } else if (letterGrade >= 84) {
            return "B";
        } else if (letterGrade >= 80) {
            return "B-";
        } else if (letterGrade >= 77) {
            return "C+";
        } else if (letterGrade >= 74) {
            return "C";
        } else if (letterGrade >= 64) {
            return "D";
        } else {
            return "F";
        }
    }
    
    // Checks to see if any other students have this course in their Enrollment
    public boolean canDelete() {
        Query query = manager.createNamedQuery("Enrollment.findByCourseID");
        query.setParameter("courseID", cInfo.getCourseID());
        List<Enrollment> data = query.getResultList();
        for (Enrollment d : data) {
            if (d.getStudentID() != studentID) {
                return false;
            }
        }
        return true;
    }
    
    // Updates course database
    public void update(Course nCourse) {
        try {
            Course dbModel = manager.find(Course.class, cInfo.getCourseID());
            if (dbModel != null) {
                manager.getTransaction().begin();
                dbModel.setCourseName(nCourse.getCourseName());
                dbModel.setCredit(nCourse.getCredit());
                dbModel.setProfessor(nCourse.getProfessor());
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Removes course from database and any enrollments with said course
    public void delete(Course course) {
        try {
            Course dbCourse = manager.find(Course.class, course.getCourseID());
            Query query = manager.createNamedQuery("Enrollment.findByStudentIDandCourseID");
            query.setParameter("studentID", studentID);
            query.setParameter("courseID", course.getCourseID());
            List<Enrollment> data = query.getResultList();
            if (dbCourse != null) {
                manager.getTransaction().begin();
                //remove model
                manager.remove(dbCourse);
                for (Enrollment e: data) {
                    if (e.getCourseID().equals(course.getCourseID())) {
                        manager.remove(e);
                    }
                }
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // Loads data for the selected course and fills in all text fields
    public void loadData(String cID) {
        Query query = manager.createNamedQuery("Enrollment.findByStudentIDandCourseID");
        query.setParameter("studentID", studentID);
        query.setParameter("courseID", cID);
        Enrollment data = (Enrollment) query.getSingleResult();
        cGrade = formatGrade(data.getGrade());
        Query query_course = manager.createNamedQuery("Course.findByCourseID");
        query_course.setParameter("courseID", cID);
        cInfo = (Course) query_course.getSingleResult();
        
        detailNumField.setText(cInfo.getCourseID());
        detailNameField.setText(cInfo.getCourseName());
        detailProfField.setText(cInfo.getProfessor());
        detailCreditField.setText(Integer.toString(cInfo.getCredit()));
        gradeField.setText(cGrade);
        deleteNumField.setText(cInfo.getCourseID());
        deleteNameField.setText(cInfo.getCourseName());
        deleteProfField.setText(cInfo.getProfessor());
        deleteCreditField.setText(Integer.toString(cInfo.getCredit()));
        updateNumField.setText(cInfo.getCourseID());
    }

    // Initializes controller
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize(String cID, int stuID) {
        assert detailTab != null : "fx:id=\"detailTab\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert detailNumField != null : "fx:id=\"detailNumField\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert detailNameField != null : "fx:id=\"detailNameField\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert detailProfField != null : "fx:id=\"detailProfField\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert detailCreditField != null : "fx:id=\"detailCreditField\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert gradeField != null : "fx:id=\"gradeField\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert updateTab != null : "fx:id=\"updateTab\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert updateNumField != null : "fx:id=\"updateNumField\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert updateNameField != null : "fx:id=\"updateNameField\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert updateProfField != null : "fx:id=\"updateProfField\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert updateCreditField != null : "fx:id=\"updateCreditField\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert updateButton != null : "fx:id=\"updateButton\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert deleteTab != null : "fx:id=\"deleteTab\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert deleteNumField != null : "fx:id=\"deleteNumField\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert deleteNameField != null : "fx:id=\"deleteNameField\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert deleteProfField != null : "fx:id=\"deleteProfField\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert deleteCreditField != null : "fx:id=\"deleteCreditField\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'CourseView.fxml'.";
        
        manager = (EntityManager) Persistence.createEntityManagerFactory("IST311ProjectD3").createEntityManager();
        studentID = stuID;
        loadData(cID);
    }

}
