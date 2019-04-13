package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXProgressBar;
import data.Achievement;
import data.LoginDetails;
import data.User;
import data.UserAchievement;

import frontend.gui.Events;
import frontend.gui.General;
import frontend.gui.Main;
import frontend.gui.NavPanel;
import frontend.gui.ProfilePageLogic;
import frontend.gui.StageSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tools.Requests;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

public class ProfilePageController implements Initializable {
    private static User thisUser;
    private static LoginDetails thisLoginDetails;

    @FXML
    JFXDrawer drawer;
    @FXML
    private Circle profilePicture;
    @FXML
    private JFXHamburger menu;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private AnchorPane headerPane;
    @FXML
    private Label userName;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label lblGoGreen;
    @FXML
    private Label age;
    @FXML
    private Label email;
    @FXML
    private Label lastSeen;
    @FXML
    private Label score;
    @FXML
    private Label level;
    @FXML
    private Label rank;
    @FXML
    private JFXButton editProfile;
    @FXML
    private JFXProgressBar levelProgress;
    @FXML
    private VBox completed;
    @FXML
    private VBox incompleted;
    @FXML
    private HBox badgeZone;

    /**
     * .
     * Update the age field with the new value
     *
     * @param newAge - new value for age
     */
    public void updateAge(String newAge) {
        age.setText(newAge);
    }

    /**
     * .
     * Update the first name field with the new value
     *
     * @param newFirstName - new value for first name
     */
    public void updateFirstName(String newFirstName) {
        firstName.setText(newFirstName);
    }

    /**
     * .
     * Update the last name field with the new value
     *
     * @param newLastName - new value for last name
     */
    public void updateLastName(String newLastName) {
        lastName.setText(newLastName);
    }

    /**
     * .
     * Update the user's profile page information
     * @param loginDetails - user to update info to
     */
    public void updateUser(LoginDetails loginDetails) {
        //update the current user
        thisUser = Requests.instance.loginRequest(loginDetails);

        //update the user information fields on the profile page
        levelProgress.setProgress(
                (thisUser.getProgress().getPoints()) / (getLevelPoints())
        );
        userName.setText(thisUser.getUsername());
        firstName.setText(thisUser.getFirstName());
        lastName.setText(thisUser.getLastName());
        email.setText(thisUser.getEmail());
        age.setText(thisUser.getAge() + "");
        lastSeen.setText(thisUser.getLastLoginDate().toString());
        level.setText("" + thisUser.getProgress().getLevel());
        score.setText("" + thisUser.getTotalCarbonSaved());
        rank.setText("" + Requests.instance.getUserRanking(loginDetails));
        profilePicture.setFill(new ImagePattern(
                new Image("avatars/" + thisUser.getAvatar() + ".jpg")));
    }

    /**
     * .
     * Update the containers with completed & uncompleted achievements
     * @param loginDetails - user view to update
     */
    public void updateAchievements(LoginDetails loginDetails) {
        //update user object on profile page
        thisUser = Requests.instance.loginRequest(loginDetails);

        //reset achievement containers before updating values
        completed.getChildren().clear();
        incompleted.getChildren().clear();
        badgeZone.getChildren().clear();

        // for every completed achievement module  is created
        // and added to a VBox small pics might be added later
        int count = 1;
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
            Text bonus = new Text("Received: " + ProfilePageLogic.getBonusString(
                    thisUser.getProgress().getAchievements().get(i)) + " Points");
            bonus.setFill(Color.GREEN);
            Text date = new Text("Completed On: " + ProfilePageLogic.getDateString(
                    thisUser.getProgress().getAchievements().get(i)) + ".");
            hbox.getChildren().addAll(achievementimage, name, bonus, date);
            completed.getChildren().add(hbox);
        }

        if (count == 0) {
            Label noAchievements = new Label("No completed achievements available for viewing.");
            completed.getChildren().add(noAchievements);
        }

        for (Achievement a : ProfilePageLogic.getList()) {
            HBox hbox = new HBox();
            hbox.setSpacing(10.0);
            if (!isComplete(a)) {
                ImageView achievementimage1 = new ImageView();
                Image path1 = new Image("achievementsimages/8.png");
                achievementimage1.setFitHeight(32);
                achievementimage1.setFitWidth(32);
                achievementimage1.setImage(path1);
                Text name = new Text(a.getName());
                Text points = new Text("Complete to receive: " + a.getBonus() + " points.");
                hbox.getChildren().addAll(achievementimage1, name, points);
                incompleted.getChildren().add(hbox);
            }
        }

        int levelcount = 1;
        for (int i = 1; i <= thisUser.getProgress().getLevel(); i++) {

            ImageView badgeimage = new ImageView();
            Image path = new Image("badges/" + levelcount + ".png");
            badgeimage.setFitHeight(150);
            badgeimage.setFitWidth(150);
            badgeimage.setImage(path);
            badgeZone.getChildren().add(badgeimage);
            levelcount++;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //add all required events & set controller in the events class
        Events.addJfxButtonHover(editProfile);
        Events.profilePageController = this;
        NavPanelController.profilePageController = this;
        EditProfilePopUpController.profilePageController = this;

        //fill in the user information on the profile page
        updateUser(thisLoginDetails);
        updateAchievements(thisLoginDetails);

        editProfile.setOnAction(e -> {
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);

            FXMLLoader loader = new FXMLLoader(
                    Main.class.getResource("/frontend/fxmlPages/EditProfilePopUp.fxml"));
            Parent popup = null;
            try {
                popup = loader.load();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            EditProfilePopUpController controller = loader.getController();

            Scene scene = new Scene(popup,
                    General.getBounds()[0] / 2, General.getBounds()[1] / 2);
            stage.setScene(scene);
            stage.show();
            stage.toFront();
        });

        try {
            NotificationPanelController.addNotificationPanel(headerPane, mainPane);
            StageSwitcher.homeDrawer = NavPanel.addNavPanel(mainPane, headerPane, menu);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ProfilePageController.setUser(thisUser);

        //setup fonts
        try {
            lblGoGreen.setFont(Main.getReenieBeanie(100));
        } catch (IOException e) {
            System.out.println("Fonts not found");
        }
    }

    /**
     * Checks completed Achievements.
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
     * Retrieves the number of points needed for each level above the users current level.
     *
     * @return Points needed to achieve the respective level.
     */
    public double getLevelPoints() {
        int level = thisUser.getProgress().getLevel();
        if (level == 1) {
            return 700.0;
        } else if (level == 2) {
            return 2000.0;
        } else if (level == 3) {
            return 5500.0;
        } else if (level == 4) {
            return 14800.0;
        } else if (level == 5) {
            return 40300.0;
        } else if (level == 6) {
            return 109700.0;
        } else if (level == 7) {
            return 298100.0;
        } else if (level == 8) {
            return 298100.0;
        } else {
            return 0.0;
        }
    }

    public static void setUser(User passedUser) {
        thisUser = passedUser;
    }

    public static void setLoginDetails(LoginDetails passedLoginDetails) {
        thisLoginDetails = passedLoginDetails;
    }


}




