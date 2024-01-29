package com.sparta.devquiz.domain.quiz.repository;

import com.sparta.devquiz.domain.quiz.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
