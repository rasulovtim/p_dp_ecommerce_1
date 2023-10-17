package com.gitlab.service;


import com.gitlab.model.User;
import com.gitlab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

        
        User user = new User();
        user.setFirstName(oidcUser.getGivenName());
        user.setEmail(oidcUser.getEmail());
        if(oidcUser.getFamilyName() == null){
            user.setLastName("No Last Name");
        }else {
            user.setLastName(oidcUser.getFamilyName());
        }
        user.setCreateDate(LocalDate.now());
        user.setPassword(new BCryptPasswordEncoder().encode("11111"));
        user.setSecurityQuestion("sevQ");
        user.setAnswerQuestion("ansQ");
        user.setBirthDate(LocalDate.now());
        user.setPhoneNumber("12345");
        user.setGender(User.Gender.MALE);

            userRepository.save(user);

        return oidcUser;
    }
}
