package tools;

import data.LoginDetails;
import data.User;
import data.UserPendingData;
import tools.SyncUserTask;

public class UserThread extends Thread {
    public static void main(String[] args) {
        LoginDetails loginDetails = new LoginDetails("test", "qwerty");
        User user = Requests.instance.loginRequest(loginDetails);
        SyncUserTask syncUserTask = new SyncUserTask(Requests.instance, loginDetails, user);
        UserThread userThread = new UserThread(syncUserTask);
        userThread.run();
    }

    private static final int sleepTime = 5000;

    private SyncUserTask syncUserTask;

    public UserThread(SyncUserTask syncUserTask) {
        this.syncUserTask = syncUserTask;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(sleepTime);
                UserPendingData userPendingData = syncUserTask.call();

                System.out.println(userPendingData.getAchievements());
                System.out.println(userPendingData.getFriendRequests());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
