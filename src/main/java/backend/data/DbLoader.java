package backend.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

@Configuration
public class DbLoader {
    private Resource achievements = new PathResource("src/main/Resources/data/achievements.json");

    @Autowired
    private AchievementRepository achievementRepository;

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

        // Iniitalize populator factory bean
        Jackson2RepositoryPopulatorFactoryBean factory =
                new Jackson2RepositoryPopulatorFactoryBean();

        // Load specified resources
        Resource[] resources = new Resource[] {achievements};
        factory.setResources(resources);

        // Return bean (this will automatically populate the database)
        return factory;
    }
}
