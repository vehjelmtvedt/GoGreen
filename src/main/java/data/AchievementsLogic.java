package data;

import tools.ActivityQueries;

import java.util.ArrayList;

public class AchievementsLogic {


    /**
     * checks transport related activity if it completes an achievement.
     *
     * @param user     current user
     * @param activity current activity
     */
    public static ArrayList<Integer> checkTranspostActivity(User user, Activity activity) {

        ArrayList<Integer> results = new ArrayList();
        results.add(0);

        ActivityQueries activityQueries = new ActivityQueries(user.getActivities());
        //Using a bicycle id 2
        if (activity instanceof UseBikeInsteadOfCar) {

            results.add(2);

        }

        //Using a bike often id 3
        //this checks if similar activity has happen to grant the frequent achievement
        if (activity instanceof UseBikeInsteadOfCar
                && activityQueries.filterActivitiesByType(activity.getClass()).size() > 4) {
            results.add(3);
        }

        //Using bus once id 4
        if (activity instanceof UseBusInsteadOfCar) {
            results.add(4);
        }


        //Using  bus often id 5
        if (activity instanceof UseBusInsteadOfCar
                && activityQueries.filterActivitiesByType(activity.getClass()).size() > 4) {
            results.add(5);

        }
        return results;
    }

    /**
     * checks more transport related activity if it completes an achievement.
     *
     * @param user     current user
     * @param activity current activity
     */

    public static ArrayList<Integer> checkTranspostActivity1(User user, Activity activity) {
        ArrayList<Integer> results = new ArrayList();
        results.add(0);

        ActivityQueries activityQueries = new ActivityQueries(user.getActivities());
        //Use dp 50 activities 7
        if (user.getActivities().size() > 49) {
            results.add(7);
        }

        //use the train once 24
        if (activity instanceof UseTrainInsteadOfCar) {
            results.add(24);
        }

        //use the train often 25
        if (activity instanceof UseTrainInsteadOfCar
                && activityQueries.filterActivitiesByType(activity.getClass()).size() > 4) {
            results.add(25);
        }
        return results;
    }

    /**
     * checks food related activity if it completes an achievement.
     *
     * @param user     current user
     * @param activity current activity
     */
    public static ArrayList<Integer> checkFoodActivity(User user, Activity activity) {


        ArrayList<Integer> results = new ArrayList();
        results.add(0);
        //Eating vegetarian food for the first time id 6
        if (activity instanceof EatVegetarianMeal) {
            results.add(6);
        }


        //Buy Local Food 15
        if (activity instanceof BuyLocallyProducedFood) {
            results.add(15);
        }

        //Buy Non Processed Food 16
        if (activity instanceof BuyNonProcessedFood) {
            results.add(16);
        }
        //buy organic food 17
        if (activity instanceof BuyOrganicFood) {
            results.add(17);
        }

        return results;

    }

    /**
     * checks for other things that complete achievements.
     *
     * @param user current user
     */
    public static ArrayList<Integer> checkOther(User user) {

        ArrayList<Integer> results = new ArrayList();

        //Saved your first CO2 id 0
        if (user.getTotalCarbonSaved() > 0) {
            results.add(0);
        }

        //Adding more than five Activites id 1
        if (user.getActivities().size() > 5) {
            results.add(1);
        }

        //Adding your first friend id 8
        // the date is the date of the time this was checked
        if (user.getFriends().size() > 0) {
            results.add(8);
        }

        //Adding more than 10 friends id 9
        if (user.getFriends().size() > 10) {
            results.add(9);
        }

        //Have a small Car 13
        if (user.getCarType().equals("small")) {
            results.add(13);
        }

        //Being Vegan 14
        if (user.getMeatAndDairyConsumption().equals("vegan")) {
            results.add(14);
        }


        return results;
    }

    /**
     * check if the user has a level to grant the corresponding achievement.
     *
     * @param user user to check
     */
    public static ArrayList<Integer> checkLevel(User user) {

        ArrayList<Integer> results = new ArrayList();
        results.add(0);

        //Achieve level 4 19
        if (user.getProgress().getLevel() >= 4) {
            results.add(19);
        }
        //Achieve level 5 20
        if (user.getProgress().getLevel() >= 5) {
            results.add(20);
        }
        //Achieve level 6 21
        if (user.getProgress().getLevel() >= 6) {
            results.add(21);
        }
        //Achieve level 7 22
        if (user.getProgress().getLevel() >= 7) {
            results.add(22);
        }
        //Achieve level 8 23
        if (user.getProgress().getLevel() == 8) {
            results.add(23);
        }
        return results;
    }

    /**
     * checks if the activity completes an achievement.
     *
     * @param user the user
     * @param activity to check
     * @return array of ids
     */
    public static ArrayList<Integer> checkotherActivities(User user, Activity activity) {
        ArrayList<Integer> results = new ArrayList<>();

        // solar panels 12
        if (activity instanceof InstallSolarPanels) {
            results.add(12);
        }

        //Recycle Paper 28
        if (activity instanceof RecyclePaper) {
            results.add(28);
        }
        //Recycle plastic 29
        if (activity instanceof RecyclePlastic) {
            results.add(29);
        }
        //Lower Home Temperature
        if (activity instanceof LowerHomeTemperature) {
            results.add(30);
        }

        return results;
    }


}
