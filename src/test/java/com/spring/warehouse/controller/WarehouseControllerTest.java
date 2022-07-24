package com.spring.warehouse.controller;

import com.spring.warehouse.common.Size;
import com.spring.warehouse.entity.Item;
import com.spring.warehouse.entity.ItemLocator;
import com.spring.warehouse.entity.Rack;
import com.spring.warehouse.model.Product;
import com.spring.warehouse.repository.RackRepository;
import com.spring.warehouse.repository.WarehouseRepository;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * @author gopal_re
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.main.allow-bean-definition-overriding=true")
public class WarehouseControllerTest {

    @MockBean
    WarehouseRepository warehouseRepository;

    @MockBean
    RackRepository rackRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testAddProductToInventory() throws JSONException {
        when(rackRepository.findById(anyLong())).thenReturn(Optional.of(createMockRackEntity(Size.MEDIUM, false)));
        when(warehouseRepository.save(ArgumentMatchers.any(ItemLocator.class))).thenReturn(createMockEntity());
        when(rackRepository.save(ArgumentMatchers.any(Rack.class)))
                .thenReturn(createMockRackEntity(Size.MEDIUM, true));
        HttpEntity<Product> httpEntity = new HttpEntity<>(createMockModel());
        ResponseEntity<String> response = restTemplate.postForEntity("/api/warehouse/product", httpEntity,
                String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JSONAssert.assertEquals("{\"id\":\"1\"}", response.getBody(), true);

    }


    @Test
    void testAddProductToInventoryRackAllocated() {
        when(rackRepository.findById(anyLong())).thenReturn(Optional.of(createMockRackEntity(Size.MEDIUM, true)));
        when(warehouseRepository.save(ArgumentMatchers.any(ItemLocator.class))).thenReturn(createMockEntity());
        when(rackRepository.save(ArgumentMatchers.any(Rack.class)))
                .thenReturn(createMockRackEntity(Size.MEDIUM, true));
        HttpEntity<Product> httpEntity = new HttpEntity<>(createMockModel());
        ResponseEntity<String> response = restTemplate.postForEntity("/api/warehouse/product", httpEntity,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody().contains("Rack is already allocated"), true);

    }

    @Test
    void testAddProductToInventoryInvalidRackSize() {
        when(rackRepository.findById(anyLong())).thenReturn(Optional.of(createMockRackEntity(Size.LARGE, false)));
        when(warehouseRepository.save(ArgumentMatchers.any(ItemLocator.class))).thenReturn(createMockEntity());
        when(rackRepository.save(ArgumentMatchers.any(Rack.class)))
                .thenReturn(createMockRackEntity(Size.MEDIUM, true));
        HttpEntity<Product> httpEntity = new HttpEntity<>(createMockModel());
        ResponseEntity<String> response = restTemplate.postForEntity("/api/warehouse/product", httpEntity,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody().contains("Please select correct rack size"), true);

    }

    @Test
    void testAddProductToInventoryRackNotFound() {
        when(rackRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(warehouseRepository.save(ArgumentMatchers.any(ItemLocator.class))).thenReturn(createMockEntity());
        when(rackRepository.save(ArgumentMatchers.any(Rack.class)))
                .thenReturn(createMockRackEntity(Size.MEDIUM, true));
        HttpEntity<Product> httpEntity = new HttpEntity<>(createMockModel());
        ResponseEntity<String> response = restTemplate.postForEntity("/api/warehouse/product", httpEntity,
                String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    void testCreateRack() {
        when(rackRepository.save(ArgumentMatchers.any(Rack.class))).thenReturn(createMockRackEntity(Size.MEDIUM, false));
        com.spring.warehouse.model.Rack rackModel = createMockRackModel();
        rackModel.setId(null);
        HttpEntity<com.spring.warehouse.model.Rack> httpEntity = new HttpEntity<>(rackModel);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/warehouse/rack", httpEntity,
                String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testCreateRackNullRack() {
        when(rackRepository.save(ArgumentMatchers.any(Rack.class))).thenReturn(createMockRackEntity(Size.MEDIUM, false));
        com.spring.warehouse.model.Rack rackModel = createMockRackModel();
        rackModel.setRackType(null);
        HttpEntity<com.spring.warehouse.model.Rack> httpEntity = new HttpEntity<>(rackModel);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/warehouse/rack", httpEntity,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testCreateRackInvalidWarehouseName() {
        when(rackRepository.save(ArgumentMatchers.any(Rack.class))).thenReturn(createMockRackEntity(Size.MEDIUM, false));
        com.spring.warehouse.model.Rack rackModel = createMockRackModel();
        rackModel.setWarehouseName("Ut");
        HttpEntity<com.spring.warehouse.model.Rack> httpEntity = new HttpEntity<>(rackModel);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/warehouse/rack", httpEntity,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    void testGetAllRacks() {
        when(rackRepository.findAllByIsAllocated(anyBoolean())).thenReturn(List.of(createMockRackEntity(Size.LARGE, false)));
        ResponseEntity<String> response = restTemplate.getForEntity("/api/warehouse/racks?allocated=true",
                String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllRacksWithoutParam() {
        when(rackRepository.findAllByIsAllocated(anyBoolean())).thenReturn(List.of());
        ResponseEntity<String> response = restTemplate.getForEntity("/api/warehouse/racks",
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testSearchProductByName() {

    }

    @Test
    public void testEmptySearchRecipeSuccess() {
        Page<ItemLocator> page = new PageImpl(List.of(createMockEntity()), PageRequest.of(0, 10), 1);
        when(warehouseRepository.findAll(ArgumentMatchers.any(Specification.class), eq(PageRequest.of(0, 10)))).thenReturn(page);
        ResponseEntity<String> response = restTemplate.getForEntity("/api/warehouse/search?productName=glass", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().contains("\"totalRecords\":1"));
    }

    @Test
    public void testSearchRecipeNoResult() {
        Page<ItemLocator> page = new PageImpl(List.of(), PageRequest.of(0, 10), 0);
        when(warehouseRepository.findAll(ArgumentMatchers.any(Specification.class), eq(PageRequest.of(0, 10)))).thenReturn(page);
        ResponseEntity<String> response = restTemplate.getForEntity("/api/warehouse/search", String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    private ItemLocator createMockEntity() {
        ItemLocator itemLocator = new ItemLocator();
        itemLocator.setId(1l);

        Item item = new Item();
        item.setId(1l);
        item.setProductName("Glass Bowl");
        item.setPackageSize(Size.MEDIUM);
        item.setOrderNumber(244557l);
        item.setQuantity(25l);
        item.setNotes("Handle carefully");
        item.setInTimestamp(LocalDateTime.now());
        item.setOutTimestamp(null);
        itemLocator.setItem(item);

        Rack rack = new Rack();
        rack.setId(1l);
        rack.setRackType(Size.MEDIUM);
        rack.setAllocated(true);
        rack.setWarehouseName("Utrecht-01");
        itemLocator.setRack(rack);

        return itemLocator;
    }

    private Product createMockModel() {
        Product product = new Product();
        product.setId(1l);
        product.setRackId(1l);
        product.setProductName("Glass Bowl");
        product.setOrderNumber(24455l);
        product.setWarehouseName("Utrecht-01");
        product.setQuantity(25l);
        product.setNotes("Handle carefully");
        product.setPackageSize(Size.MEDIUM);
        product.setInTimestamp(LocalDateTime.now());
        product.setOutTimestamp(null);
        return product;
    }

    private Rack createMockRackEntity(Size size, Boolean allocated) {
        Rack rack = new Rack();
        rack.setId(1l);
        rack.setRackType(size);
        rack.setAllocated(allocated);
        rack.setWarehouseName("Utrecht-01");
        return rack;
    }

    private com.spring.warehouse.model.Rack createMockRackModel() {
        com.spring.warehouse.model.Rack rack = new com.spring.warehouse.model.Rack();
        rack.setId(1l);
        rack.setRackType(Size.MEDIUM);
        rack.setWarehouseName("Utrecht-01");
        return rack;
    }

}
