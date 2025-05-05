package ru.skypro.homework.dto;

import java.util.List;

public class Comments {

    private Integer count;
    private List<Comment> results;

    public Comments() {
    }

    public Integer getCount() {
        return count;
    }

    public List<Comment> getResults() {
        return results;
    }
}
