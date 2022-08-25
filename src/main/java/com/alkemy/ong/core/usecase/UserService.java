package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserService {


    Long createEntity(User user);
}
