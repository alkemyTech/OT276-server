package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.core.model.Role;
import com.alkemy.ong.core.model.User;
import com.alkemy.ong.core.repository.RoleRepository;
import com.alkemy.ong.core.repository.UserRepository;
import com.alkemy.ong.core.usecase.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    public Long createEntity(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        /*Role role = new Role();
         role.setName("ROLE_USER");
         role.setDescription("Usuario normal");
        roleRepository.save(role);*/
        user.setRole(roleRepository.findById(1L).get());
        return userRepository.save(user).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        return (UserDetails) userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("username %s not found".formatted(email)));
    }
}
