package com.arhimisha.intech.dto;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "authority")
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(generator = "AUTHORITY_GENERATOR", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "AUTHORITY_GENERATOR", allocationSize = 1, sequenceName = "authority_id_seq")
    @Column(name = "id", nullable = false)
    private long id;

    @Column (name = "authority", nullable = false)
    private String authority;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Authority(long id, String authority, User user) {
        this.id = id;
        this.authority = authority;
        this.user = user;
    }

    public Authority() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
