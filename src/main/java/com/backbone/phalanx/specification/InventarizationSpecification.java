package com.backbone.phalanx.specification;

import com.backbone.phalanx.inventarization.dto.InventarizationFilterRequestDto;
import com.backbone.phalanx.inventarization.model.Inventarization;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class InventarizationSpecification {

    public static Specification<Inventarization> filterBy(InventarizationFilterRequestDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.status() != null) {
                predicates.add(cb.equal(root.get("status"), filter.status()));
            }

            if (filter.conductedBy() != null && !filter.conductedBy().trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("conductedBy")), "%" + filter.conductedBy().toLowerCase() + "%"));
            }

            if (filter.startedFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("startedAt"), filter.startedFrom()));
            }

            if (filter.startedTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("startedAt"), filter.startedTo()));
            }

            if (filter.completedFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("completedAt"), filter.completedFrom()));
            }

            if (filter.completedTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("completedAt"), filter.completedTo()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Sort getSort(String sortBy, String sortDirection) {
        if (sortBy == null || sortBy.isBlank()) {
            return Sort.by(Sort.Direction.DESC, "startedAt");
        }

        Sort.Direction direction = (sortDirection != null && sortDirection.equalsIgnoreCase("asc"))
                ? Sort.Direction.ASC : Sort.Direction.DESC;

        return Sort.by(direction, sortBy);
    }
}
