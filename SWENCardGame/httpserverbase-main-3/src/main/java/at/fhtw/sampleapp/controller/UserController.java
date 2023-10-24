package at.fhtw.sampleapp.controller;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.RestController;
import at.fhtw.sampleapp.persistence.DatabaseManager;
import at.fhtw.sampleapp.persistence.repository.UserRepository;
import at.fhtw.sampleapp.persistence.repository.UserRepositoryImpl;
import at.fhtw.sampleapp.service.UserService;
import at.fhtw.sampleapp.persistence.UnitOfWork;

public class UserController implements RestController {
    private final UserService userService;

    public UserController() {
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            UserRepository userRepository = new UserRepositoryImpl(unitOfWork);
            this.userService = new UserService(userRepository);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize UserController", e);
        }
    }


    @Override
    public Response handleRequest(Request request) {
        if (request.getMethod() == Method.GET &&
                request.getPathParts().size() > 1) {
            return this.userService.getUser(request.getPathParts().get(1));
        } else if (request.getMethod() == Method.GET) {
            return this.userService.getAllUsers();
        } else if (request.getMethod() == Method.POST) {
            return this.userService.addUser(request);
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}
