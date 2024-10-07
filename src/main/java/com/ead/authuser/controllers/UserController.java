package com.ead.authuser.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ead.authuser.models.User;
import com.ead.authuser.services.UserSerivce;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

@RestController
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", maxAge = 3600)
public class UserController {
    @Autowired
    private UserSerivce userService;

    @GetMapping("api/v1/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }
    
    @GetMapping("api/v1/users/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable(value = "userId") UUID userId) {
        Optional<User> user = userService.FindById(userId);

        if(!user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user.get());
    }

    @DeleteMapping("api/v1/users/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId) {
        Optional<User> user = userService.FindById(userId);

        if(!user.isEmpty()) {
            userService.delete(user.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(user.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user.get());
    }
    
}
