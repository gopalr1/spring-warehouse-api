package com.spring.warehouse.controller;

import com.spring.warehouse.common.ApplicationConstants;
import com.spring.warehouse.entity.ItemLocator;
import com.spring.warehouse.exception.WarehouseException;
import com.spring.warehouse.mapper.WarehouseMapper;
import com.spring.warehouse.model.Product;
import com.spring.warehouse.model.Rack;
import com.spring.warehouse.model.SearchRequest;
import com.spring.warehouse.model.SearchResponse;
import com.spring.warehouse.service.RackService;
import com.spring.warehouse.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author gopal_re
 */
@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/api/warehouse")
@CrossOrigin("*")
public class WarehouseController {

    private final WarehouseService warehouseService;

    private final RackService rackService;

    private final WarehouseMapper warehouseMapper;

    /**
     * This Controller method is to add product to warehouse inventory based on size and available racks
     *
     * @param product
     * @return
     */
    @PostMapping("/product")
    public ResponseEntity<Object> addProductToInventory(@Valid @RequestBody Product product) {

        com.spring.warehouse.entity.Rack rack = rackService.findById(product.getRackId());

        if (rack.getRackType() != product.getPackageSize()) {
            throw new WarehouseException(ApplicationConstants.RACK_SIZE_ERROR_MESSAGE);
        }

        if (rack.isAllocated()) {
            throw new WarehouseException(ApplicationConstants.RACK_ALLOCATED_ERROR_MESSAGE);
        }
        ItemLocator itemLocator = warehouseMapper.mapToEntity(product);

        Product productResponse = warehouseMapper.mapToModel(warehouseService.addProductToInventory(itemLocator));
        if (productResponse != null) {
            itemLocator.getRack().setAllocated(true);
            itemLocator.getRack().setRackType(itemLocator.getItem().getPackageSize());
            rackService.createOrUpdateRack(itemLocator.getRack());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("{\"id\":\"%s\"}", productResponse.getId()));
    }

    /**
     * This controller method is to create/add new racks with different sizes
     *
     * @param rack
     * @return
     */
    @PostMapping("/rack")
    public ResponseEntity<Object> createRack(@Valid @RequestBody Rack rack) {
        com.spring.warehouse.entity.Rack rackEntity = warehouseMapper.mapToEntityRack(rack);
        Rack rackResponse = warehouseMapper.mapToModelRack(rackService.createOrUpdateRack(rackEntity));
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("{\"id\":\"%s\"}", rackResponse.getId()));
    }

    /**
     * This controller is to list all the racks based on allocations
     * @param allocated
     * @return
     */
    @GetMapping("/racks")
    public ResponseEntity<Object> getAllRacks(@RequestParam Boolean allocated) {
        List<Rack> rackResponse = warehouseMapper.mapToModelRackList(rackService.findAll(allocated));
        return ResponseEntity.ok(rackResponse);
    }

    /**
     *
     * This controller is to search product by name to know the exact rack and warehouse location
     * @param productName
     * @param noOfRecords
     * @param page
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<SearchResponse> searchProductByName(@RequestParam String productName, @RequestParam(defaultValue = "10") String noOfRecords,
                                                              @RequestParam(defaultValue = "0") String page) {
        int start = NumberUtils.toInt(page.trim());
        int size = NumberUtils.toInt(noOfRecords.trim());

        SearchRequest searchRequest = new SearchRequest(productName, start, size);
        Page<com.spring.warehouse.entity.ItemLocator> pagedItemLocator = warehouseService.searchProductByName(searchRequest);
        List<Product> products = warehouseMapper.mapToModelList(pagedItemLocator.getContent());
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.getProducts().addAll(products);
        searchResponse.setTotalRecords(pagedItemLocator.getTotalElements());
        searchResponse.setTotalPages(pagedItemLocator.getTotalPages());
        return ResponseEntity.ok(searchResponse);
    }
}
