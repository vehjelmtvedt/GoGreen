package Frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Set;

public class Main extends Application {

    @Override
    public void start(Stage window) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        //setup stage
        SetupStructure.setPrimaryStage(window, "Go Green sign in");

        //create borderPane for quick form setup
        BorderPane mainLayout = new BorderPane();
        mainLayout.setId("mainLayout");

        //initialise forms
        GridPane signupForm = SetupStructure.createRegistrationForm();
        GridPane loginForm = SetupStructure.createRegistrationForm();


        //add the ui controls to the form
        SetupStructure.addUIControls(signupForm, 1, window, null);
        SetupStructure.addUIControls(loginForm, 2, window, new Scene(signupForm, SetupStructure.getBounds()[0], SetupStructure.getBounds()[1]));

        //set top/right/bottom/center/left for main borderPane
        mainLayout.setCenter(loginForm);

        //set final primary stage aka window and add css
        Scene scene = new Scene(mainLayout, 600, 400);
        String css = this.getClass().getResource("/Frontend/Style.css").toExternalForm();
        scene.getStylesheets().add(css);

        SetupStructure.finaliseStage(window, scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
