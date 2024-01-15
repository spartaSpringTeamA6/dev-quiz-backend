package com.sparta.devquiz.domain.user.dto.response;

import com.sparta.devquiz.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "유저 정보 응답 dto")
public class UserInfoResponse {

    @Schema(description = "유저 id", defaultValue = "1")
    private Long userId;

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
            .map(user -> UserInfoResponse.of(user))
            .toList();
    }
}
