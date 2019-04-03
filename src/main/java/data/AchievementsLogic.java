package data;

import java.util.Calendar;
import java.util.Date;


public class AchievementsLogic {


    /**
     * checks activity if it completes an achievement.
     *
     * @param user     current user
     * @param activity current activity
     */
    public static void checkActivity(User user, Activity activity) {


        //Using a bicycle id 2

        //Using a bike often id 3

        //Using public transport once id 4

        //Using public transport often id 5

        //Eating vegetarian food for the first time id 6
        if (activity instanceof EatVegetarianMeal) {

            addAchievemnt(user , 6 , activity.getDate());



        }
        //Eating fish once id 7
        //Getting solar Power 12
        //Using an electric Car 13
        //Being Vegan 14

        //Buy Local Food15
        if (activity instanceof BuyLocallyProducedFood) {

            addAchievemnt(user , 15 , activity.getDate());


        }

        //Buy Non Processed Food 16
        if (activity instanceof BuyNonProcessedFood) {

            addAchievemnt(user , 16 , activity.getDate());


        }
        //buy organic food 17
        if (activity instanceof BuyOrganicFood) {

            addAchievemnt(user , 17 , activity.getDate());

        }

    }

    /**
     * checks for other things that complete achievements.
     *
     * @param user current user
     */
    public static void checkOther(User user) {

        //Saved your first CO2 id 0
        if (user.getTotalCarbonSaved() > 0  && user.getActivities().size() > 0) {

            addAchievemnt(user , 0 , user.getActivities().get(0).getDate());



        }

        //Being consistent for more than two days id 1
        if (user.getActivities().size() > 5) {

            addAchievemnt(user , 1 , user.getActivities().get(4).getDate());

        }


        //Adding your first friend id 8
        // the date is the date of the time this was checked
        if (user.getFriends().size() > 0) {

            addAchievemnt(user , 8 , Calendar.getInstance().getTime());
        }

        //Adding more than 10 friends id 9
        if (user.getFriends().size() > 10) {

            addAchievemnt(user , 9 , Calendar.getInstance().getTime());

        }

        //Being on the top of the board for a day id 10
        //Being on the top of the board for a week id 11


    }

    /**
     * this method checks every achievement if its already in the List, if not add it.
     * @param user current user
     * @param id achievement to check
     * @param date date to add
     */
    public static void addAchievemnt(User user , int id , Date date) {

        boolean alreadythere = false;

        for (UserAchievement userAchievement : user.getProgress().getAchievements()) {

            if (userAchievement.getId() == id) {

                alreadythere = true;
                break;
            }

        }

        //List<Achievement> list = Requests.instance.getAllAchievements();

        if (!alreadythere) {

            UserAchievement userAchievement = new UserAchievement(id, true, date);

            user.getProgress().getAchievements().add(userAchievement);

            // adds point depending on the achievements
            //user.getProgress().setPoints(user.getProgress().getPoints() +
            // list.get(id).getBonus());

            //for now its hard codded
            user.getProgress().setPoints(user.getProgress().getPoints() + 100);
        }




    }

}
