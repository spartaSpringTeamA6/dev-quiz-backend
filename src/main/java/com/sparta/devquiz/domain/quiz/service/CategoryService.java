package com.sparta.devquiz.domain.quiz.service;

import com.sparta.devquiz.domain.quiz.dto.category.request.CategoryCreateRequest;
import com.sparta.devquiz.domain.quiz.dto.category.response.CategoryGetResponse;
import com.sparta.devquiz.domain.quiz.entity.Category;
import com.sparta.devquiz.domain.quiz.exception.CategoryCustomException;
import com.sparta.devquiz.domain.quiz.exception.CategoryExceptionCode;
import com.sparta.devquiz.domain.quiz.repository.CategoryRepository;
import com.sparta.devquiz.domain.quiz.repository.QuizRepository;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.enums.UserRole;
import com.sparta.devquiz.domain.user.exception.UserCustomException;
import com.sparta.devquiz.domain.user.exception.UserExceptionCode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final QuizRepository quizRepository;
    private com.sparta.devquiz.domain.quiz.enums.QuizCategory QuizCategory;

    @Transactional(readOnly = true)
    public List<CategoryGetResponse> getCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> {
                    Long quizCount = quizRepository.countByCategory(QuizCategory);
                    return CategoryGetResponse.builder()
                            .categoryId(category.getId())
                            .categoryTitle(category.getCategoryTitle())
                            .categoryDescription(category.getCategoryDescription())
                            .quizCount(quizCount)
                            .build();
                })
                .toList();
    }

    @Transactional
    public void createCategory(CategoryCreateRequest request, User user) {
        verifyAdminRole(user);

        String categoryTitle = request.getCategoryTitle().toUpperCase();

        if (!EnumUtils.isValidEnum(com.sparta.devquiz.domain.quiz.enums.QuizCategory.class, categoryTitle)) {
            throw new CategoryCustomException(CategoryExceptionCode.INVALID_CATEGORY_TITLE);
        }

        Category category = Category.builder()
                .categoryTitle(categoryTitle)
                .categoryDescription(request.getCategoryDescription())
                .build();
        categoryRepository.save(category);
    }

    private void verifyAdminRole(User user) {
        if (user == null || user.getRole() != UserRole.ROLE_ADMIN) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }
    }
}