package com.backbone.phalanx.inventarization.service;

import com.backbone.phalanx.inventarization.dto.CountRequestDto;
import com.backbone.phalanx.inventarization.dto.InventarizationResponseDto;

public interface InventarizationService {

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