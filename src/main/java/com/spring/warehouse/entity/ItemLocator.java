package com.spring.warehouse.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author gopal_re
 */
@Getter
@Setter
@Entity(name = "item_locator")
public class ItemLocator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "rack_id", referencedColumnName = "id")
    private Rack rack;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;
}
