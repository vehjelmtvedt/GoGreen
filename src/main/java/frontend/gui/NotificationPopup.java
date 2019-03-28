package frontend.gui;

import com.jfoenix.controls.JFXDrawer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;


public class NotificationPopup {

    boolean d1Closed = true;

    boolean d2Closed = true;

    boolean d3Closed = true;

    boolean d4Closed = true;


    /**
     * Opens a notification to the side of your page.
     * @param mainPane - pane surrounding your entire content (root)
     * @param headerPane - the goGreen header (used to position the popup)
     * @param headerText - text of the heading on the popup
     * @param bodyText - text of the body on the popup
     * @param icon - can use any icon in frontend/pics here (if it is png)
     * @throws IOException - if it fails to load fonts
     */
    public void newNotification(AnchorPane mainPane, AnchorPane headerPane,
                                String headerText, String bodyText,
                                String icon) throws IOException {
        if (d1Closed) {
            JFXDrawer drawer = addDrawer(new JFXDrawer(), 0, mainPane, headerPane, headerText,
                    bodyText, icon);
            animateUsingTimeline(drawer, 0);
        } else if (d2Closed) {
            JFXDrawer drawer = addDrawer(new JFXDrawer(), 1, mainPane, headerPane, headerText,
                    bodyText, icon);
            animateUsingTimeline(drawer, 1);
        } else if (d3Closed) {
            JFXDrawer drawer = addDrawer(new JFXDrawer(), 2, mainPane, headerPane, headerText,
                    bodyText, icon);
            animateUsingTimeline(drawer, 2);
        } else if (d4Closed) {
            JFXDrawer drawer = addDrawer(new JFXDrawer(), 3, mainPane, headerPane, headerText,
                    bodyText, icon);
            animateUsingTimeline(drawer, 3);
        }
    }

    private void animateUsingTimeline(JFXDrawer drawer, int drawerNumber) {
        Timeline beat = new Timeline(
                new KeyFrame(Duration.ZERO, event -> {
                    drawer.open();
                    switch (drawerNumber) {
                        case 0: d1Closed = false;
                                break;
                        case 1: d2Closed = false;
                                break;
                        case 2: d3Closed = false;
                                break;
                        case 3: d4Closed = false;
                                break;
                        default: break;
                    }
                }),
                new KeyFrame(Duration.seconds(5.0), event -> {
                    drawer.close();
                    switch (drawerNumber) {
                        case 0: d1Closed = true;
                            break;
                        case 1: d2Closed = true;
                            break;
                        case 2: d3Closed = true;
                            break;
                        case 3: d4Closed = true;
                            break;
                        default: break;
                    }
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
     * @param headerText - text of the header
     * @param bodyText - text of the body
     * @param icon - which icon to use
     * @return - the drawer (popup)
     * @throws IOException - if fails to load the font
     */
    public JFXDrawer addDrawer(JFXDrawer drawer, int drawerNumber, AnchorPane mainPane,
                             AnchorPane headerPane, String headerText,
                               String bodyText, String icon) throws IOException {
        Label heading = new Label(headerText);
        heading.setFont(Main.getRobotoBold(24));
        Label body = new Label(bodyText);
        body.setFont(Main.getRobotoThin(19));
        ImageView image = new ImageView(new Image("frontend/Pics/" + icon + ".png"));
        image.setFitHeight(40);
        image.setFitWidth(40);
        AnchorPane content = new AnchorPane();
        content.getChildren().addAll(heading, body, image);

        AnchorPane.setTopAnchor(heading, 10.0);
        AnchorPane.setLeftAnchor(heading, 10.0);
        AnchorPane.setTopAnchor(image, 55.0);
        AnchorPane.setLeftAnchor(image, 10.0);
        AnchorPane.setTopAnchor(body, 65.0);
        AnchorPane.setLeftAnchor(body, 70.0);



        drawer.setSidePane(content);
        drawer.setDefaultDrawerSize(270);
        drawer.setMinHeight(130);
        drawer.setDirection(JFXDrawer.DrawerDirection.RIGHT);
        mainPane.getChildren().addAll(drawer);
        AnchorPane.setRightAnchor(drawer, 0.0);
        AnchorPane.setTopAnchor(drawer, headerPane.getHeight()
                + (drawerNumber * (drawer.getMinHeight() + 10)));
        return drawer;
    }
}
