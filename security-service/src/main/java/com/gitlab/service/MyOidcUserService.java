package com.gitlab.service;

import com.gitlab.model.User;
import com.gitlab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MyOidcUserService extends OidcUserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

            return processOidcUser(userRequest, oidcUser);

    }
    private OidcUser processOidcUser(OidcUserRequest userRequest, OidcUser oidcUser) {

        System.out.println(oidcUser.toString());
        User user = new User();
        user.setFirstName(oidcUser.getGivenName());
        user.setEmail(oidcUser.getEmail());
        user.setLastName(oidcUser.getFamilyName());
        user.setCreateDate(LocalDate.now());

            userRepository.save(user);

        return oidcUser;
    }
}
