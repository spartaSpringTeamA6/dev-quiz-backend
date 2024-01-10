package com.sparta.devquiz.domain.team.service;

import com.sparta.devquiz.domain.team.entity.Team;
import com.sparta.devquiz.domain.team.entity.TeamUser;
import com.sparta.devquiz.domain.team.enums.TeamUserRole;
import com.sparta.devquiz.domain.team.repository.TeamUserRepository;
import com.sparta.devquiz.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamUserService {

    private final TeamUserRepository teamUserRepository;

    public void saveTeamUser(Team team, User user){
        TeamUser teamUser = TeamUser.builder().team(team).user(user)
                .userRole(TeamUserRole.USER).build();
        teamUserRepository.save(teamUser);
    }

    public void saveTeamAdmin(Team team, User user){
        TeamUser teamUser = TeamUser.builder().team(team).user(user)
                .userRole(TeamUserRole.ADMIN).build();
        teamUserRepository.save(teamUser);
    }

    public Boolean isExistedUser(Team team, User user){
        return teamUserRepository.existsByUserIdAndTeamIdAndIsAcceptedTrue(user.getId(), team.getId());
    }

    public Boolean isExistedAdmin(Team team, User user){
        return teamUserRepository.existsByUserIdAndTeamIdAndIsAcceptedTrueAndUserRole(user.getId(), team.getId(),
                TeamUserRole.ADMIN);
    }

}
