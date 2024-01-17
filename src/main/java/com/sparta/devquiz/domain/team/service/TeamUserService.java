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

    public void createTeamAdmin(Team team, User user){
        TeamUser teamUser = TeamUser.builder()
                .user(user)
                .team(team)
                .userRole(TeamUserRole.ADMIN)
                .isAccepted(true)
                .build();

        teamUserRepository.save(teamUser);
    }

    public void inviteTeamUser(Team team, User user){
        TeamUser teamUser = TeamUser.builder()
                .team(team)
                .user(user)
                .userRole(TeamUserRole.USER)
                .isAccepted(false)
                .build();

        teamUserRepository.save(teamUser);
    }

    public TeamUser getTeamAdmin(Long teamId){
        return teamUserRepository.findByTeamIdAndIsAcceptedTrueAndUserRole(teamId, TeamUserRole.ADMIN)
                .orElseThrow(
                        ()-> new TeamCustomException(TeamExceptionCode.NOT_FOUND_TEAM_ADMIN)
                );
    }

    public List<TeamUser> getTeamUser(Long teamId){
        return teamUserRepository.findAllByTeamIdAndIsAcceptedTrueAndUserRole(teamId, TeamUserRole.USER);
    }

    public void updateTeamUserRole(Long teamId, Long userId, TeamUserRole teamUserRole){
        TeamUser teamUser = getTeamUserByTeamIdAndUserId(teamId, userId);
        teamUser.updateTeamUserRole(teamUserRole);
        teamUserRepository.save(teamUser);
    }

    public void deleteTeamUser(Long teamId, Long userId) {
        TeamUser teamUser = getTeamUserByTeamIdAndUserId(teamId, userId);
        teamUserRepository.delete(teamUser);
    }

    public void acceptInvitation(Long teamId, Long userId) {
        TeamUser findTeamUser = getTeamUserByTeamAndUserAndWait(teamId, userId);
        findTeamUser.acceptInvitation();
    }

    public void rejectInvitation(Long teamId, Long userId) {
        TeamUser findTeamUser = getTeamUserByTeamAndUserAndWait(teamId, userId);
        teamUserRepository.delete(findTeamUser);
    }

    public TeamUser getTeamUserByTeamIdAndUserId(Long teamId, Long userId){
        return teamUserRepository.findByTeamIdAndUserId(teamId, userId).orElseThrow(
                () -> new TeamCustomException(TeamExceptionCode.NOT_FOUND_TEAM_USER)
        );
    }

    public Boolean isExistedUser(Long teamId, Long userId){
        return teamUserRepository.existsByTeamIdAndUserIdAndIsAcceptedTrue(teamId, userId);
    }

    public Boolean isExistedAdmin(Long teamId, Long userId){
        return teamUserRepository.existsByTeamIdAndUserIdAndIsAcceptedTrueAndUserRole(teamId, userId, TeamUserRole.ADMIN);
    }

    public List<TeamUser> getTeamUserByUser(Long userId) {
        return teamUserRepository.findAllByUserIdAndIsAcceptedTrue(userId);
    }

    public List<TeamUser> getTeamUserByUserAndWait(Long userId) {
        return teamUserRepository.findAllByUserIdAndIsAcceptedFalse(userId);
    }

    public TeamUser getTeamUserByTeamAndUserAndWait(Long teamId, Long userId) {
        return teamUserRepository.findByTeamIdAndUserIdAndIsAcceptedFalse(teamId, userId).orElseThrow(
            () -> new TeamCustomException(TeamExceptionCode.NOT_FOUND_TEAM_USER_WAIT)
        );
    }

}