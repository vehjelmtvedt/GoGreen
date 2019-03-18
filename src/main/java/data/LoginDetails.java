package data;

import org.springframework.data.annotation.Id;

public class LoginDetails {

    @Id
    private String identifier;

    private String password;

    public LoginDetails(String identifier,String password) {
        this.identifier = identifier;
        this.password = password;
    }

    public LoginDetails(){ }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getPassword() {
        return this.password;
    }

    public String toString() {
        return "Email: " + this.identifier + "\nPassword: " + this.password + "\n";
    }


}
