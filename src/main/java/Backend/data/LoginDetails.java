package Backend.data;

import org.springframework.data.annotation.Id;

public class LoginDetails {

    @Id
    private String email;

    private String password;

    public LoginDetails(String email,String password){
        this.email = email;
        this.password = password;
    }

    public LoginDetails(){ }
    public String getEmail(){return this.email;}

    public String getPassword(){return this.password;}

    public String toString(){return "Email: "+this.email+"\nPassword: "+this.password+"\n";}


}
