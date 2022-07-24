package com.spring.warehouse.repository;

import com.spring.warehouse.entity.ItemLocator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



/**
 * @author gopal_re
 */
public interface WarehouseRepository extends JpaRepository<ItemLocator, Long>, JpaSpecificationExecutor<ItemLocator> {

}
