package frontend.controllers;

import data.Achievement;
import data.User;
import data.UserAchievement;
import frontend.gui.Main;
import frontend.gui.ProfilePageLogic;
import frontend.gui.StageSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;


public class ProfilePageController implements Initializable {

    private static User thisUser;


    @FXML
    AnchorPane anchorPanecom;

    @FXML
    AnchorPane anchorPaneIn;

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

    @FXML
    private Label score;

    @FXML
    private VBox com;

    @FXML
    private VBox incom;

    @FXML
    private ToolBar toolBar;


    /**
     * how should the page be set up.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        score.setText("TotalCarbonSaved: " + thisUser.getTotalCarbonSaved());

        // userNameLabel.setText("UserName: " + thisUser.getUsername());

        nameLabel.setText("Name: " + thisUser.getFirstName() + thisUser.getLastName());

        emailLabel.setText("eMail: " + thisUser.getEmail());

        ageLabel.setText("Age: " + thisUser.getAge() + "");

        lastseenLabel.setText("Last LogIn: " + thisUser.getLastLoginDate().toString());

        level.setText("Level: " + ProfilePageLogic.getLevel(thisUser));

        Image badgeimg = new Image(ProfilePageLogic.getBadge(thisUser));

        badge.setImage(badgeimg);

        int count = 0;

        // for every completed achievement module  is created
        // and added to a VBox small pics might be added later
        for (int i = 0; i < thisUser.getProgress().getAchievements().size(); i++) {

            count++;

            HBox hbox = new HBox();

            hbox.setSpacing(10.0);

            ImageView achievementimage = new ImageView();

            Image path = new Image("achievementsimages/" + thisUser.getProgress()
                    .getAchievements().get(i).getId() + ".png");

            achievementimage.setFitHeight(32);

            achievementimage.setFitWidth(32);

            achievementimage.setImage(path);


            Text name = new Text(i + 1 + ") " + ProfilePageLogic.getNameString(
                    thisUser.getProgress().getAchievements().get(i)));
            name.setFill(Color.GREEN);

            Text bonus = new Text(",Got: " + ProfilePageLogic.getBonusString(
                    thisUser.getProgress().getAchievements().get(i)) + " Points");
            Text date = new Text(",Completed On: " + ProfilePageLogic.getDateString(
                    thisUser.getProgress().getAchievements().get(i)) + ".");

            hbox.getChildren().addAll(achievementimage, name, bonus, date);

            com.getChildren().add(hbox);

        }

        if (count == 0) {

            Label noachiements = new Label("No completed achievements yet");
            anchorPanecom.getChildren().add(noachiements);
            anchorPanecom.setMinWidth(300.0);
            //completedtab.disableProperty();

        }

        for (Achievement a : ProfilePageLogic.getList()) {

            HBox hbox = new HBox();

            hbox.setSpacing(10.0);

            if (!isComplete(a)) {

                ImageView achievementimage1 = new ImageView();

                String image = "achievementsimages/"  + a.getId() + ".png";


                Image path1 = new Image("achievementsimages/8.png");

                achievementimage1.setFitHeight(32);

                achievementimage1.setFitWidth(32);

                achievementimage1.setImage(path1);


                Text name = new Text(a.getName());
                Text points = new Text(",Complete to Get: " + a.getBonus() + " points.");


                hbox.getChildren().addAll(achievementimage1 , name, points);

                incom.getChildren().add(hbox);
            }

        }


        backButton.setOnAction(e ->
                StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getHomepage()));


    }

    /**
     * checks if an achievement is already completed.
     *
     * @param achievement to check
     * @return boolean flag
     */
    public static boolean isComplete(Achievement achievement) {
        for (UserAchievement userAchievement : thisUser.getProgress().getAchievements()) {
            if (achievement.getId() == userAchievement.getId()) {
                return true;
            }

        }
        return false;
    }

    /**
     * Set user to loged in User.
     *
     * @param user loged in user
     */
    public static void setUser(User user) {
        thisUser = user;
        //for testing


        thisUser.getProgress().getAchievements().add(new UserAchievement(
                1, true, new Date(11, 11, 11)));
        thisUser.getProgress().getAchievements().add(new UserAchievement(
                2, true, new Date(12, 11, 11)));
        thisUser.getProgress().getAchievements().add(new UserAchievement(
                12, true, new Date(12, 11, 11)));
        thisUser.getProgress().getAchievements().add(new UserAchievement(
                14, true, new Date(12, 11, 11)));

        thisUser.getProgress().setPoints(1000.0);
    }


}

