package frontend.controllers;

import backend.data.Achievement;
import backend.data.User;
import frontend.Main;
import frontend.ProfilePageLogic;
import frontend.StageSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;


public class ProfilePageController implements Initializable {

    private static User thisUser;

    @FXML
    Label completed;

    @FXML
    private TableView<Achievement> all;

    @FXML
    private TableColumn<Achievement, String> achievementNameTableColumn;

    @FXML
    private TableColumn<Achievement, Integer> bonus;

    @FXML
    private Label level;

    @FXML
    private ImageView badge;

    @FXML
    private Button backButton;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label ageLabel;

    @FXML
    private Label lastseenLabel;

    /**
     * how should the page be set up.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userNameLabel.setText(thisUser.getUsername());

        nameLabel.setText(thisUser.getFirstName() + thisUser.getLastName());

        emailLabel.setText(thisUser.getEmail());

        ageLabel.setText(thisUser.getAge() + "");

        lastseenLabel.setText(thisUser.getLastLoginDate().toString());

        level.setText("Level: " + ProfilePageLogic.getLevel(thisUser));

        Image badgeimg = new Image(ProfilePageLogic.getBadge(thisUser));

        badge.setImage(badgeimg);

        //completed.setText(ProfilePageLogic.getAchievementsString(user));
        completed.setText(thisUser.getProgress().getAchievements().toString());


        bonus.setCellValueFactory(new PropertyValueFactory<Achievement, Integer>("Bonus"));
        achievementNameTableColumn.setCellValueFactory(
                new PropertyValueFactory<Achievement, String>("Name"));


        ObservableList<Achievement> allachievements =
                FXCollections.<Achievement>observableArrayList();

        all.setItems(allachievements);

        backButton.setOnAction(e ->
                StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getHomepage()));
    }

    public static void setUser(User user) {
        thisUser = user;
    }



}

