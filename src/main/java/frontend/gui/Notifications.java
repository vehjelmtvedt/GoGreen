package frontend.gui;

import data.Achievement;
import data.UserAchievement;
import frontend.controllers.ActivitiesController;
import frontend.controllers.FriendspageController;
import frontend.controllers.HomepageController;
import tools.Requests;

import java.io.IOException;
import java.util.ArrayList;

public class Notifications {

    /**
     * Adds a friend request popup about every page.
     * @param friendRequests - The new friend requests
     * @throws IOException - fails to load fonts
     */
    public static void friendRequest(ArrayList<String> friendRequests) throws IOException {
        int counter = 0;
        for (String name : friendRequests) {
            HomepageController.popup("Friend Request!", "From " + name,
                    "sucess", counter);
            FriendspageController.popup("Friend Request!", "From " + name,
                    "sucess", counter);
            ActivitiesController.popup("Friend Request!", "From " + name,
                    "sucess", counter);
            counter++;
        }
    }

    /**.
     * Add new achievement popup upon completing
     * @param achievements - achievements list
     * @throws IOException - exception to throw if something goes wrong
     */
    public static void newAchievement(ArrayList<UserAchievement> achievements) throws IOException {
        int counter = 0;
        Achievement currAchievement;
        ArrayList<Achievement> all = (ArrayList<Achievement>)
                Requests.instance.getAllAchievements();

        for (UserAchievement userAchievement : achievements) {
            currAchievement = all.get(userAchievement.getId());
            HomepageController.popup("Achievement!", currAchievement.getName(),
                    "sucess", counter);
            FriendspageController.popup("Achievement!", currAchievement.getName(),
                    "sucess", counter);
            ActivitiesController.popup("Achievement!", currAchievement.getName(),
                    "sucess", counter);
            counter++;
        }
    }
}
