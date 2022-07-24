package com.spring.warehouse.service;

import com.spring.warehouse.common.ApplicationConstants;
import com.spring.warehouse.entity.Rack;
import com.spring.warehouse.exception.ResourceNotFoundException;
import com.spring.warehouse.repository.RackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author gopal_re
 */
@Service("rackService")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional
public class RackServiceImpl implements RackService {

    private final RackRepository rackRepository;

    @Override
    public Rack createOrUpdateRack(Rack rack) {
        if (rack.getId() == null) {
            rack.setAllocated(false);
        }
        return rackRepository.save(rack);
    }

    @Override
    public Rack findById(Long id) {
        Optional<Rack> rackOptional = rackRepository.findById(id);
        Rack rack = rackOptional.orElseThrow(() -> new ResourceNotFoundException(ApplicationConstants.RACK_ID_NOT_FOUND));
        return rack;
    }

    @Override
    public List<Rack> findAll(Boolean allocated) {
        return rackRepository.findAllByIsAllocated(allocated);
    }
}
