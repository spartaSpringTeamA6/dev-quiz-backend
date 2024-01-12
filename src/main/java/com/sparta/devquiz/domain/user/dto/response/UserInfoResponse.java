package com.sparta.devquiz.domain.user.dto.response;

import com.sparta.devquiz.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
@Schema(description = "유저 정보 응답 dto")
public class UserInfoResponse {

    @Column
    @Schema(description = "유저 id", defaultValue = "1")
    private Long userId;

    @Column
    @Schema(description = "유저 이름", defaultValue = "봉골레파스타")
    private String username;

    public static UserInfoResponse of(User user) {
        return UserInfoResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .build();
    }

    public static List<UserInfoResponse> of(List<User> userList) {
        return userList.stream()
                .map(i -> new UserInfoResponse(i.getId(),i.getUsername()))
                .collect(Collectors.toList());
    }

}
