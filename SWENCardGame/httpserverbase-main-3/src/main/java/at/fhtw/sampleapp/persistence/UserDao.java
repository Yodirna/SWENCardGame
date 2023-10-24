package at.fhtw.sampleapp.persistence;

import at.fhtw.sampleapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private List<User> userData;

    public UserDao() {
        userData = new ArrayList<>();
        // Sample data
        userData.add(new User(1, "Alice", "alice@example.com", "password123"));
        userData.add(new User(2, "Bob", "bob@example.com", "password456"));
        userData.add(new User(3, "Charlie", "charlie@example.com", "password789"));
    }

    // GET /user/:id
    public User getUser(Integer ID) {
        User foundUser = userData.stream()
                .filter(user -> ID.equals(user.getId()))
                .findAny()
                .orElse(null);

        return foundUser;
    }

    // GET /user
    public List<User> getUsers() {
        return userData;
    }

    // POST /user
    public void addUser(User user) {
        userData.add(user);
    }
}
