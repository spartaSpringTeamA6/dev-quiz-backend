package com.sparta.devquiz.global.oauth.service;

import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.enums.OauthType;
import com.sparta.devquiz.domain.user.repository.UserRepository;
import com.sparta.devquiz.global.oauth.OAuth2Attributes;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>  {

  private final UserRepository userRepository;
  @Override
  public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(request);

    String registrationId = request.getClientRegistration().getRegistrationId();

    OauthType oauthType = OauthType.valueOf(registrationId.toUpperCase());

    String userNameAttributeName = request.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

    Map<String, Object> attributes = oAuth2User.getAttributes();

    OAuth2Attributes extractAttributes = OAuth2Attributes.of(oauthType, userNameAttributeName, attributes);

    User createdUser = getUser(extractAttributes, oauthType);

    return new DefaultOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority(createdUser.getRole().getAuthority())),
        attributes,
        extractAttributes.getNameAttributeKey()
    );
  }

  private User getUser(OAuth2Attributes attributes, OauthType oauthType) {
    User findUser = userRepository.findByOauthIdAndIsDeletedFalse(attributes.getOauth2UserInfo().getOauthId()).orElse(null);

    if(findUser == null) {
      return createUser(attributes, oauthType);
    }
    return findUser;
  }

  private User createUser(OAuth2Attributes attributes, OauthType oauthType) {
    User createdUser = attributes.toEntity(oauthType, attributes.getOauth2UserInfo(), makeUsername());
    return userRepository.save(createdUser);
  }
  
  private String makeUsername() {
    String username = UUID.randomUUID().toString().substring(0, 27);
    while (userRepository.existsByUsername(username)) {
      username = UUID.randomUUID().toString().substring(0, 27);
    }
    return "tmp" + username;
  }
}