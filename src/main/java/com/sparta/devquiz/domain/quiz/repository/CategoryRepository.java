package com.sparta.devquiz.domain.quiz.repository;

import com.sparta.devquiz.domain.quiz.entity.Category;
import com.sparta.devquiz.domain.quiz.enums.QuizCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
        Optional<Category> findById(Long categoryId);
        Optional<Category> findQuizByCategory(QuizCategory category);
}
