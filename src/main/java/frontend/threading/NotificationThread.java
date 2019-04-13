package frontend.threading;

import data.UserPendingData;
import frontend.controllers.NotificationPanelController;
import frontend.gui.Notifications;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;
import tools.SyncUserTask;

import java.io.IOException;

public class NotificationThread extends ScheduledService<UserPendingData> {
    public static NotificationPanelController notificationPanelController;

    private static final int sleepTimeSeconds = 5;

    private SyncUserTask syncUserTask;



    /**.
     * Creates a new NotificationThread for the specified User
     * @param syncUserTask - SyncUserTask containing User data
     */
    public NotificationThread(SyncUserTask syncUserTask) {
        this.syncUserTask = syncUserTask;
        this.setPeriod(Duration.seconds(sleepTimeSeconds));

        // succeeded?
        this.setOnSucceeded(s -> {
            System.out.println("SUCCEEDED");
            System.out.println(this.getValue().getFriendRequests());
            System.out.println(this.getValue().getAchievements());

            //Check for new friend requests
            if (this.getValue().getFriendRequests().size() != 0) {
                try {
                    Notifications.friendRequest(this.getValue().getFriendRequests());
                    notificationPanelController.fillFriendRequests();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //Check for new achievements
            if (this.getValue().getAchievements().size() != 0) {
                try {
                    System.out.println("NEW ACHIEVEMENT");
                    Notifications.newAchievement(this.getValue().getAchievements());
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
    public Task<UserPendingData> createTask() {
        return new Task<UserPendingData>() {
            @Override
            protected UserPendingData call() throws Exception {
                return syncUserTask.call();
            }
        };
    }
}
