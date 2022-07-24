package com.spring.warehouse.service;


import com.spring.warehouse.common.Size;
import com.spring.warehouse.entity.Item;
import com.spring.warehouse.entity.ItemLocator;
import com.spring.warehouse.entity.Rack;
import com.spring.warehouse.model.SearchRequest;
import com.spring.warehouse.repository.WarehouseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author gopal_re
 */
@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {

    @Mock
    WarehouseRepository warehouseRepository;

    @InjectMocks
    WarehouseServiceImpl warehouseService;

    @Test
    void testAddProductToInventorySuccess() {
        when(warehouseRepository.save(any(ItemLocator.class)))
                .thenReturn(createMockEntity());
        ItemLocator itemLocator = createMockEntity();
        itemLocator.setId(null);
        assertThat(1l, equalTo(warehouseService.addProductToInventory(itemLocator).getId()));

    }

    @Test
    void testSearchRecipeWithSearchParam() {
        Page<ItemLocator> page = new PageImpl(List.of(createMockEntity()), PageRequest.of(0, 10), 1);

        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery criteriaQuery = mock(CriteriaQuery.class);
        Root<ItemLocator> root = mock(Root.class);
        Join<Object, Object> joinItem = mock(Join.class);
        Path<Object> productNamePath = mock(Path.class);

        when(root.join("item")).thenReturn(joinItem);
        when(joinItem.get("productName")).thenReturn(productNamePath);

        Predicate productNamePredicate = mock(Predicate.class);

        when(criteriaBuilder.like(criteriaBuilder.lower(root.join("item").get("productName")), "%glass%")).thenReturn(productNamePredicate);

        Predicate finalPredicate = mock(Predicate.class);

        when(criteriaBuilder.and(productNamePredicate)).thenReturn(finalPredicate);

        when(warehouseRepository.findAll(any(Specification.class), eq(PageRequest.of(0, 10)))).thenReturn(page);
        SearchRequest searchRequest = new SearchRequest("Glass", 0, 10);

        Page<ItemLocator> result = warehouseService.searchProductByName(searchRequest);

        assertEquals(1, result.get().count());

        ArgumentCaptor<Specification<ItemLocator>> argumentCaptor = ArgumentCaptor.forClass(Specification.class);
        verify(warehouseRepository).findAll(argumentCaptor.capture(), eq(PageRequest.of(0, 10)));
        Specification<ItemLocator> specificationValue = argumentCaptor.getValue();
        assertEquals(finalPredicate, specificationValue.toPredicate(root, criteriaQuery, criteriaBuilder));
    }


    @Test
    void testSearchRecipeNoFilterParam() {
        Page<ItemLocator> page = new PageImpl(List.of(createMockEntity()), PageRequest.of(0, 10), 1);

        when(warehouseRepository.findAll(any(Specification.class), eq(PageRequest.of(0, 10)))).thenReturn(page);
        SearchRequest searchRequest = new SearchRequest(null, 0, 10);
        Page<ItemLocator> result = warehouseService.searchProductByName(searchRequest);
        assertEquals(1, result.get().count());

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

}
