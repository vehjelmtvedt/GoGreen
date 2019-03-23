package tools;

public enum DateUnit {
    TODAY(0),
    DAY(1),
    WEEK(7),
    MONTH(30),
    YEAR(365);

    private final int numDays;

    private DateUnit(int numDays) {
        this.numDays = numDays;
    }

    public int getNumDays() {
        return this.numDays;
    }
}
