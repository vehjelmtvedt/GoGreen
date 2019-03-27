package frontend.gui;

import com.jfoenix.controls.JFXDrawer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


public class NotificationPopup {

    private JFXDrawer drawer1;
    boolean d1Closed = true;

    private JFXDrawer drawer2;
    boolean d2Closed = true;

    private JFXDrawer drawer3;
    boolean d3Closed = true;

    private JFXDrawer drawer4;
    boolean d4Closed = true;


    public void newNotification() {
        if (d1Closed) {
            animateUsingTimeline(drawer1, 1);
        } else if (d2Closed) {
            animateUsingTimeline(drawer2, 2);
        } else if (d3Closed) {
            animateUsingTimeline(drawer3, 3);
        } else if (d4Closed) {
            animateUsingTimeline(drawer4, 4);
        }
    }

    private void animateUsingTimeline(JFXDrawer drawer, int drawerNumber) {
        Timeline beat = new Timeline(
                new KeyFrame(Duration.ZERO, event -> {
                    drawer.open();
                    switch (drawerNumber) {
                        case 1: d1Closed = false;
                                break;
                        case 2: d2Closed = false;
                                break;
                        case 3: d3Closed = false;
                                break;
                        case 4: d4Closed = false;
                                break;
                    }
                }),
                new KeyFrame(Duration.seconds(3.0), event -> {
                    drawer.close();
                    switch (drawerNumber) {
                        case 1: d1Closed = true;
                            break;
                        case 2: d2Closed = true;
                            break;
                        case 3: d3Closed = true;
                            break;
                        case 4: d4Closed = true;
                            break;
                    }
                })
        );
        beat.setCycleCount(1);
        beat.play();
    }

    public void addAllDrawers(AnchorPane mainPane) {
        drawer1 = new JFXDrawer();
        drawer2 = new JFXDrawer();
        drawer3 = new JFXDrawer();
        drawer4 = new JFXDrawer();
        setUpDrawers(drawer1, 0,  mainPane);
        setUpDrawers(drawer2, 1, mainPane);
        setUpDrawers(drawer3, 2, mainPane);
        setUpDrawers(drawer4, 3, mainPane);
    }

    public void setUpDrawers(JFXDrawer drawer, int drawerNumber, AnchorPane mainPane) {
        StackPane content = new StackPane();
        Label heading = new Label("Heading");
        Label body = new Label("text of the body");
        ImageView image = new ImageView(new Image("frontend/Pics/sucess.png"));
        image.setFitHeight(40);
        image.setFitWidth(40);

        content.getChildren().addAll(heading, body, image);
        drawer.setSidePane(content);
        drawer.setDefaultDrawerSize(270);
        drawer.setMinHeight(130);
        drawer.setDirection(JFXDrawer.DrawerDirection.RIGHT);
        mainPane.getChildren().addAll(drawer);
        AnchorPane.setRightAnchor(drawer, 0.0);
        AnchorPane.setTopAnchor(drawer, 143 + (drawerNumber * (drawer.getMinHeight() + 10)));
    }
}
