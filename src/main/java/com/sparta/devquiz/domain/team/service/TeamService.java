package com.sparta.devquiz.domain.team.service;

import com.sparta.devquiz.domain.team.dto.request.TeamCreateRequest;
import com.sparta.devquiz.domain.team.dto.request.TeamGetRequest;
import com.sparta.devquiz.domain.team.dto.response.TeamCreateResponse;
import com.sparta.devquiz.domain.team.dto.response.TeamGetResponse;
import com.sparta.devquiz.domain.team.entity.Team;
import com.sparta.devquiz.domain.team.entity.TeamUser;
import com.sparta.devquiz.domain.team.exception.TeamCustomException;
import com.sparta.devquiz.domain.team.exception.TeamExceptionCode;
import com.sparta.devquiz.domain.team.repository.TeamRepository;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
