package com.spring.warehouse.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.warehouse.common.Size;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * @author gopal_re
 */
@Getter
@Setter
public class Rack {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotNull
    private Size rackType;
    @NotBlank
    @javax.validation.constraints.Size(min = 3, max = 50)
    private String warehouseName;
}
