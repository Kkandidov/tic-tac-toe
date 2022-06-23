package org.astashonok.userservice.security;

import lombok.Builder;
import lombok.Data;
import org.astashonok.userservice.entities.User;
import org.astashonok.userservice.models.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Data
@Builder
public class SecurityUser implements UserDetails {

    private final String userName;
    private final String password;
    private final Set<SimpleGrantedAuthority> authorities;
    boolean enabled;
    boolean accountNonExpired;
    boolean credentialsNonExpired;
    boolean accountNonLocked;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public static UserDetails fromUser(User user) {
        return SecurityUser.builder()
                .userName(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole().getAuthorities())
                .enabled(user.getStatus().equals(Status.ACTIVE))
                .accountNonExpired(user.getStatus().equals(Status.ACTIVE))
                .credentialsNonExpired(user.getStatus().equals(Status.ACTIVE))
                .accountNonLocked(user.getStatus().equals(Status.ACTIVE))
                .build();
    }
}
