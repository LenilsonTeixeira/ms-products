package com.reactive.msproducts.enums;

import lombok.Getter;

@Getter
public enum ServerResponseEnum {

    ACCEPT_RANGE("accept-range"),
    CONTENT_RANGE("content-range"),
    ACCEPT_RANGE_DEFAULT_VALUE("100"),
    COUNT("count");

    private ServerResponseEnum(String attribute) {
        this.attribute = attribute;
    }

    private String attribute;
}
