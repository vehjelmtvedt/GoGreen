package tools;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DateUnitTest {
    @Test
    public void testTodayUnit() {
        DateUnit today = DateUnit.TODAY;

        Assert.assertEquals(0, today.getNumDays());
    }

    @Test
    public void testDayUnit() {
        DateUnit day = DateUnit.DAY;

        Assert.assertEquals(1, day.getNumDays());
    }

    @Test
    public void testWeekUnit() {
        DateUnit week = DateUnit.WEEK;

        Assert.assertEquals(7, week.getNumDays());
    }

    @Test
    public void testMonthUnit() {
        DateUnit month = DateUnit.MONTH;

        Assert.assertEquals(30, month.getNumDays());
    }

    @Test
    public void testYearUnit() {
        DateUnit year = DateUnit.YEAR;

        Assert.assertEquals(365, year.getNumDays());
    }
}