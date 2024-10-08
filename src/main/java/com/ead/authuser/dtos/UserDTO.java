package com.ead.authuser.dtos;

import lombok.Data;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private UUID userId;
    private String username;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;
}
