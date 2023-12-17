package com.bci.auth.models.user;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class User {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    private java.util.UUID userId;
    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "email", length = 150, nullable = false, unique = true, updatable = false)
    private String email;
    @Column(name = "password", length = 100, nullable = false)
    private String password;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserPhone> phones;
    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;
    @Column(name = "lastLogin")
    private LocalDateTime lastLogin;
    @Column(name = "isActive", nullable = false)
    private boolean isActive;

    public User() {
    }

    public User(String name, String email, String password, List<UserPhone> phones,
                LocalDateTime created, LocalDateTime lastLogin, boolean isActive) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.phones = phones;
        this.created = created;
        this.lastLogin = lastLogin;
        this.isActive = isActive;
    }

    public UUID getId() {
        return userId;
    }

    public void setId(UUID id) {
        this.userId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserPhone> getPhones() {
        return phones;
    }

    public void setPhones(List<UserPhone> phones) {
        this.phones = phones;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
