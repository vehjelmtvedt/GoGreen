package data;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class UserStatistics {
    @Id
    private String timePeriod;

    private int totalUsers;
    private double totalCO2Saved;

    public UserStatistics(String timePeriod, int totalUsers, double totalCO2Saved) {
        this.timePeriod = timePeriod;
        this.totalUsers = totalUsers;
        this.totalCO2Saved = totalCO2Saved;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public double getTotalCO2Saved() {
        return totalCO2Saved;
    }

    public void setTotalCO2Saved(double totalCO2Saved) {
        this.totalCO2Saved = totalCO2Saved;
    }

    public void addTotalCo2Saved(double co2) {
        this.totalCO2Saved += co2;
    }

    public void incrementTotalUsers() {
        totalUsers++;
    }

    public void deleteUser(User user) {
        for (Activity a : user.getActivities()) {
            totalCO2Saved -= a.getCarbonSaved();
        }

        totalUsers--;
    }

    public double getAverageCO2Saved() {
        // Prevent DivByZero errors
        if (totalUsers == 0) {
            return 0;
        }

        return totalCO2Saved / totalUsers;
    }
}
