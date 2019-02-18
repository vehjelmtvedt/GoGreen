package Backend.data;

public class User {

    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String password;

    public User(String firstNameIn, String lastNameIn, int ageIn, String emailIn, String passwordIn) {
        this.firstName = firstNameIn;
        this.lastName = lastNameIn;
        this.age = ageIn;
        this.email = emailIn;
        this.password = passwordIn;
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
