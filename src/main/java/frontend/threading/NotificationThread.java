package frontend.threading;


import data.UserPendingData;
import frontend.controllers.HomepageController;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.util.Duration;
import tools.SyncUserTask;

import java.beans.EventHandler;
import java.io.IOException;

public class NotificationThread extends ScheduledService<UserPendingData> {
    private static final int sleepTimeSeconds = 5;

    private SyncUserTask syncUserTask;

    public NotificationThread(SyncUserTask syncUserTask) {
        this.syncUserTask = syncUserTask;
        this.setPeriod(Duration.seconds(sleepTimeSeconds));

        // succeeded?
        this.setOnSucceeded(s -> {
            System.out.println("SUCCEEDED");
            System.out.println(this.getValue().getFriendRequests());
            System.out.println(this.getValue().getAchievements());

            try {
                HomepageController.popup();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        return new Task<UserPendingData>() {
            @Override
            protected UserPendingData call() throws Exception {
                return syncUserTask.call();
            }
        };
    }
}
