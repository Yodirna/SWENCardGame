package at.fhtw.sampleapp.service;

import at.fhtw.sampleapp.model.User;
import at.fhtw.sampleapp.persistence.repository.UserRepository;
import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.List;

public class UserService extends AbstractService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.objectMapper = new ObjectMapper();  // Used for JSON serialization
    }

    // GET /user/:id
    public Response getUser(String id) {
        try {
            User user = userRepository.findById(Integer.parseInt(id));
            if (user != null) {
                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        objectMapper.writeValueAsString(user)
                );
            } else {
                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        "{\"message\":\"User not found\"}"
                );
            }
        } catch (Exception e) {
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{\"message\":\"Internal server error\"}"
            );
        }
    }

    // GET /user
    public Response getAllUsers() {
        try {
            Collection<User> users = userRepository.findAllUsers();
            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    objectMapper.writeValueAsString(users)
            );
        } catch (Exception e) {
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{\"message\":\"Internal server error\"}"
            );
        }
    }

    // POST /user
    public Response addUser(Request request) {
        try {
            // Deserialize request body into a User object
            User user = objectMapper.readValue(request.getBody(), User.class);

            // Save user to the database (assuming UserRepository has an 'add' method)
            userRepository.addUser(user);

            return new Response(
                    HttpStatus.CREATED,
                    ContentType.JSON,
                    "{\"message\":\"User added successfully\"}"
            );
        } catch (Exception e) {
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{\"message\":\"Internal server error\"}"
            );
        }
    }
}
