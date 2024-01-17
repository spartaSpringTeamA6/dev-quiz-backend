package com.sparta.devquiz.domain.team.service;

import com.sparta.devquiz.domain.team.entity.Team;
import com.sparta.devquiz.domain.team.entity.TeamUser;
import com.sparta.devquiz.domain.team.enums.TeamUserRole;
import com.sparta.devquiz.domain.team.exception.TeamCustomException;
import com.sparta.devquiz.domain.team.exception.TeamExceptionCode;
import com.sparta.devquiz.domain.team.repository.TeamUserRepository;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.service.UserService;
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

    public TeamUser getTeamAdmin(Team team){
        return teamUserRepository.findByTeamAndIsAcceptedTrueAndUserRole(team, TeamUserRole.ADMIN)
                .orElseThrow(
                        ()-> new TeamCustomException(TeamExceptionCode.NOT_FOUND_TEAM_ADMIN)
                );
    }

    public List<TeamUser> getTeamUser(Team team){
        return teamUserRepository.findAllByTeamAndIsAcceptedTrueAndUserRole(team, TeamUserRole.USER);
    }

    public void updateTeamUserRole(Team team, User user, TeamUserRole teamUserRole){
        TeamUser teamUser = getTeamUserByTeamIdAndUserId(team, user);
        teamUser.updateTeamUserRole(teamUserRole);
        teamUserRepository.save(teamUser);
    }

    public void deleteTeamUser(Team team, User user) {
        TeamUser teamUser = getTeamUserByTeamIdAndUserId(team,user);
        teamUserRepository.delete(teamUser);
    }

    public void acceptInvitation(Team team, User user) {
        TeamUser findTeamUser = getTeamUserByTeamAndUserAndWait(team, user);
        findTeamUser.acceptInvitation();
    }

    public void rejectInvitation(Team team, User user) {
        TeamUser findTeamUser = getTeamUserByTeamAndUserAndWait(team, user);
        teamUserRepository.delete(findTeamUser);
    }

    public TeamUser getTeamUserByTeamIdAndUserId(Team team, User user){
        return teamUserRepository.findByTeamAndUser(team, user).orElseThrow(
                () -> new TeamCustomException(TeamExceptionCode.NOT_FOUND_TEAM_USER)
        );
    }

    public Boolean isExistedUser(Team team, User user){
        return teamUserRepository.existsByTeamAndUserAndIsAcceptedTrue(team, user);
    }

    public Boolean isExistedAdmin(Team team, User user){
        return teamUserRepository.existsByTeamAndUserAndIsAcceptedTrueAndUserRole(team, user,
                TeamUserRole.ADMIN);
    }

    public List<TeamUser> getTeamUserByUser(User user) {
        return teamUserRepository.findAllByUserAndIsAcceptedTrue(user);
    }

    public List<TeamUser> getTeamUserByUserAndWait(User user) {
        return teamUserRepository.findAllByUserAndIsAcceptedFalse(user);
    }

    public TeamUser getTeamUserByTeamAndUserAndWait(Team team, User user) {
        return teamUserRepository.findByTeamAndUserAndIsAcceptedFalse(team, user).orElseThrow(
            () -> new TeamCustomException(TeamExceptionCode.NOT_FOUND_TEAM_USER_WAIT)
        );
    }

}