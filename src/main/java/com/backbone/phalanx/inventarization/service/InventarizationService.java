package com.backbone.phalanx.inventarization.service;

import com.backbone.phalanx.inventarization.dto.CountRequestDto;
import com.backbone.phalanx.inventarization.dto.InventarizationFilterRequestDto;
import com.backbone.phalanx.inventarization.dto.InventarizationFilterResponseDto;
import com.backbone.phalanx.inventarization.dto.InventarizationResponseDto;

public interface InventarizationService {

    /**
     * Retrieves a filtered and paginated list of inventarizations based on specified criteria.
     *
     * @param filter the filtering and pagination criteria
     * @return a wrapped response containing the current page's results and metadata
     */
    InventarizationFilterResponseDto getFiltered(InventarizationFilterRequestDto filter);

    /**
     * Start inventarization
     * 
     * @return inventarization response dto
     */
    InventarizationResponseDto start();

    /**
     * Count inventarization item
     * 
     * @param id         inventarization id
     * @param requestDto count request dto
     * @return inventarization response dto
     */
    InventarizationResponseDto count(Long id, CountRequestDto requestDto);

    /**
     * Complete inventarization
     * 
     * @param id inventarization id
     * @return inventarization response dto
     */
    InventarizationResponseDto complete(Long id);

    /**
     * Get inventarization by id
     * 
     * @param id inventarization id
     * @return inventarization response dto
     */
    InventarizationResponseDto getById(Long id);

    /**
     * Get active inventarization
     *
     * @return inventarization response dto
     */
    InventarizationResponseDto getActive();
}