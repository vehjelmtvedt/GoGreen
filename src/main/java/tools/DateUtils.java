package tools;

import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static DateUtils instance = new DateUtils();

    /**.
     * Constructs a DateUtils instance
     */
    protected DateUtils() {

    }

    /**.
     * Returns the date of today
     * @return - Date corresponding to today's date
     */
    public Date dateToday() {
        return Calendar.getInstance().getTime();
    }

    /**.
     * Return date which is x days ago the specified date
     * @param date - Date to use as reference
     * @param dateUnit - the date unit to use (DAY, WEEK, MONTH, YEAR)
     * @return - the new date with the current date and the difference applied
     */
    public Date getDateBefore(Date date, DateUnit dateUnit) {
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
     * Gets the name of the day of the week the specified Date is in (Monday, Tuesday, ..)
     * @param date - Date
     * @return - Day Of Week of the Date as String
     */
    public String getDayName(Date date) {
        // Set Calendar's date to the one specified
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }

        // Get DayOfWeek as String
        return DayOfWeek.of(dayOfWeek).toString();
    }

    /**.
     * Helper method to set date's hour, minute, second to 0
     * @param calendar - calendar instance to modify
     */
    public void setDateToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**.
     * Helper method set date's hour, minute, second to the very near end of the day
     * @param calendar - calendar instance to modify
     */
    public void setDateToEnd(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getMaximum(Calendar.MILLISECOND));
    }

    /**.
     * Returns true if specified date is between (inclusive) the
     * two specified dates.
     * @param date - Date to check
     * @param from - Starting date
     * @param to - End date
     * @return - True if date within range
     */
    public boolean checkDateInRange(Date date, Date from, Date to) {
        return (date.after(from) && date.before(to))
                || date.equals(from)
                || date.equals(to);
    }
}
