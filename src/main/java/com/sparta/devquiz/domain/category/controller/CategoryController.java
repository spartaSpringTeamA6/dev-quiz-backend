package com.sparta.devquiz.domain.category.controller;

import com.sparta.devquiz.domain.category.dto.request.CategoryCreateRequest;
import com.sparta.devquiz.domain.category.dto.response.CategoryGetResponse;
import com.sparta.devquiz.domain.category.response.CategoryResponseCode;
import com.sparta.devquiz.domain.category.service.CategoryService;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.global.annotation.AuthUser;
import com.sparta.devquiz.global.response.CommonResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "6. Category API", description = "Category 관련 API 입니다.")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(operationId = "CATEGORY-001", summary = "카테고리 조회")
    @GetMapping("/categories")
    public ResponseEntity<CommonResponseDto> getCategories(
    ) {
        List<CategoryGetResponse> categoryResponseList = categoryService.getCategories();

        return ResponseEntity.ok(CommonResponseDto.of(CategoryResponseCode.OK_GET_CATEGORY, categoryResponseList));
    }

    @Operation(operationId = "ADMIN-009", summary = "카테고리 생성")
    @PostMapping("/admin/categories")
    public ResponseEntity<CommonResponseDto> createCategory(
            @AuthUser User user,
            @RequestBody CategoryCreateRequest request
    ) {
        categoryService.createCategory(request, user);

        return ResponseEntity.status(CategoryResponseCode.OK_CREATE_CATEGORY.getHttpStatus())
                .body(CommonResponseDto.of(CategoryResponseCode.OK_CREATE_CATEGORY));
    }
}
