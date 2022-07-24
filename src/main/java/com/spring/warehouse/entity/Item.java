package com.spring.warehouse.entity;

import com.spring.warehouse.common.Size;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author gopal_re
 */
@Getter
@Setter
@Entity(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderNumber;
    private String productName;
    private Long quantity;
    @Enumerated(EnumType.STRING)
    private Size packageSize;
    private String notes;
    private LocalDateTime inTimestamp;
    private LocalDateTime outTimestamp;
}
