package ru.skypro.homework.dto;

/**
 * DTO-класс для редактирования объявления.
 *
 * @see Ad для DTO-класса единичного объявления.
 */
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
