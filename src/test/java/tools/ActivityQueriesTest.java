package tools;

import data.*;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ActivityQueriesTest {
    User activeUser = new User("Active", "User", 20, "active_user@email.com", "active_user", "pwd123");

    ActivityQueries activityQuery = new ActivityQueries(activeUser.getActivities());
    Date today = Calendar.getInstance().getTime();


    @Test
    public void testConstructor() {
        ActivityQueries activityQueries = new ActivityQueries(activeUser.getActivities());

        Assert.assertEquals(activeUser.getActivities(), activityQueries.getActivities());
    }

    @Test
    public void testGetter() {
        Assert.assertEquals(activeUser.getActivities(), activityQuery.getActivities());
    }

    @Test
    public void testSetter() {
        ActivityQueries activityQueries = new ActivityQueries(activeUser.getActivities());
        activityQueries.setActivities(null);

        Assert.assertNull(activityQueries.getActivities());
    }

    @Test
    public void testFilterByCategory() {
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(new EatVegetarianMeal());
        activities.add(new EatVegetarianMeal());
        activities.add(new BuyLocallyProducedFood());
        activities.add(new BuyNonProcessedFood());
        activities.add(new EatVegetarianMeal());

        for (Activity a : activities)
            activeUser.addActivity(a);

        List<Activity> filteredActivities = activityQuery.filterActivities("Food");

        Assert.assertEquals(activities, filteredActivities);
    }

    @Test
    public void testFilterByCategories() {
        Activity a1 = new EatVegetarianMeal();
        Activity a2 = new EatVegetarianMeal();
        Activity a3 = new BuyOrganicFood();
        Activity a4 = new UseTrainInsteadOfCar();
        Activity a5 = new UseBikeInsteadOfCar();
        Activity a6 = new BuyNonProcessedFood();

        activeUser.addActivity(a1);
        activeUser.addActivity(a2);
        activeUser.addActivity(a3);
        activeUser.addActivity(a4);
        activeUser.addActivity(a5);
        activeUser.addActivity(a6);

        ArrayList<Activity> expected = new ArrayList<>();
        expected.add(a4);
        expected.add(a5);

        List<Activity> filteredActivities = activityQuery.filterActivities(a4.getCategory());

        Assert.assertEquals(expected, filteredActivities);
    }

    @Test
    public void testTotalCO2Saved() {
        double sum = 0.0;

        for (int i = 0; i < 10; ++i) {
            Activity activity = new EatVegetarianMeal();
            double CO2Saved = (i+1)*(i+1);
            activity.setCarbonSaved(CO2Saved);

            sum += CO2Saved;
            activeUser.addActivity(activity);
        }

        Assert.assertEquals(sum, activityQuery.getTotalCO2Saved(), 0.1);
    }

    private void addActivitiesToUser(User user, int diffFromToday, int entries, int dateDiff) {
        // Rewind Calendar by difference
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DATE, -diffFromToday);

        for (int i = 0; i < entries; ++i) {
            Activity activity = new EatVegetarianMeal();
            activity.setDate(calendar.getTime());
            activity.setCarbonSaved((i+1)*15);

            user.addActivity(activity);

            calendar.add(Calendar.DATE, dateDiff);
        }
    }

    private void addActivitiesToUserByCO2(User user, double CO2, int entries, int CO2diff) {
        for (int i = 0; i < entries; ++i) {
            Activity activity = new EatVegetarianMeal();
            activity.setCarbonSaved(CO2);
            user.addActivity(activity);
            CO2 += CO2diff;
        }
    }

    private ArrayList<Activity> getExpectedDateFilteredList(List<Activity> activityList, Date fromDate, Date toDate) {
        return activityList.stream()
                .filter((activity -> activity.getDate().equals(fromDate)
                        || activity.getDate().equals(toDate)
                        || (activity.getDate().before(toDate)
                        && activity.getDate().after(fromDate))))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Activity> getExpectedCO2FilteredList(List<Activity> activityList, double fromCO2,
                                                                  double toCO2) {
        return activityList.stream()
                .filter((activity -> activity.getCarbonSaved() >= fromCO2
                        && activity.getCarbonSaved() <= toCO2))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private double filteredByDateSum(List<Activity> activityList) {
        return activityList.stream()
                .mapToDouble(Activity::getCarbonSaved)
                .sum();
    }

    private Date getDateRewind(int diff) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DATE, -diff);
        return calendar.getTime();
    }

    @Test
    public void testFilterByDate() {
        Date fromDate = getDateRewind(20);

        addActivitiesToUser(activeUser, 40, 100, 1);
        ArrayList<Activity> expected = getExpectedDateFilteredList(activeUser.getActivities(), fromDate, today);

        Assert.assertEquals(expected, activityQuery.filterActivitiesByDate(fromDate, today));
    }

    @Test
    public void testFilterByDateNone() {
        Date fromDate = getDateRewind(20);

        addActivitiesToUser(activeUser, 200, 100, 1);
        ArrayList<Activity> expected = getExpectedDateFilteredList(activeUser.getActivities(), fromDate, today);

        Assert.assertEquals(expected, activityQuery.filterActivitiesByDate(fromDate, today));
    }

    @Test
    public void testFilterByDateOne() {
        addActivitiesToUser(activeUser, 0, 5, 1);
        Assert.assertEquals(1, activityQuery.filterActivitiesByDate(today, today).size());
    }

    @Test
    public void testFilterByDateOneFrom() {
        Date fromDate = DateUtils.instance.getDateBefore(Calendar.getInstance().getTime(), DateUnit.WEEK);
        addActivitiesToUser(activeUser, 0, 5, 1);
        Assert.assertEquals(1, activityQuery.filterActivitiesByDate(fromDate, today).size());
    }

    @Test
    public void testFilterByDateAllInRage() {
        Date fromDate = getDateRewind(10);

        addActivitiesToUser(activeUser, 7, 5, 1);
        ArrayList<Activity> expected = getExpectedDateFilteredList(activeUser.getActivities(), fromDate, today);

        Assert.assertEquals(expected, activityQuery.filterActivitiesByDate(fromDate, today));
    }

    @Test
    public void testFilterByDateDateUnit() {
        Date fromDate = getDateRewind(DateUnit.YEAR.getNumDays());

        addActivitiesToUser(activeUser, 365, 100, 1);
        ArrayList<Activity> expected = getExpectedDateFilteredList(activeUser.getActivities(), fromDate, today);

        Assert.assertEquals(expected, activityQuery.filterActivitiesByDate(DateUnit.YEAR));
    }

    @Test
    public void testFilterByDateNoActivities() {
        ArrayList<Activity> expected = new ArrayList<>();

        Assert.assertEquals(expected, activityQuery.filterActivitiesByDate(today, today));
    }

    @Test
    public void testGetTotalCO2SavedOverWeek() {
        Date fromDate = getDateRewind(DateUnit.WEEK.getNumDays());

        addActivitiesToUser(activeUser, 14, 21, 1);
        ArrayList<Activity> activityList = getExpectedDateFilteredList(activeUser.getActivities(), fromDate, today);

        double expected = filteredByDateSum(activityList);

        Assert.assertEquals(expected, activityQuery.getTotalCO2Saved(DateUnit.WEEK), 0.1);
    }

    @Test
    public void testGetTotalCO2SavedOverMonth() {
        Date fromDate = getDateRewind(DateUnit.MONTH.getNumDays());

        addActivitiesToUser(activeUser, 125, 500, 2);
        ArrayList<Activity> activityList = getExpectedDateFilteredList(activeUser.getActivities(), fromDate, today);

        double expected = filteredByDateSum(activityList);

        Assert.assertEquals(expected, activityQuery.getTotalCO2Saved(DateUnit.MONTH), 0.1);
    }

    @Test
    public void testGetTotalCO2SavedOverPeriod() {
        Date fromDate = getDateRewind(DateUnit.MONTH.getNumDays()*3);

        addActivitiesToUser(activeUser, 145, 600, 1);
        ArrayList<Activity> activityList = getExpectedDateFilteredList(activeUser.getActivities(), fromDate, today);

        double expected = filteredByDateSum(activityList);

        Assert.assertEquals(expected, activityQuery.getTotalCO2Saved(fromDate), 0.1);
    }

    @Test
    public void testFilterByDateDuplicates() {
        addActivitiesToUser(activeUser, 0, 243, 0);
        ArrayList<Activity> expected = getExpectedDateFilteredList(activeUser.getActivities(), today, today);

        Assert.assertEquals(expected, activityQuery.filterActivitiesByDate(today, today));
    }

    @Test
    public void testFilterByCO2Greater() {
        addActivitiesToUserByCO2(activeUser, 500, 100, 10);
        ArrayList<Activity> expected = getExpectedCO2FilteredList(activeUser.getActivities(), 500, 1500);

        Assert.assertEquals(expected, activityQuery.filterActivitiesByCO2Saved(500, true));
    }

    @Test
    public void testFilterByCO2Lesser() {
        addActivitiesToUserByCO2(activeUser, 50, 100, 10);
        ArrayList<Activity> expected = getExpectedCO2FilteredList(activeUser.getActivities(), 50, 100);

        Assert.assertEquals(expected, activityQuery.filterActivitiesByCO2Saved(100, false));
    }

    @Test
    public void testFilterByCO2AllEqual() {
        addActivitiesToUserByCO2(activeUser, 50, 1000, 0);

        Assert.assertEquals(1000, activityQuery.filterActivitiesByCO2Saved(50, false).size());
    }

    @Test
    public void testFilterByCO2EmptyLesser() {
        addActivitiesToUserByCO2(activeUser, 70, 100, 5);

        Assert.assertEquals(new ArrayList<Activity>(), activityQuery.filterActivitiesByCO2Saved(50, false));
    }

    @Test
    public void testFilterByCO2EmptyGreater() {
        addActivitiesToUserByCO2(activeUser, 10, 100, 5);

        Assert.assertEquals(new ArrayList<Activity>(), activityQuery.filterActivitiesByCO2Saved(1500, true));
    }

    @Test
    public void testFilterByCO2Range() {
        addActivitiesToUserByCO2(activeUser, 10, 500, 5);
        ArrayList<Activity> expected = getExpectedCO2FilteredList(activeUser.getActivities(), 25, 75);

        Assert.assertEquals(expected, activityQuery.filterActivitiesByCO2Saved(25, 75));
    }

    @Test
    public void testFilterByType() {
        for (int i = 0; i < 10; ++i) {
            activeUser.addActivity(new EatVegetarianMeal());
            activeUser.addActivity(new BuyNonProcessedFood());
            activeUser.addActivity(new BuyOrganicFood());
        }

        activeUser.addActivity(new BuyOrganicFood());

        Assert.assertEquals(11, activityQuery.filterActivitiesByType(BuyOrganicFood.class).size());
    }

    @Test
    public void testFilterByTypes() {
        for (int i = 0; i < 10; ++i) {
            activeUser.addActivity(new EatVegetarianMeal());
            activeUser.addActivity(new BuyNonProcessedFood());
            activeUser.addActivity(new BuyOrganicFood());
        }

        activeUser.addActivity(new BuyOrganicFood());

        ArrayList<Class> types = new ArrayList<>();
        types.add(EatVegetarianMeal.class);
        types.add(BuyNonProcessedFood.class);

        Assert.assertEquals(20, activityQuery.filterActivitiesByType(types).size());
    }

    @Test
    public void testGetWeeklyCO2SavingsDays() {
        // Construct an expected list of dates
        List<String> expected = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 7; ++i) {
            expected.add(DateUtils.instance.getDayName(calendar.getTime()));
            calendar.add(Calendar.DATE, -1);
        }

        // Reverse the list (since first day added was today's date)
        Collections.reverse(expected);

        // Get actual result
        ObservableList<XYChart.Data> list = activityQuery.getWeeklyCO2Savings();

        List<Object> days = list.stream().map(XYChart.Data::getXValue)
                .collect(Collectors.toList());

        Assert.assertEquals(expected, days);
    }

    @Test
    public void testGetWeeklyCO2SavingsCarbon() {
        List<Double> expected = new ArrayList<>();
        ArrayList<Activity> activities = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();

        for (int i = 1; i <= 7; ++i) {
            // Construct CO2Saved variable and add it to expected list of results
            double co2saved = (i*i)+5*i;
            expected.add(co2saved);

            // Create new activity and set it with appropriate date & CO2 Saved
            Activity activity = new EatVegetarianMeal();
            activity.setDate(calendar.getTime());
            activity.setCarbonSaved(co2saved);

            activities.add(activity);

            // Rewind 1 day back
            calendar.add(Calendar.DATE, -1);
        }

        // Reverse the expected & activities lists (since we keep rewinding days)
        Collections.reverse(expected);
        Collections.reverse(activities);

        // Load the activities to activityQuery
        activityQuery.setActivities(activities);

        // Get result
        ObservableList<XYChart.Data> list = activityQuery.getWeeklyCO2Savings();

        List<Object> savings = list.stream().map(XYChart.Data::getYValue)
                .collect(Collectors.toList());

        Assert.assertEquals(expected, savings);
    }
}