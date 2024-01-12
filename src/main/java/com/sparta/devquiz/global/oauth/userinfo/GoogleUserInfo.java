package com.sparta.devquiz.global.oauth.userinfo;

import java.util.Map;

public class GoogleUserInfo extends OAuth2UserInfo {

    public GoogleUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getOauthId() {
        return (String) attributes.get("sub");
    }
}
