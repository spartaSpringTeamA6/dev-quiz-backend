package com.sparta.devquiz.domain.comment.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "COMMENT", description = "Comment API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {


}
