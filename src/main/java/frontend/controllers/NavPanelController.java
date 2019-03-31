package frontend.controllers;

import frontend.gui.Events;
import frontend.gui.General;
import frontend.gui.Main;
import frontend.gui.StageSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class NavPanelController implements Initializable {

    @FXML
    private Button myProfile;

    @FXML
    private Button home;

    @FXML
    private Button activity;

    @FXML
    private Button friends;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //add button hover event
        Events.addNavButtonHover(home);
        Events.addNavButtonHover(activity);
        Events.addNavButtonHover(friends);
        Events.addNavButtonHover(myProfile);
        home.setOnAction(e -> StageSwitcher.sceneSwitch(
                Main.getPrimaryStage(), Main.getHomepage()));
        activity.setOnAction(e -> StageSwitcher.sceneSwitch(
                Main.getPrimaryStage(), Main.getActivities()));
        friends.setOnAction(e -> {
            try {
                FXMLLoader loader3 = new FXMLLoader(
                        Main.class.getResource("/frontend/fxmlPages/FriendPage.fxml"));
                Parent root3 = loader3.load();
                Scene friendPage = new Scene(root3, General.getBounds()[0], General.getBounds()[1]);
                Main.setFriendPage(friendPage);
            } catch (IOException e1) {
                e1.printStackTrace();
            }


            StageSwitcher.sceneSwitch(
                    Main.getPrimaryStage(), Main.getFriendsPage());
        });
        myProfile.setOnAction(e -> StageSwitcher.sceneSwitch(
                Main.getPrimaryStage() , Main.getProfilePage()));
    }
}
