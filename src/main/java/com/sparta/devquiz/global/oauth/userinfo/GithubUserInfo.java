package com.sparta.devquiz.global.oauth.userinfo;

import java.util.Map;

public class GithubUserInfo extends OAuth2UserInfo {

    public GithubUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getOauthId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getUsername() {
        return String.valueOf(attributes.get("login"));
    }
}
