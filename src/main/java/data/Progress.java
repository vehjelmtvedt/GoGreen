package data;

import java.util.ArrayList;
import java.util.Observable;

public class Progress extends Observable {

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

    public ArrayList<UserAchievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(ArrayList<UserAchievement> achievements) {
        this.achievements = achievements;
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
     * points needed for each level :
     * Level 1 needs 300
     * Level 2 needs 700
     * Level 3 needs 2000
     * Level 4 needs 5500
     * Level 5 needs 14800
     * Level 6 needs 40300
     * Level 7 needs 109700
     * Level 8 needs 298100
     *
     * @return level
     */
    public int getLevel() {

        if (this.points <= Math.exp(1) * 100) {
            return 1;
        }

        double res = this.getPoints();

        double level = Math.log(res / 100);

        int result = (int) Math.floor(level);

        if (result > 8) {
            return 8;
        }

        return result;
    }

    /**
     * calculate how many points needed for next level.
     *
     * @return points needed
     */
    public double pointsNeeded() {


        if (this.getLevel() < 8) {

            double points = this.getPoints();

            int level = this.getLevel() + 1;

            double need = Math.round(Math.exp(level) * 100 - points);

            return need;

        } else {
            return 0;

        }
    }

    //    /**notify Observers that the progress has changed.
    //     *
    //     */
    //    public void hasChangedCheck() {
    //
    //        setChanged();
    //        notifyObservers();
    //
    //    }
}


