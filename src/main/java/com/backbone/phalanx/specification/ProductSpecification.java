package com.backbone.phalanx.specification;

import com.backbone.phalanx.product.dto.ProductFilterRequestDto;
import com.backbone.phalanx.product.model.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> filterBy(ProductFilterRequestDto filter) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filter.search() != null && !filter.search().trim().isEmpty()) {

                String pattern = "%" + filter.search().toLowerCase() + "%";

                predicates.add(
                        cb.or(
                                cb.like(cb.lower(root.get("name")), pattern),
                                cb.like(cb.lower(root.get("sku")), pattern),
                                cb.like(cb.lower(root.get("barcode")), pattern)
                        )
                );
            }

            if (filter.category() != null) {
                predicates.add(
                        cb.equal(root.get("category"), filter.category())
                );
            }

            if (filter.priceFrom() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("sellingPrice"), filter.priceFrom())
                );
            }

            if (filter.priceTo() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get("sellingPrice"), filter.priceTo())
                );
            }

            if (filter.stockBalanceFrom() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("stockBalance"), filter.stockBalanceFrom())
                );
            }

            if (filter.stockBalanceTo() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get("stockBalance"), filter.stockBalanceTo())
                );
            }

            if (filter.createdFrom() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("createdAt"), filter.createdFrom())
                );
            }

            if (filter.createdTo() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get("createdAt"), filter.createdTo())
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Sort getSort(String sortBy, String sortDirection) {
        if (sortBy == null || sortBy.isBlank()) {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }

        if (sortDirection == null || sortDirection.isBlank()) {
            return Sort.by(Sort.Direction.DESC, sortBy);
        }

        return Sort.by(
                sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy
        );
    }

}
