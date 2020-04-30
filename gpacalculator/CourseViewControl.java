/**
 * Sample Skeleton for 'CourseView.fxml' Controller Class
 */

package gpacalculator;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

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
    
    EntityManager manager;
    
    public void loadData(String cID) {
        Query query = manager.createNamedQuery("Enrollment.findByStudentIDandCourseID");
        query.setParameter("studentID", 123); //first is variable name second is the actual value being searched
        query.setParameter("courseID", cID);
        Enrollment data = (Enrollment) query.getSingleResult();
        Query query_course = manager.createNamedQuery("Course.findByCourseID");
        query_course.setParameter("courseID", cID);
        Course c = (Course) query_course.getSingleResult();
        String grade = formatGrade(data.getGrade());
        
        courseNumField.setText(c.getCourseID());
        courseNameField.setText(c.getCourseName());
        professorField.setText(c.getProfessor());
        creditField.setText(Integer.toString(c.getCredit()));
        gradeField.setText(grade);
        

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize(String cID) {
        assert courseNumField != null : "fx:id=\"courseNumField\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert courseNameField != null : "fx:id=\"courseNameField\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert professorField != null : "fx:id=\"professorField\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert creditField != null : "fx:id=\"creditField\" was not injected: check your FXML file 'CourseView.fxml'.";
        assert gradeField != null : "fx:id=\"gradeField\" was not injected: check your FXML file 'CourseView.fxml'.";
        //need to fix this so it takes in a course model as a parameter
        
        manager = (EntityManager) Persistence.createEntityManagerFactory("IST311ProjectD3").createEntityManager();

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
}
