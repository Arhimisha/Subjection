package com.arhimisha.intech.domain;

import javax.persistence.*;

@Entity
@Table(name = "authority")
public class Authority {
    @Id
    @GeneratedValue(generator = "AUTHORITY_GENERATOR", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "AUTHORITY_GENERATOR", allocationSize = 1, sequenceName = "authority_id_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column (name = "authority", nullable = false)
    private String authority;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Authority(Long id, String authority, User user) {
        this.id = id;
        this.authority = authority;
        this.user = user;
    }

    public Authority() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
