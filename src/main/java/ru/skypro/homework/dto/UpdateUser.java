package ru.skypro.homework.dto;

/**
 * DTO-класс для обновления персональной информации аутентифицированным пользователем.
 */
public class UpdateUser {

    private String firstName;
    private String lastName;
    private String phone;

    public UpdateUser() {
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
}
