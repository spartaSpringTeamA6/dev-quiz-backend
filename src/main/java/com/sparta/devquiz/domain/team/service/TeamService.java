package com.sparta.devquiz.domain.team.service;

import com.sparta.devquiz.domain.team.dto.request.TeamCreateRequest;
import com.sparta.devquiz.domain.team.dto.request.TeamDeleteUserRequest;
import com.sparta.devquiz.domain.team.dto.request.TeamInviteUserRequest;
import com.sparta.devquiz.domain.team.dto.request.TeamUpdateAdminRequest;
import com.sparta.devquiz.domain.team.dto.request.TeamUpdateNameRequest;
import com.sparta.devquiz.domain.team.dto.request.TeamWithdrawRequest;
import com.sparta.devquiz.domain.team.dto.response.TeamCreateResponse;
import com.sparta.devquiz.domain.team.dto.response.TeamGetResponse;
import com.sparta.devquiz.domain.team.entity.Team;
import com.sparta.devquiz.domain.team.entity.TeamUser;
import com.sparta.devquiz.domain.team.enums.TeamUserRole;
import com.sparta.devquiz.domain.team.exception.TeamCustomException;
import com.sparta.devquiz.domain.team.exception.TeamExceptionCode;
import com.sparta.devquiz.domain.team.repository.TeamRepository;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserService userService;
    private final TeamUserService teamUserService;

    @Transactional
    public TeamCreateResponse createTeam(User user, TeamCreateRequest request) {
        User createdBy = userService.getUserById(user.getId());

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
        Team team = getTeamAndCheckAuth(user,teamId);
        TeamUser admin= teamUserService.getTeamAdmin(team);
        List<TeamUser> userList = teamUserService.getTeamUser(team);

        return TeamGetResponse.of(team,admin,userList);
    }

    @Transactional
    public void updateTeamName(User user, Long teamId, TeamUpdateNameRequest request) {
        Team team = getTeamAndCheckAuth(user,teamId);

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

        Team team = getTeamAndCheckAuth(admin,teamId);
        if(!teamUserService.isExistedAdmin(team,admin)){
            throw new TeamCustomException(TeamExceptionCode.FORBIDDEN_TEAM_ADMIN);
        }

        User newAdmin = userService.getUserByUsername(request.getUsername());
        if(!teamUserService.isExistedUser(team,newAdmin)){
            throw new TeamCustomException(TeamExceptionCode.NOT_FOUND_TEAM_USER);
        }

        teamUserService.updateTeamUserRole(team, admin, TeamUserRole.USER);
        teamUserService.updateTeamUserRole(team, newAdmin, TeamUserRole.ADMIN);
    }

    @Transactional
    public void deleteTeamUser(User admin, Long teamId, TeamDeleteUserRequest request) {
        Team team = getTeamAndCheckAuth(admin,teamId);

        if(!teamUserService.isExistedAdmin(team,admin)){
            throw new TeamCustomException(TeamExceptionCode.FORBIDDEN_TEAM_ADMIN);
        }

        User deleteUser = userService.getUserByUsername(request.getUsername());
        if(!teamUserService.isExistedUser(team,deleteUser)){
            throw new TeamCustomException(TeamExceptionCode.NOT_FOUND_TEAM_USER);
        }

        teamUserService.deleteTeamUser(team,deleteUser);
    }

    @Transactional
    public void withdrawTeam(User user, Long teamId, TeamWithdrawRequest request) {
        Team team = getTeamAndCheckAuth(user,teamId);

        if(teamUserService.isExistedAdmin(team,user)){
            if(team.getTeamUserList().size()==1){
                deleteTeam(user,teamId);
                return;
            }else {
                throw new TeamCustomException(
                        TeamExceptionCode.BAD_REQUEST_INVALID_REQUEST_DELETE_ADMIN);
            }
        }
        if(!teamUserService.isExistedUser(team,user)){
            throw new TeamCustomException(TeamExceptionCode.FORBIDDEN_TEAM_USER);
        }

        teamUserService.deleteTeamUser(team,user);
    }

    @Transactional
    public void deleteTeam(User admin, Long teamId) {
        Team team = getTeamAndCheckAuth(admin,teamId);

        if(!teamUserService.isExistedAdmin(team,admin)){
            throw new TeamCustomException(TeamExceptionCode.FORBIDDEN_TEAM_ADMIN);
        }

        List<TeamUser> teamUserList = team.getTeamUserList();
        for(TeamUser teamUser:teamUserList){
            teamUserService.deleteTeamUser(team,teamUser.getUser());
        }

        team.deleteTeam();
    }

    @Transactional
    public void inviteTeamUser(User admin, Long teamId, TeamInviteUserRequest request) {
        Team team = getTeamAndCheckAuth(admin,teamId);

        if(!teamUserService.isExistedAdmin(team,admin)){
            throw new TeamCustomException(TeamExceptionCode.FORBIDDEN_TEAM_ADMIN);
        }

        for(String username: request.getUser()){
            User inviteUser = userService.getUserByUsername(username);
            if(teamUserService.isExistedUser(team,inviteUser)){
                throw new TeamCustomException(TeamExceptionCode.CONFLICT_INVITE_USERNAME_IN_TEAM);
            }
            teamUserService.inviteTeamUser(team,inviteUser);
        }
    }

//    public TeamGetUserRankingResponse getUserRankingInTeam(User user, Long teamId, Long userId, TeamGetUserRankingResponse request) {
//        if(!user.getId().equals(userId)){
//
//        }
//        Team team = getTeamAndCheckAuth(user,teamId);
//        return TeamGetUserRankingResponse.;
//    }

    public Team getTeamAndCheckAuth(User user, Long teamId){
        Team team = getTeamById(teamId);
        User loginUser = userService.getUserById(user.getId());

        if(!teamUserService.isExistedUser(team, loginUser)){
            throw new TeamCustomException(TeamExceptionCode.FORBIDDEN_TEAM_USER);
        }

        return team;
    }

    public Team getTeamById(Long id) {
        return teamRepository.findByIdAndIsDeletedFalse(id).orElseThrow(
                () -> new TeamCustomException(TeamExceptionCode.NOT_FOUND_TEAM)
        );
    }

}
