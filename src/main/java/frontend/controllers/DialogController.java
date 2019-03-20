package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DialogController implements Initializable {
    @FXML
    private JFXDialog dialog;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private JFXDialogLayout dialogLayout;
    @FXML
    private JFXButton closeButton;
    @FXML
    private Label heading;
    @FXML
    private Text body;
    @FXML
    private ImageView icon;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        contentPane.prefWidthProperty().bind(dialogLayout.prefWidthProperty());
        contentPane.prefHeightProperty().bind(dialogLayout.prefHeightProperty());
        closeButton.setOnAction(e -> dialog.close());
    }

    public void setHeading(String text) {
        heading.setText(text);
    }

    public void setBody(String text) {
        body.setText(text);
    }

    public void setButtonText(String text) {
        closeButton.setText(text);
    }

    public void setDialogParent(StackPane parent) {
        dialog.setDialogContainer(parent);
    }

    public JFXDialog getDialog() {
        return this.dialog;
    }

    public void setIcon(String src) {
        icon.setImage(new Image("frontend/Pics/" + src + ".png"));

    }
}
