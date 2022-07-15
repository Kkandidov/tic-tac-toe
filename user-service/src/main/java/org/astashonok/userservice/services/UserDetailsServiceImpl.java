package org.astashonok.userservice.services;

import lombok.RequiredArgsConstructor;
import org.astashonok.userservice.repository.UserRepository;
import org.astashonok.userservice.models.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service(UserDetailsServiceImpl.NAME)
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public static final String NAME = "userDetailsServiceImpl";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(SecurityUser::fromUser)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
    }
}
