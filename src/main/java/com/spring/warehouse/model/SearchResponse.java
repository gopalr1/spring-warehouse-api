package com.spring.warehouse.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gopal_re
 */
@Getter
@Setter
public class SearchResponse {
    private List<Product> products = new ArrayList<>();
    private Long totalRecords;
    private Integer totalPages;
}
