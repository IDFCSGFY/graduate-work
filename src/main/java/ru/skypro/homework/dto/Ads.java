package ru.skypro.homework.dto;

import java.util.List;

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

    public List<Ad> getResults() {
        return results;
    }
}
