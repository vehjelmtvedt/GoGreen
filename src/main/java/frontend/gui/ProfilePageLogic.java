package frontend.gui;

import data.Achievement;
import data.User;
import data.UserAchievement;
import javafx.scene.image.Image;
import tools.Requests;

import java.util.List;

public class ProfilePageLogic {


    private static List<Achievement>  list = Requests.instance.getAllAchievements();


    public static List<Achievement> getList() {
        return list;
    }

    /**
     * calls the get level from progress.
     */
    public static int getLevel(User user) {

        return user.getProgress().getLevel();

    }

    /**
     * gets score from user.
     */
    public static double getScore(User user) {

        return user.getTotalCarbonSaved();

    }


    /**
     * returns string with badge path.
     */
    public static String getBadge(User user) {

        return "badges/" + user.getProgress().getLevel() + ".png";

    }


    /**
     * this returns a String with Achievement name and completion date.
     *
     * @return String with Achievement name and completion date
     */
    public static String toString(UserAchievement userAchievement) {

        String achievement = list.get(userAchievement.getId()).getName()
                + ", Earned: " + list.get(userAchievement.getId()).getBonus()
                + ", Completed on :" + userAchievement.getDate().toString() + ".";

        return achievement;

    }

    /**
     * this returns a String with Achievement name.
     *
     * @return name
     */
    public static String getNameString(UserAchievement userAchievement) {

        return list.get(userAchievement.getId()).getName();

    }

    /**
     * this returns a String with Achievement bonus.
     *
     * @return bonus
     */
    public static int getBonusString(UserAchievement userAchievement) {

        return list.get(userAchievement.getId()).getBonus();
    }

    /**
     * this returns a String with Achievement completion date.
     *
     * @return date
     */
    public static String getDateString(UserAchievement userAchievement) {


        return userAchievement.getDate().toString();

    }

    /**creates an Image for the achievement.
     *
     *
     * @param achievement the achievement that needs the image
     * @return the correct image to the achievement
     */
    public static Image getAchievementImagePath(Achievement achievement) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(achievement.getId());

        String path = "achievementsimages/" +  stringBuilder.toString() + ".png";

        return new Image(path);

    }

    /**creates an Image for the achievement.
     *
     *
     * @param userAchievement the achievement that needs the image
     * @return the correct image to the achievement
     */
    public static Image getUserAchievementImagePath(UserAchievement userAchievement) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(userAchievement.getId());

        String path = "achievementsimages/" +  stringBuilder.toString() + ".png";



        return new Image(path);

    }



}
