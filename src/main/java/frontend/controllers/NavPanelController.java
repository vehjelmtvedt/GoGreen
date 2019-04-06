package frontend.controllers;

import frontend.gui.Events;
import frontend.gui.Main;
import frontend.gui.StageSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

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
        friends.setOnAction(e -> StageSwitcher.sceneSwitch(
                Main.getPrimaryStage(), Main.getFriendsPage()));
        myProfile.setOnAction(e -> StageSwitcher.sceneSwitch(
                Main.getPrimaryStage() , Main.getProfilePage())
        );
    }
}
