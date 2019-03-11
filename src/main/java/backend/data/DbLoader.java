package backend.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

import java.util.ArrayList;

@Configuration
public class DbLoader {
    @Autowired
    AchievementRepository achievementRepository;

    // Specify JSON to load here
    private static final Resource achievements = new ClassPathResource("data/achievements.json");
    private static final Resource[] resources = new Resource[] {achievements};

    @Bean
    public Jackson2RepositoryPopulatorFactoryBean populateDatabase() {
        // Wipe achievement repository to overwrite with new data
        achievementRepository.deleteAll();

        // Iniitalize populator factory bean
        Jackson2RepositoryPopulatorFactoryBean factory =
                new Jackson2RepositoryPopulatorFactoryBean();

        // Load specified resources
        factory.setResources(resources);

        // Return bean (this will automatically populate the database)
        return factory;
    }
}
