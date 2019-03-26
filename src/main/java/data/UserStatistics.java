package data;

public class UserStatistics {
    private int totalUsers;
    private double totalCO2Saved;

    public UserStatistics(int totalUsers, double totalCO2Saved) {
        this.totalUsers = totalUsers;
        this.totalCO2Saved = totalCO2Saved;
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
}
