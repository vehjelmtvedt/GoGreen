package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import frontend.gui.Events;
import frontend.gui.General;
import frontend.gui.InputValidation;
import frontend.gui.Main;
import frontend.gui.StageSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private AnchorPane graphics;

    @FXML
    private ImageView background;

    @FXML
    private Label signupForward;

    @FXML
    private Label goGreen;

    @FXML
    private Label line1;

    @FXML
    private Label line2;

    @FXML
    private Label line3;

    @FXML
    private Label login;

    @FXML
    private Label forgotPass;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginButton.setOnAction(e -> {
            try {
                InputValidation.signInValidate(usernameField,
                        passwordField, mainPane);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            usernameField.setText(null);
            passwordField.setText(null);
        });

        background.fitWidthProperty().bind(graphics.widthProperty());
        background.fitHeightProperty().bind(graphics.heightProperty());
        signupForward.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> StageSwitcher.sceneSwitch(
                Main.getPrimaryStage(), Main.getSignUp()));
        try {
            setFonts();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainPane.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                loginButton.fire();
            }
        });

        Events.addHoverOnFilter(forgotPass);
        forgotPass.setCursor(Cursor.HAND);
        forgotPass.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                forgotpass();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void forgotpass() throws IOException {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader forgotPassLoader = new FXMLLoader(
                Main.class.getResource("/frontend/fxmlPages/forgotpass.fxml"));
        Parent forgotpass = forgotPassLoader.load();
        Scene scene = new Scene(forgotpass,
                General.getBounds()[0] / 2, General.getBounds()[1] / 2);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Adds fonts to all labels.
     */
    public void setFonts() throws IOException {
        goGreen.setFont(Main.getReenieBeanie(100));
        line1.setFont(Main.getReenieBeanie(40));
        line2.setFont(Main.getReenieBeanie(40));
        line3.setFont(Main.getReenieBeanie(50));
        login.setFont(Main.getRobotoThin(45));
        signupForward.setFont(Main.getRobotoThin(45));
        loginButton.setFont(Main.getRobotoThin(28));

    }
}
