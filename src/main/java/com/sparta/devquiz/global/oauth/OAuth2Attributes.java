package com.sparta.devquiz.global.oauth;

import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.enums.OauthType;
import com.sparta.devquiz.domain.user.enums.UserRole;
import com.sparta.devquiz.global.oauth.userinfo.GithubUserInfo;
import com.sparta.devquiz.global.oauth.userinfo.GoogleUserInfo;
import com.sparta.devquiz.global.oauth.userinfo.OAuth2UserInfo;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuth2Attributes {

    private String nameAttributeKey;
    private OAuth2UserInfo oauth2UserInfo;

    @Builder
    public OAuth2Attributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    public static OAuth2Attributes of(OauthType oauthType, String userNameAttributeName, Map<String, Object> attributes) {
        if (oauthType == OauthType.GITHUB) {
            return github(userNameAttributeName, attributes);
        }
        return google(userNameAttributeName, attributes);
    }


    public static OAuth2Attributes google(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new GoogleUserInfo(attributes))
                .build();
    }

    public static OAuth2Attributes github(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new GithubUserInfo(attributes))
                .build();
    }

    public User toEntity(OauthType oauthType, OAuth2UserInfo oauth2UserInfo, String username) {
        return User.builder()
                .oauthId(oauth2UserInfo.getOauthId())
                .oauthType(oauthType)
                .username(username)
                .role(UserRole.USER)
                .build();
    }
}
