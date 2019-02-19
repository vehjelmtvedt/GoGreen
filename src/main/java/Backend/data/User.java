package Backend.data;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class User {

    @Id
    private String email;

    private String firstName;
    private String lastName;
    private int age;
    private String password;
    private ArrayList<String> friends;

    public User(String firstName, String lastName, int age, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.friends = new ArrayList<String>();
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

    public ArrayList<String> getFriends() { return this.friends; }

    public String toString() {
        StringBuilder userString = new StringBuilder();
        userString.append("First name: " + this.firstName + '\n');
        userString.append("Last name: " + this.lastName + '\n');
        userString.append("Age: " + this.age + '\n');
        userString.append("Email: " + this.email + '\n');
        userString.append("Password: " + this.password + '\n');

        userString.append("Friend emails: \n");
        for (String friendEmail : friends)
            userString.append("-" + friendEmail + "\n");

        return userString.toString();
    }

    /** Adds a friend to friends list
     * @param email - email of the user to become friends with
     */
    public void addFriend(String email)
    {
        friends.add(email);
    }

    /**
     * Removes a friend from the friends list
     * @param email - email of the user to unfriend
     * @return true if the user was successfully unfriended (found & removed from the list)
     */
    public boolean removeFriend(String email)
    {
        return friends.remove(email);
    }
}
