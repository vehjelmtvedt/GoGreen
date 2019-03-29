package backend.repos;

import data.UserStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserStatisticsRepository extends MongoRepository<UserStatistics, String> {
}
