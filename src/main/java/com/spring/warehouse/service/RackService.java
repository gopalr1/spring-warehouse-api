package com.spring.warehouse.service;

import com.spring.warehouse.entity.Rack;

import java.util.List;

/**
 * @author gopal_re
 */
public interface RackService {
    Rack createOrUpdateRack(Rack rack);

    Rack findById(Long id);

    List<Rack> findAll(Boolean allocated);
}
