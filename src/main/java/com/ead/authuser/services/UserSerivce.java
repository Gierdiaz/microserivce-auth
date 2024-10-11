package com.ead.authuser.services;

import com.ead.authuser.models.User;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;

import java.util.Optional;


public interface UserSerivce {

    List<User> findAll();

    Optional<User> FindById(UUID userId);

    void delete(User user);

    void save(User user);

    boolean existsByEmail(String email);

    Page<User> findAll(Specification<User> spec ,Pageable pageable);
  
}

