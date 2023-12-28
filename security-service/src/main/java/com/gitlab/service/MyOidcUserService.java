package com.gitlab.service;


import com.gitlab.model.Role;
import com.gitlab.model.User;
import com.gitlab.repository.UserRepository;
import com.nimbusds.openid.connect.sdk.claims.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

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

        User existedUser = userRepository.findByEmail(oidcUser.getEmail());

        //TODO: Сделать более "пользовательский" ввод данных.
        if (Objects.isNull(existedUser)) {
            User user = new User();
            System.out.println(oidcUser);
            user.setPassword("1111");
            user.setEmail(oidcUser.getEmail());
            user.setSecurityQuestion("que");
            user.setAnswerQuestion("ans");
            user.setFirstName(oidcUser.getGivenName());
            if (oidcUser.getFamilyName() == null) {
                user.setLastName("No Last Name");
            } else {
                user.setLastName(oidcUser.getFamilyName());
            }
            user.setBirthDate(LocalDate.now());
            user.setGender(Gender.MALE.getValue());
            user.setPhoneNumber("132131321");
            user.setCreateDate(LocalDate.now());
            userRepository.save(user);

        }
        return oidcUser;
    }
}
