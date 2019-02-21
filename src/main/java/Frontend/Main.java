package Frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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
        mainLayout.setRight(signupForm);
        mainLayout.setLeft(loginForm);

        //set final primary stage aka window
        Scene scene = new Scene(mainLayout, 600, 400);
//        scene.getStylesheets().add("C:\\Users\\Alexandru\\Documents\\template\\src\\java\\Frontend\\Style.css");
        String css = this.getClass().getResource("C:\\Users\\Alexandru\\Documents\\template\\main\\src\\java\\Frontend\\Style.css").toExternalForm();
        scene.getStylesheets().add(css);
        window.setScene(scene);
        window.setMaximized(true);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
