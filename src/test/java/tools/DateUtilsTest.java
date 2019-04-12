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
        long today2 = DateUtils.instance.dateToday().getTime();

        // Allow minor difference of 100ms
        Assert.assertEquals(today, today2, 100);
    }

    @Test
    public void testDateBefore() {
        // Current date
        Date today = DateUtils.instance.dateToday();

        // Set calendar 1 week back from today
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DATE, -7);

        // set to midnight
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date result = DateUtils.instance.getDateBefore(today, DateUnit.WEEK);

        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetDayName() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, 5);

        String expected = "THURSDAY";
        String result = DateUtils.instance.getDayName(calendar.getTime());

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetDayNameSunday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, 1);

        String expected = "SUNDAY";
        String result = DateUtils.instance.getDayName(calendar.getTime());

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testDateInRange() {
        Calendar calendar = Calendar.getInstance();
        Date date = DateUtils.instance.dateToday();
        calendar.setTime(date);

        calendar.add(Calendar.DATE, -1);
        Date from = calendar.getTime();

        calendar.add(Calendar.DATE, 3);
        Date to = calendar.getTime();

        Assert.assertTrue(DateUtils.instance.checkDateInRange(date, from, to));
    }

    @Test
    public void testDateNotInRange() {
        Calendar calendar = Calendar.getInstance();
        Date date = DateUtils.instance.dateToday();
        calendar.setTime(date);

        calendar.add(Calendar.DATE, 5);
        Date from = calendar.getTime();

        calendar.add(Calendar.DATE, 8);
        Date to = calendar.getTime();

        Assert.assertFalse(DateUtils.instance.checkDateInRange(date, from, to));
    }

    @Test
    public void testDateEquals1() {
        Calendar calendar = Calendar.getInstance();
        Date date = DateUtils.instance.dateToday();
        calendar.setTime(date);

        Date from = calendar.getTime();
        Date to = calendar.getTime();

        Assert.assertTrue(DateUtils.instance.checkDateInRange(date, from, to));
    }

    @Test
    public void testDateEquals2() {
        Calendar calendar = Calendar.getInstance();
        Date date = DateUtils.instance.dateToday();
        calendar.setTime(date);

        Date to = calendar.getTime();

        calendar.add(Calendar.DATE, -1);
        Date from = calendar.getTime();

        Assert.assertTrue(DateUtils.instance.checkDateInRange(date, from, to));
    }
}