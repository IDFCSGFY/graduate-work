package ru.skypro.homework.dto;

public class CreateOrUpdateAd {

    private String title;
    private Integer price;
    private String description;

    public CreateOrUpdateAd() {
    }

    public String getTitle() {
        return title;
    }

    public Integer getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
