package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class Login {

    private String password;
    private String username;

    public Login() {
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
