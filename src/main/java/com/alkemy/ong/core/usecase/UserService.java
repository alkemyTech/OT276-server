package com.alkemy.ong.core.usecase;


import com.alkemy.ong.core.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {


    Long createEntity(User user);


}

