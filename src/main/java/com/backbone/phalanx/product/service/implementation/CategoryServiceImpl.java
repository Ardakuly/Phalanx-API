package com.backbone.phalanx.product.service.implementation;

import com.backbone.phalanx.exception.CategoryAlreadyExistsException;
import com.backbone.phalanx.product.model.Category;
import com.backbone.phalanx.product.repository.CategoryRepository;
import com.backbone.phalanx.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategoryIfNotExists(String name) {

        categoryRepository.findByNameIgnoreCase(name).orElseThrow(
                () -> new CategoryAlreadyExistsException(name)
        );

        return categoryRepository.save(Category.builder().name(name).build());
    }
}