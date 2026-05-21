package com.backbone.phalanx.specification;

import com.backbone.phalanx.inbound_document.dto.InboundDocumentFilterRequestDto;
import com.backbone.phalanx.inbound_document.model.InboundDocument;
import com.backbone.phalanx.inbound_document.model.InboundGood;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class InboundDocumentSpecification {

    public static Specification<InboundDocument> filterBy(InboundDocumentFilterRequestDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.search() != null && !filter.search().trim().isEmpty()) {
                String pattern = "%" + filter.search().toLowerCase() + "%";

                // Search by document number
                Predicate docNumberPredicate = cb.like(cb.lower(root.get("documentNumber")), pattern);

                // Search by external ID
                Predicate externalIdPredicate = cb.like(cb.lower(root.get("externalId")), pattern);

                // Join with inboundGoods to search by product details
                Join<InboundDocument, InboundGood> goodsJoin = root.join("inboundGoods", JoinType.LEFT);
                Predicate productNamePredicate = cb.like(cb.lower(goodsJoin.get("name")), pattern);
                Predicate skuPredicate = cb.like(cb.lower(goodsJoin.get("sku")), pattern);
                Predicate barcodePredicate = cb.like(cb.lower(goodsJoin.get("barcode")), pattern);

                predicates.add(cb.or(
                        docNumberPredicate,
                        externalIdPredicate,
                        productNamePredicate,
                        skuPredicate,
                        barcodePredicate
                ));

                // Ensure distinct results if products are joined
                if (query != null) {
                    query.distinct(true);
                }
            }

            if (filter.createdFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), filter.createdFrom()));
            }

            if (filter.createdTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), filter.createdTo()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Sort getSort(String sortBy, String sortDirection) {
        if (sortBy == null || sortBy.isBlank()) {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }

        Sort.Direction direction = (sortDirection != null && sortDirection.equalsIgnoreCase("asc"))
                ? Sort.Direction.ASC : Sort.Direction.DESC;

        return Sort.by(direction, sortBy);
    }
}
