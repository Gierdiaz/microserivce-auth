package com.ead.authuser.services;

import com.ead.authuser.models.User;

import java.util.List;
import java.util.UUID;
import java.util.Optional;


public interface UserSerivce {

    List<User> findAll();

    Optional<User> FindById(UUID userId);

    void delete(User user);

}

