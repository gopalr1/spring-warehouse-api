package com.spring.warehouse.entity;

import com.spring.warehouse.common.Size;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author gopal_re
 */
@Getter
@Setter
@Entity(name = "rack")
public class Rack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Size rackType;
    private boolean isAllocated;
    private String warehouseName;
//    @Version
//    private Long version;
}
