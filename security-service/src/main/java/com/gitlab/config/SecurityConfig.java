package com.gitlab.config;


import com.gitlab.service.MyOidcUserService;
import com.gitlab.service.UserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@AllArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userService;
    private final MyOidcUserService myOidcUserService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(getPasswordEncoder());

        http.csrf(AbstractHttpConfigurer::disable);

        // Set authorization
        http.authorizeHttpRequests(auth -> {
                    try {
                        auth

                                .antMatchers("/login", "/logout", "/auth/register", "/auth/token", "/auth/validate").permitAll()
                                .anyRequest().authenticated()
                                .and()
                                .formLogin()
                                .defaultSuccessUrl("http://localhost:8080")
                                .and()
                                .oauth2Login()
                                .userInfoEndpoint()
                                .oidcUserService(myOidcUserService)
                                .and()
                                .defaultSuccessUrl("http://localhost:8080")
                                .and()
                                .logout()
                                .deleteCookies("JSESSIONID")
                                .logoutSuccessUrl("/login")
                                .permitAll();



                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        return http.build();
    }

/*FIXME: Ввод паролей происходит через liquibase в GazonMain, они не шифруются, соответственно, с ними нельзя
  работать через модуль секьюрности, потому шифрование отключено. Необходимо поправить это после добавления регистрации*/
    public class FakePasswordEncoder implements PasswordEncoder {
        @Override
        public String encode(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override
        public boolean matches(CharSequence charSequence, String s) {
            return charSequence.toString().equals(s);
        }
    }
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new FakePasswordEncoder();
    }

}


