package frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage primaryStage;
    private static Scene signIn;
    private static Scene signUp;
    private static Scene homepage;
    private static Scene activities;
    private static Scene friendPage;
    private static Scene progress;
    private static String cssIntro;


    @Override
    public void start(Stage window) {
        //setup the primary stage

        primaryStage = window;
        General.setPrimaryStage(primaryStage, "Go Green");

        //create scenes necessary for scene switching
        signIn = SignIn.createScene();
        signUp = SignUp.createScene();
        progress = ProgressPage.createScene();

        //add button switching due to java being a synchronous programming language
        StageSwitcher.buttonSwitch(SignIn.getSignUpButton(), primaryStage, signUp);

        //add path for css files and add them to the specific scenes they belong
        String cssPathIntro = "/frontend/Stylesheets/Style.css";
        cssIntro = this.getClass().getResource(cssPathIntro).toExternalForm();
        signIn.getStylesheets().add(cssIntro);
        signUp.getStylesheets().add(cssIntro);

        //setup the first scene for the primary stage
        General.finaliseStage(primaryStage, friendPage); //TODO: CHANGE TO SIGNIN PAGE WHEN FINISHED WITH DESIGN
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

    public static Scene getProgress() {
        return progress;
    }

    public static Scene getActivities() {
        return activities;
    }

    public static Scene getHomepage() {
        return homepage;
    }

    public static Scene getFriendsPage() {
        return friendPage;
    }

    public static String getCssIntro() {
        return cssIntro;
    }

    //testing
    public static void setActivities(Scene scene) {
        activities = scene;
    }

    public static void setHomepage(Scene scene) {
        homepage = scene;
    }

    public static void setFriendPage(Scene scene) {
        friendPage = scene;
    }
}
