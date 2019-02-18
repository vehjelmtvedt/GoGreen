package Backend.data;

import java.util.ArrayList;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;

import javax.websocket.RemoteEndpoint;

@SpringBootApplication
public class DBTest implements CommandLineRunner
{
    @Autowired
    private UserRepository users;

    @Autowired
    private MongoTemplate mongoTemplate;

    public static void main(String[] args)
    {
        SpringApplication.run(DBTest.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {
        users.deleteAll();
        users.save(new User("Jane", "Doe", 24, "testp@email.com", "test-pw432"));
        users.save(new User("Jane", "Doe", 24, "test2@email.com", "test-pw432"));
        users.save(new User("John", "Doe", 43, "testz@email.com", "test-pw111"));

        System.out.println("Print users:");
        System.out.println("--------------------");

        for (User user : users.findAll())
            System.out.println(user);

        System.out.println("Find user with single e-mail:");
        System.out.println("--------------------");
        System.out.println(users.findById("testp@email.com"));

        System.out.println("Lookup test");
        System.out.println("--------------------");

/*        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("users")
                .localField("email")
                .foreignField("email")
                .as("friends");

        Aggregation aggregation = Aggregation.newAggregation(lookupOperation);

        AggregationResults<User> results = mongoTemplate.aggregate(aggregation, User.class, User.class);

        for (User u : results.getMappedResults())
            System.out.println(u);*/
    }
}
