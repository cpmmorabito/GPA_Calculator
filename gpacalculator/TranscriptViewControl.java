/**
 * Sample Skeleton for 'TranscriptView.fxml' Controller Class
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
* This class is responsible for TranscriptView
* It connects to NewCourseView and EnrollmentView and CouresView
* It contains create functionality for Enrollment entity
* It uses the Enrollment and Course database table
**/

public class TranscriptViewControl {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="modelTable"
    private TableView<Enrollment> modelTable; // Value injected by FXMLLoader

    @FXML // fx:id="modelColumnID"
    private TableColumn<Enrollment, String> modelColumnID; // Value injected by FXMLLoader

    @FXML // fx:id="modelColumnSemester"
    private TableColumn<Enrollment, Integer> modelColumnSemester; // Value injected by FXMLLoader

    @FXML // fx:id="modelColumnGrade"
    private TableColumn<Enrollment, Float> modelColumnGrade; // Value injected by FXMLLoader

    @FXML // fx:id="courseButton"
    private Button courseButton; // Value injected by FXMLLoader

    @FXML // fx:id="editEnrollmentButton"
    private Button editEnrollmentButton; // Value injected by FXMLLoader

    @FXML // fx:id="addButton"
    private Button addButton; // Value injected by FXMLLoader

    @FXML // fx:id="courseIDChoiceBox"
    private ChoiceBox<String> courseIDChoiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="semesterChoiceBox"
    private ChoiceBox<Integer> semesterChoiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="gradeTextField"
    private TextField gradeTextField; // Value injected by FXMLLoader

    @FXML // fx:id="addToTranscriptButton"
    private Button addToTranscriptButton; // Value injected by FXMLLoader

    private int studentID;
    Alert nAlert = new Alert(AlertType.CONFIRMATION);
    Alert cAlert = new Alert(AlertType.CONFIRMATION);
    Alert eAlert = new Alert(AlertType.CONFIRMATION);
    EntityManager manager;
    
    // opens New course view
    @FXML
    void addCourseAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewCourseView.fxml"));

        // load the ui elements
        Parent newCourseView = loader.load();
        // load the scene
        Scene newCourseViewScene = new Scene(newCourseView);

        //access the controller and call a method
        NewCourseViewControl controller = loader.getController();
        controller.initialize(studentID);

        controller.setPreviousScene(((Node) event.getSource()).getScene());

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newCourseViewScene);
        window.show();
    }

    // adds enrollment to selected semester
    @FXML
    void addEnrollmentAction(ActionEvent event) {
        try {
            // makes sure there is actual data in the fields
            if (courseIDChoiceBox.getValue() != null && gradeTextField.getText() != null && semesterChoiceBox.getValue() != null) {
                long id = getNextID();
                int stuID = studentID;
                String cID = courseIDChoiceBox.getValue();
                int sID = semesterChoiceBox.getValue();

                // creates new enrollment model
                Enrollment model = new Enrollment();
                model.setId(id);
                model.setCourseID(cID);
                model.setSemesterID(sID);
                model.setStudentID(stuID);

                model.setGrade(Float.parseFloat(gradeTextField.getText()));
                // add model to tableview
                if (!alreadyExists(model)) {
                     modelTable.getItems().add(model);

                // add model to database
                    create(model);
                } else {
                    // Tells user Enrollment already exists
                    nAlert.setTitle("Error Dialog");
                    nAlert.setHeaderText("New Enrollment Creation Failed");
                    nAlert.setContentText("Enrollment already exists");
                    nAlert.showAndWait();
                }
            } else {
                // Tells user the enrollment creation failed
                nAlert.setTitle("Error Dialog");
                nAlert.setHeaderText("New Enrollment Creation Failed");
                nAlert.setContentText("Please check that all fields are selected and filled in and try again.");
                nAlert.showAndWait();
            }
            
        } catch (NumberFormatException | NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Opens course view with selected course
    @FXML
    void displayCourseAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseView.fxml"));
            Parent courseView = loader.load();
            Scene courseViewScene = new Scene(courseView);

            // getting the controller from FXLoader
            CourseViewControl controller =  loader.getController();
            controller.initialize(modelTable.getSelectionModel().getSelectedItem().getCourseID(), studentID);
            // Show Second FXML in new a window
            controller.setPreviousScene(((Node) event.getSource()).getScene());

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(courseViewScene);
            window.show();
        } catch (IOException | NullPointerException ex) {
            // tells user to select a course to view
            System.err.println(ex);
            cAlert.setTitle("Error Dialog");
            cAlert.setHeaderText("View Course Failed");
            cAlert.setContentText("Please select a item from the table and try again.");
            cAlert.showAndWait();
        }
    }

    // Opens enrollment view with selected enrollment function
    @FXML
    void displayEnrollmentAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EnrollmentView.fxml"));
            Parent enrollView = loader.load();
            Scene enrollmentViewScene = new Scene(enrollView);

            // getting the controller from FXLoader
            EnrollmentViewControl controller =  loader.getController();
            // Show Second FXML in new a window
            controller.setPreviousScene(((Node) event.getSource()).getScene());
            controller.initialize(modelTable.getSelectionModel().getSelectedItem());

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(enrollmentViewScene);
            window.show();
        } catch (IOException | NullPointerException ex) {
            // tells uset to select an enrollment from the table
            System.err.println(ex);
            eAlert.setTitle("Error Dialog");
            eAlert.setHeaderText("View Enrollment Failed");
            eAlert.setContentText("Please select a item from the table and try again.");
            eAlert.showAndWait();
        }
    }
    
    // gets unique id for new enrollment entity
    public long getNextID(){
        Query query = manager.createNamedQuery("Enrollment.findAll");
        List<Enrollment> data = query.getResultList();
        long id = 0L;
        for(Enrollment d: data){
            id = d.getId();
        }
        id++;
        return id;
    }
    
    // checks to see if the enrollment entity already exists
    public boolean alreadyExists(Enrollment eCheck){
        Query query = manager.createNamedQuery("Enrollment.findAll");
        List<Enrollment> data = query.getResultList();

        for(Enrollment d: data){
            if(d.getCourseID().equals(eCheck.getCourseID())){
                if(d.getSemesterID() == eCheck.getSemesterID()){
                    if(d.getStudentID() == eCheck.getStudentID()){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // creates Enrollment entity in database
    public void create(Enrollment model) {
        try {
            manager.getTransaction().begin();
            if (model.getId() != null) {
                // create model
                manager.persist(model);
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // Loads data for the selected student and fills table
    public void loadData() {
        Query query = manager.createNamedQuery("Enrollment.findByStudentID");
        query.setParameter("studentID", studentID); 
        List<Enrollment> data = query.getResultList();

        ObservableList<Enrollment> odata = FXCollections.observableArrayList();

        for (Enrollment d : data) {
            odata.add(d);
        }
        modelTable.setItems(odata);
    }
    
    // Initializes controller
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize(int stuID) {
        assert modelTable != null : "fx:id=\"modelTable\" was not injected: check your FXML file 'TranscriptView.fxml'.";
        assert modelColumnID != null : "fx:id=\"modelColumnID\" was not injected: check your FXML file 'TranscriptView.fxml'.";
        assert modelColumnSemester != null : "fx:id=\"modelColumnSemester\" was not injected: check your FXML file 'TranscriptView.fxml'.";
        assert modelColumnGrade != null : "fx:id=\"modelColumnGrade\" was not injected: check your FXML file 'TranscriptView.fxml'.";
        assert courseButton != null : "fx:id=\"courseButton\" was not injected: check your FXML file 'TranscriptView.fxml'.";
        assert editEnrollmentButton != null : "fx:id=\"editEnrollmentButton\" was not injected: check your FXML file 'TranscriptView.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'TranscriptView.fxml'.";
        assert courseIDChoiceBox != null : "fx:id=\"courseIDChoiceBox\" was not injected: check your FXML file 'TranscriptView.fxml'.";
        assert semesterChoiceBox != null : "fx:id=\"semesterChoiceBox\" was not injected: check your FXML file 'TranscriptView.fxml'.";
        assert gradeTextField != null : "fx:id=\"gradeTextField\" was not injected: check your FXML file 'TranscriptView.fxml'.";
        assert addToTranscriptButton != null : "fx:id=\"addToTranscriptButton\" was not injected: check your FXML file 'TranscriptView.fxml'.";
        
        semesterChoiceBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        
        modelTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        //set up the columns in the table
        modelColumnID.setCellValueFactory(new PropertyValueFactory<>("CourseID")); 
        modelColumnSemester.setCellValueFactory(new PropertyValueFactory<>("SemesterID")); 
        modelColumnGrade.setCellValueFactory(new PropertyValueFactory<>("Grade"));
        // loading data from database
        manager = (EntityManager) Persistence.createEntityManagerFactory("IST311ProjectD3").createEntityManager();
        
        Query query = manager.createNamedQuery("Course.findAll");
        List<Course> data = query.getResultList();
        ObservableList<String> odata = FXCollections.observableArrayList();
        for (Course d : data) {
            odata.add(d.getCourseID());
        }
        courseIDChoiceBox.setItems(odata);
        studentID = stuID;
        loadData();
    }
}
