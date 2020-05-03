/**
 * Sample Skeleton for 'TranscriptView.fxml' Controller Class
 */

package ist311project;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

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

    @FXML // fx:id="addButton"
    private Button addButton; // Value injected by FXMLLoader

    @FXML // fx:id="deleteButton"
    private Button deleteButton; // Value injected by FXMLLoader

    @FXML
    void addAction(ActionEvent event) {

    }

    @FXML
    void displayCourseAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseView.fxml"));
            Parent secondRoot = loader.load();

            // getting the controller from FXLoader
            CourseViewControl secondController =  loader.getController();
            //secondController.displayMessage(messageInput.getText());
            secondController.initialize(modelTable.getSelectionModel().getSelectedItem().getCourseID());
            // Show Second FXML in new a window
            Stage stage = new Stage();
            stage.setScene(new Scene(secondRoot));
            stage.setTitle("Second Window");
            stage.show();
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @FXML
    void removeAction(ActionEvent event) {

    }

    EntityManager manager;

    public void loadData() {
        Query query = manager.createNamedQuery("Enrollment.findByStudentID");
        query.setParameter("studentID", 123); //first is variable name second is the actual value being searched
        //query.setParameter("couseID", "IST311");
        List<Enrollment> data = query.getResultList();

        ObservableList<Enrollment> odata = FXCollections.observableArrayList();

        for (Enrollment d : data) {
            //Query query_course = manager.createNamedQuery("Course.findCoursebyID");
            //query_course.setParameter("courseID", d.getCourseID());
            
            //Course c = query_course.getSingleResult();
            //int cred = c.getCredit();
            odata.add(d);
        }

        modelTable.setItems(odata);
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert modelTable != null : "fx:id=\"modelTable\" was not injected: check your FXML file 'TranscriptView.fxml'.";
        assert modelColumnID != null : "fx:id=\"modelColumnID\" was not injected: check your FXML file 'TranscriptView.fxml'.";
        assert modelColumnSemester != null : "fx:id=\"modelColumnSemester\" was not injected: check your FXML file 'TranscriptView.fxml'.";
        assert modelColumnGrade != null : "fx:id=\"modelColumnGrade\" was not injected: check your FXML file 'TranscriptView.fxml'.";
        assert courseButton != null : "fx:id=\"courseButton\" was not injected: check your FXML file 'TranscriptView.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'TranscriptView.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'TranscriptView.fxml'.";

        modelTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        //set up the columns in the table
        modelColumnID.setCellValueFactory(new PropertyValueFactory<>("CourseID")); //should match with attribute Id (e.g., getId/setId methods) in SimpleModel
        modelColumnSemester.setCellValueFactory(new PropertyValueFactory<>("SemesterID")); //should match with attribute Value (e.g., getValue/setValue methods) in SimpleModel
        modelColumnGrade.setCellValueFactory(new PropertyValueFactory<>("Grade"));
        // loading data from database
        //database reference: "IST311ProjectPU"
        manager = (EntityManager) Persistence.createEntityManagerFactory("IST311ProjectD3").createEntityManager();

        //loading data
        loadData();
        
    }
}