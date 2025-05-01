package ru.skypro.homework.dto;

public class ApiResponse {

    private Integer code;
    private String type;
    private String message;

    public ApiResponse() {
    }

    public ApiResponse(Integer code, String type, String message) {
        this.code = code;
        this.type = type;
        this.message = message;
    }
}
