package tools;


import data.UserPendingData;
import frontend.controllers.HomepageController;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;

public class UserThread extends Service {
    private static final int sleepTime = 5000;

    private SyncUserTask syncUserTask;

    public UserThread(SyncUserTask syncUserTask) {
        this.syncUserTask = syncUserTask;
        // succeeded?
        this.setOnSucceeded(s -> {
            System.out.println("SUCCEEDED");
        });

        // failed
        this.setOnFailed(f -> {
            System.out.println("FAILED");
        });

        // cancelled?
        this.setOnCancelled(c -> {
            System.out.println("CANCELLED");
        });
    }

    @Override
    public Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                while (true) {
                    try {
                        Thread.sleep(sleepTime);
                        UserPendingData userPendingData = syncUserTask.call();

                        System.out.println(userPendingData.getAchievements());
                        System.out.println(userPendingData.getFriendRequests());
                        if (userPendingData.getFriendRequests().size() != 0) {
                            //Alert the controllers and make them add a popup and notification
                            //in the notification panel
                            return userPendingData.getFriendRequests();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}
