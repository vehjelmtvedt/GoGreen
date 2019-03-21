package frontend.controllers;

import data.Achievement;
import data.User;
import data.UserAchievement;
import frontend.gui.Main;
import frontend.gui.ProfilePageLogic;
import frontend.gui.StageSwitcher;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;


public class ProfilePageController implements Initializable {

    private static User thisUser;

    @FXML
    VBox completed;

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

        // add the completed achievements
        ArrayList<UserAchievement> userAchievements = thisUser.getProgress().getAchievements();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < userAchievements.size() ; i++) {

            stringBuilder.append(i + 1 + ") " + userAchievements.get(i).toString() + "\n") ;


        }
        completed.getChildren().add(new Text(stringBuilder.toString()));


        bonus.setCellValueFactory(new PropertyValueFactory<Achievement, Integer>("Bonus"));
        achievementNameTableColumn.setCellValueFactory(
                new PropertyValueFactory<Achievement, String>("Name"));


        ObservableList<Achievement> allachievements =
                FXCollections.<Achievement>observableArrayList();

        all.setItems(allachievements);

        backButton.setOnAction(e ->
                StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getHomepage()));
    }

    /**
     * Set user to loged in User.
     * @param user loged in user
     */
    public static void setUser(User user) {
        thisUser = user;

        //for testing

        thisUser.getProgress().getAchievements().add(new UserAchievement(
                1 , true , new Date(11 , 11, 11)));
        thisUser.getProgress().getAchievements().add(new UserAchievement(
                2 , true , new Date(12 , 11, 11)));

        thisUser.getProgress().setPoints(1000.0);
    }



}

