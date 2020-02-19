package com.arhimisha.subjection.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringJoiner;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "USER_GENERATOR", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "USER_GENERATOR", allocationSize = 1, sequenceName = "user_id_seq")
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "account_non_expired")
    private boolean accountNonExpired;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private Collection<Authority> authorities;

    public User() {
    }

    public User(long id,
                String email,
                String username,
                String password,
                String firstName,
                String lastName,
                boolean enabled,
                boolean accountNonExpired,
                boolean accountNonLocked,
                boolean credentialsNonExpired,
                Collection<Authority> authorities) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.authorities = new ArrayList<Authority>();
        this.authorities.addAll(authorities);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<Authority> authorityCollection = new ArrayList<Authority>();
        authorityCollection.addAll(this.authorities);
        return authorityCollection;

    }

    public void setAuthorities(Collection<Authority> authorities) {
        this.authorities = new ArrayList<Authority>();
        this.authorities.addAll(authorities);
    }

    public String getFullName() {
        final StringJoiner joiner = new StringJoiner(" ");
        if (this.firstName != null) {
            joiner.add(firstName);
        }
        if (this.lastName != null) {
            joiner.add(this.lastName);
        }
        return joiner.length() > 0 ? joiner.toString() : "User_without_name";
    }

    public boolean isAdmin(){
        if(this.authorities != null){
            return this.authorities.stream().anyMatch(a-> "ROLE_ADMIN".equals(a.getAuthority()));
        }
        return false;
    }
}
