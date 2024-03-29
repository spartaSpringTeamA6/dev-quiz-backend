package com.sparta.devquiz.domain.team.service;

import com.sparta.devquiz.domain.team.dto.request.TeamCreateRequest;
import com.sparta.devquiz.domain.team.dto.request.TeamDeleteUserRequest;
import com.sparta.devquiz.domain.team.dto.request.TeamInviteUserRequest;
import com.sparta.devquiz.domain.team.dto.request.TeamUpdateAdminRequest;
import com.sparta.devquiz.domain.team.dto.request.TeamUpdateNameRequest;
import com.sparta.devquiz.domain.team.dto.response.TeamCreateResponse;
import com.sparta.devquiz.domain.team.dto.response.TeamGetResponse;
import com.sparta.devquiz.domain.team.entity.Team;
import com.sparta.devquiz.domain.team.entity.TeamUser;
import com.sparta.devquiz.domain.team.enums.TeamUserRole;
import com.sparta.devquiz.domain.team.exception.TeamCustomException;
import com.sparta.devquiz.domain.team.exception.TeamExceptionCode;
import com.sparta.devquiz.domain.team.repository.TeamRepository;
import com.sparta.devquiz.domain.team.repository.TeamUserRepository;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamUserRepository teamUserRepository;

    @Transactional
    public TeamCreateResponse createTeam(User user, TeamCreateRequest request) {
        User createdBy = userRepository.findByIdOrElseThrow(user.getId());

        if (teamRepository.existsTeamByName(request.getName())) {
            throw new TeamCustomException(TeamExceptionCode.CONFLICT_TEAM_NAME_IN_USE);
        }

        Team team = Team.builder()
                .name(request.getName())
                .isDeleted(false)
                .build();
        teamRepository.save(team);
        teamUserRepository.save(TeamUser.createTeamAdmin(team, createdBy));

        return TeamCreateResponse.of(team);
    }

    public TeamGetResponse getTeam(User user, Long teamId) {
        Team team = getTeamAndCheckAuthUser(user, teamId);
        TeamUser admin = teamUserRepository.findByTeamAdminOrElseThrow(team.getId());
        List<TeamUser> userList = teamUserRepository.getTeamUserByTeam(team.getId());

        return TeamGetResponse.of(team, admin, userList);
    }

    @Transactional
    public void updateTeamName(User user, Long teamId, TeamUpdateNameRequest request) {
        Team team = getTeamAndCheckAuthUser(user, teamId);

        if (teamRepository.existsTeamByName(request.getName())) {
            throw new TeamCustomException(TeamExceptionCode.CONFLICT_TEAM_NAME_IN_USE);
        }

        team.updateName(request.getName());
        teamRepository.save(team);
    }

    @Transactional
    public void updateTeamAdmin(User admin, Long teamId, TeamUpdateAdminRequest request) {
        if (admin.getUsername().equals(request.getUsername())) {
            throw new TeamCustomException(TeamExceptionCode.BAD_REQUEST_INVALID_REQUEST_USERNAME);
        }

        Team team = getTeamAndCheckAuthAdmin(admin, teamId);

        User newAdmin = userRepository.findByUsernameOrElseThrow(request.getUsername());
        if (!teamUserRepository.existsByTeamUser(team.getId(), newAdmin.getId())) {
            throw new TeamCustomException(TeamExceptionCode.NOT_FOUND_TEAM_USER);
        }

        TeamUser teamUserOldAdmin = teamUserRepository.findByTeamUserOrElseThrow(teamId,admin.getId());
        teamUserOldAdmin.updateTeamUserRole(TeamUserRole.USER);
        teamUserRepository.save(teamUserOldAdmin);

        TeamUser teamUserNewAdmin = teamUserRepository.findByTeamUserOrElseThrow(teamId,newAdmin.getId());
        teamUserNewAdmin.updateTeamUserRole(TeamUserRole.ADMIN);
        teamUserRepository.save(teamUserNewAdmin);
    }

    @Transactional
    public void deleteTeamUser(User admin, Long teamId, TeamDeleteUserRequest request) {
        Team team = getTeamAndCheckAuthAdmin(admin, teamId);

        User deleteUser = userRepository.findByUsernameOrElseThrow(request.getUsername());
        if (!teamUserRepository.existsByTeamUser(team.getId(), deleteUser.getId())) {
            throw new TeamCustomException(TeamExceptionCode.NOT_FOUND_TEAM_USER);
        }

        TeamUser teamUser = teamUserRepository.findByTeamUserOrElseThrow(teamId,deleteUser.getId());
        teamUserRepository.delete(teamUser);
    }

    @Transactional
    public void withdrawTeam(User user, Long teamId) {
        Team team = getTeamAndCheckAuthUser(user, teamId);

        if (teamUserRepository.existsByTeamAdmin(team.getId(), user.getId())) {
            deleteTeam(user, teamId);
            return;
        }

        TeamUser teamUser = teamUserRepository.findByTeamUserOrElseThrow(teamId,user.getId());
        teamUserRepository.delete(teamUser);
    }

    @Transactional
    public void deleteTeam(User admin, Long teamId) {
        Team team = getTeamAndCheckAuthAdmin(admin, teamId);
        List<TeamUser> teamUserList = team.getTeamUserList();
        teamUserRepository.deleteAll(teamUserList);
        team.deleteTeam();
    }

    @Transactional
    public void inviteTeamUser(User admin, Long teamId, TeamInviteUserRequest request) {
        Team team = getTeamAndCheckAuthAdmin(admin, teamId);

        User inviteUser = userRepository.findByUsernameOrElseThrow(request.getUsername());
        if (teamUserRepository.existsByTeamUser(team.getId(), inviteUser.getId())) {
            throw new TeamCustomException(TeamExceptionCode.CONFLICT_INVITE_USERNAME_IN_TEAM);
        }
        teamUserRepository.save(TeamUser.inviteTeamUser(team,inviteUser));
    }

    @Transactional
    public void acceptInvitation(User authUser, Long teamId) {
        TeamUser findTeamUser = teamUserRepository.getTeamUserWaitOrElseThrow(teamId, authUser.getId());
        findTeamUser.acceptInvitation();
    }

    @Transactional
    public void rejectInvitation(User authUser, Long teamId) {
        TeamUser findTeamUser = teamUserRepository.getTeamUserWaitOrElseThrow(teamId, authUser.getId());
        teamUserRepository.delete(findTeamUser);
    }

//    public TeamGetUserRankingResponse getUserRankingInTeam(User user, Long teamId, Long userId, TeamGetUserRankingResponse request) {
//        if(!user.getId().equals(userId)){
//
//        }
//        Team team = getTeamAndCheckAuth(user,teamId);
//        return TeamGetUserRankingResponse.;
//    }

    public Team getTeamAndCheckAuthUser(User user, Long teamId) {
        Team team = teamRepository.findTeamByIdOrElseThrow(teamId);
        if (!teamUserRepository.existsByTeamUser(team.getId(), user.getId())) {
            throw new TeamCustomException(TeamExceptionCode.FORBIDDEN_TEAM_USER);
        }
        return team;
    }

    public Team getTeamAndCheckAuthAdmin(User user, Long teamId) {
        Team team = teamRepository.findTeamByIdOrElseThrow(teamId);
        if (!teamUserRepository.existsByTeamAdmin(team.getId(), user.getId())) {
            throw new TeamCustomException(TeamExceptionCode.FORBIDDEN_TEAM_ADMIN);
        }
        return team;
    }

}
