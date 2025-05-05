package ru.skypro.homework.dto;

public class ExtendedAd {

    private Integer pk;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private String image;
    private String phone;
    private Integer price;
    private String title;

    public ExtendedAd() {
    }

    public Integer getPk() {
        return pk;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }
}
