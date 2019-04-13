package frontend.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class Main extends Application {
    private static Stage primaryStage;
    private static Scene signIn;
    private static Scene signUp;
    private static Scene Questionnaire;
    private static Scene homepage;
    private static Scene activities;
    private static Scene friendPage;
    private static Scene profilePage;
    private static String cssIntro;

    @Override
    public void start(Stage window) throws IOException {
        //setup the primary stage
        primaryStage = window;
        General.setPrimaryStage(primaryStage, "Go Green");

        //Load fxml pages for signIn and signUp
        FXMLLoader loaderSignIn = new FXMLLoader(
                Main.class.getResource("/frontend/fxmlPages/Login.fxml"));
        Parent rootSignIn = loaderSignIn.load();
        signIn = new Scene(rootSignIn, General.getBounds()[0], General.getBounds()[1]);

        FXMLLoader loaderSignUp = new FXMLLoader(
                Main.class.getResource("/frontend/fxmlPages/signup.fxml"));
        Parent rootSignUp = loaderSignUp.load();
        signUp = new Scene(rootSignUp, General.getBounds()[0], General.getBounds()[1]);

        FXMLLoader loaderQuestionnaire = new FXMLLoader(
                Main.class.getResource("/frontend/fxmlPages/Questionnaire.fxml"));
        Parent rootQuestionnaire = loaderQuestionnaire.load();
        Questionnaire = new Scene(
                rootQuestionnaire, General.getBounds()[0], General.getBounds()[1]
        );

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

    public static Scene getQuestionnaire() {
        return Questionnaire;
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

    public static Font getRobotoThin(double size) throws IOException {
        return Font.loadFont(new ClassPathResource("/fonts/Roboto-thin.ttf")
                .getInputStream(), size);
    }

    public static Font getRobotoBold(double size) throws IOException {
        return Font.loadFont(new ClassPathResource("/fonts/Roboto-Bold.ttf")
                .getInputStream(), size);
    }

    public static Font getReenieBeanie(double size) throws IOException {
        return Font.loadFont(new ClassPathResource("/fonts/ReenieBeanie.ttf")
                .getInputStream(), size);
    }
}
