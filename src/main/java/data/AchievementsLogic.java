package data;

import tools.Requests;

import java.util.Calendar;
import java.util.List;

public class AchievementsLogic {

    /**
     *checks activity if it completes an achievement.
     *
     * @param user current user
     * @param activity current activity
     */
    public static void checkActivity(User user , Activity activity) {

        List<Achievement> list = Requests.getAllAchievements();

        //Saved your first CO2 id 0
        if (user.getTotalCarbonSaved() > 0) {

            user.getProgress().getAchievements().add(
                    new UserAchievement(0 , true , user.getActivities().get(0).getDate()));

            user.setTotalCarbonSaved(user.getTotalCarbonSaved() + list.get(1).getBonus());
        }

        //Being consistent for more than two days id 1
        for (int i  = 0 ; i < user.getActivities().size() - 1 ; i++ ) {


            if (user.getActivities().get(i).getDate().getDay()
                    == user.getActivities().get(i + 1).getDate().getDay()) {

                // do nothing if its on the same day

            }
            if (user.getActivities().get(i).getDate().getDay()
                    == user.getActivities().get(i + 1).getDate().getDay() + 1) {

                user.getProgress().getAchievements().add(
                        new UserAchievement(1 , true , user.getActivities().get(1 + i).getDate()));

                break;

            }

        }

        //Using a bicycle id 2

        //Using a bike often id 3

        //Using public transport once id 4

        //Using public transport often id 5

        //Eating vegetarian food for the first time id 6
        if (activity instanceof EatVegetarianMeal) {

            user.getProgress().getAchievements().add(
                    new UserAchievement(6, true , user.getActivities().get(1).getDate()));

        }
        //Eating fish once id 7

        //Adding your first friend id 8
        // the date is the date of the time this was checked
        if (user.getFriends().size() > 0) {
            user.getProgress().getAchievements().add(
                    new UserAchievement(8, true , user.getActivities().get(1).getDate()));
        }

    }

    /**
     * checks for other things that complete achievements.
     * @param user current user
     *
     */
    public static void checkOther(User user) {

        //Adding your first friend id 8
        // the date is the date of the time this was checked
        if (user.getFriends().size() > 0) {
            user.getProgress().getAchievements().add(
                    new UserAchievement(8, true , Calendar.getInstance().getTime()));
        }

        //Adding more than 10 friends id 9
        if (user.getFriends().size() > 10) {
            user.getProgress().getAchievements().add(
                    new UserAchievement(9, true , Calendar.getInstance().getTime()));
        }

        //Being on the top of the board for a day id 10


    }

}
