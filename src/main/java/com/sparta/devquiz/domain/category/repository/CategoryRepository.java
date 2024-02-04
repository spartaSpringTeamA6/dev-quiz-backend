package com.sparta.devquiz.domain.category.repository;

import com.sparta.devquiz.domain.category.entity.Category;
import com.sparta.devquiz.domain.category.exception.CategoryCustomException;
import com.sparta.devquiz.domain.category.exception.CategoryExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
        Optional<Category> findById(Long categoryId);

        Optional<Category> findByCategoryTitle(String categoryTitle);

        default Category findByIdOrElseThrow(Long categoryId) {
                return findById(categoryId).orElseThrow(
                        () -> new CategoryCustomException(CategoryExceptionCode.NOT_FOUND_CATEGORY)
                );
        }

        default Category findByCategoryTitleOrElseThrow(String categoryTitle) {
                return findByCategoryTitle(categoryTitle).orElseThrow(
                        () -> new CategoryCustomException(CategoryExceptionCode.INVALID_CATEGORY_TITLE)
                );
        }
}
