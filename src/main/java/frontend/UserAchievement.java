package frontend;

import java.util.Date;

public class UserAchievement {

    public int id;
    boolean completed;
    Date date;

    public UserAchievement(int id, boolean completed, Date date) {
        this.id = id;
        this.completed = completed;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
