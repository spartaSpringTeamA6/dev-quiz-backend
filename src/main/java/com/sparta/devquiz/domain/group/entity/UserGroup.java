package com.sparta.devquiz.domain.group.entity;

import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.group.enums.GroupUserRole;
import com.sparta.devquiz.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_groups")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserGroup extends BaseTimeEntity {

    @EmbeddedId
    private UserGroupId userGroupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("groupId")
    private Group group;

    @Column
    @Enumerated(value = EnumType.STRING)
    private GroupUserRole role;



}
