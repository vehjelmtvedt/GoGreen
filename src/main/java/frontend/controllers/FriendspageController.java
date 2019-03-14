package frontend.controllers;

import backend.data.User;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;


public class FriendspageController {

    private static User user;

    @FXML
    private AnchorPane main;

    @FXML
    private Button getFriends;

    @FXML
    private ScrollPane scrollPaneFriends;

    @FXML
    private JFXHamburger menu;

    @FXML
    private JFXDrawer drawer;

    public void initialize() throws IOException {
        initNavBar();
        getFriends.setOnAction(e -> fillFriendsPane());
    }

    public void fillFriendsPane() {
        VBox root = new VBox();

        for (String username : user.getFriends()) {
            Pane friendPane = new Pane();
            friendPane.setMinHeight(50);
            friendPane.prefWidthProperty().bind(root.widthProperty());
            friendPane.setBackground(new Background(new BackgroundFill(Color.web("#4245f4"), CornerRadii.EMPTY, Insets.EMPTY)));
            Label friendLabel = new Label(username);
            friendPane.getChildren().add(friendLabel);
            root.getChildren().add(friendPane);
        }
        scrollPaneFriends.setContent(root);
        scrollPaneFriends.setPannable(true);
    }



    public static void setUser(User passedUser) {
        user = passedUser;
    }

    public void initNavBar() throws IOException {
        VBox box = FXMLLoader.load(getClass().getResource("/frontend/fxmlPages/navigationpane.fxml"));

        drawer.setVisible(false);
        drawer.setSidePane(box);


        //Handle nav panel
        HamburgerBasicCloseTransition burgerTask1 = new HamburgerBasicCloseTransition(menu);
        burgerTask1.setRate(-1);
        menu.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            burgerTask1.setRate(burgerTask1.getRate() * -1);
            burgerTask1.play();
            //drawer.setVisible(true);
            if (drawer.isOpened()) {
                drawer.close();
                drawer.setVisible(false);
            } else {
                drawer.open();
                drawer.setVisible(true);
            }
        });

        main.addEventHandler(MouseEvent.MOUSE_PRESSED, ev -> {
            if (drawer.isOpened()) {
                drawer.close();
                burgerTask1.play();
                
                drawer.setVisible(false);
            }
        });
    }


}
