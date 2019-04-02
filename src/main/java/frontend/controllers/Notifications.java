package frontend.controllers;

import java.io.IOException;
import java.util.ArrayList;

public class Notifications {

    public static void friendRequest(ArrayList<String> friendRequests) throws IOException {
        int counter = 0;
        for (String name : friendRequests) {
            HomepageController.popup("Test", "Testing again " + name,
                    "sucess", counter);
            counter++;
        }
    }
}
