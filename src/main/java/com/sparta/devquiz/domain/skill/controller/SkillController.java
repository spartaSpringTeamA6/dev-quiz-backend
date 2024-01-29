package com.sparta.devquiz.domain.skill.controller;

import com.sparta.devquiz.domain.skill.response.SkillResponseCode;
import com.sparta.devquiz.domain.user.enums.UserSkill;
import com.sparta.devquiz.global.response.CommonResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/skills")
@Tag(name = "7. SKILL API", description = "Skill 관련 API 입니다.")
public class SkillController {

  @Operation(operationId = "SKILL-001", summary = "전체 SKILL 조회")
  @GetMapping
  public ResponseEntity<CommonResponseDto> getAllSkills() {
    return ResponseEntity.status(SkillResponseCode.OK.getHttpStatus())
        .body(CommonResponseDto.of(SkillResponseCode.OK, UserSkill.values()));
  }
}