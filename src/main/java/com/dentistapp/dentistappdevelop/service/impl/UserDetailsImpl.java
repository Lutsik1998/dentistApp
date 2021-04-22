package com.dentistapp.dentistappdevelop.service.impl;


import com.dentistapp.dentistappdevelop.model.LoginUser;
import com.dentistapp.dentistappdevelop.model.Patient;
import com.dentistapp.dentistappdevelop.model.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private List<String> id;

    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String id, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = new ArrayList<>();
        this.id.add(id);
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(LoginUser loginUser) {
//        Set<Roles> test = new HashSet<> ();
//        test.add(patient.getRoles().n);
//        System.out.println("-------------------------------------");
////        List<GrantedAuthority> authorities = Collections.emptyList();
////        authorities.add(new SimpleGrantedAuthority(APIRole.ROLE_USER.name()));
//        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//        authorities.add(new SimpleGrantedAuthority(patient.getRoles().name()));
//        List<GrantedAuthority> authorities = loginUser.getRoles().stream()
//                .map(role -> new SimpleGrantedAuthority(role.name()))
//                .collect(Collectors.toList());
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Roles role: loginUser.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.name()));
//            role.getPrivileges().stream()
//                    .map(p -> new SimpleGrantedAuthority(p.getName()))
//                    .forEach(authorities::add);
        }
        return new UserDetailsImpl(
                loginUser.getId(),
                loginUser.getEmail(),
                loginUser.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    public List<String> getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
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
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}

