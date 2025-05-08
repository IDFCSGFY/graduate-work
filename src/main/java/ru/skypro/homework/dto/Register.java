package ru.skypro.homework.dto;

import lombok.Data;

/**
 * DTO-класс для получения информации для создания нового пользователя на странице для регистрации.
 */
@Data
public class Register {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;

    public Register() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public Role getRole() {
        return role;
    }
}
