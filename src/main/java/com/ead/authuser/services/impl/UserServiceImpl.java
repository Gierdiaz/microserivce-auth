package com.ead.authuser.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserSerivce;

@Service
public class UserServiceImpl implements UserSerivce {
    @Autowired
    private UserRepository userRepository;	
}
