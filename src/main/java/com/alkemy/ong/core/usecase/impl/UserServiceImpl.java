package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.core.model.User;
import com.alkemy.ong.core.repository.UserRepository;
import com.alkemy.ong.core.usecase.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;


    private final UserRepository userRepository;


    @Override
    @Transactional
    public Long createEntity(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user).getId();
    }
}
