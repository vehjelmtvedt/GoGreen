package Frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

//    private Button switchButton;

    @Override
    public void start(Stage window) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        //setup main window
        window.setTitle("GoGreen sign in");
        window.getIcons().add(new Image("file:C:\\Users\\Alexandru\\Documents\\template\\src\\main\\Resources\\Frontend\\Pics\\GoGreenIcon.png"));
        //close button handling
        window.setOnCloseRequest(e -> {
            e.consume();
            ConfirmBox.closeProgram(window);
        });
        //create borderPane for quick form setup
        BorderPane mainLayout = new BorderPane();
        mainLayout.setId("mainLayout");
        //initialise forms
        GridPane signupForm = SetupStructure.createRegistrationForm();
        GridPane loginForm = SetupStructure.createRegistrationForm();


        //add the ui controls to the form
        SetupStructure.addUIControls(signupForm, 1, window, null);
        SetupStructure.addUIControls(loginForm, 2, window, new Scene(signupForm));

        //set top/right/bottom/center/left for main borderPane
        mainLayout.setCenter(loginForm);

        //set final primary stage aka window



//        mainLayout.setBottom(switchButton);
//        Scene sceneRegister = new Scene(signupForm);
//        switchButton.setOnAction(e ->{
//            window.setScene(sceneRegister);
//        });


        Scene scene = new Scene(mainLayout, 600, 400);
        String css = this.getClass().getResource("/Frontend/Style.css").toExternalForm();
        scene.getStylesheets().add(css);

        window.setScene(scene);
        window.setMaximized(true);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
