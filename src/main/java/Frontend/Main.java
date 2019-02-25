package Frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    private static String css;

    @Override
    public void start(Stage window) {
        //setup stage
        SetupStructure.setPrimaryStage(window, "Go Green sign in");

        //create borderPane for quick form setup
        BorderPane mainLayoutSignIn = new BorderPane();
        BorderPane mainLayoutSignUp = new BorderPane();
        mainLayoutSignIn.setId("mainLayoutSignIn");
        mainLayoutSignUp.setId("mainLayoutSignUp");

        //initialise forms
        GridPane signUpForm = SetupStructure.createRegistrationForm();
        GridPane signInForm = SetupStructure.createRegistrationForm();

        //set top/right/bottom/center/left for main borderPane
        mainLayoutSignIn.setCenter(signInForm);
        mainLayoutSignUp.setCenter(signUpForm);

        //set final primary stage aka window and add css
        Scene signIn = new Scene(mainLayoutSignIn,
                SetupStructure.getBounds()[0], SetupStructure.getBounds()[1]);

        Scene signUp = new Scene(mainLayoutSignUp,
                SetupStructure.getBounds()[0], SetupStructure.getBounds()[1]);

        String cssPath = "/Frontend/Style.css";
        css = this.getClass().getResource(cssPath).toExternalForm();
        signIn.getStylesheets().add(css);
        signUp.getStylesheets().add(css);

        SetupStructure.addUiControls(signUpForm, 1, window, signUp, signIn);
        SetupStructure.addUiControls(signInForm, 2, window, signUp, signIn);

        SetupStructure.finaliseStage(window, signIn);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static String getCss() {
        return css;
    }
}
