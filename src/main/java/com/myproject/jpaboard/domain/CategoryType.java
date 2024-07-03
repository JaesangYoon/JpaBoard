package com.myproject.jpaboard.domain;

public enum CategoryType {
    FOOD("음식"),
    TRAVEL("여행"),
    DAILY("일상"),
    ADVERTISING("광고");

    private final String description;

    // Enum 생성자는 기본적으로 private
    CategoryType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

