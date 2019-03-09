package backend.data;

import java.util.Date;

public abstract class Activity {
    private Date date;
    private int carbonSaved;
    

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCarbonSaved() {
        return carbonSaved;
    }

    public void setCarbonSaved(int carbonSaved) {
        this.carbonSaved = carbonSaved;
    }

    public abstract int dailylMeatAndDairyConsumption(User user);
}
