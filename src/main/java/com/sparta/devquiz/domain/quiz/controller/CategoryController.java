package com.sparta.devquiz.domain.quiz.controller;

import com.sparta.devquiz.domain.quiz.dto.category.response.CategoryGetCustomResponse;
import com.sparta.devquiz.domain.quiz.dto.category.response.CategoryGetResponse;
import com.sparta.devquiz.domain.quiz.response.CategoryResponseCode;
import com.sparta.devquiz.domain.quiz.service.CategoryService;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.global.annotation.AuthUser;
import com.sparta.devquiz.global.response.CommonResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
@Tag(name = "6. Category API", description = "Category 관련 API 입니다.")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(operationId = "CATEGORY-001", summary = "카테고리 조회")
    @GetMapping
    public ResponseEntity<CommonResponseDto> getCategories(
            @AuthUser User user,
            @RequestParam int page
    ) {
        List<CategoryGetResponse> categoryResponseList = categoryService.getCategories(user, page);

        return ResponseEntity.status(CategoryResponseCode.OK_GET_CATEGORY.getHttpStatus())
                .body(CommonResponseDto.of(CategoryResponseCode.OK_GET_CATEGORY, categoryResponseList));
    }

    @Operation(operationId = "CATEGORY-002", summary = "맞춤 카테고리 조회")
    @GetMapping("/custom")
    public ResponseEntity<CommonResponseDto> getCustomCategories(
            @AuthUser User user,
            @RequestParam int page
    ) {
        List<CategoryGetCustomResponse> categoryResponseList = categoryService.getCustomCategories(user, page);

        return ResponseEntity.status(CategoryResponseCode.OK_GET_CUSTOM_CATEGORY.getHttpStatus())
                .body(CommonResponseDto.of(CategoryResponseCode.OK_GET_CUSTOM_CATEGORY, categoryResponseList));
    }

    @Operation(operationId = "CATEGORY-003", summary = "카테고리 별 총 퀴즈 수 조회")
    @GetMapping("/{categoryId}/totalcount")
    public ResponseEntity<CommonResponseDto> getTotalQuizzes(
            @AuthUser User user
    ) {
        List<CategoryTotalResponse> categoryTotalResponseList = categoryService.getTotalQuizzes(user);

        return ResponseEntity.status(CategoryResponseCode.OK_GET_TOTAL_QUIZ_COUNT.getHttpStatus())
                .body(CommonResponseDto.of(CategoryResponseCode.OK_GET_TOTAL_QUIZ_COUNT, categoryTotalResponseList));
    }
}
