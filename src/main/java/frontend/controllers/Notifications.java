package frontend.controllers;

import java.io.IOException;
import java.util.ArrayList;

public class Notifications {

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
