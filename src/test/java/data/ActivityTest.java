package data;

import data.Activity;
import data.EatVegetarianMeal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import tools.DateUnit;
import tools.DateUtils;
import tools.Requests;

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
        Date date = DateUtils.instance.dateToday();
        EatVegetarianMeal activity = new EatVegetarianMeal();
        activity.setDate(date);
        EatVegetarianMeal activity2 = new EatVegetarianMeal();
        activity2.setDate(date);

        Assert.assertEquals(activity, activity2);
    }

    @Test
    public void testEqualsDifferentDate() {
        EatVegetarianMeal activity = new EatVegetarianMeal();
        activity.setDate(DateUtils.instance.getDateBefore(Calendar.getInstance().getTime(), DateUnit.MONTH));
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

    @Test
    public void testPerformActivity() {
        // Flaw in this test: If no connection is made with the database,
        // the activity is still added to the User. Therefore this test tests for that.
        // But ideally, that should not be the case.
        
        User userOne = new User("Vetle", "Hjelmtvedt", 19,
                "vetle@hjelmtvedt.com","test", "password123");

        EatVegetarianMeal activity = new EatVegetarianMeal();
        activity.performActivity(userOne, Requests.instance);

        Assert.assertEquals(1, userOne.getActivities().size());
    }

    @Test
    public void testPerformActivityRequest() {
        User userOne = new User("Vetle", "Hjelmtvedt", 19,
                "vetle@hjelmtvedt.com","test", "password123");

        // Mock Requests class
        Requests mockRequests = Mockito.mock(Requests.class);

        // Create expected new User that is returned by request
        User newUser = new User(userOne.getFirstName(), userOne.getLastName(), userOne.getAge(),
                userOne.getEmail(), userOne.getUsername(), userOne.getPassword());

        // Create new activity and add it
        EatVegetarianMeal activity = new EatVegetarianMeal();
        newUser.addActivity(activity);

        // Mock mockRequests object to return updated user upon adding activity
        Mockito.when(mockRequests.addActivityRequest(activity, userOne.getUsername()))
                .thenReturn(newUser);

        // Perform the activity by the User
        activity.performActivity(userOne, mockRequests);

        // Check if userOne is updated accordingly
        Assert.assertEquals(activity, userOne.getActivities().get(0));
    }
}
