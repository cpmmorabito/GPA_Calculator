/**
 * Sample Skeleton for 'LoginView.fxml' Controller Class
 */

package gpacalculator;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
* This class is responsible for LoginView
* It connects to OverallView 
* It contains create functionality for Student entity
* It uses the Student database table
**/

public class LoginViewControl {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="userChoiceBox"
    private ChoiceBox<Integer> userChoiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="loginButton"
    private Button loginButton; // Value injected by FXMLLoader

    @FXML // fx:id="newNameField"
    private TextField newNameField; // Value injected by FXMLLoader

    @FXML // fx:id="newMajorField"
    private TextField newMajorField; // Value injected by FXMLLoader

    @FXML // fx:id="newStudentButton"
    private Button newStudentButton; // Value injected by FXMLLoader

    EntityManager manager;
    Alert sAlert = new Alert(AlertType.CONFIRMATION);
    Alert nAlert = new Alert(AlertType.CONFIRMATION);
    
    // Creates new student from new student tab
    @FXML
    void createStudentAction(ActionEvent event) throws IOException {
        // makes sure that fields are filled
        if (newNameField.getText() != null && newMajorField.getText() != null) {
            int stuID = getNextID();
            String name = newNameField.getText();
            String major = newMajorField.getText();
            Student newStudent = new Student();
            newStudent.setStudentId(stuID);
            newStudent.setStudentName(name);
            newStudent.setMajor(major);
            // makes new student in database
            create(newStudent);
            // logs in
            login(stuID, event);
        } else {
            // tells user to fill in the fields
            nAlert.setTitle("Error Dialog");
            nAlert.setHeaderText("New Student Creation Failed");
            nAlert.setContentText("Please check that all fields are filled in and try again.");
            nAlert.showAndWait();
        }
    }

    // calls login method from existing student screen
    @FXML
    void loginAction(ActionEvent event) throws IOException {
        // checks to make sure input isnt null
        if (userChoiceBox.getSelectionModel().getSelectedItem() != null) {
            int stuID = userChoiceBox.getSelectionModel().getSelectedItem();
            login(stuID, event);
        } else {
            //Tells user to select a student id
            sAlert.setTitle("Error Dialog");
            sAlert.setHeaderText("Login Failed");
            sAlert.setContentText("Please select student id and try again.");
            sAlert.showAndWait();
        }
    }
    
    // Opens OverallView in same window
    public void login(int stuID, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OverallView.fxml"));
        // load the ui elements
        Parent overallView = loader.load();
        // load the scene
        Scene overallViewScene = new Scene(overallView);
        //access the controller and call a method
        OverallViewControl controller = loader.getController();
        controller.initialize(stuID);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(overallViewScene);
        window.show();
    }
    
    // generates id for student that does not already exist
    public int getNextID() {
        Query query = manager.createNamedQuery("Student.findAll");
        List<Student> data = query.getResultList();
        int id = 0;
        for(Student d: data){
            id = d.getStudentId();
        }
        id++;
        return id;
    }
    
    // Creates student in database
    public void create(Student model) {
        try {
            manager.getTransaction().begin();
            if (model.getStudentId() != null) {
                // create model
                manager.persist(model);
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Initializes controller
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert userChoiceBox != null : "fx:id=\"userChoiceBox\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert newNameField != null : "fx:id=\"newNameField\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert newMajorField != null : "fx:id=\"newMajorField\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert newStudentButton != null : "fx:id=\"newStudentButton\" was not injected: check your FXML file 'LoginView.fxml'.";

        manager = (EntityManager) Persistence.createEntityManagerFactory("IST311ProjectD3").createEntityManager();
        
        Query query = manager.createNamedQuery("Student.findAll");
        List<Student> data = query.getResultList();
        ObservableList<Integer> odata = FXCollections.observableArrayList();
        for (Student d : data) {
            int id = d.getStudentId();
            odata.add(id);
        }
        userChoiceBox.setItems(odata);
    }
}
