package ru.skypro.homework.dto;

/**
 * DTO-класс для редактирования комментария.
 *
 * @see Comment для DTO-класса единичного комментария.
 */
public class CreateOrUpdateComment {

    private String text;

    public CreateOrUpdateComment() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
