package frontend.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import data.User;
import frontend.gui.ProfilePageLogic;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ProfilePageController implements Initializable {

    private static User thisUser;

    @FXML
    JFXHamburger menu;

    @FXML
    JFXDrawer drawer;

    @FXML
    AnchorPane mainPane;

    @FXML
    AnchorPane headerPane;

    @FXML
    private ImageView profilePicture;

    @FXML
    private Label userName;

    @FXML
    private Label name;

    @FXML
    private Label email;

    @FXML
    private Label age;

    @FXML
    private Label lastseen;

    @FXML
    private Label score;

    @FXML
    private Label   level;

//    @FXML
//    private VBox com;
//
//    @FXML
//    private VBox incom;
//
//    @FXML
//    private ToolBar toolBar;

    public static void setUser(User user) {
        thisUser = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        userName.setText("UserName: " + thisUser.getUsername());

//        name.setText("Name: " + thisUser.getFirstName() + " " + thisUser.getLastName());

//        email.setText("eMail: " + thisUser.getEmail());
//
//        age.setText("Age: " + thisUser.getAge() + "");
//
//        lastseen.setText("Last LogIn: " + thisUser.getLastLoginDate().toString());
//
//        level.setText("Level: " + ProfilePageLogic.getLevel(thisUser));
//
//        score.setText("TotalCarbonSaved: " + thisUser.getTotalCarbonSaved());

        try {
            NotificationPanelController.addNotificationPanel(headerPane, mainPane);
            NavPanelController.setup(drawer, menu);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

//        int count = 0;
//
//        // for every completed achievement module  is created
//        // and added to a VBox small pics might be added later
//        for (int i = 0; i < thisUser.getProgress().getAchievements().size(); i++) {
//
//            count++;
//
//            HBox hbox = new HBox();
//
//            hbox.setSpacing(10.0);
//
//            ImageView achievementimage = new ImageView();
//
//            Image path = new Image("achievementsimages/" + thisUser.getProgress()
//                    .getAchievements().get(i).getId() + ".png");
//
//            achievementimage.setFitHeight(32);
//
//            achievementimage.setFitWidth(32);
//
//            achievementimage.setImage(path);
//
//
//            Text name = new Text(i + 1 + ") " + ProfilePageLogic.getNameString(
//                    thisUser.getProgress().getAchievements().get(i)));
//            name.setFill(Color.GREEN);
//
//            Text bonus = new Text(",Got: " + ProfilePageLogic.getBonusString(
//                    thisUser.getProgress().getAchievements().get(i)) + " Points");
//            Text date = new Text(",Completed On: " + ProfilePageLogic.getDateString(
//                    thisUser.getProgress().getAchievements().get(i)) + ".");
//
//            hbox.getChildren().addAll(achievementimage, name, bonus, date);
//
//            com.getChildren().add(hbox);
//
//        }
//
//        if (count == 0) {
//
//            Label noachiements = new Label("No completed achievements yet");
//            anchorPanecom.getChildren().add(noachiements);
//            anchorPanecom.setMinWidth(300.0);
//            //completedtab.disableProperty();
//
//        }
//
//        for (Achievement a : ProfilePageLogic.getList()) {
//
//            HBox hbox = new HBox();
//
//            hbox.setSpacing(10.0);
//
//            if (!isComplete(a)) {
//
//                ImageView achievementimage1 = new ImageView();
//
//                String image = "achievementsimages/" + a.getId() + ".png";
//
//
//                Image path1 = new Image("achievementsimages/8.png");
//
//                achievementimage1.setFitHeight(32);
//
//                achievementimage1.setFitWidth(32);
//
//                achievementimage1.setImage(path1);
//
//
//                Text name = new Text(a.getName());
//                Text points = new Text(",Complete to Get: " + a.getBonus() + " points.");
//
//
//                hbox.getChildren().addAll(achievementimage1, name, points);
//
//                incom.getChildren().add(hbox);
//            }
//
//        }
//
//    }

//    /**
//     * checks if an achievement is already completed.
//     *
//     * @param achievement to check
//     * @return boolean flag
//     */
//    public static boolean isComplete(Achievement achievement) {
//        for (UserAchievement userAchievement : thisUser.getProgress().getAchievements()) {
//            if (achievement.getId() == userAchievement.getId()) {
//                return true;
//            }
//
//        }
//        return false;
//    }
//
//    /**
//     * Set user to loged in User.
//     *
//     */
//    public static void setUser() {
//        setUser();
//    }
//
//    /**
//     * Set user to loged in User.
//     *
//     * @param user loged in user
//     */
//    public static void setUser(User user) {
//        thisUser = user;
//        //for testing
//
//    }
//



