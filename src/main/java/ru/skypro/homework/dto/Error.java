package ru.skypro.homework.dto;

public class Error {

    private String code;
    private String message;

    public Error() {
    }

    public Error(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
