package com.backbone.phalanx.specification;

import com.backbone.phalanx.outbound_document.dto.OutboundDocumentFilterRequestDto;
import com.backbone.phalanx.outbound_document.model.OutboundDocument;
import com.backbone.phalanx.outbound_document.model.OutboundGood;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OutboundDocumentSpecification {

    public static Specification<OutboundDocument> filterBy(OutboundDocumentFilterRequestDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.search() != null && !filter.search().trim().isEmpty()) {
                String pattern = "%" + filter.search().toLowerCase() + "%";

                // Join with seller to search by email
                Predicate sellerPredicate = cb.like(cb.lower(root.get("seller").get("email")), pattern);

                // Search by document number
                Predicate docNumberPredicate = cb.like(cb.lower(root.get("documentNumber")), pattern);

                // Join with outboundGoods to search by product name
                Join<OutboundDocument, OutboundGood> goodsJoin = root.join("outboundGoods", JoinType.LEFT);
                Predicate productNamePredicate = cb.like(cb.lower(goodsJoin.get("name")), pattern);

                predicates.add(cb.or(sellerPredicate, docNumberPredicate, productNamePredicate));
                
                // Ensure distinct results if products are joined
                if (query != null) {
                    query.distinct(true);
                }
            }

            if (filter.paymentType() != null) {
                predicates.add(cb.equal(root.get("paymentType"), filter.paymentType()));
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
