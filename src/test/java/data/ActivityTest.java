package data;

import data.Activity;
import data.EatVegetarianMeal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tools.DateUnit;
import tools.DateUtils;

import java.util.*;
import java.util.stream.Collectors;

public class ActivityTest {
    private static ArrayList<Activity> activityList = new ArrayList<>();

    @Before
    public void setup() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.JANUARY, 1);

        for (int i = 0; i < 100; ++i) {
            activityList.add(new EatVegetarianMeal());
            activityList.get(i).setDate(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
            activityList.get(i).setCarbonSaved((i+1)*10);
        }

        // To assist in testing
        Collections.shuffle(activityList);
    }

    @Test
    public void testOriginalListUnaffected() {
        List<Activity> sortedList = Activity.sortByDate(activityList);
        Assert.assertNotEquals(activityList, sortedList);
    }

    @Test
    public void testSortByDate() {
        List<Activity> sortedList = Activity.sortByDate(activityList);

        // Construct expected result (dates sorted)
        List<Date> expected = activityList.stream().map(Activity::getDate)
                .sorted()
                .collect(Collectors.toList());

        // Construct actual resulting dates
        List<Date> result = sortedList.stream().map(Activity::getDate)
                .collect(Collectors.toList());

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testSortByCarbonSaved() {
        List<Activity> sortedList = Activity.sortByCarbonSaved(activityList);

        // Construct expected result (sorted by carbon saved)
        List<Double> expected = activityList.stream().map(Activity::getCarbonSaved)
                .sorted()
                .collect(Collectors.toList());

        // Construct actual resulting list sorted by carbon saved
        List<Double> result = sortedList.stream().map(Activity::getCarbonSaved)
                .collect(Collectors.toList());

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testSortByClass() {
        List<Activity> sortedList = Activity.sortByClass(activityList);
        List<Activity> expected = activityList.stream().sorted(Activity.getClassComparator()).collect(Collectors.toList());
        Assert.assertEquals(expected, sortedList);
    }

    @Test
    public void testSumNull() {
        Assert.assertEquals(0.0, Activity.getSum(null), 0);
    }

    @Test
    public void testSumEmpty() {
        Assert.assertEquals(0.0, Activity.getSum(null), 0);
    }

    @Test
    public void testActivitySum() {
        ArrayList<Activity> activities = new ArrayList<>();

        double sum = 0.0;
        for (int i = 0; i < 100; ++i) {
            Activity activity = new EatVegetarianMeal();

            double co2 = (i+1)*10;
            activity.setCarbonSaved(co2);

            activities.add(activity);
            sum += co2;
        }

        Assert.assertEquals(sum, Activity.getSum(activities), 0.1);
    }

    @Test
    public void testEqualsSameObject() {
        EatVegetarianMeal activity = new EatVegetarianMeal();

        Assert.assertEquals(activity, activity);
    }

    @Test
    public void testEqualsNull() {
        EatVegetarianMeal activity = new EatVegetarianMeal();

        Assert.assertNotEquals(activity, null);
    }

    @Test
    public void testEqualsDifferentClass() {
        EatVegetarianMeal activity = new EatVegetarianMeal();
        BuyLocallyProducedFood activity2 = new BuyLocallyProducedFood();

        Assert.assertNotEquals(activity, activity2);
    }

    @Test
    public void testEqualsSame() {
        Date date = DateUtils.dateToday();
        EatVegetarianMeal activity = new EatVegetarianMeal();
        activity.setDate(date);
        EatVegetarianMeal activity2 = new EatVegetarianMeal();
        activity2.setDate(date);

        Assert.assertEquals(activity, activity2);
    }

    @Test
    public void testEqualsDifferentDate() {
        EatVegetarianMeal activity = new EatVegetarianMeal();
        activity.setDate(DateUtils.getDateBefore(Calendar.getInstance().getTime(), DateUnit.MONTH));
        EatVegetarianMeal activity2 = new EatVegetarianMeal();

        Assert.assertNotEquals(activity, activity2);
    }

    @Test
    public void testEqualsDifferentCo2() {
        EatVegetarianMeal activity = new EatVegetarianMeal();
        activity.setCarbonSaved(15);
        EatVegetarianMeal activity2 = new EatVegetarianMeal();
        activity2.setCarbonSaved(10);

        Assert.assertNotEquals(activity, activity2);
    }
}
