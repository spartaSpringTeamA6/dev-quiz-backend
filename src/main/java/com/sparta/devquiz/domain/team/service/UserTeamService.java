package com.sparta.devquiz.domain.team.service;

import com.sparta.devquiz.domain.team.entity.Team;
import com.sparta.devquiz.domain.team.repository.UserTeamRepository;
import com.sparta.devquiz.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserTeamService {

    private final UserTeamRepository userTeamRepository;

    public Boolean isExistedUser(User user, Team team){
        return userTeamRepository.existsByUserIdAndTeamIdAndIsAcceptedTrue(user.getId(), team.getId());
    }

}
