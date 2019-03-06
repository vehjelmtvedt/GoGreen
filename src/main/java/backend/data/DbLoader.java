package backend.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

@Configuration
public class DbLoader {

    // Specify JSON to load here
    private static final Resource[] resources = new Resource[] {};

    @Bean
    public Jackson2RepositoryPopulatorFactoryBean populateDatabase() {
        // Iniitalize populator factory bean
        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();

        // Load specified resources
        factory.setResources(resources);

        // Return bean (this will automatically populate the database)
        return factory;
    }
}
