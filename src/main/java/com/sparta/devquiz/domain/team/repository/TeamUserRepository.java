package com.sparta.devquiz.domain.team.repository;

import com.sparta.devquiz.domain.team.entity.TeamUser;
import com.sparta.devquiz.domain.team.entity.TeamUserId;
import com.sparta.devquiz.domain.team.enums.TeamUserRole;
import com.sparta.devquiz.domain.team.exception.TeamCustomException;
import com.sparta.devquiz.domain.team.exception.TeamExceptionCode;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.exception.UserCustomException;
import com.sparta.devquiz.domain.user.exception.UserExceptionCode;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeamUserRepository extends JpaRepository<TeamUser, TeamUserId> {

    @Query("select tu from TeamUser tu where tu.team.id = :teamId and tu.user.id = :userId")
    Optional<TeamUser> findByTeamIdAndUserId(Long teamId, Long userId);

    @Query("select tu from TeamUser tu where tu.team.id = :teamId and tu.isAccepted = TRUE and tu.userRole = :userRole")
    Optional<TeamUser> findByTeamIdAndIsAcceptedTrueAndUserRole(Long teamId, TeamUserRole userRole);

    @Query("select tu from TeamUser tu where tu.team.id = :teamId and tu.user.id = :userId and tu.isAccepted = FALSE")
    Optional<TeamUser> findByTeamIdAndUserIdAndIsAcceptedFalse(Long teamId, Long userId);

    @Query("select tu from TeamUser tu where tu.team.id = :teamId and tu.isAccepted = TRUE")
    List<TeamUser> findAllByTeamIdAndIsAcceptedTrue(Long teamId);

    @Query("select tu from TeamUser tu where tu.user.id = :userId and tu.isAccepted = TRUE")
    List<TeamUser> findAllByUserIdAndIsAcceptedTrue(Long userId);

    @Query("select tu from TeamUser tu where tu.user.id = :userId and tu.isAccepted = FALSE")
    List<TeamUser> findAllByUserIdAndIsAcceptedFalse(Long userId);

    @Query("SELECT CASE WHEN COUNT(tu) > 0 THEN true ELSE false END FROM TeamUser tu WHERE tu.team.id = :teamId AND tu.user.id = :userId AND tu.isAccepted = TRUE")
    boolean existsByTeamIdAndUserIdAndIsAcceptedTrue(Long teamId, Long userId);

    @Query("SELECT CASE WHEN COUNT(tu) > 0 THEN true ELSE false END FROM TeamUser tu WHERE tu.team.id = :teamId AND tu.user.id = :userId AND tu.isAccepted = TRUE and tu.userRole = :userRole")
    boolean existsByTeamIdAndUserIdAndIsAcceptedTrueAndUserRole(Long teamId, Long userId, TeamUserRole userRole);

    default TeamUser findByTeamUserOrElseThrow(Long teamId, Long userId){
        return findByTeamIdAndUserId(teamId, userId).orElseThrow(
                () -> new TeamCustomException(TeamExceptionCode.NOT_FOUND_TEAM_USER)
        );
    }

    default TeamUser findByTeamAdminOrElseThrow(Long teamId){
        return findByTeamIdAndIsAcceptedTrueAndUserRole(teamId, TeamUserRole.ADMIN)
                .orElseThrow(()-> new TeamCustomException(TeamExceptionCode.NOT_FOUND_TEAM_ADMIN));
    }

    default TeamUser getTeamUserWaitOrElseThrow(Long teamId, Long userId) {
        return findByTeamIdAndUserIdAndIsAcceptedFalse(teamId, userId).orElseThrow(
                () -> new TeamCustomException(TeamExceptionCode.NOT_FOUND_TEAM_USER_WAIT)
        );
    }

    default List<TeamUser> getTeamUserByTeam(Long teamId){
        return findAllByTeamIdAndIsAcceptedTrue(teamId);
    }

    default List<TeamUser> getTeamUserByUser(Long userId) {
        return findAllByUserIdAndIsAcceptedTrue(userId);
    }

    default List<TeamUser> getTeamUserByUserAndWait(Long userId) {
        return findAllByUserIdAndIsAcceptedFalse(userId);
    }

    default boolean existsByTeamUser(Long teamId, Long userId){
        return existsByTeamIdAndUserIdAndIsAcceptedTrue(teamId, userId);
    }

    default boolean existsByTeamAdmin(Long teamId, Long userId){
        return existsByTeamIdAndUserIdAndIsAcceptedTrueAndUserRole(teamId, userId, TeamUserRole.ADMIN);
    }

}
