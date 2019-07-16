package com.reactive.msproducts.dtos;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SearchFiltersDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String customerId;

    private Integer limit;

    private Integer offset;

}
