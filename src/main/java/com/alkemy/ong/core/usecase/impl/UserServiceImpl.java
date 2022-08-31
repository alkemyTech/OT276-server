package com.alkemy.ong.core.usecase.impl;


import com.alkemy.ong.config.exception.ConflictException;
import com.alkemy.ong.core.model.User;
import com.alkemy.ong.core.repository.RoleRepository;
import com.alkemy.ong.core.repository.UserRepository;
import com.alkemy.ong.core.usecase.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final PasswordEncoder passwordEncoder;


    private final UserRepository userRepository;


    private final RoleRepository roleRepository;


    @Override
    @Transactional
    public Long createEntity(User user) {
        if (exist(user.getEmail())) {
            throw new ConflictException("There is already an account with that email address: " + user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleRepository.findById(1L).get());
        return userRepository.save(user).getId();
    }

    @Override
    public Boolean exist(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("username %s not found".formatted(email)));
    }
}
