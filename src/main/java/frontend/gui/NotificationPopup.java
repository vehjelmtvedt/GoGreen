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


    public void newNotification(AnchorPane mainPane, String headerText, String bodyText, String icon) throws IOException {
        if (d1Closed) {
            JFXDrawer drawer = addDrawer(new JFXDrawer(), 0, mainPane, headerText,
                    bodyText, icon);
            animateUsingTimeline(drawer, 0);
        } else if (d2Closed) {
            System.out.println("IM HERE");
            JFXDrawer drawer = addDrawer(new JFXDrawer(), 1, mainPane, headerText,
                    bodyText, icon);
            animateUsingTimeline(drawer, 1);
        } else if (d3Closed) {
            JFXDrawer drawer = addDrawer(new JFXDrawer(), 2, mainPane, headerText,
                    bodyText, icon);
            animateUsingTimeline(drawer, 2);
        } else if (d4Closed) {
            JFXDrawer drawer = addDrawer(new JFXDrawer(), 3, mainPane, headerText,
                    bodyText, icon);
            animateUsingTimeline(drawer, 3);
        } else {
            System.out.println("ALL DRAWERS CLOSED");
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
                    }
                })
        );
        beat.setCycleCount(1);
        beat.play();
    }

//    public void init() {
//        drawer1 = new JFXDrawer();
//        drawer2 = new JFXDrawer();
//        drawer3 = new JFXDrawer();
//        drawer4 = new JFXDrawer();
//    }

    public JFXDrawer addDrawer(JFXDrawer drawer, int drawerNumber, AnchorPane mainPane,
                             String headerText, String bodyText, String icon) throws IOException {
        AnchorPane content = new AnchorPane();
        Label heading = new Label(headerText);
        heading.setFont(Main.getRobotoBold(24));
        Label body = new Label(bodyText);
        heading.setFont(Main.getRobotoThin(19));
        ImageView image = new ImageView(new Image("frontend/Pics/" + icon + ".png"));
        image.setFitHeight(40);
        image.setFitWidth(40);
        content.getChildren().addAll(heading, body, image);

        AnchorPane.setTopAnchor(heading, 10.0);
        AnchorPane.setLeftAnchor(heading, 10.0);
        AnchorPane.setTopAnchor(image, 40.0);
        AnchorPane.setLeftAnchor(image, 10.0);
        AnchorPane.setTopAnchor(body, 50.0);
        AnchorPane.setLeftAnchor(body, 70.0);



        drawer.setSidePane(content);
        drawer.setDefaultDrawerSize(270);
        drawer.setMinHeight(130);
        drawer.setDirection(JFXDrawer.DrawerDirection.RIGHT);
        mainPane.getChildren().addAll(drawer);
        AnchorPane.setRightAnchor(drawer, 0.0);
        AnchorPane.setTopAnchor(drawer, 143 + (drawerNumber * (drawer.getMinHeight() + 10)));
        return drawer;
    }
}
