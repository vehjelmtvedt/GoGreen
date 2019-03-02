package frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage primaryStage;
    private static Scene signIn;
    private static Scene signUp;
    private static Scene homepage;
    private static String cssIntro;

    @Override
    public void start(Stage window) {
        primaryStage = window;
        General.setPrimaryStage(primaryStage, "Go Green");

        signIn = SignIn.createScene();
        signUp = SignUp.createScene();
        homepage = Homepage.createScene();

        StageSwitcher.buttonSwitch(SignIn.getSignUpButton(), primaryStage, signUp);

        String cssPathIntro = "/frontend/Style.css";
        cssIntro = this.getClass().getResource(cssPathIntro).toExternalForm();
        signIn.getStylesheets().add(cssIntro);
        signUp.getStylesheets().add(cssIntro);

        General.finaliseStage(primaryStage, signIn);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static Scene getSignIn() {
        return signIn;
    }

    public static Scene getSignUp() {
        return signUp;
    }

    public static Scene getHomepage() {
        return homepage;
    }

    public static String getCssIntro() {
        return cssIntro;
    }
}
