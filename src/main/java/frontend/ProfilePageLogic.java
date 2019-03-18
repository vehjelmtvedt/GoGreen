package frontend;

import backend.data.User;

public class ProfilePageLogic {

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
    /** achievements returns a string with achievements.
     */

    public static void getAchievements(User user){
       //tbd
    }

    /**
     * returns string with badge path.
     */
    public static String getBadge(User user) {

        return "badges/" + user.getProgress().getLevel() + ".png";

    }


}
