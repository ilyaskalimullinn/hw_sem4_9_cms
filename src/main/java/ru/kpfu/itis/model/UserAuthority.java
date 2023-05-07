package ru.kpfu.itis.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class UserAuthority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Size(max = 30)
    @Column(length = 30, nullable = false, unique = true)
    private String authority;

    public UserAuthority() {}

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
