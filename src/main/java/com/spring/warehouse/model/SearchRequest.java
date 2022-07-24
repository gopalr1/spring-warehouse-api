package com.spring.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author gopal_re
 */
@Getter
@AllArgsConstructor
public class SearchRequest {

    private String productName;
    private Integer pageNumber;
    private Integer noOfRecords;

}
