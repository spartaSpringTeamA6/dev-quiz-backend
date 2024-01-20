package com.sparta.devquiz.global.security;

import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.service.command.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String oauthId) throws UsernameNotFoundException {
        User user = userService.getOptUserByOauthId(oauthId);
        return new UserDetailsImpl(user);
    }
}