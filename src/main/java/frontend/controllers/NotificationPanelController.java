package frontend.controllers;

import data.LoginDetails;
import data.User;
import frontend.gui.Events;
import frontend.gui.FriendRequest;
import frontend.gui.Main;
import frontend.threading.NotificationThread;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tools.Requests;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class    NotificationPanelController implements Initializable {
    private static User loggedUser;
    private static LoginDetails loginDetails;
    private static boolean notifySelected = false;

    @FXML
    private AnchorPane notificationPane;
    @FXML
    private Label markAllRead;
    @FXML
    private VBox friendsContainer;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NotificationThread.notificationPanelController = this;


        fillFriendRequests();

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

    /**
     * Fill the friend requests in the notification panel.
     */
    public void fillFriendRequests() {
        User currUser = Requests.instance.loginRequest(loginDetails);

        System.out.println("NEW SIZE OF FRIEND REQUESTS:" + currUser.getFriendRequests().size());

        friendsContainer.getChildren().clear();

        for (String fromUser : currUser.getFriendRequests()) {
            addFriendRequest(friendsContainer, fromUser);
        }
        setUser(currUser);
    }


    private void addFriendRequest(VBox container, String fromUser) {
        FriendRequest friendRequest = new FriendRequest();
        try {
            friendRequest.newFriendRequest(container, fromUser);
        } catch (IOException exp) {
            System.out.println("Something went wrong");
        }
    }




    private static void setup(ImageView notificationIcon,
                              ImageView logoutIcon, AnchorPane parentPane) throws IOException {
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
        Events.addLogout(logoutIcon, Main.getSignIn());
        AnchorPane.setTopAnchor(notificationPane, 137.0);
        AnchorPane.setRightAnchor(notificationPane, 0.0);
    }

    /**
     * Adds notification panel to the page.
     * @param headerPane - header bar on top
     * @param mainPane - main pane (window)
     * @throws IOException - if fails to load the panel
     */
    public static void addNotificationPanel(AnchorPane headerPane,
                                            AnchorPane mainPane) throws IOException {
        HBox iconBox = new HBox();
        iconBox.setLayoutX(1280);
        iconBox.setLayoutY(56);
        iconBox.setPrefHeight(47);
        iconBox.setPrefWidth(102);
        AnchorPane.setRightAnchor(iconBox, 14.0);

        ImageView notificationIcon = new ImageView();
        notificationIcon.setImage(new Image("frontend/Pics/notificationIconClose.png"));
        notificationIcon.setFitWidth(43);
        notificationIcon.setFitHeight(44);
        notificationIcon.setPreserveRatio(true);
        notificationIcon.setPickOnBounds(true);

        ImageView logoutIcon = new ImageView();
        logoutIcon.setImage(new Image("frontend/Pics/logoutIcon.png"));
        logoutIcon.setFitWidth(43);
        logoutIcon.setFitHeight(44);
        logoutIcon.setPreserveRatio(true);
        logoutIcon.setPickOnBounds(true);

        HBox.setMargin(notificationIcon, new Insets(0, 20, 0, 0));
        iconBox.getChildren().addAll(notificationIcon, logoutIcon);
        headerPane.getChildren().add(iconBox);
        setup(notificationIcon, logoutIcon, mainPane);
    }

    /**
     * .
     * Sets the current logged in User to the one that was passed
     *
     * @param passedUser Logged in current user
     */
    public static void setUser(User passedUser) {
        loggedUser = passedUser;

    }

    /**
     * Sets the login details and starts the notification thread.
     * @param passedLoginDetails - login details from sign in form
     */
    public static void setLoginDetails(LoginDetails passedLoginDetails) {
        loginDetails = passedLoginDetails;
    }

    //public static fillNotifications()
}
