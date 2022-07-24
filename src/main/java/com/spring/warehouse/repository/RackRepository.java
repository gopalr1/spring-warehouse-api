package com.spring.warehouse.repository;

import com.spring.warehouse.entity.ItemLocator;
import com.spring.warehouse.entity.Rack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author gopal_re
 */
public interface RackRepository extends JpaRepository<Rack, Long>{
    List<Rack> findAllByIsAllocated(Boolean allocated);
}
