package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.config.exception.NotFoundException;
import com.alkemy.ong.core.model.User;
import com.alkemy.ong.core.repository.UserRepository;
import com.alkemy.ong.core.usecase.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> getList() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void updateEntityIfExists(Long id, User user) {
        userRepository.findById(id)
                .map(userJpa -> {
                    Optional.ofNullable(user.getFirstName()).ifPresent(userJpa::setFirstName);
                    Optional.ofNullable(user.getLastName()).ifPresent(userJpa::setLastName);

                    if (user.getPassword() != null) {
                        user.setPassword(passwordEncoder.encode(user.getPassword()));
                    }
                    Optional.ofNullable(user.getPassword()).ifPresent(userJpa::setPassword);

                    Optional.ofNullable(user.getPhoto()).ifPresent(userJpa::setPhoto);
                    return userRepository.save(userJpa);

                }).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("username %s not found".formatted(email)));
    }

}
