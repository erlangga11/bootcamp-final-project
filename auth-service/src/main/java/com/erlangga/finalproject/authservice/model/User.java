package com.erlangga.finalproject.authservice.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Data
@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String role="user";
    @Column(unique = true)
    private String email;
    @Column(columnDefinition = "boolean default true")
    private boolean is_registered=true;
    @Column
    @UpdateTimestamp
    private Date updatedAt;
    @Column
    @CreationTimestamp
    private Date createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.is_registered;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.is_registered;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.is_registered;
    }

    @Override
    public boolean isEnabled() {
        return this.is_registered;
    }
}
