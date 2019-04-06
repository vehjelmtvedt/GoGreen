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

import java.io.FileNotFoundException;
import java.net.URL;

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

        lastseenLabel.setText("Last LogIn: " + thisUser.getLastLoginDate().toString());


        nameLabel.setText("Name: " + thisUser.getFirstName() + thisUser.getLastName());

        emailLabel.setText("eMail: " + thisUser.getEmail());

        ageLabel.setText("Age: " + thisUser.getAge() + "");

        updateStates();
        updateAchievements();

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

    }

    /**
     * Adds the completed achievements to the Vbox.
     *
     * @param com the one that contains the completed achievements
     */
    public static void addCompletedAchievements(VBox com) {


            ProfilePageController.setUser(thisUser);

            // for every completed achievement module  is created
            // and added to a VBox small pics might be added later
            for (int i = 0; i < thisUser.getProgress().getAchievements().size(); i++) {


                HBox hbox = new HBox();

                hbox.setSpacing(10.0);

                ImageView achievementimage = new ImageView();

                achievementimage.setFitHeight(32);

                achievementimage.setFitWidth(32);

                achievementimage.setImage(ProfilePageLogic.getUserAchievementImagePath(
                        thisUser.getProgress().getAchievements().get(i)));

                Text name = new Text(i + 1 + ") " + ProfilePageLogic.getNameString(
                        thisUser.getProgress().getAchievements().get(i)));
                name.setFill(Color.GREEN);

                Text bonus = new Text(", Earned: " + ProfilePageLogic.getBonusString(
                        thisUser.getProgress().getAchievements().get(i)) + " Points");
                Text date = new Text(", Completed On: " + ProfilePageLogic.getDateString(
                        thisUser.getProgress().getAchievements().get(i)) + ".");

                hbox.getChildren().addAll(achievementimage, name, bonus, date);

                com.getChildren().add(hbox);

            }

    }
    /**
     * Adds the incompleted achievements to the Vbox.
     *
     * @param incom the one that contains the incompleted achievements
     */

    public static void addPendingAchievements(VBox incom) {

        try {

            for (Achievement a : ProfilePageLogic.getList()) {

                if (!isComplete(a)) {
                    ImageView achievementimage = new ImageView();

                    HBox hbox = new HBox();

                    hbox.setSpacing(10.0);

                    achievementimage.setFitHeight(32);

                    achievementimage.setFitWidth(32);

                    achievementimage.setImage(ProfilePageLogic.getAchievementImagePath(a));

                    Text name = new Text(a.getName());
                    Text points = new Text(" , Complete to Get: " + a.getBonus() + " points.");


                    hbox.getChildren().addAll(achievementimage, name, points);

                    incom.getChildren().add(hbox);

                }
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.toString());
        }
    }

    /**this updates the achievements .
     *
     */
    public void updateAchievements() {

        com.getChildren().clear();
        incom.getChildren().clear();

        addCompletedAchievements(com);
        addPendingAchievements(incom);

        updateStates();


    }

    /**this updates the states.
     *
     */
    public void updateStates() {

        score.setText("Score: " + thisUser.getProgress().getPoints());

        level.setText("Level: " + ProfilePageLogic.getLevel(thisUser));

        Image badgeimg = new Image(ProfilePageLogic.getBadge(thisUser));

        badge.setImage(badgeimg);

    }

}

