package com.spring.warehouse.mapper;

import com.spring.warehouse.common.Size;
import com.spring.warehouse.entity.Item;
import com.spring.warehouse.entity.ItemLocator;
import com.spring.warehouse.entity.Rack;
import com.spring.warehouse.model.Product;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author gopal_re
 */
public class WarehouseMapperTest {
    @InjectMocks
    WarehouseMapper mapper = Mappers.getMapper(WarehouseMapper.class);

    @Test
    void testMapToEntity() {
        Product model = createMockModel();
        ItemLocator itemLocatorEntity = mapper.mapToEntity(model);
        assertThat(itemLocatorEntity.getId(), equalTo(model.getId()));
        assertThat(itemLocatorEntity.getRack().getId(), equalTo(model.getRackId()));
        assertThat(itemLocatorEntity.getItem().getOrderNumber(), equalTo(model.getOrderNumber()));
        assertThat(itemLocatorEntity.getItem().getQuantity(), equalTo(model.getQuantity()));
        assertThat(itemLocatorEntity.getItem().getPackageSize(), equalTo(model.getPackageSize()));
        assertThat(itemLocatorEntity.getItem().getProductName(), equalTo(model.getProductName()));
        assertThat(itemLocatorEntity.getItem().getNotes(), equalTo(model.getNotes()));
        assertThat(itemLocatorEntity.getItem().getInTimestamp(), equalTo(model.getInTimestamp()));
        assertThat(itemLocatorEntity.getRack().getWarehouseName(), equalTo(model.getWarehouseName()));

    }

    @Test
    void testMapToModel() {
        ItemLocator entity = createMockEntity();
        Product model = mapper.mapToModel(entity);
        assertThat(model.getId(), equalTo(entity.getId()));
        assertThat(model.getRackId(), equalTo(entity.getRack().getId()));
        assertThat(model.getOrderNumber(), equalTo(entity.getItem().getOrderNumber()));
        assertThat(model.getQuantity(), equalTo(entity.getItem().getQuantity()));
        assertThat(model.getPackageSize(), equalTo(entity.getItem().getPackageSize()));
        assertThat(model.getProductName(), equalTo(entity.getItem().getProductName()));
        assertThat(model.getNotes(), equalTo(entity.getItem().getNotes()));
        assertThat(model.getInTimestamp(), equalTo(entity.getItem().getInTimestamp()));
        assertThat(model.getWarehouseName(), equalTo(entity.getRack().getWarehouseName()));

    }

    @Test
    void testToMapToModalList() {
        List<ItemLocator> entity = List.of(createMockEntity());
        List<Product> recipeList = mapper.mapToModelList(entity);
        assertThat(entity.size(), equalTo(recipeList.size()));
    }

    @Test
    void testMapToRackEntity() {
        com.spring.warehouse.model.Rack model = createMockRackModel();
        Rack rackEntity = mapper.mapToEntityRack(model);
        assertThat(rackEntity.getId(), equalTo(model.getId()));
        assertThat(rackEntity.getRackType(), equalTo(model.getRackType()));
        assertThat(rackEntity.getWarehouseName(), equalTo(model.getWarehouseName()));

    }

    @Test
    void testMapToRackModel() {
        Rack entity = createMockRackEntity();
        com.spring.warehouse.model.Rack model = mapper.mapToModelRack(entity);
        assertThat(model.getId(), equalTo(entity.getId()));
        assertThat(model.getRackType(), equalTo(entity.getRackType()));
        assertThat(model.getWarehouseName(), equalTo(entity.getWarehouseName()));

    }

    @Test
    void testToMapToRackModalList() {
        List<Rack> entity = List.of(createMockRackEntity());
        List<com.spring.warehouse.model.Rack> modelList = mapper.mapToModelRackList(entity);
        assertThat(entity.size(), equalTo(modelList.size()));
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
        product.setOrderNumber(244557l);
        product.setWarehouseName("Utrecht-01");
        product.setQuantity(25l);
        product.setNotes("Handle carefully");
        product.setPackageSize(Size.MEDIUM);
        product.setInTimestamp(LocalDateTime.now());
        product.setOutTimestamp(null);
        return product;
    }

    private Rack createMockRackEntity() {
        Rack rack = new Rack();
        rack.setId(1l);
        rack.setRackType(Size.MEDIUM);
        rack.setAllocated(true);
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
