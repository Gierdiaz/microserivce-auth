package com.ead.authuser.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.User;
import com.ead.authuser.services.UserSerivce;
import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(originPatterns = "*", maxAge = 3600)
public class AuthenticationController {
    @Autowired
    private UserSerivce userService;

    @PostMapping("api/v1/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated @JsonView(UserDTO.UserView.RegistrationPost.class) UserDTO userDTO) {
        System.out.println("UserDTO received: " + userDTO);
        if (userService.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is already in use!");
        }

        var user = new User();

        BeanUtils.copyProperties(userDTO, user);
        user.setUserStatus(UserStatus.ACTIVE);
        user.setUserType(UserType.STUDENT); 
        user.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        user.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        userService.save(user);

        try {     
            userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: User could not be created.");
        }
    }
}
