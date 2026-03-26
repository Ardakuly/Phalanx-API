package com.backbone.phalanx.inventarization.service;

import com.backbone.phalanx.inventarization.model.InventarizationItem;

import java.util.List;
import java.util.Optional;

public interface InventarizationItemService {

    /**
     * Save all inventarization items
     * 
     * @param items list of inventarization items
     * @return list of inventarization items
     */
    List<InventarizationItem> saveAll(List<InventarizationItem> items);

    /**
     * Save inventarization item
     * 
     * @param item inventarization item
     * @return inventarization item
     */
    InventarizationItem save(InventarizationItem item);

    /**
     * Find inventarization item by inventarization id and product id
     * 
     * @param inventarizationId inventarization id
     * @param productId         product id
     * @return optional of inventarization item
     */
    Optional<InventarizationItem> findByInventarizationIdAndProductId(Long inventarizationId, Long productId);

    /**
     * Find inventarization items by inventarization id
     * 
     * @param inventarizationId inventarization id
     * @return list of inventarization items
     */
    List<InventarizationItem> findByInventarizationId(Long inventarizationId);
}