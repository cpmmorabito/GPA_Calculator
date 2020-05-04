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
    EntityManager manager;
    
    @FXML
    void addCourseAction(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewCourseView.fxml"));

        // load the ui elements
        Parent newCourseView = loader.load();
        // load the scene
        Scene newCourseViewScene = new Scene(newCourseView);

        //access the controller and call a method
        NewCourseViewControl controller = loader.getController();

        controller.setPreviousScene(((Node) event.getSource()).getScene());

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newCourseViewScene);
        window.show();
    }

    @FXML
    void addEnrollmentAction(ActionEvent event) {
        try{
            long id = getNextID();
            int stuID = studentID;
            String cID = courseIDChoiceBox.getValue();
            int sID = semesterChoiceBox.getValue();

            Enrollment model = new Enrollment();
            model.setId(id);
            model.setCourseID(cID);
            model.setSemesterID(sID);
            model.setStudentID(stuID);

            model.setGrade(Float.parseFloat(gradeTextField.getText()));
            // add model to tableview
            if(!alreadyExists(model)){
                 modelTable.getItems().add(model);

            // add model to database
                create(model);
            }
        } catch (NumberFormatException | NullPointerException ex){
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void displayCourseAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseView.fxml"));
            Parent courseView = loader.load();
            Scene courseViewScene = new Scene(courseView);

            // getting the controller from FXLoader
            CourseViewControl controller =  loader.getController();
            //secondController.displayMessage(messageInput.getText());
            controller.initialize(modelTable.getSelectionModel().getSelectedItem().getCourseID(), studentID);
            // Show Second FXML in new a window
            controller.setPreviousScene(((Node) event.getSource()).getScene());

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(courseViewScene);
            window.show();
        }
        catch (IOException | NullPointerException ex) {
            System.err.println(ex);
        }
    }

    @FXML
    void displayEnrollmentAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EnrollmentView.fxml"));
            Parent enrollmentView = loader.load();
            Scene enrollmentViewScene = new Scene(enrollmentView);

            // getting the controller from FXLoader
            EnrollmentViewControl controller =  loader.getController();
            //secondController.displayMessage(messageInput.getText());
            
            // Show Second FXML in new a window
            controller.setPreviousScene(((Node) event.getSource()).getScene());
            controller.initialize(modelTable.getSelectionModel().getSelectedItem());

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(enrollmentViewScene);
            window.show();
        }
        catch (IOException | NullPointerException ex) {
            System.err.println(ex);
        }
    }

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
        modelColumnID.setCellValueFactory(new PropertyValueFactory<>("CourseID")); //should match with attribute Id (e.g., getId/setId methods) in SimpleModel
        modelColumnSemester.setCellValueFactory(new PropertyValueFactory<>("SemesterID")); //should match with attribute Value (e.g., getValue/setValue methods) in SimpleModel
        modelColumnGrade.setCellValueFactory(new PropertyValueFactory<>("Grade"));
        // loading data from database
        //database reference: "IST311ProjectPU"
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
