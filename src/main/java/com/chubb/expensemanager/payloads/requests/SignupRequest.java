package com.chubb.expensemanager.payloads.requests;

import java.util.Set;

import com.chubb.expensemanager.models.Team;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    @Email
    private String email;
    private Set<String> roles;
    private String password;
    private Team team;
}
