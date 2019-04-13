package frontend.gui;

import data.Achievement;
import data.UserAchievement;
import tools.Requests;

import java.util.List;

public class ProfilePageLogic {


    private static final List<Achievement>  list = Requests.instance.getAllAchievements();


    public static List<Achievement> getList() {
        return list;
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


}
