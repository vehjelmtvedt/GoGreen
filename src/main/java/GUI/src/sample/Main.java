package GUI.src.sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import jdk.internal.util.xml.impl.Input;

import java.util.Set;

public class Main extends Application {
    @Override
    public void start(Stage window) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        //setup main window
        window.setTitle("Login/Sign-up");

        //close button handling
        window.setOnCloseRequest(e -> {
            e.consume();
            ConfirmBox.closeProgram(window);
        });
        //create borderPane for quick form setup
        BorderPane mainLayout = new BorderPane();

        //initialise forms
        GridPane signupForm = SetupStructure.createRegistrationForm();
        GridPane loginForm = SetupStructure.createRegistrationForm();

        //add the ui controls to the form
        SetupStructure.addUIControls(signupForm, 1);
        SetupStructure.addUIControls(loginForm, 2);

        //set top/right/bottom/center/left for main borderPane
        mainLayout.setCenter(signupForm);
//        mainLayout.setTop(loginForm);

        //set final primary stage aka window
        Scene scene = new Scene(mainLayout, 600, 400);
        window.setScene(scene);
//        window.setMaximized(true);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
