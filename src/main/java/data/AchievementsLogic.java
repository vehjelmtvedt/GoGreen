package data;

import java.util.Calendar;
import java.util.Date;


public class AchievementsLogic {


    /**
     * checks transport related activity if it completes an achievement.
     *
     * @param user     current user
     * @param activity current activity
     */
    public static int checkTranspostActivity(User user, Activity activity) {

        //Using a bicycle id 2
        if (activity instanceof UseBikeInsteadOfCar) {

            addAchievemnt(user, 2, activity.getDate());
            return 19;

        }

        //Using a bike often id 3
        //this checks if similar activity has happen to grant the frequent achievement
        if (activity instanceof UseBikeInsteadOfCar
                && user.getSimilarActivities(activity).size() > 4) {

            addAchievemnt(user, 3, activity.getDate());
            return 19;
        }

        //Using public transport once id 4
        if (activity instanceof UseBusInsteadOfCar || activity instanceof UseTrainInsteadOfCar) {
            addAchievemnt(user, 4, activity.getDate());
            return 19;
        }
        //Using public transport often id 5
        if ((activity instanceof UseBusInsteadOfCar || activity instanceof UseTrainInsteadOfCar)
                && (user.getSimilarActivities(activity).size() > 4)) {
            addAchievemnt(user, 5, activity.getDate());
            return 19;

        }
        return -1;
    }
    /**
     * checks more transport related activity if it completes an achievement.
     *
     * @param user     current user
     * @param activity current activity
     */

    public static int checkTranspostActivity1(User user , Activity activity) {
        //Use the Bus once 7
        if (activity instanceof UseBusInsteadOfCar) {

            addAchievemnt(user, 7, activity.getDate());
            return 19;
        }

        //use the train once 24
        if (activity instanceof UseTrainInsteadOfCar) {
            addAchievemnt(user, 24, activity.getDate());
            return 19;
        }

        //use the train once 25
        if (activity instanceof UseTrainInsteadOfCar
                && user.getSimilarActivities(activity).size() > 4) {
            addAchievemnt(user, 25, activity.getDate());
            return 19;
        }
        return -1;
    }

    /**
     * checks food related activity if it completes an achievement.
     *
     * @param user     current user
     * @param activity current activity
     */
    public static int checkFoodActivity(User user, Activity activity) {


        //Eating vegetarian food for the first time id 6
        if (activity instanceof EatVegetarianMeal) {

            addAchievemnt(user, 6, activity.getDate());
            return 19;
        }


        //Buy Local Food 15
        if (activity instanceof BuyLocallyProducedFood) {

            addAchievemnt(user, 15, activity.getDate());
            return 19;
        }

        //Buy Non Processed Food 16
        if (activity instanceof BuyNonProcessedFood) {

            addAchievemnt(user, 16, activity.getDate());
            return 19;
        }
        //buy organic food 17
        if (activity instanceof BuyOrganicFood) {

            addAchievemnt(user, 17, activity.getDate());
            return 19;
        }

        return -1;

    }

    /**
     * checks for other things that complete achievements.
     *
     * @param user current user
     */
    public static int checkOther(User user) {

        //Saved your first CO2 id 0
        if (user.getTotalCarbonSaved() > 0 && user.getActivities().size() > 0) {

            addAchievemnt(user, 0, user.getActivities().get(0).getDate());
            return 19;
        }

        //Adding more than five Activites id 1
        if (user.getActivities().size() > 5) {

            addAchievemnt(user, 1, user.getActivities().get(4).getDate());
            return 19;
        }

        //Adding your first friend id 8
        // the date is the date of the time this was checked
        if (user.getFriends().size() > 0) {

            addAchievemnt(user, 8, Calendar.getInstance().getTime());
            return 19;
        }

        //Adding more than 10 friends id 9
        if (user.getFriends().size() > 10) {

            addAchievemnt(user, 9, Calendar.getInstance().getTime());
            return 19;
        }

        //Have a small Car 13
        if (user.getCarType().equals("small")) {
            addAchievemnt(user, 13, Calendar.getInstance().getTime());
            return 19;
        }

        //Being Vegan 14
        if (user.getMeatAndDairyConsumption().equals("vegan")) {
            addAchievemnt(user, 14, Calendar.getInstance().getTime());
            return 19;
        }



        //todo
        //Being on the top of the board id 10
        //Being second on top of the board id 11
        //Be third on top of the larboard 18
        //Getting solar Power 12
        return -1;
    }

    /**check if the user has a level to grant the corresponding achievement.
     *
     * @param user user to check
     */
    public static int checkLevel(User user) {

        //Achieve level 4 19
        if (user.getProgress().getLevel() >= 4) {
            addAchievemnt(user, 19, Calendar.getInstance().getTime());
            return 19;
        }
        //Achieve level 5 20
        if (user.getProgress().getLevel() >= 5) {
            addAchievemnt(user, 20, Calendar.getInstance().getTime());
            return 19;
        }
        //Achieve level 6 21
        if (user.getProgress().getLevel() >= 6) {
            addAchievemnt(user, 21, Calendar.getInstance().getTime());
            return 19;
        }
        //Achieve level 7 22
        if (user.getProgress().getLevel() >= 7) {
            addAchievemnt(user, 22, Calendar.getInstance().getTime());
            return 19;
        }
        //Achieve level 8 23
        if (user.getProgress().getLevel() == 8) {
            addAchievemnt(user, 23, Calendar.getInstance().getTime());
            return 19;
        }
        return -1;
    }


    /**
     * this method checks every achievement if its already in the List, if not add it.
     *
     * @param user current user
     * @param id   achievement to check
     * @param date date to add
     */
    public static void addAchievemnt(User user, int id, Date date) {

    }

}
