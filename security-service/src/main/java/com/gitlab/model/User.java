package com.gitlab.model;


import com.nimbusds.openid.connect.sdk.claims.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", schema = "public", catalog = "postgres")
@NamedEntityGraph(name = "userWithSets",
        attributeNodes = {@NamedAttributeNode("rolesSet")})
public class User implements UserDetails, OidcUser {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "security_question")
    private String securityQuestion;

    @Column(name = "answer_question")
    private String answerQuestion;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "security_users_roles",
            joinColumns = @JoinColumn(name = "security_user_id"),
            inverseJoinColumns = @JoinColumn(name = "security_role_id"))
    private Set<Role> rolesSet;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rolesSet;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    @Transient
    public Map<String, Object> getClaims() {
        return null;
    }

    @Override
    @Transient
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    @Transient
    public OidcIdToken getIdToken() {
        return null;
    }

    @Override
    @Transient
    public String getName() {
        return null;
    }

    public String getGender() {
        return gender.toString();
    }
}
