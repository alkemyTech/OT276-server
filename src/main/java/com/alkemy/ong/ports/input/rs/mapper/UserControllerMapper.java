package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.core.model.User;
import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import org.mapstruct.Mapper;

@Mapper
public interface UserControllerMapper {

    User createUserRequestToUser(CreateUserRequest userRequest);
}
