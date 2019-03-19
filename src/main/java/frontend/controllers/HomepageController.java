package frontend.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import frontend.gui.Events;
import frontend.gui.Main;
import javafx.fxml.FXML;
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

public class HomepageController implements Initializable {
    @FXML
    private JFXHamburger menu;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private ImageView logoutIcon;
    @FXML
    private ImageView notificationIcon;
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
        try {
            NavPanelController.setup(drawer, menu);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
    }
}
