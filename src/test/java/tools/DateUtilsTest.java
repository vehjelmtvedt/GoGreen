package tools;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class DateUtilsTest {
    @Test
    public void testDateToday() {
        long today = Calendar.getInstance().getTime().getTime();
        long today2 = DateUtils.dateToday().getTime();

        // Allow minor difference of 100ms
        Assert.assertEquals(today, today2, 100);
    }

    @Test
    public void testDateBefore() {
        // Current date
        Date today = DateUtils.dateToday();

        // Set calendar 1 week back from today
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DATE, -7);

        // set to midnight
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date result = DateUtils.getDateBefore(today, DateUnit.WEEK);

        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetDayName() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, 5);

        String expected = "THURSDAY";
        String result = DateUtils.getDayName(calendar.getTime());

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetDayNameSunday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, 1);

        String expected = "SUNDAY";
        String result = DateUtils.getDayName(calendar.getTime());

        Assert.assertEquals(expected, result);
    }
}