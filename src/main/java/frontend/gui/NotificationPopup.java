package frontend.gui;

import com.jfoenix.controls.JFXDrawer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;


public class NotificationPopup {

    boolean[] closed = {true, true, true, true};

    /**
     * Opens a notification to the side of your page.
     * @param mainPane - pane surrounding your entire content (root)
     * @param headerPane - the goGreen header (used to position the popup)
     * @param popupText - 1: header text, 2: body text, 3: icon
     * @throws IOException - if it fails to load fonts
     */
    public void newNotification(AnchorPane mainPane, AnchorPane headerPane,
                                String[] popupText, int drawerNumber) throws IOException {
        JFXDrawer drawer = addDrawer(new JFXDrawer(),
                drawerNumber, mainPane, headerPane, popupText);
        animateUsingTimeline(drawer, drawerNumber);
    }

    private void animateUsingTimeline(JFXDrawer drawer, int drawerNumber) {
        Timeline beat = new Timeline(
                new KeyFrame(Duration.ZERO, event -> {
                    drawer.open();
                    closed[drawerNumber] = false;
                }),
                new KeyFrame(Duration.seconds(5.0), event -> {
                    drawer.close();
                    closed[drawerNumber] = true;
                })
        );
        beat.setCycleCount(1);
        beat.play();
    }

    /**
     * Adds a drawer to contain the popup.
     * @param drawer - the drawer to use
     * @param drawerNumber - to adjust the positioning
     * @param mainPane - root of your page
     * @param headerPane - goGreen header
     * @param popupText - 1: header text, 2: body text, 3: icon
     * @return - the drawer (popup)
     * @throws IOException - if fails to load the font
     */
    public JFXDrawer addDrawer(JFXDrawer drawer, int drawerNumber, AnchorPane mainPane,
                             AnchorPane headerPane, String[] popupText) throws IOException {
        Label heading = new Label(popupText[0]);
        heading.setFont(Main.getRobotoBold(24));
        Text body = new Text(popupText[1]);
        body.setFont(Main.getRobotoThin(19));

        ImageView image = new ImageView(new Image("frontend/Pics/" + popupText[2] + ".png"));
        image.setFitHeight(40);
        image.setFitWidth(40);
        AnchorPane content = new AnchorPane();
        content.getChildren().addAll(heading, body, image);
        content.setStyle("-fx-background-color: #c6c6c6;");

        AnchorPane.setTopAnchor(heading, 10.0);
        AnchorPane.setLeftAnchor(heading, 10.0);
        AnchorPane.setTopAnchor(image, 55.0);
        AnchorPane.setLeftAnchor(image, 10.0);
        AnchorPane.setTopAnchor(body, 65.0);
        AnchorPane.setLeftAnchor(body, 70.0);


        drawer.setSidePane(content);
        drawer.setDefaultDrawerSize(320);
        drawer.setMinHeight(130);
        drawer.setDirection(JFXDrawer.DrawerDirection.RIGHT);
        mainPane.getChildren().addAll(drawer);
        AnchorPane.setRightAnchor(drawer, 0.0);
        AnchorPane.setTopAnchor(drawer, headerPane.getHeight()
                + (drawerNumber * (drawer.getMinHeight() + 10)));
        return drawer;
    }
}
