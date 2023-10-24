package at.fhtw.sampleapp.persistence.repository;

import at.fhtw.sampleapp.model.User;

import java.util.Collection;

public interface UserRepository {

    User findById(int id);
    Collection<User> findAllUsers();
    void addUser(User user);  // Added this method

}
