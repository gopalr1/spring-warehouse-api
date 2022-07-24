package com.spring.warehouse.service;


import com.spring.warehouse.entity.ItemLocator;
import com.spring.warehouse.model.SearchRequest;
import com.spring.warehouse.repository.WarehouseRepository;
import com.spring.warehouse.util.SearchSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;


/**
 * @author gopal_re
 */
@Service("wareHouseService")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    @Override
    public ItemLocator addProductToInventory(ItemLocator itemLocator) {
        itemLocator.getItem().setInTimestamp(LocalDateTime.now());
        return warehouseRepository.save(itemLocator);
    }

    @Override
    public Page<ItemLocator> searchProductByName(SearchRequest searchRequest) {
        SearchSpecification specification = new SearchSpecification(searchRequest);
        return warehouseRepository.findAll(specification, PageRequest.of(searchRequest.getPageNumber() < 0 ? 0 : searchRequest.getPageNumber(), searchRequest.getNoOfRecords()));
    }

}
