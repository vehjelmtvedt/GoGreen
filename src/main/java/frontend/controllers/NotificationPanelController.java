package frontend.controllers;

import frontend.gui.Events;
import frontend.gui.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NotificationPanelController implements Initializable {


    @FXML
    private AnchorPane notificationPane;

    @FXML
    private Label markAllRead;

    private static boolean notifySelected = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        markAllRead.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            markAllRead.setUnderline(true);
            markAllRead.setCursor(Cursor.HAND);
        });
        markAllRead.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            markAllRead.setUnderline(false);
            markAllRead.setCursor(Cursor.DEFAULT);
        });

        notificationPane.setVisible(false);
    }

    public static void setup(ImageView notificationIcon, ImageView logoutIcon, AnchorPane parentPane) throws IOException {
        AnchorPane notificationPane = FXMLLoader.load(NavPanelController.class.getResource(
                "/frontend/fxmlPages/NotificationPanel.fxml"));
        parentPane.getChildren().addAll(notificationPane);


        Events.addImageCursor(notificationIcon);
        Events.addImageCursor(logoutIcon);
        notificationIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (!notifySelected) {
                notificationIcon.setImage(new Image("frontend/Pics/notificationIconOpen.png"));
                notificationPane.setVisible(true);
            } else {
                notificationIcon.setImage(new Image("frontend/Pics/notificationIconClose.png"));
                notificationPane.setVisible(false);
            }
            notifySelected = !notifySelected;
        });
        Events.addImageSceneSwitch(logoutIcon, Main.getSignIn());
        AnchorPane.setTopAnchor(notificationPane, 137.0);
        AnchorPane.setRightAnchor(notificationPane, 0.0);
    }
}
