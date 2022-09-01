package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    Long createEntity(User user);

    Boolean exist(String email);

    List<User> getList();

}
