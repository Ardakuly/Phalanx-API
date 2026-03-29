package com.backbone.phalanx.good_return_document.service;

import com.backbone.phalanx.good_return_document.model.ReturnedGood;
import java.math.BigDecimal;
import java.util.List;

public interface ReturnedGoodService {

    /**
     * Retrieves all returned goods associated with the specified outbound good ID.
     *
     * @param outboundGoodId the unique identifier of the outbound good whose
     *                       associated returned goods are to be retrieved
     * @return a list of returned goods linked to the specified outbound good ID
     */
    List<ReturnedGood> findAllByOutboundGoodId(Long outboundGoodId);

    /**
     * Retrieves the total quantity of goods that have already been returned for a
     * specific outbound good identified by its unique ID.
     *
     * @param outboundGoodId the unique identifier of the outbound good whose
     *                       returned quantity is to be retrieved
     * @return the total quantity of goods that have already been returned as a
     *         BigDecimal value
     */
    BigDecimal getAlreadyReturnedQuantity(Long outboundGoodId);
}
