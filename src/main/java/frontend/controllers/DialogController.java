package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
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
    private AnchorPane contentPane;
    @FXML
    private JFXDialogLayout dialogLayout;
    @FXML
    private JFXButton closeButton;
    @FXML
    private Label heading;
    @FXML
    private Text body;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        contentPane.prefWidthProperty().bind(dialogLayout.prefWidthProperty());
        contentPane.prefHeightProperty().bind(dialogLayout.prefHeightProperty());
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

    public void addDialog(AnchorPane mainPane) throws IOException {

        JFXDialogLayout layout = getDialogLayout();


        StackPane stackPane = new StackPane();
        stackPane.autosize();
        JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.LEFT, true);
        dialog.setDialogContainer(stackPane);
        AnchorPane.setRightAnchor(stackPane, mainPane.getHeight());
        AnchorPane.setTopAnchor(stackPane, (mainPane.getHeight()));
        AnchorPane.setBottomAnchor(stackPane, (mainPane.getHeight()));
        AnchorPane.setLeftAnchor(stackPane, (mainPane.getWidth()));
        mainPane.getChildren().addAll(stackPane);
        dialog.show();
    }

    private JFXDialogLayout getDialogLayout() {
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setPrefWidth(600);
        layout.setPrefHeight(300);
        AnchorPane pane = new AnchorPane();
        pane.prefHeightProperty().bind(layout.prefHeightProperty());
        pane.prefWidthProperty().bind(layout.prefWidthProperty());
        Label heading = new Label("Heading");
        heading.setFont(Font.font("System", 32));
        AnchorPane.setTopAnchor(heading, 10.0);
        AnchorPane.setLeftAnchor(heading, 10.0);
        Text body = new Text("This is the body of the dialog.");
        AnchorPane.setTopAnchor(body, 90.0);
        AnchorPane.setLeftAnchor(body, 10.0);
        JFXButton button = new JFXButton("ACCEPT");
        button.setTextFill(Paint.valueOf("#0033ff"));
        AnchorPane.setBottomAnchor(button, 20.0);
        AnchorPane.setRightAnchor(button, 30.0);

        pane.getChildren().addAll(heading, body, button);
        layout.getChildren().addAll(pane);
        return layout;
    }
}
