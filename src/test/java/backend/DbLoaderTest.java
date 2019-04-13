package backend;

import backend.repos.AchievementRepository;
import backend.repos.UserStatisticsRepository;
import data.UserStatistics;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class DbLoaderTest {
    @InjectMocks
    private
    DbLoader dbLoader = new DbLoader();

    @Mock
    AchievementRepository achievements;

    @Mock
    UserStatisticsRepository userStatistics;

    private final UserStatistics userStatisticsObj = new UserStatistics("all", 0, 0);
    private final Optional<UserStatistics> userStatisticsOptional = Optional.of(userStatisticsObj);

    @Before
    public void setup() {
        // Upon calling insert, update mock
        Mockito.when(userStatistics.insert(userStatisticsObj)).then(
                invocationOnMock -> {
                    // FindById "all" will now return a non-null value
                    Mockito.when(userStatistics.findById("all")).thenReturn(userStatisticsOptional);
                    return null;
                }
        );
    }

    @Test
    public void testStatisticsPresent() {
        UserStatistics userStatisticsAlt = new UserStatistics("all", 1, 1);
        Optional<UserStatistics> userStatisticsAltOptional = Optional.of(userStatisticsAlt);
        Mockito.when(userStatistics.findById("all")).thenReturn(userStatisticsAltOptional);

        dbLoader.populateDatabase();

        // If insert is called, then userStatistics.findById("all") is changed.
        // This ensures that it has not been changed
        Assert.assertNotEquals(userStatisticsOptional, userStatistics.findById("all"));
    }

    @Test
    public void testStatisticsNotPresent() {
        Optional<UserStatistics> userStatisticsOptional = Optional.of(userStatisticsObj);

        dbLoader.populateDatabase();

        Optional<UserStatistics> answer = userStatistics.findById("all");
        Assert.assertEquals(answer, userStatisticsOptional);
    }
}