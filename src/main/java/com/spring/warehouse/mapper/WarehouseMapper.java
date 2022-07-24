package com.spring.warehouse.mapper;

import com.spring.warehouse.entity.ItemLocator;
import com.spring.warehouse.model.Product;
import com.spring.warehouse.model.Rack;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author gopal_re
 */
@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    @InheritInverseConfiguration
    Product mapToModel(ItemLocator source);

    @Mapping(target = "rack.id", source = "rackId")
    @Mapping(target = "rack.warehouseName", source = "warehouseName")
    @Mapping(target = "item.orderNumber", source = "orderNumber")
    @Mapping(target = "item.productName", source = "productName")
    @Mapping(target = "item.quantity", source = "quantity")
    @Mapping(target = "item.packageSize", source = "packageSize")
    @Mapping(target = "item.notes", source = "notes")
    @Mapping(target = "item.inTimestamp", source = "inTimestamp")
    @Mapping(target = "item.outTimestamp", source = "outTimestamp")
    ItemLocator mapToEntity(Product source);

    Rack mapToModelRack(com.spring.warehouse.entity.Rack source);

    List<Rack> mapToModelRackList(List<com.spring.warehouse.entity.Rack> sourceList);

    com.spring.warehouse.entity.Rack mapToEntityRack(Rack source);

    List<Product> mapToModelList(List<ItemLocator> itemLocatorList);
}
