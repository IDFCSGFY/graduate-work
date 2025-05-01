package ru.skypro.homework.dto;

public class Tag {

    private Long id;
    private String name;

    public Tag() {
    }

    public Tag(String name, Long id) {
        this.name = name;
        this.id = id;
    }
}
