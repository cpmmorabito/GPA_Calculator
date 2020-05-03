/**
 * Sample Skeleton for 'StudentInfoUView.fxml' Controller Class
 */

package ist311project;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class StudentInfoUViewControl {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="nameField"
    private TextField nameField; // Value injected by FXMLLoader

    @FXML // fx:id="majorField"
    private TextField majorField; // Value injected by FXMLLoader

    @FXML // fx:id="studentIDField"
    private TextField studentIDField; // Value injected by FXMLLoader

    @FXML // fx:id="closeButton"
    private Button closeButton; // Value injected by FXMLLoader

    EntityManager manager;
    
    @FXML
    void closeAction(ActionEvent event) {

    }
    
    public void loadData(int stuID) {
        Query query = manager.createNamedQuery("Student.findByStudentId");
        query.setParameter("studentId", stuID); //first is variable name second is the actual value being searched
        Student data = (Student) query.getSingleResult();
        nameField.setText(data.getStudentName());
        majorField.setText(data.getMajor());
        studentIDField.setText(Integer.toString(data.getStudentId()));

    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize(int stuID) {
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'StudentInfoUView.fxml'.";
        assert majorField != null : "fx:id=\"majorField\" was not injected: check your FXML file 'StudentInfoUView.fxml'.";
        assert studentIDField != null : "fx:id=\"studentIDField\" was not injected: check your FXML file 'StudentInfoUView.fxml'.";
        assert closeButton != null : "fx:id=\"closeButton\" was not injected: check your FXML file 'StudentInfoUView.fxml'.";

        manager = (EntityManager) Persistence.createEntityManagerFactory("IST311ProjectD3").createEntityManager();

        //loading data
        loadData(stuID);    
    }
}
