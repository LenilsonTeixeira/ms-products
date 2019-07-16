package com.reactive.msproducts.enums;

import lombok.Getter;

@Getter
public enum ServerRequestEnum {
    HEADER_CUSTOMER_ID("customerId"),
    START_DATE("startDate"),
    END_DATE("endDate"),
    ORIGIN("origin"),
    LIMIT("_limit"),
    LIMIT_DEFAULT("5"),
    POINT_OF_ACTION_NAME("pointOfActionName"),
    POINT_OF_ACTION("pointOfAction"),
    ACTION_TYPE("actionType"),
    RESPONSIBLE("responsible"),
    OFFSET("_offset"),
    LIMIT_DEFAULT_VALUE("10"),
    OFFSET_DEFAULT_VALUE("0"),
    STATUS("STATUS");

    ServerRequestEnum(String attribute) {
        this.attribute = attribute;
    }

    private String attribute;

}
