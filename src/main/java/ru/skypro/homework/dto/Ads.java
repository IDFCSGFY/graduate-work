package ru.skypro.homework.dto;

import java.util.List;

/**
 * DTO-класс для списка объявлений. Используется для вывода.
 *
 * @see Ad для DTO-класса единичного объявления.
 */
public class Ads {

    private Integer count;
    private List<Ad> results;

    public Ads() {
    }

    public Ads(Integer count, List<Ad> results) {
        this.count = count;
        this.results = results;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Ad> getResults() {
        return results;
    }

    public void setResults(List<Ad> results) {
        this.results = results;
    }
}
