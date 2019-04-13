package backend.repos;

import data.Achievement;
import org.springframework.data.mongodb.repository.MongoRepository;

@SuppressWarnings("all")
public interface AchievementRepository extends MongoRepository<Achievement, String> {
}
