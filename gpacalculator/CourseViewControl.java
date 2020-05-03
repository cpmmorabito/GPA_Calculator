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
    private Course cInfo;
    private int studentID;
    private String cGrade;
    private long eID;
    Scene previousScene;
    private boolean isDeleted = false;
    

    @FXML
    void deleteCourseAction(ActionEvent event) {
         // delete confirmation dialog
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete this course?");
        alert.setContentText("Are you sure you want to delete " + cInfo.getCourseID() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if(canDelete()){
                delete();
                updateButton.setDisable(true);
                isDeleted = true;
                deleteNumField.setText("");
                deleteNameField.setText("");
                deleteProfField.setText("");
                deleteCreditField.setText("");  
                alert.setHeaderText("Deletion Complete");
                alert.setContentText("Please hit the Back button to return to the Semester view, or close the window to exit the Semester view.");
                alert.showAndWait();
            } else {
                alert.setHeaderText("Deletion Failed");
                alert.setContentText("This course cannot be deleted at this time. Please hit the Back button to return to the Semester view, or close the window to exit the Semester view.");
                alert.showAndWait();
            }
        }
        
        deleteButton.setDisable(true);
        
    }

    @FXML
    void tabDeleteChanged(Event event) {
        detailNumField.setText(cInfo.getCourseID());
        detailNameField.setText(cInfo.getCourseName());
        detailProfField.setText(cInfo.getProfessor());
        detailCreditField.setText(Integer.toString(cInfo.getCredit()));
        
        if(isDeleted){
            deleteNumField.setText("");
            deleteNameField.setText("");
            deleteProfField.setText("");
            deleteCreditField.setText("");
        }
    }

    @FXML
    void tabDetailChanged(Event event) {
        detailNumField.setText(cInfo.getCourseID());
        detailNameField.setText(cInfo.getCourseName());
        detailProfField.setText(cInfo.getProfessor());
        detailCreditField.setText(Integer.toString(cInfo.getCredit()));
        gradeField.setText(cGrade);
        
        if(isDeleted){
            detailNumField.setText("");
            detailNameField.setText("");
            detailProfField.setText("");
            detailCreditField.setText("");
            gradeField.setText("");
        }
    }

    @FXML
    void tabUpdateChanged(Event event) {
        updateNumField.setText(cInfo.getCourseID());
        updateNameField.setText("");
        updateProfField.setText("");
        updateCreditField.setText("");
        
        if(isDeleted){
            updateNumField.setText("");
            updateNameField.setText("");
            updateProfField.setText("");
            updateCreditField.setText("");
        }
    }

    @FXML
    void updateCourseAction(ActionEvent event) {
        try{
            Course nCourse = new Course();
            nCourse.setCourseID(cInfo.getCourseID());
            nCourse.setCourseName(updateNameField.getText());
            nCourse.setProfessor(updateProfField.getText());
            nCourse.setCredit(Integer.parseInt(updateCreditField.getText()));

            update(nCourse);
        } catch (NumberFormatException | NullPointerException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    void showBackAction(ActionEvent event) {
        Stage stage = (Stage)backButton.getScene().getWindow();
        if (previousScene != null){
            stage.setScene(previousScene);
        }
    }

    public void setPreviousScene(Scene scene) {
        previousScene = scene;
        backButton.setDisable(false);
    }
    
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

    public void delete() {
        try {
            Enrollment dbModel = manager.find(Enrollment.class, eID);
            Course dbCourse = manager.find(Course.class, cInfo.getCourseID());
            if (dbModel != null && dbCourse != null) {
                manager.getTransaction().begin();
                //remove model
                manager.remove(dbModel);
                manager.remove(dbCourse);
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void loadData(String cID) {
        Query query = manager.createNamedQuery("Enrollment.findByStudentIDandCourseID");
        query.setParameter("studentID", studentID);
        query.setParameter("courseID", cID);
        Enrollment data = (Enrollment) query.getSingleResult();
        Query query_course = manager.createNamedQuery("Course.findByCourseID");
        query_course.setParameter("courseID", cID);
        cInfo = (Course) query_course.getSingleResult();
        cGrade = formatGrade(data.getGrade());
        eID = data.getId();
        
        detailNumField.setText(cInfo.getCourseID());
        detailNameField.setText(cInfo.getCourseName());
        detailProfField.setText(cInfo.getProfessor());
        detailCreditField.setText(Integer.toString(cInfo.getCredit()));
        gradeField.setText(cGrade);
    }

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
        
        backButton.setDisable(true);
        studentID = stuID;
        //loading data
        loadData(cID);
    }

    private String formatGrade(Float grade) {
        int nGrade = grade.intValue();
        if (nGrade >= 94){
            return "A";
        } else if (nGrade >= 90){
            return "A-";
        } else if (nGrade >= 87){
            return "B+";
        } else if (nGrade >= 84){
            return "B";
        } else if (nGrade >= 80){
            return "B-";
        } else if (nGrade >= 77){
            return "C+";
        } else if (nGrade >= 74){
            return "C";
        } else if (nGrade >= 64){
            return "D";
        } else {
            return "F";
        }
    }
    
    public boolean canDelete(){
        Query query = manager.createNamedQuery("Enrollment.findByCourseID");
        query.setParameter("courseID", cInfo.getCourseID());
        List<Enrollment> data = query.getResultList();
        for (Enrollment d : data) {
            if(d.getStudentID() != studentID){
                return false;
            }
        }
        return true;
    }
}
