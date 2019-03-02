package frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    private static String cssIntro;
    private static String cssHomepage;
    private static Scene signIn;
    private static Scene signUp;
    private static Stage primaryStage;

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
        signIn = new Scene(mainLayoutSignIn,
                SetupStructure.getBounds()[0], SetupStructure.getBounds()[1]);

        signUp = new Scene(mainLayoutSignUp,
                SetupStructure.getBounds()[0], SetupStructure.getBounds()[1]);

        String cssPathIntro = "/frontend/Style.css";
        String cssPathHomepage = "/frontend/Homepage.css";
        cssIntro = this.getClass().getResource(cssPathIntro).toExternalForm();
        cssHomepage = this.getClass().getResource(cssPathHomepage).toExternalForm();

        signIn.getStylesheets().add(cssIntro);
        signUp.getStylesheets().add(cssIntro);

        SetupStructure.addUiControls(signUpForm, 1, window, signUp, signIn);
        SetupStructure.addUiControls(signInForm, 2, window, signUp, signIn);

        SetupStructure.finaliseStage(window, signIn);
        primaryStage = window;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static String getCssIntro() {
        return cssIntro;
    }

    public static String getCssHomepage() {
        return cssHomepage;
    }

    public static Scene getSignIn() {
        return signIn;
    }

    public static Scene getSignUp() {
        return signUp;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
