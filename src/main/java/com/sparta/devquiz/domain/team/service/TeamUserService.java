package com.sparta.devquiz.domain.team.service;

import com.sparta.devquiz.domain.team.entity.Team;
import com.sparta.devquiz.domain.team.entity.TeamUser;
import com.sparta.devquiz.domain.team.enums.TeamUserRole;
import com.sparta.devquiz.domain.team.exception.TeamCustomException;
import com.sparta.devquiz.domain.team.exception.TeamExceptionCode;
import com.sparta.devquiz.domain.team.repository.TeamUserRepository;
import com.sparta.devquiz.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamUserService {

    private final TeamUserRepository teamUserRepository;

    public void saveTeamAdmin(Team team, User user){
        TeamUser teamUser = TeamUser.builder().team(team).user(user)
                .userRole(TeamUserRole.ADMIN).build();
        teamUserRepository.save(teamUser);
    }

    public void saveTeamUser(Team team, User user){
        TeamUser teamUser = TeamUser.builder().team(team).user(user)
                .userRole(TeamUserRole.USER).build();
        teamUserRepository.save(teamUser);
    }

    public TeamUser getTeamAdmin(Team team){
        return teamUserRepository.findByTeamIdAndIsAcceptedTrueAndUserRole(team.getId(), TeamUserRole.ADMIN)
                .orElseThrow(
                        ()-> new TeamCustomException(TeamExceptionCode.NOT_FOUND_TEAM_ADMIN)
                );
    }

    public List<TeamUser> getTeamUser(Team team){
        return teamUserRepository.findAllByTeamIdAndIsAcceptedTrueAndUserRole(team.getId(), TeamUserRole.USER);
    }


    public Boolean isExistedUser(Team team, User user){
        return teamUserRepository.existsByUserIdAndTeamIdAndIsAcceptedTrue(user.getId(), team.getId());
    }

    public Boolean isExistedAdmin(Team team, User user){
        return teamUserRepository.existsByUserIdAndTeamIdAndIsAcceptedTrueAndUserRole(user.getId(), team.getId(),
                TeamUserRole.ADMIN);
    }

}