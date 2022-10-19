package com.hangaries.constants;

public enum OrderStatus {

    ACCEPTED("ACCEPTED"),
    SUBMITTED("SUBMITTED"),
    DELIVERED("DELIVERED"),
    PROCESSING("PROCESSING"),
    FOOD_READY("FOOD READY");
    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

}
