package com.spring.warehouse.common;

import lombok.AllArgsConstructor;

/**
 * @author gopal_re
 */
@AllArgsConstructor
public enum Size {
    SMALL("Small"), MEDIUM("Medium"), LARGE("Large"), EXTRA_LARGE("Extra Large");
    private String type;

    public String getType() {
        return type;
    }
}
