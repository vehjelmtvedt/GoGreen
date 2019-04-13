package backend;

import backend.repos.AchievementRepository;
import backend.repos.UserStatisticsRepository;
import data.UserStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

@Configuration
class DbLoader {
    // Specify JSON to load here
    private final Resource achievements = new ClassPathResource("data/achievements.json");

    private final Resource[] resources = new Resource[] {achievements};

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private UserStatisticsRepository userStatisticsRepository;

    /**.
     * Populator bean for the database.
     * First wipes all the data and reloads it from JSON files.
     * Currently loads only achievements into the achievement repository.
     * @return Populator bean for the database
     */
    @Bean
    public Jackson2RepositoryPopulatorFactoryBean populateDatabase() {
        // Wipe achievement repository to overwrite with new data
        achievementRepository.deleteAll();

        if (!userStatisticsRepository.findById("all").isPresent()) {
            UserStatistics allStats = new UserStatistics("all", 0, 0);
            userStatisticsRepository.insert(allStats);
        }

        // Initialise populator factory bean
        Jackson2RepositoryPopulatorFactoryBean factory =
                new Jackson2RepositoryPopulatorFactoryBean();

        // Load specified resources
        factory.setResources(resources);

        // Return bean (this will automatically populate the database)
        return factory;
    }
}
