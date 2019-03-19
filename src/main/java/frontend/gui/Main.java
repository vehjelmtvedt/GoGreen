package frontend.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage primaryStage;
    private static Scene signIn;
    private static Scene signUp;
    private static Scene homepage;
    private static Scene activities;
    private static Scene friendPage;
    private static Scene profilePage;
    private static Scene progress;
    private static String cssIntro;


    @Override
    public void start(Stage window) throws IOException {
        //setup the primary stage


        primaryStage = window;
        General.setPrimaryStage(primaryStage, "Go Green");

        //create scenes necessary for scene switching
        FXMLLoader loaderSignIn = new FXMLLoader(
                Main.class.getResource("/frontend/fxmlPages/Login.fxml"));
        Parent root1 = loaderSignIn.load();
        signIn = new Scene(root1, 1500, 850);

        FXMLLoader loaderSignUp = new FXMLLoader(
                Main.class.getResource("/frontend/fxmlPages/signup.fxml"));
        Parent root2 = loaderSignUp.load();
        signUp = new Scene(root2, 1500, 850);

        //add button switching due to java being a synchronous programming language
        StageSwitcher.buttonSwitch(SignIn.getSignUpButton(), primaryStage, signUp);

        //add path for css files and add them to the specific scenes they belong
        String cssPathIntro = "/frontend/Stylesheets/Style.css";
        cssIntro = this.getClass().getResource(cssPathIntro).toExternalForm();


        //setup the first scene for the primary stage
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

    public static Scene getProfilePage() {
        return profilePage;
    }

    public static String getCssIntro() {
        return cssIntro;
    }

    public static void setActivities(Scene scene) {
        activities = scene;
    }

    public static void setHomepage(Scene scene) {
        homepage = scene;
    }

    public static void setFriendPage(Scene scene) {
        friendPage = scene;
    }

    public static void setProfilePage(Scene scene) {
        profilePage = scene;
    }
}