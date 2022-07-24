package com.spring.warehouse.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.warehouse.common.Size;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author gopal_re
 */
@Getter
@Setter
public class Product {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @Min(1)
    @Max(5000)
    private Long rackId;

    @NotBlank
    @javax.validation.constraints.Size(min = 3, max = 50)
    private String warehouseName;

    @Min(1)
    @Max(100000)
    private Long orderNumber;

    @NotBlank
    @javax.validation.constraints.Size(min = 3, max = 50)
    private String productName;

    @Min(1)
    @Max(10000)
    private Long quantity;

    @NotNull
    private Size packageSize;

    private String notes;

    private LocalDateTime inTimestamp;

    private LocalDateTime outTimestamp;
}
