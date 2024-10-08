package com.ead.authuser.dtos;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    public interface UserView {
        public static interface RegistrationPost {}
        public static interface UserPut {}
        public static interface PasswordPut {}
        public static interface ImagePut {}
    }

    @NotNull(message = "Username is required.", groups = UserView.RegistrationPost.class)
    @JsonView(UserView.RegistrationPost.class)
    private String username;

    @NotNull(message = "Full name is required.", groups = UserView.RegistrationPost.class)
    @Size(max = 150, message = "Full name can't exceed 150 characters.")
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String fullName;

    @NotNull(message = "Email is required.", groups = UserView.RegistrationPost.class)
    @Email(message = "Email should be valid.")
    @JsonView(UserView.RegistrationPost.class)
    private String email;

    @NotBlank(message = "Password is required.", groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
    @Size(min = 3, message = "Password must be at least 3 characters long.")
    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    private String password;

    @NotBlank(message = "Phone number is required.", groups = {UserView.RegistrationPost.class, UserView.UserPut.class})
    @Size(max = 20, message = "Phone number can't exceed 20 characters.")
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String phoneNumber;

    @NotBlank(message = "Cpf is required.", groups = {UserView.RegistrationPost.class, UserView.UserPut.class})
    @Size(min = 11, max = 11, message = "CPF must be 11 characters long.")
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String cpf;

    @NotBlank(message = "Image URL is required.", groups = UserView.ImagePut.class)
    @JsonView(UserView.ImagePut.class)
    private String imageUrl;
}
