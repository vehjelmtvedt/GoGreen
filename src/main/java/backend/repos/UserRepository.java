package backend.repos;

import data.User;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    public Optional<User> findByUsername(String username);
}
