package Backend;

import Backend.data.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Server {
    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}
