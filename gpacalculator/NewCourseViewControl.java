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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

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

    EntityManager manager;
    
    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    private int studentID;
    
    @FXML
    void addCourseAction(ActionEvent event) {
        try{
            String id = courseNumField.getText();
            String name = courseNameField.getText();
            String prof = professorField.getText();
            int credit = Integer.parseInt(creditField.getText());

            Course course = new Course();
            course.setCourseID(id);
            course.setCourseName(name);
            course.setProfessor(prof);
            course.setCredit(credit);
            if(!doesExist(id)){
                create(course);
                closeAction(event);
            }
        } catch (NumberFormatException | NullPointerException ex){
            System.out.println(ex.getMessage());
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
    
    Scene previousScene;

    public void setPreviousScene(Scene scene) {
        previousScene = scene;
        backButton.setDisable(false);
    } 
    
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
    
    public boolean doesExist(String id){
        Query query = manager.createNamedQuery("Course.findAll");
        List<Course> data = query.getResultList();
        for(Course c: data){
            if(id.equals(c.getCourseID())){
                return true;
            }
        }
        return false;
    }

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
