package Backend;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RequestHandler {

    @RequestMapping("/greeting")
    public String respond() {
        return "Success!";
    }





}