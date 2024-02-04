package com.sparta.devquiz.domain.category.service;

import com.sparta.devquiz.domain.category.dto.request.CategoryCreateRequest;
import com.sparta.devquiz.domain.category.dto.response.CategoryGetResponse;
import com.sparta.devquiz.domain.category.entity.Category;
import com.sparta.devquiz.domain.category.exception.CategoryCustomException;
import com.sparta.devquiz.domain.category.exception.CategoryExceptionCode;
import com.sparta.devquiz.domain.category.repository.CategoryRepository;
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
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final QuizRepository quizRepository;

    @Transactional(readOnly = true)
    public List<CategoryGetResponse> getCategories() {
        return categoryRepository.findAll().stream()
            .map(category -> CategoryGetResponse.of(category, quizRepository.countByCategoryAndIsDeletedFalse(category)))
            .toList();
    }

    public void createCategory(CategoryCreateRequest request, User user) {
        verifyAdminRole(user);

        String categoryTitle = request.getCategoryTitle().toUpperCase();

        if (!EnumUtils.isValidEnum(com.sparta.devquiz.domain.category.enums.QuizCategory.class, categoryTitle)) {
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