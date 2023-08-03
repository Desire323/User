package com.desire323.user.service;

import com.desire323.user.DTO.AddUserRequest;
import com.desire323.user.DTO.AddUserResponse;
import com.desire323.user.DTO.CreatePersonDTO;
import com.desire323.user.DTO.UserLoginResponse;
import com.desire323.user.entity.Role;
import com.desire323.user.entity.User;
import com.desire323.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public UserService(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }
    public Optional<UserLoginResponse> getByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new UserLoginResponse(optionalUser.get()));
    }

    public AddUserResponse addUser(AddUserRequest request) {
        String email = request.getEmail();

        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(email);
        user.setPassword(request.getPassword());
        user.setRole(Role.USER);
        userRepository.save(user);
        Long id = userRepository.findByEmail(email).get().getId();
        CreatePersonDTO createPersonRequest = new CreatePersonDTO(id, request.getFirstname(), request.getLastname());
        restTemplate.postForObject(System.getenv("FRIENDS_SERVICE_URL") + "/persons", createPersonRequest, CreatePersonDTO.class);

        return new AddUserResponse(id, email);
    }


    public Optional<String> getFirstName(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        User user = optionalUser.get();
        return Optional.of(user.getFirstname());
    }

    public Optional<String> getFirstName(String email) {
        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        User user = optionalUser.get();
        return Optional.of(user.getFirstname());
    }

    public Optional<String> getLastName(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        User user = optionalUser.get();
        return Optional.of(user.getLastname());
    }

    public Optional<String> getLastName(String email) {
        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        User user = optionalUser.get();
        return Optional.of(user.getLastname());
    }

    public Optional<String> getFullName(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        User user = optionalUser.get();
        return Optional.of(user.getFirstname() + " " + user.getLastname());
    }

    public Optional<String> getFullName(String email) {
        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        User user = optionalUser.get();
        return Optional.of(user.getFirstname() + " " + user.getLastname());
    }

    public String getEmailFromToken(String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        String url = System.getenv("AUTHENTICATION_SERVICE_URL") + "/jwt/email/" + token;
        String email = restTemplate.getForObject(url, String.class);

        return email;
    }

    public String sayHello(String authorizationHeader) {
        String email = getEmailFromToken(authorizationHeader);
        User user = userRepository.findByEmail(email).get();
        return ("Hello from our API, " + user.getFirstname() + " " + user.getLastname() + "!");
    }
}
