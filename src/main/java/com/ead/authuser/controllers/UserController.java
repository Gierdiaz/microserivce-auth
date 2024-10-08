package com.ead.authuser.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.dtos.UserDTO.UserView;
import com.ead.authuser.models.User;
import com.ead.authuser.services.UserSerivce;
import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import java.util.Optional;

@RestController
@CrossOrigin(originPatterns = "*", maxAge = 3600)
public class UserController {
    @Autowired
    private UserSerivce userService;

    @GetMapping("api/v1/users")
    public ResponseEntity<Page<User>> getUsers(@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<User> userPage = userService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(userPage);
    }
    
    @GetMapping("api/v1/users/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable(value = "userId") UUID userId) {
        Optional<User> user = userService.FindById(userId);

        if(!user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user.get());
    }

    @PutMapping("api/v1/users/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId, @RequestBody @Validated @JsonView(UserView.UserPut.class) UserDTO userDTO) {
        Optional<User> user = userService.FindById(userId);

        if(user.isPresent()) {
            var userModel = user.get();
            userModel.setFullName(userDTO.getFullName());
            userModel.setPhoneNumber(userDTO.getPhoneNumber());
            userModel.setCpf(userDTO.getCpf());
            userModel.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

            userService.save(userModel);
            Map<String, Object> response = new HashMap<>();
            response.put("Message", "User updated successfully.");
            response.put("user", userModel);

            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

    @PutMapping("api/v1/users/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID userId, @RequestBody @Validated @JsonView(UserView.PasswordPut.class) UserDTO userDTO) {
        Optional<User> user = userService.FindById(userId);

        if(user.isPresent()) {
            var userModel = user.get();
            userModel.setPassword(userDTO.getPassword());
            userModel.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

            userService.save(userModel);

            Map<String, Object> response = new HashMap<>();
            response.put("Message", "Password updated successfully.");
            response.put("user", userModel);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password not found.");
    }

    @PutMapping("api/v1/users/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "userId") UUID userId, @RequestBody @Validated @JsonView(UserView.ImagePut.class) UserDTO userDTO) {
        Optional<User> user = userService.FindById(userId);

        if(user.isPresent()) {
            var userModel = user.get();
            userModel.setImageUrl(userDTO.getImageUrl());
            userModel.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

            userService.save(userModel);

            Map<String, Object> response = new HashMap<>();
            response.put("Message", "Image updated successfully.");
            response.put("user", userModel);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found.");
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
