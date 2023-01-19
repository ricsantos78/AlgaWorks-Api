package com.algafoodapiv2.infra.repository;


import com.algafoodapiv2.domain.model.RestaurantModel;
import com.algafoodapiv2.domain.repository.RestaurantInterfaceQueries;
import com.algafoodapiv2.domain.service.RestaurantService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.algafoodapiv2.infra.spec.RestaurantSpecs.restaurantByFreightFree;
import static com.algafoodapiv2.infra.spec.RestaurantSpecs.restaurantBySimilarName;

@Repository
public class RestaurantRepositoryImpl implements RestaurantInterfaceQueries {
    @PersistenceContext
    private EntityManager manager;

    @Lazy
    private RestaurantService restaurantService;

    @Override
    public List<RestaurantModel> find(String name,
                                      BigDecimal initialRate,
                                      BigDecimal finalRate){

        var builder = manager.getCriteriaBuilder();
        var criteriaQuery = builder.createQuery(RestaurantModel.class);
        var root = criteriaQuery.from(RestaurantModel.class);

        var predicates = new ArrayList<Predicate>();

        if(StringUtils.hasLength(name)){
            predicates.add(builder.like(root.get("name"), "%" + name + "%"));
        }
        if(initialRate != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("freight"), initialRate));
        }
        if(finalRate != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("freight"), finalRate));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        var query = manager
                .createQuery(criteriaQuery);
                return query.getResultList();
    }

    @Override
    public List<RestaurantModel> findByFreeFreight(String name) {
        return restaurantService.findAll(restaurantByFreightFree()
                .and(restaurantBySimilarName(name)));
    }
}
