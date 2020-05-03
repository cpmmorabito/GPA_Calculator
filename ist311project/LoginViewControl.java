/**
 * Sample Skeleton for 'LoginView.fxml' Controller Class
 */

package ist311project;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class LoginViewControl {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="userChoiceBox"
    private ChoiceBox<?> userChoiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="loginButton"
    private Button loginButton; // Value injected by FXMLLoader

    @FXML
    void loginAction(ActionEvent event) {
        //should take whatever user is selected in the choice box and initialize that as the student
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert userChoiceBox != null : "fx:id=\"userChoiceBox\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'LoginView.fxml'.";

    }
}