package com.spring.warehouse.util;


import com.spring.warehouse.common.ApplicationConstants;
import com.spring.warehouse.entity.Item;
import com.spring.warehouse.entity.ItemLocator;
import com.spring.warehouse.model.SearchRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gopal_re
 */
@Slf4j
@AllArgsConstructor
public class SearchSpecification implements Specification<ItemLocator> {


    private final transient SearchRequest request;

    @Override
    public Predicate toPredicate(Root<ItemLocator> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        Join<ItemLocator, Item> joinItem = root.join(ApplicationConstants.ITEM);
        if (!StringUtils.isBlank(request.getProductName())) {
            predicates.add(cb.like(cb.lower(joinItem.get(ApplicationConstants.PRODUCT_NAME)), "%" + request.getProductName().toLowerCase() + "%"));
            query.orderBy(cb.asc(joinItem.get(ApplicationConstants.PRODUCT_NAME)));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
