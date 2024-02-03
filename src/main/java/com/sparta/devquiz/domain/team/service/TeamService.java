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
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.repository.UserRepository;
import com.sparta.devquiz.domain.user.service.command.UserService;
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
    private final TeamUserService teamUserService;

    @Transactional
    public TeamCreateResponse createTeam(User user, TeamCreateRequest request) {
        User createdBy = userRepository.findByIdOrElseThrow(user.getId());

        if(teamRepository.existsByName(request.getName())){
            throw new TeamCustomException(TeamExceptionCode.CONFLICT_TEAM_NAME_IN_USE);
        }

        Team team = Team.builder()
                .name(request.getName())
                .isDeleted(false)
                .build();
        teamRepository.save(team);
        teamUserService.createTeamAdmin(team, createdBy);

        return TeamCreateResponse.of(team);
    }

    public TeamGetResponse getTeam(User user, Long teamId) {
        Team team = getTeamAndCheckAuthUser(user,teamId);
        TeamUser admin= teamUserService.getTeamAdmin(team.getId());
        List<TeamUser> userList = teamUserService.getTeamUser(team.getId());

        return TeamGetResponse.of(team,admin,userList);
    }

    @Transactional
    public void updateTeamName(User user, Long teamId, TeamUpdateNameRequest request) {
        Team team = getTeamAndCheckAuthUser(user,teamId);

        if(teamRepository.existsByName(request.getName())){
            throw new TeamCustomException(TeamExceptionCode.CONFLICT_TEAM_NAME_IN_USE);
        }

        team.updateName(request.getName());
        teamRepository.save(team);
    }

    @Transactional
    public void updateTeamAdmin(User admin, Long teamId, TeamUpdateAdminRequest request) {
        if(admin.getUsername().equals(request.getUsername())){
            throw new TeamCustomException(TeamExceptionCode.BAD_REQUEST_INVALID_REQUEST_USERNAME);
        }

        Team team = getTeamAndCheckAuthAdmin(admin, teamId);

        User newAdmin = userRepository.findByUsernameOrElseThrow(request.getUsername());
        if(!teamUserService.isExistedUser(team.getId(), newAdmin.getId())){
            throw new TeamCustomException(TeamExceptionCode.NOT_FOUND_TEAM_USER);
        }

        teamUserService.updateTeamUserRole(team.getId(), admin.getId(), TeamUserRole.USER);
        teamUserService.updateTeamUserRole(team.getId(), newAdmin.getId(), TeamUserRole.ADMIN);
    }

    @Transactional
    public void deleteTeamUser(User admin, Long teamId, TeamDeleteUserRequest request) {
        Team team = getTeamAndCheckAuthAdmin(admin,teamId);

        User deleteUser = userRepository.findByUsernameOrElseThrow(request.getUsername());
        if(!teamUserService.isExistedUser(team.getId(), deleteUser.getId())){
            throw new TeamCustomException(TeamExceptionCode.NOT_FOUND_TEAM_USER);
        }

        teamUserService.deleteTeamUser(team.getId(), deleteUser.getId());
    }

    @Transactional
    public void withdrawTeam(User user, Long teamId) {
        Team team = getTeamAndCheckAuthUser(user,teamId);

        if(teamUserService.isExistedAdmin(team.getId(), user.getId())){
            deleteTeam(user,teamId);
            return;
        }

        teamUserService.deleteTeamUser(team.getId(), user.getId());
    }

    @Transactional
    public void deleteTeam(User admin, Long teamId) {
        Team team = getTeamAndCheckAuthAdmin(admin,teamId);

        List<TeamUser> teamUserList = team.getTeamUserList();
        for(TeamUser teamUser:teamUserList){
            teamUserService.deleteTeamUser(team.getId(), teamUser.getUser().getId());
        }

        team.deleteTeam();
    }

    @Transactional
    public void inviteTeamUser(User admin, Long teamId, TeamInviteUserRequest request) {
        Team team = getTeamAndCheckAuthAdmin(admin,teamId);

        User inviteUser = userRepository.findByUsernameOrElseThrow(request.getUsername());
        if(teamUserService.isExistedUser(team.getId(), inviteUser.getId())){
            throw new TeamCustomException(TeamExceptionCode.CONFLICT_INVITE_USERNAME_IN_TEAM);
        }
        teamUserService.inviteTeamUser(team,inviteUser);

    }

    @Transactional
    public void acceptInvitationTeamUser(User user, Long teamId) {
        Team team = getTeamById(teamId);
        teamUserService.acceptInvitation(teamId,user.getId());
    }

    @Transactional
    public void rejectInvitationTeamUser(User user, Long teamId) {
        Team team = getTeamById(teamId);
        teamUserService.rejectInvitation(teamId,user.getId());
    }


//    public TeamGetUserRankingResponse getUserRankingInTeam(User user, Long teamId, Long userId, TeamGetUserRankingResponse request) {
//        if(!user.getId().equals(userId)){
//
//        }
//        Team team = getTeamAndCheckAuth(user,teamId);
//        return TeamGetUserRankingResponse.;
//    }

    public Team getTeamAndCheckAuthUser(User user, Long teamId){
        Team team = getTeamById(teamId);
        User loginUser = userRepository.findByIdOrElseThrow(user.getId());

        if(!teamUserService.isExistedUser(team.getId(), loginUser.getId())){
            throw new TeamCustomException(TeamExceptionCode.FORBIDDEN_TEAM_USER);
        }

        return team;
    }

    public Team getTeamAndCheckAuthAdmin(User user, Long teamId){
        Team team = getTeamById(teamId);
        User admin = userRepository.findByIdOrElseThrow(user.getId());

        if(!teamUserService.isExistedAdmin(team.getId(), admin.getId())){
            throw new TeamCustomException(TeamExceptionCode.FORBIDDEN_TEAM_ADMIN);
        }

        return team;
    }

    public Team getTeamById(Long id) {
        return teamRepository.findByIdAndIsDeletedFalse(id).orElseThrow(
                () -> new TeamCustomException(TeamExceptionCode.NOT_FOUND_TEAM)
        );
    }

}
