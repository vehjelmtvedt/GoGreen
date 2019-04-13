package backend.repos;

import data.User;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

@SuppressWarnings("all")
public interface UserRepository extends MongoRepository<User, String> {
    @SuppressWarnings("unused")
    Optional<User> findByUsername(String username);
}
