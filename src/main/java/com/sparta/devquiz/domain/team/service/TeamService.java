package com.sparta.devquiz.domain.team.service;

import com.sparta.devquiz.domain.team.dto.request.TeamCreateRequest;
import com.sparta.devquiz.domain.team.dto.request.TeamDeleteUserRequest;
import com.sparta.devquiz.domain.team.dto.request.TeamGetRequest;
import com.sparta.devquiz.domain.team.dto.request.TeamUpdateAdminRequest;
import com.sparta.devquiz.domain.team.dto.request.TeamUpdateNameRequest;
import com.sparta.devquiz.domain.team.dto.response.TeamCreateResponse;
import com.sparta.devquiz.domain.team.dto.response.TeamDeleteUserResponse;
import com.sparta.devquiz.domain.team.dto.response.TeamGetResponse;
import com.sparta.devquiz.domain.team.dto.response.TeamUpdateAdminResponse;
import com.sparta.devquiz.domain.team.dto.response.TeamUpdateNameResponse;
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

        Team team = Team.builder()
                .name(request.getName())
                .isDeleted(false)
                .build();
        teamRepository.save(team);

        teamUserService.saveTeamAdmin(team,createdBy);

        return TeamCreateResponse.of(team);
    }

    public TeamGetResponse getTeam(User user, Long teamId, TeamGetRequest request) {
        Team team = getTeamAndCheckAuth(user,teamId);
        TeamUser admin= teamUserService.getTeamAdmin(team);
        List<TeamUser> userList = teamUserService.getTeamUser(team);

        return TeamGetResponse.of(team,admin,userList);
    }

    public TeamUpdateNameResponse updateTeamName(User user, Long teamId, TeamUpdateNameRequest request) {
        Team team = getTeamAndCheckAuth(user,teamId);

        if(teamRepository.existsByName(request.getName())){
            throw new TeamCustomException(TeamExceptionCode.CONFLICT_TEAM_NAME_IN_USE);
        }

        team.updateName(request.getName());
        teamRepository.save(team);

        return new TeamUpdateNameResponse();
    }

    public TeamUpdateAdminResponse updateTeamAdmin(User user, Long teamId, TeamUpdateAdminRequest request) {
        if(user.getUsername().equals(request.getUsername())){
            // TODO: exceptioncode
            throw new TeamCustomException(TeamExceptionCode.FORBIDDEN_TEAM_ADMIN);
        }

        Team team = getTeamAndCheckAuth(user,teamId);
        if(!teamUserService.isExistedAdmin(team,user)){
            throw new TeamCustomException(TeamExceptionCode.FORBIDDEN_TEAM_ADMIN);
        }

        User newAdmin = userService.getUserByUsername(request.getUsername());
        if(!teamUserService.isExistedUser(team,newAdmin)){
            throw new TeamCustomException(TeamExceptionCode.NOT_FOUND_TEAM_USER);
        }

        teamUserService.updateTeamUserRole(team, user, TeamUserRole.USER);
        teamUserService.updateTeamUserRole(team, newAdmin, TeamUserRole.ADMIN);

        return new TeamUpdateAdminResponse();
    }

    public TeamDeleteUserResponse deleteTeamUser(User user, Long teamId, TeamDeleteUserRequest request) {
        Team team = getTeamAndCheckAuth(user,teamId);

        if(!teamUserService.isExistedAdmin(team,user)){
            throw new TeamCustomException(TeamExceptionCode.FORBIDDEN_TEAM_ADMIN);
        }

        User deleteUser = userService.getUserByUsername(request.getUsername());
        if(!teamUserService.isExistedUser(team,deleteUser)){
            throw new TeamCustomException(TeamExceptionCode.NOT_FOUND_TEAM_USER);
        }

        teamUserService.deleteTeamUser(team,user);

        return new TeamDeleteUserResponse();
    }


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
