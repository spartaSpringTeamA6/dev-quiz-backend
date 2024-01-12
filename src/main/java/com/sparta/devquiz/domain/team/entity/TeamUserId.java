package com.sparta.devquiz.domain.team.entity;

import com.sparta.devquiz.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamUserId implements Serializable {

    private Long userId;

    private Long teamId;

}
