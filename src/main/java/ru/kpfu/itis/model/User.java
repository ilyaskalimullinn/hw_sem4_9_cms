package ru.kpfu.itis.model;

import jakarta.validation.constraints.Size;
import org.hibernate.annotations.DynamicUpdate;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) /* http://www.objectdb.com/java/jpa/entity/generated */
    private Integer id;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String fullName;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @NotBlank
    @Transient
    private String passwordRepeat;

    @ManyToMany(fetch = FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "users_userauthority",
            joinColumns = @JoinColumn(name = "users", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "userauthority", referencedColumnName = "id")
    )
    private Set<UserAuthority> authorities = new HashSet<>();

    @NotBlank
    @Column(nullable = false, unique = true)
    private String username;

    public User() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Set<UserAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<UserAuthority> authorities) {
        this.authorities = authorities;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }
}
