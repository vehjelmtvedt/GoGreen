package frontend.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class QuestionaireController implements Initializable {

    @FXML
    private JFXTextField textHousehold;

    @FXML
    private JFXTextField textElectricity;

    @FXML
    private JFXTextField textOil;

    @FXML
    private JFXTextField textCarUsage;

    @FXML
    private AnchorPane graphics;

    @FXML
    private ImageView background;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        background.fitWidthProperty().bind(graphics.widthProperty());
        background.fitHeightProperty().bind(graphics.heightProperty());
        
    }

    @Override
    public void NewTextFeildValue(JFXTextField jfxTextField, String oldValue, String newValue) {
        if (!newValue.matches("^[0-9]{0,7}$")) {
            jfxTextField.setText(oldValue);
        }
    }


}
