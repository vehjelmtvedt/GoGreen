package tools;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    /**.
     * Constructs a DateUtils instance
     */
    public DateUtils() {

    }

    /**.
     * Returns the date of today
     * @return - Date corresponding to today's date
     */
    public static Date dateToday() {
        return Calendar.getInstance().getTime();
    }

    /**.
     * Return date which is x days ago the specified date
     * @param date - Date to use as reference
     * @param dateUnit - the date unit to use (DAY, WEEK, MONTH, YEAR)
     * @return - the new date with the current date and the difference applied
     */
    public static Date getDateBefore(Date date, DateUnit dateUnit) {
        // Get the time difference
        int diff = dateUnit.getNumDays();

        // Shift the date by the difference of days
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -diff);

        // Set time to midnight (to get the coverage of the entire day)
        setDateToMidnight(calendar);

        // Return new Date
        return calendar.getTime();
    }

    /**.
     * Helper method to set date's hour, minute, second to 0
     * @param calendar - calendar instance to modify
     */
    private static void setDateToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

    }
}