package ru.skypro.homework.dto;

/**
 * DTO-класс для получения информации для смены собственного пароля аутентифицированным пользователем.
 */
public class NewPassword {

    private String currentPassword;
    private String newPassword;

    public NewPassword() {
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
