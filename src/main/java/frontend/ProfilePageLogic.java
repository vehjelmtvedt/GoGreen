package frontend;

import backend.data.User;

public class ProfilePageLogic {

    /**
     calls the get level from progress.
     */
    public static int getLevel(User user) {

        return user.getProgress().getLevel();

    }
    /**
     gets score from user.

     */
    public static double getScore(User user) {

        return user.getTotalCarbonSaved();

    }

    /** achievements
     returns a string with achievements.
     */
//    public static String getAchievementsString(User user) {
//
//
//        //method to use the Ids in the arraylist to get actual Achievements
//
//        String completed = "completed :";
//
//        for (int i = 0; i < user.getProgress().getAchievements().size(); i++) {
//
//            completed += user.getProgress().getAchievements().get(i).getString() + "\n";
//
//        }
//
//        return completed;
//
//    }

    /** returns string with badge path.


     */
    public static String getBadge(User user) {

        return "badges/" + user.getProgress().getLevel() + ".png";

    }


}
