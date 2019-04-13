package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import frontend.gui.Events;
import frontend.gui.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DialogController implements Initializable {
    @FXML
    private Label heading;
    @FXML
    private Text body;
    @FXML
    private JFXButton button;
    @FXML
    private ImageView icon;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //add required events
        Events.addJfxButtonHover(button);

        button.setOnAction(e -> {
            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
        });

        try {
            heading.setFont(Main.getRobotoBold(36.0));
            body.setFont(Main.getRobotoThin(26.0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setHeading(String text) {
        heading.setText(text);
    }

    public void setBody(String text) {
        body.setText(text);
    }

    public void setButtonText(String text) {
        button.setText(text);
    }

    public void setIcon(String src) {
        icon.setImage(new Image("frontend/Pics/" + src + ".png"));
    }
}
