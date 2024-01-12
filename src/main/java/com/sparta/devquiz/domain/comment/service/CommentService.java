package com.sparta.devquiz.domain.comment.service;

import com.sparta.devquiz.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;

}
