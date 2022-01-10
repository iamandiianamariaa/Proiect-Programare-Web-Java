package com.example.shop.exception;

import lombok.Builder;

@Builder
public class ProductNotFoundException extends RuntimeException {

    private String searchField;
    private String name;

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
