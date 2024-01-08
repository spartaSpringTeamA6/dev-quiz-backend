package com.sparta.devquiz.domain.group.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GroupInvitationId implements Serializable {
    @Column(name = "userId")
    private Long userId;

    @Column(name = "groupId")
    private Long groupId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupInvitationId groupInvitationId = (GroupInvitationId) o;
        return Objects.equals(userId, groupInvitationId.userId) && Objects.equals(groupId, groupInvitationId.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, groupId);
    }
}
