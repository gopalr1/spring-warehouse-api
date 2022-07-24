package com.spring.warehouse.service;

import com.spring.warehouse.entity.ItemLocator;
import com.spring.warehouse.model.SearchRequest;
import org.springframework.data.domain.Page;

/**
 * @author gopal_re
 */
public interface WarehouseService {

    Page<ItemLocator> searchProductByName(SearchRequest searchRequest);

    ItemLocator addProductToInventory(ItemLocator itemLocator);


}
