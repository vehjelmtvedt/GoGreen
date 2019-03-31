package tools;


import data.UserPendingData;
import frontend.controllers.HomepageController;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.util.Duration;

import java.beans.EventHandler;
import java.io.IOException;

public class UserThread extends ScheduledService {
    private static final int sleepTimeSeconds = 5;

    private SyncUserTask syncUserTask;

    public UserThread(SyncUserTask syncUserTask) {
        this.syncUserTask = syncUserTask;
        this.setPeriod(Duration.seconds(sleepTimeSeconds));

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
                UserPendingData userPendingData = syncUserTask.call();

                System.out.println(userPendingData.getAchievements());
                System.out.println(userPendingData.getFriendRequests());

                return userPendingData;
            }
        };
    }
}
