package backend.data;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class User {

    @Id
    private String email;

    private String username;
    private String firstName;
    private String lastName;
    private int age;
    private String password;
    private ArrayList<String> friends;
    private ArrayList<String> friendRequests;

    /**
     * Constructor of User object.
     * @param firstName - first name of user.
     * @param lastName - last name of user.
     * @param age - age of user.
     * @param email - email of user.
     * @param password - user's password.
     */
    public User(String firstName, String lastName, int age, String email,
                String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.username = username;
        this.password = password;
        this.friends = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
    }

    public User() {
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

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getFriends() {
        return this.friends;
    }

    public ArrayList<String> getFriendRequests() {
        return this.friendRequests;
    }


    /**
     * Returns string representation of the User object.
     * @return String
     */
    public String toString() {
        StringBuilder userString = new StringBuilder();
        userString.append("First name: ").append(this.firstName).append('\n');
        userString.append("Last name: ").append(this.lastName).append('\n');
        userString.append("Age: ").append(this.age).append('\n');
        userString.append("Email: ").append(this.email).append('\n');
        userString.append("Username: ").append(this.username).append('\n');
        userString.append("Password: ").append(this.password).append('\n');

        userString.append("Friend emails: \n");
        for (String friendEmail : friends) {
            userString.append("-").append(friendEmail).append("\n");
        }


        return userString.toString();
    }

    /**
     * Adds a friend to friends list.
     *
     * @param friend - user to become friends with
     */
    public void addFriend(String friend) {
        friends.add(friend);
    }

    /**
     * Adds a friend request to friend request list.
     * @param username - username of the person who sent the request.
     */

    public void newFriendRequest(String username) {
        friendRequests.add(username);
    }

    /**
     * Delete a request from the friend request list.
     * @param username - username of the request to delete.
     */
    public void deleteFriendRequest(String username) {
        friendRequests.remove(username);
    }




    /*
     * Removes a friend from the friends list
     *
     * @param email - email of the user to not be friends with anymore
     * @return true if the user was successfully removed from
     * friends list (found & removed from the list)
     */
    //    public boolean removeFriend(String email) {
    //        return friends.remove(email);
    //    }
}
