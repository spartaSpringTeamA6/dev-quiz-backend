package com.sparta.devquiz.domain.quiz.entity;

import com.sparta.devquiz.global.entity.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, name = "category_title")
    private String categoryTitle;

    @Column(nullable = true, name = "category_description")
    private String categoryDescription;

    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Quiz> quizzes;

    public void updateCategoryName(String categoryTitle) {
        if (categoryTitle != null && !categoryTitle.isEmpty()) {
            this.categoryTitle = categoryTitle;
        }
    }

    public void updateCategoryDescription(String categoryDescription) {
        if (categoryDescription != null && !categoryDescription.isEmpty()) {
            this.categoryDescription = categoryDescription;
        }
    }

}
