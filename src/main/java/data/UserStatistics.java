package data;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class UserStatistics {
    @Id
    private String timePeriod;

    private int totalUsers;
    private double totalCO2Saved;

    /**.
     * Creates a new UserStatistics object with the specified data
     * @param timePeriod - Time period String to consider ("all" for all time)
     * @param totalUsers - Total Users
     * @param totalCO2Saved - Total CO2 Saved
     */
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

    /**.
     * Handles User removal statistic updating
     * @param user - User that has been removed
     */
    public void deleteUser(User user) {
        for (Activity a : user.getActivities()) {
            totalCO2Saved -= a.getCarbonSaved();
        }

        totalUsers--;
    }

    /**.
     * Returns the average CO2 saved by all Users
     * @return - Average CO2 saved by all Users
     */
    public double getAverageCO2Saved() {
        // Prevent DivByZero errors
        if (totalUsers == 0) {
            return 0;
        }

        return totalCO2Saved / totalUsers;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        UserStatistics that = (UserStatistics) object;

        return totalUsers == that.totalUsers
                && Double.compare(that.totalCO2Saved, totalCO2Saved) == 0
                && Objects.equals(timePeriod, that.timePeriod);
    }
}
