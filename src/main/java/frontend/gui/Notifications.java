package frontend.gui;

import frontend.controllers.ActivitiesController;
import frontend.controllers.FriendspageController;
import frontend.controllers.HomepageController;

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
}
