package frontend.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import frontend.Main;
import frontend.StageSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class NavPanelController {

    @FXML
    private Button myProfile;

    @FXML
    private Button home;

    @FXML
    private Button activity;

    @FXML
    private Button friends;



    public void initialize() {
        //TODO: Add scene change to profile page
        home.setOnAction(e -> StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getHomepage()));
        activity.setOnAction(e -> StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getActivities()));
        friends.setOnAction(e -> StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getFriendsPage()));
    }


    public static void setup(JFXDrawer drawer, JFXHamburger menu, AnchorPane main) throws IOException {
        VBox box = FXMLLoader.load(NavPanelController.class.getResource("/frontend/fxmlPages/navigationpane.fxml"));
        box.setMinHeight(drawer.getDefaultDrawerSize());


        drawer.setVisible(false);
        drawer.setSidePane(box);


        //Handle nav panel
        HamburgerBasicCloseTransition burgerTask1 = new HamburgerBasicCloseTransition(menu);
        burgerTask1.setRate(-1);
        menu.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            burgerTask1.setRate(burgerTask1.getRate() * -1);
            burgerTask1.play();
            if (drawer.isOpened()) {
                drawer.close();
            } else {
                drawer.open();
                drawer.setVisible(true);
            }
        });
    }






}
