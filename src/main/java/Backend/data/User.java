package Backend.data;

import org.springframework.data.annotation.Id;

public class User {

    @Id
    private String email;

    private String firstName;
    private String lastName;
    private int age;
    private String password;

    public User(String firstName, String lastName, int age, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getAge() {
        return this.age;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String toString() {
        StringBuilder userString = new StringBuilder();
        userString.append("First name: " + this.firstName + '\n');
        userString.append("Last name: " + this.lastName + '\n');
        userString.append("Age: " + this.age + '\n');
        userString.append("Email: " + this.email + '\n');
        userString.append("Password: " + this.password + '\n');

        return userString.toString();
    }




}
