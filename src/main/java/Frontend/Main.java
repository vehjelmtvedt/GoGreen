package Frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Set;

public class Main extends Application {

    public Scene signIn, signUp;

    @Override
    public void start(Stage window) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        //setup stage
        SetupStructure.setPrimaryStage(window, "Go Green sign in");
        //create borderPane for quick form setup
        BorderPane mainLayout = new BorderPane();
        mainLayout.setId("mainLayout");

        //initialise forms
        GridPane signUpForm = SetupStructure.createRegistrationForm();
        GridPane signInForm = SetupStructure.createRegistrationForm();

//        signIn = new Scene(signInForm, SetupStructure.getBounds()[0], SetupStructure.getBounds()[1]);
        signUp = new Scene(signUpForm, SetupStructure.getBounds()[0], SetupStructure.getBounds()[1]);
        //add the ui controls to the form
        SetupStructure.addUIControls(signUpForm, 1, window, null);
        SetupStructure.addUIControls(signInForm, 2, window, signUp);

        //set top/right/bottom/center/left for main borderPane
        mainLayout.setCenter(signInForm);

        //set final primary stage aka window and add css
        signIn = new Scene(mainLayout, 600, 400);
        String css = this.getClass().getResource("/Frontend/Style.css").toExternalForm();
        signIn.getStylesheets().add(css);

        SetupStructure.finaliseStage(window, signIn);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
