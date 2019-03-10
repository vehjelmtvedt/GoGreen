package backend.data;

import java.util.ArrayList;

public class Progress {

    private double points;
    private ArrayList<UserAchievement> achievements;

    public Progress() {
        this.points = 0;
        this.achievements = new ArrayList<UserAchievement>();
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    /**
     * update the points.
     *
     * @param po points to be added
     */
    public void addPoints(double po) {

        this.points = this.points + po;

    }

    /**
     * this method is a general way to get
     * the level, they higher the level the more points it needs to level up
     * tops up on 8.
     *
     * @return level
     */
    public int getLevel() {

        if (this.points == 0) {
            return 1;
        }

        double res = this.getPoints();

        double level = Math.log(res);

        int result = (int) Math.floor(level);

        if (result > 8) {
            return 8;
        }

        return result;


    }

}
