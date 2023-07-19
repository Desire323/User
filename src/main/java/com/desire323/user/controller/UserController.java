package com.desire323.user.controller;

import com.desire323.user.DTO.AddUserRequest;
import com.desire323.user.DTO.AddUserResponse;
import com.desire323.user.DTO.UserLoginResponse;
import com.desire323.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserLoginResponse> getUserByEmail(@PathVariable("email") String email){
        UserLoginResponse response = userService.getByEmail(email).get();
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<AddUserResponse> addUser(@RequestBody AddUserRequest request) {
        AddUserResponse response = userService.addUser(request);
        return ResponseEntity.ok(response);
    }
}
