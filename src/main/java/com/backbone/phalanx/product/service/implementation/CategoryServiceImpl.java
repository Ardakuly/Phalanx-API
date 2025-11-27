package com.backbone.phalanx.product.service.implementation;

import com.backbone.phalanx.exception.CategoryAlreadyExistsException;
import com.backbone.phalanx.product.model.Category;
import com.backbone.phalanx.product.repository.CategoryRepository;
import com.backbone.phalanx.product.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category createCategoryIfNotExists(String name) {

        log.info("Create category with name {}", name);

        Optional<Category> category = categoryRepository.findByNameIgnoreCase(name);

        return category.orElseGet(() -> categoryRepository.save(Category.builder().name(name).build()));

    }
}