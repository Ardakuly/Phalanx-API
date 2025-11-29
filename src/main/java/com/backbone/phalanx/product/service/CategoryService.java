package com.backbone.phalanx.product.service;

import com.backbone.phalanx.product.model.Category;

public interface CategoryService {

    Category createCategoryIfNotExists(String name);



}
