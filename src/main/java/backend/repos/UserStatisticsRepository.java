package backend.repos;

import data.UserStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

@SuppressWarnings("unused")
public interface UserStatisticsRepository extends MongoRepository<UserStatistics, String> {
}
