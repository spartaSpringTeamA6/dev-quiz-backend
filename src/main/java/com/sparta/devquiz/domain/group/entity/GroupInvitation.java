package com.sparta.devquiz.domain.group.entity;

import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "group_invitations")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupInvitation extends BaseTimeEntity {

    @EmbeddedId
    private GroupInvitationId groupInvitationId;

    @ManyToOne
    @MapsId("groupId")
    private Group group;

    @ManyToOne
    @MapsId("userId")
    private User user;
}
