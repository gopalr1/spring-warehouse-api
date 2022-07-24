package com.spring.warehouse.service;

import com.spring.warehouse.common.Size;
import com.spring.warehouse.entity.Rack;
import com.spring.warehouse.exception.ResourceNotFoundException;
import com.spring.warehouse.repository.RackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author gopal_re
 */
@ExtendWith(MockitoExtension.class)
public class RackServiceTest {

    @Mock
    RackRepository rackRepository;

    @InjectMocks
    RackServiceImpl rackService;

    @Test
    void testCreateOrUpdateRackSaveSuccess() {
        when(rackRepository.save(any(Rack.class)))
                .thenReturn(createMockRackEntity());
        Rack entity = createMockRackEntity();
        entity.setId(null);
        assertThat(1l, is(rackService.createOrUpdateRack(entity).getId()));

    }

    @Test
    void testCreateOrUpdateRackUpdateSuccess() {
        when(rackRepository.save(any(Rack.class)))
                .thenReturn(createMockRackEntity());
        assertThat(1l, is(rackService.createOrUpdateRack(createMockRackEntity()).getId()));

    }

    @Test
    void testFindByIdSuccess() {
        when(rackRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(createMockRackEntity()));
        assertThat(1l, is(rackService.findById(1l).getId()));
    }

    @Test
    void testFindByIdNotFound() {
        when(rackRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> rackService.findById(1l));
        assertTrue(exception.getMessage().contains("not found"));

    }

    @Test
    void testFindAll() {
        when(rackRepository.findAllByIsAllocated(Mockito.anyBoolean()))
                .thenReturn(List.of(createMockRackEntity()));
        assertThat(1, is(rackService.findAll(true).size()));

    }

    @Test
    void findAllNullAllocation() {
        when(rackRepository.findAllByIsAllocated(Mockito.anyBoolean()))
                .thenReturn(List.of());
        assertThat(0, is(rackService.findAll(true).size()));

    }

    private Rack createMockRackEntity() {
        Rack rack = new Rack();
        rack.setId(1l);
        rack.setRackType(Size.MEDIUM);
        rack.setAllocated(true);
        rack.setWarehouseName("Utrecht-01");
        return rack;
    }


}
